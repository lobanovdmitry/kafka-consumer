package ru.dlobanov.kafka

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.consumer.{Consumer, ConsumerRecord, KafkaConsumer}
import org.apache.kafka.common.errors.WakeupException

import scala.jdk.CollectionConverters._

trait ConsumerHelper[K, V] extends LazyLogging {

  def start(props: Map[String, AnyRef], topics: Seq[String])(f: Iterable[ConsumerRecord[K, V]] => Unit): Unit = {
    withConsumer(props, topics) { consumer =>
      while (true) {
        val s = consumer.poll(100000).asScala
        f(s)
        consumer.commitAsync(OffsetLoggingCallback)
      }
    }
  }

  def createKafkaConsumer(props: Map[String, AnyRef], topics: Seq[String]): KafkaConsumer[K, V] = {
    val consumer = new KafkaConsumer[K, V](props.asJava)
    consumer.subscribe(topics.asJava)
    consumer
  }

  def withConsumer(props: Map[String, AnyRef], topics: Seq[String])(f: Consumer[K, V] => Unit): Unit = {
    val consumer = createKafkaConsumer(props, topics)
    registerShutdownHook(Thread.currentThread(), consumer)
    try {
      f(consumer)
    } catch {
      case _: WakeupException => logger.info("Consumer is being gracefully shut down.")
    } finally {
      try {
        consumer.commitSync()
      } catch {
        case e: Throwable => logger.error("Commit failed", e)
      } finally {
        consumer.close()
      }
    }
  }

  private def registerShutdownHook(mainThread: Thread, consumer: Consumer[K, V]) = {
    sys.addShutdownHook {
      consumer.wakeup()
      try {
        mainThread.join()
      } catch {
        case e: Throwable => logger.error("Error when shutting down", e)
      }
    }
  }
}

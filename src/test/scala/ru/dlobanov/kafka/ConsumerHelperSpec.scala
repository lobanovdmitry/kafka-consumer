package ru.dlobanov.kafka

import org.apache.kafka.clients.consumer.{ConsumerRecord, MockConsumer, OffsetResetStrategy}
import org.apache.kafka.common.TopicPartition
import org.scalatest.flatspec.AnyFlatSpec

import scala.jdk.CollectionConverters._

class ConsumerHelperSpec extends AnyFlatSpec {

  private def prepareKafkaConsumer() = {
    val consumer = new MockConsumer[String, String](OffsetResetStrategy.EARLIEST)
    consumer.assign(List(new TopicPartition("SourceTopic", 0)).asJava)
    val beginningOffsets = Map(new TopicPartition("SourceTopic", 0) -> java.lang.Long.valueOf(0))
    consumer.updateBeginningOffsets(beginningOffsets.asJava)
    consumer
  }

  "it" should "work" in {
    val consumer = prepareKafkaConsumer()
    consumer.addRecord(new ConsumerRecord[String, String]("topic", 0, 0, "k", "v"))
  }

}

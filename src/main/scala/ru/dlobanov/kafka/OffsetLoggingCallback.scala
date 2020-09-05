package ru.dlobanov.kafka

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.consumer.{OffsetAndMetadata, OffsetCommitCallback}
import org.apache.kafka.common.TopicPartition

object OffsetLoggingCallback extends OffsetCommitCallback with LazyLogging {

  override def onComplete(offsets: java.util.Map[TopicPartition, OffsetAndMetadata], ex: Exception): Unit = {
    if (ex != null) {
      logger.error(s"Error when committing offset: $offsets", ex)
    }
  }

}
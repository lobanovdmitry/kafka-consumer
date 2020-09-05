package ru.dlobanov.kafka

import org.apache.kafka.common.serialization.StringDeserializer

object Main extends ConsumerHelper[String, String] {

  def main(args: Array[String]): Unit = {

    val props = Map(
      "bootstrap.servers" -> "localhost:9092",
      "group.id" -> "test2",
      "auto.offset.reset" -> "earliest",
      "key.deserializer" -> classOf[StringDeserializer].getName,
      "value.deserializer" -> classOf[StringDeserializer].getName,
      "enable.auto.commit" -> "false")

    start(props, List("sourceTopic")) { records =>
      records.foreach(r => println(r.value()))
    }

  }
}

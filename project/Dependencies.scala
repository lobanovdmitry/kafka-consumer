import sbt._

object Dependencies {

  lazy val kafkaDependencies = Seq(
    "org.apache.kafka" % "kafka-clients" % "0.11.0.2" excludeAll "org.slf4j",
    "io.confluent" % "kafka-avro-serializer" % "3.3.1" intransitive(),
    "io.confluent" % "kafka-schema-registry-client" % "3.3.1" intransitive(),
    "io.confluent" % "common-config" % "3.3.1" intransitive(),
    "io.confluent" % "common-utils" % "3.3.1" intransitive()
  )

  lazy val `scala-logging` = Seq(
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
    "ch.qos.logback" % "logback-classic" % "1.2.3"
  )

  lazy val scalaTest = Seq(
    "org.scalatest" %% "scalatest" % "3.1.1"
  ).map(_ % Test)
}

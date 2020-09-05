import Dependencies._

ThisBuild / scalaVersion     := "2.13.2"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "ru.dlobanov"
ThisBuild / organizationName := "dlobanov"

lazy val root = (project in file("."))
  .settings(
    name := "kafka-consumer",
    libraryDependencies ++= kafkaDependencies ++ `scala-logging` ++ scalaTest
  )

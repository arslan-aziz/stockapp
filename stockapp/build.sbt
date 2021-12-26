import Dependencies._

ThisBuild / scalaVersion := "2.13.7"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.default"
ThisBuild / organizationName := "default"

lazy val root = (project in file("."))
  .settings(
    name := "StockApp"
  )

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.3" % "test",
  "com.softwaremill.sttp.client3" %% "core" % "3.3.18",
  "com.lihaoyi" %% "ujson" % "1.4.3"
)

mainClass in (Compile, run) := Some("default.Main")
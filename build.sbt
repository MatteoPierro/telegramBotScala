
organization := "com.elios85"

version := "0.1.0"

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
  "com.squareup.retrofit" % "retrofit" % "1.9.0",
  "io.reactivex" %% "rxscala" % "0.23.0",
  "com.typesafe" % "config" % "1.3.0",
  "org.scala-lang.modules" %% "scala-async" % "0.9.5",
  "com.typesafe.akka" %% "akka-actor" % "2.3.4",
  "com.typesafe.akka" %% "akka-slf4j" % "2.3.4"
)

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "3.4" % "test",
  "org.specs2" %% "specs2-mock" % "3.4" % "test",
  "org.specs2" %% "specs2-junit" % "3.4" % "test",
  "org.scalatest" %% "scalatest" % "2.2.0" % "test",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.4" % "test",
  "org.mockito" % "mockito-core" % "1.9.5" % "test"
)

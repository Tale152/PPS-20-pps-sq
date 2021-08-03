import sbt._

object Dependencies {
  // Versions
  lazy val scalaTestVersion = "3.0.5"
  lazy val akkaVersion = "2.5.32"

  // Libraries
  lazy val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion
  lazy val akkaTyped = "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion
}

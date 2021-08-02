import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"

lazy val root = (project in file("."))
  .settings(
    name := "PPS-20-scala-quest",
    libraryDependencies += scalaTest % Test
  )

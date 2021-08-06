import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"

lazy val root = (project in file("."))
  .settings(
      name := "PPS-20-scala-quest",
      libraryDependencies += scalaTest % Test
  )

// System.exit calls are trapped to prevent the JVM from terminating.
// This is useful for executing user code that may call System.exit.
trapExit := false

// Tests Configurations
val numberOfTestProcessors = java.lang.Runtime.getRuntime.availableProcessors + 1
// Run tests in parallel
IntegrationTest / testForkedParallel := true
concurrentRestrictions in Global := Seq(Tags.limitAll(numberOfTestProcessors))

/* Plugins Configurations */

// a publish job is not desired
ThisBuild / githubWorkflowPublishTargetBranches := Seq()

// scoverage plugin keys
coverageEnabled := true
coverageMinimum := 60 //%
coverageFailOnMinimum := true
coverageHighlighting := true

// scoverage file exclusion
lazy val exclusionsRegex = Seq(
  "util", "MasterController", "SubControllersContainer", "StoryView"
).map(s => ".*" + s + "*.").mkString(";")
coverageExcludedPackages := exclusionsRegex

// Add scoverage to the workflow
ThisBuild / githubWorkflowBuildPostamble ++= List(
  WorkflowStep.Sbt(List("coverageReport"), name = Some("Coverage"))
)

// add scalafix settings
addCompilerPlugin(scalafixSemanticdb)

scalacOptions ++= Seq(
  "-Ywarn-unused",
  "-Yrangepos",
  "-Ywarn-adapted-args"
)


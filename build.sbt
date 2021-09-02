import Dependencies._

ThisBuild / scalaVersion := "2.12.8"
ThisBuild / version := "0.3.0-SNAPSHOT"

lazy val root = (project in file("."))
  .settings(
    name := "PPS-20-scala-quest",
    libraryDependencies += scalaTest % Test
  )

// Tests Configurations
val numberOfTestProcessors = java.lang.Runtime.getRuntime.availableProcessors + 1
// Run tests in parallel
concurrentRestrictions in Global := Seq(Tags.limitAll(numberOfTestProcessors))

/* Plugins Configurations */

// a publish job is not desired
ThisBuild / githubWorkflowPublishTargetBranches := Seq()

// scoverage plugin keys
coverageMinimum := 60 //%
coverageFailOnMinimum := true
coverageHighlighting := true

// scoverage file exclusion
coverageExcludedPackages := "view.*;controller.*;Main"

// Add scoverage to the workflow
ThisBuild / githubWorkflowBuild := Seq(WorkflowStep.Sbt(List("clean", "coverage", "test"),
  name = Some("Test (coverage enabled)")))
ThisBuild / githubWorkflowBuildPostamble ++= List(WorkflowStep.Sbt(List("coverageReport"),
  name = Some("Coverage"))
)

// add scalafix settings
addCompilerPlugin(scalafixSemanticdb)

scalacOptions ++= Seq(
  "-Ywarn-unused",
  "-Yrangepos",
  "-Ywarn-adapted-args"
)


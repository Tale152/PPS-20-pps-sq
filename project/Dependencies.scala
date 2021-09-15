import sbt._

object Dependencies {

  // Versions
  lazy val scalaTestVersion = "3.0.5"

  lazy val tuPrologVersion = "3.3.0"

  lazy val graphStreamCoreVersion = "2.0"

  lazy val graphStreamUiSwingVersion = "2.0"

  lazy val uiBoosterVersion = "1.13.1"

  // Libraries
  lazy val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion

  lazy val tuProlog = "it.unibo.alice.tuprolog" % "tuprolog" % tuPrologVersion

  private lazy val graphStream = "org.graphstream"

  lazy val graphStreamCore = graphStream % "gs-core" % graphStreamCoreVersion

  lazy val graphStreamUiSwing = graphStream % "gs-ui-swing" % graphStreamUiSwingVersion

}

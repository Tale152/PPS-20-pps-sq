import sbt._

object Dependencies {

  // Resolvers
  lazy val uiBoosterResolver = "jitpack" at "https://jitpack.io"

  // Versions
  lazy val scalaTestVersion = "3.0.5"

  lazy val graphStreamCoreVersion = "2.0"

  lazy val graphStreamUiSwingVersion = "2.0"

  lazy val uiBoosterVersion = "1.13.1"

  // Libraries
  lazy val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion

  private lazy val graphStream = "org.graphstream"

  lazy val graphStreamCore = graphStream % "gs-core" % graphStreamCoreVersion

  lazy val graphStreamUiSwing = graphStream % "gs-ui-swing" % graphStreamUiSwingVersion

  lazy val uiBooster = "com.github.Milchreis" % "UiBooster" % uiBoosterVersion
}

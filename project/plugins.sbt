// Used to setup GitHub Actions
addSbtPlugin("com.codecommit" % "sbt-github-actions" % "0.13.0")

// Coverage plugin
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")

// Scalafix
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.9.5")

// Used to create executable JAR
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "1.0.0")
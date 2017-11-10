addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.12-rc6")
libraryDependencies += "com.trueaccord.scalapb" %% "compilerplugin" % "0.6.0"

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.21")

addSbtPlugin("com.geirsson" % "sbt-scalafmt" % "1.2.0")

addSbtPlugin("com.47deg" % "sbt-microsites" % "0.7.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-ghpages" % "0.6.2")

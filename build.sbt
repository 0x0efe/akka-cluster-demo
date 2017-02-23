name := "AkkaClusterDemo"
version := "1.0"
mainClass in Compile := Some("me.ekahraman.Main")

scalaVersion := "2.12.1"
val akkaVersion = "2.4.17"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-remote" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion
)

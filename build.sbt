name := "RTP_Reexaminare_Lab1"

version := "0.1"

scalaVersion := "2.13.6"

val akkaVersion = "2.6.15"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.lightbend.akka" %% "akka-stream-alpakka-sse" % "2.0.2",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
"com.typesafe.akka" %% "akka-stream" % akkaVersion
)
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime
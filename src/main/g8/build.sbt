ThisBuild / resolvers ++= Seq(
    "Apache Development Snapshot Repository" at "https://repository.apache.org/content/repositories/snapshots/",
    Resolver.mavenLocal
)

name := "$name$"

version := "$version$"
organization := "$organization$"

ThisBuild / scalaVersion := "$scala_version$"

val flinkVersion = "$flink_version$"
val kdaVersion = "2.0.0"
val kdaRuntimeVersion = "1.2.0"

val flinkDependencies = Seq(
  "com.amazonaws"   % "aws-kinesisanalytics-runtime" % kdaRuntimeVersion,
  "com.amazonaws"   % "aws-kinesisanalytics-flink" % kdaVersion,
  "org.apache.flink" %% "flink-clients" % flinkVersion % "provided",
  "org.apache.flink" %% "flink-scala" % flinkVersion % "provided",
  "org.apache.flink" %% "flink-streaming-scala" % flinkVersion % "provided",
  "org.apache.flink" %% "flink-connector-jdbc" % flinkVersion,
  "org.apache.flink" %% "flink-table-api-scala-bridge" % flinkVersion % "provided",
  "org.apache.flink" %% "flink-table-planner-blink" % flinkVersion % "provided",
  "org.apache.flink" %% "flink-connector-kinesis" % flinkVersion,
  "org.apache.flink" % "flink-core" % flinkVersion,
  "org.apache.flink" % "flink-json" % flinkVersion,
  "org.apache.flink" % "flink-avro" % flinkVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.3"
  )

val testDependencies = Seq(
  "org.scalatest" %% "scalatest" % "3.2.9" % "test",
  "org.scalactic" %% "scalactic" % "3.2.9",
  "org.apache.flink" %% "flink-test-utils" % flinkVersion % "test",
  "org.apache.flink" %% "flink-runtime" % flinkVersion % "test",
  "org.apache.flink" %% "flink-streaming-scala" % flinkVersion % "test"
)

lazy val root = (project in file(".")).
  settings(
    libraryDependencies ++= flinkDependencies,
    libraryDependencies ++= testDependencies
  )

assembly / mainClass := Some("$organization$.Job")

// make run command include the provided dependencies
Compile / run  := Defaults.runTask(Compile / fullClasspath,
                                   Compile / run / mainClass,
                                   Compile / run / runner
                                  ).evaluated

// stays inside the sbt console when we press "ctrl-c" while a Flink programme executes with "run" or "runMain"
Compile / run / fork := true
Global / cancelable := true

// exclude Scala library from assembly
assembly / assemblyOption  := (assembly / assemblyOption).value.copy(includeScala = false)

assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 case PathList("module-info.class") => MergeStrategy.discard
 case x => MergeStrategy.deduplicate
}
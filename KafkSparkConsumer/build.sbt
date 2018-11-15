name := "KafkaSparkConsumer"

version := "0.1"

scalaVersion := "2.11.7"

mainClass in Compile := Some("com.test.KafkaSConsumer")

libraryDependencies ++= Seq("org.apache.spark" %% "spark-streaming" % "2.1.0-mapr-1703",
  "org.apache.spark" %% "spark-streaming-kafka-0-9" % "2.1.0-mapr-1703",
)

assemblyMergeStrategy  in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
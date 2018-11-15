
name := "SparkXMLParser"

version := "0.1"

scalaVersion := "2.12.7"

mainClass in Compile := Some("com.emtec.SparkMapR")

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-sql_2.11" % "2.1.1",
  "com.databricks"%"spark-xml_2.11"%"0.4.1")


assemblyMergeStrategy  in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
package com.test

import com.mapr.db.MapRDB
import com.mapr.db.spark.MapRDBSpark
import com.mapr.db.spark.streaming._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.kafka09.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.ojai.Document

import scala.util.Random

object KafkaSConsumer {

  def main(args: Array[String]): Unit = {
    val sparkContext=new SparkContext(new SparkConf().setAppName("MyKafkaProducer").setMaster("local[2]"))
    val streamingContext = new StreamingContext(sparkContext, Seconds(5))
    val kData=KafkaUtils.createDirectStream(streamingContext, LocationStrategies.PreferConsistent, ConsumerStrategies.Subscribe[String, String](Array(args(0)).toList, Map("group.id" -> "groupId", "enable.auto.commit" -> "false", "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer", "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer", "auto.offset.reset" -> "earliest"))).map(_.value())
      kData.print()
    streamingContext.start()
    streamingContext.awaitTermination()
  }

}

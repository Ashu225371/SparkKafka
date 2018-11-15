package com.dmo.spark

import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

object Processxml {
  def main(args: Array[String]): Unit = {
    val Conf = new SparkConf()
      .setAppName("Processxml")
      .setMaster("local")
    val sc = new SparkContext(Conf)
    val sqlContext = SQLContext(sc)
    loadBookData(sqlContext) //load book.xml
    loadUblData(sqlContext) //load ubl data
  }


  def loadBookData(sqlContext: SQLContext) = {
    var df: DataFrame = null
    var newDF: DataFrame = null
    df = sqlContext.read
      .format("xml")
      .option("row Tag", "book")
      .load("datafiles/books.xml")
    df.printSchema()
    df.registerTempTable("books")
    sqlContext.sql("select * from books where upper(description) LIKE '%MICROSOFT%'").show()
    convertToDateDF(df).printSchema() //to have its own schema
  }


  def convertToDateDF(df: DataFrame) = {
    import org.apache.spark.sql.functions._

    val toDate = udf(
      (entry: String) => { //lambda func
        var retVal: java.sql.Date = null
        val sdf = new java.text.SimpleDateFormat("yyyy-mm-dd")
        try {
          retVal = new java.sql.Date(sdf.parse(entry).getTime)

        }
        catch {
          case e: Exception => {
            retVal = null
          }
        }
        retVal
      })

    df.withColumn("pub_date", toDate(df.col("publish_date")))
      .select("author", "description", "genre", "id", "price", "pub_date", "title")

  }

  def loadUblData(sqlContext: SQLContext) = {
    val df = sqlContext.read
      .format("xml")
      .option("row Tag", "order")
      .load("datafiles'/UBL-Order-2.1-Example.xml'")
    df.printSchema()
    df.select(df.col("cbc:ID")).show()
  }
}
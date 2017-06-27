package com.jsl.nlp.annotators

import com.jsl.nlp.{Annotation, AnnotatorBuilder, Document}
import org.apache.spark.sql.{Dataset, Row}
import org.scalatest._

trait EntityExtractorBehaviors { this: FlatSpec =>

  def fullEntityExtractorPipeline(dataset: => Dataset[Row]) {
    "An EntityExtractor Annotator" should "successfully transform data" in {
      println(dataset.schema)
      AnnotatorBuilder.withFullEntityExtractor(dataset)
        .collect().foreach {
        row =>
          val document = Document(row.getAs[Row](0))
          println(document)
          row.getSeq[Row](3)
            .map(Annotation(_))
            .foreach {
              case entity: Annotation if entity.annotatorType == "entity" =>
                println(entity, document.text.substring(entity.begin, entity.end))
              case _ => ()
            }
      }
    }
  }
}

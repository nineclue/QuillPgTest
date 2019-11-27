import scala.collection.JavaConverters._

object Demo {
    import io.getquill._

    def main(as: Array[String]): Unit = {
        import DB._, DB.ctx._
        
        val cs = File2DB("cities.csv").drop(1).map(File2DB.splitedString).map(as =>City(as)).toSeq.flatten
        // DB.ctx.run(liftQuery(cs).foreach(c => query[City].insert(c).returningGenerated(_.id)))
    }
}
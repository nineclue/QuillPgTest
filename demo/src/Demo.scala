import scala.collection.JavaConverters._

object Demo {
    import io.getquill._

    def main(as: Array[String]): Unit = {
        import DB._, DB.ctx._
        println(DB.ctx.run(query[City].size))
        println(s"${DB.ctx.run(query[Country].size)}개의 나라를 찾았습니다")
        DB.ctx.run(query[Country]).foreach(println)
        
        val cs = File2DB("cities.csv").drop(1).map(File2DB.splitedString).map(as =>City(as)).toSeq.flatten
        DB.ctx.run(liftQuery(cs).foreach(c => query[City].insert(c).returningGenerated(_.id)))
    }
}
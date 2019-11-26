object Demo {
    import io.getquill._

    def main(as: Array[String]): Unit = {
        import DB._, DB.ctx._
        println(DB.ctx.run(query[City].size))
        println(s"${DB.ctx.run(query[Country].size)}개의 나라를 찾았습니다")
        DB.ctx.run(query[Country]).foreach(println)
        
        File2DB.make(File2DB("cities.csv"))(City.maker)
    }
}
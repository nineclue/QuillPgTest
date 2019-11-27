case class City(id: Int, name: String, countryId: Int, continentId: Int)

object City {
    import DB._, DB.ctx._

    def apply(ss: Seq[String]): Option[City] = {
        // println(s"processing : $ss")
        val (cityName, countryName, continentName) = (ss(0), ss(1), ss(2))
        val countryId = DB.ctx.run(query[Country].filter(_.name == lift(countryName)).map(_.id)) match {
            case Nil => 
                DB.ctx.run(query[Country].insert(lift(Country(0, countryName))).returningGenerated(_.id))
            case id::_ => 
                id
        }
        val continentId = DB.ctx.run(query[Continent].filter(_.name == lift(continentName)).map(_.id)) match {
            case Nil => 
                DB.ctx.run(query[Continent].insert(lift(Continent(0, continentName))).returningGenerated(_.id))
            case id::_ => 
                id
        }
        Some(City(0, cityName, countryId, continentId))
    }
}
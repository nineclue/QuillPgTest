case class City(id: Integer, name: String, countryId: Integer, continentId: Integer)

object City {
    def maker(ss: Seq[String]): Option[City] = {
        val (cityName, countryName, continentName) = (ss(0), ss(1), ss(2))
        None                
    }
}
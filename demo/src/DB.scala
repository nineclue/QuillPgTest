import io.getquill._

object DB {
    lazy val ctx = new PostgresJdbcContext(SnakeCase, "ctx")

    import ctx._
    implicit val citySchema = schemaMeta[City]("cities", _.id -> "city_id", _.name -> "city_name")
    implicit val countrySchema = schemaMeta[Country]("countries", _.id -> "country_id", _.name -> "country_name")
    implicit val continentSchema = schemaMeta[Continent]("continents", _.id -> "continent_id", _.name -> "continent_name")

    // def searchByName(q: Query[_], n: String) = q.filter(_.name == n).map(_.id)
}

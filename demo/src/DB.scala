import io.getquill._

object DB {
    lazy val ctx = new PostgresJdbcContext(SnakeCase, "ctx")

    import ctx._
    implicit val citySchema = schemaMeta[City]("cities")
    implicit val countrySchema = schemaMeta[Country]("countries")
    implicit val continentSchema = schemaMeta[Continent]("continents")
    def getId() = ???
}
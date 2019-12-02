import mill._, scalalib._

object demo extends ScalaModule {
    def scalaVersion = "2.13.1"
    def ivyDeps = Agg(
        ivy"io.getquill::quill-jdbc:3.4.10",
        ivy"org.postgresql:postgresql:42.2.8"
    )
}
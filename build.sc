import mill._, scalalib._

object demo extends ScalaModule {
    def scalaVersion = "2.12.10"
    def ivyDeps = Agg(
        ivy"io.getquill::quill-jdbc:3.4.10",
        ivy"org.postgresql:postgresql:42.2.8"
    )
}
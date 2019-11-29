trait DBFlexible {
    import io.getquill.context.Context
    import DB.ctx._

    val id: Int
    val name: String
    def selectOrInsert[A <: DBFlexible](q: Quoted[Query[A]], name: String)(implicit en: Encoder[A], maker: String => A): Int = {
        DB.ctx.run(q.filter(_.name == lift(name)).map(_.id)) match {
            case Nil => 
                DB.ctx.run(q.insert(lift(maker(name))).returningGenerated(_.id))
            case iid::_ => 
                iid
        }
    }
}
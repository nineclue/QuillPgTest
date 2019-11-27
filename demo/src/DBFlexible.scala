trait DBFlexible {
    import io.getquill.context.Context
    import DB.ctx._

    val id: Int
    val name: String
    def getIdOrCreate[A <: DBFlexible](ctx: Context[_, _], q: Query[A], maker: String => A)(name: String): Int = {
        val nameQuery = quote { q.filter(_.name == lift(name)) }
        ctx.run(nameQuery) match {
            case Nil => 
                ctx.run(q.insert(lift(maker(name))).returningGenerated(_.id))
            case iid::_ => 
                iid
        }
    }
}
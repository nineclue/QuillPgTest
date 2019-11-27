trait DBFlexible {
    import io.getquill.context.Context
    import DB.ctx._

    val id: Int
    val name: String
    def selectOrInsert[A <: DBFlexible](ctx: Context[_, _], q: Query[A], maker: String => A)(name: String): Int = {
        val nameQuery = q.filter(_.name == lift(name)).map(_.id) 
        ctx.run(nameQuery) match {
            case Nil => 
                ctx.run(q.insert(lift(maker(name))).returningGenerated(_.id))
            case iid::_ => 
                iid
        }
    }
}
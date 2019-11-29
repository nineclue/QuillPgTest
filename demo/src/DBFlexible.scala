trait DBFlexible {
    import io.getquill.context.Context
    import DB.ctx._

    val id: Int
    val name: String
    def selectOrInsert[A <: DBFlexible](tname: String, name: String)(implicit maker: String => A, encoder: Encoder[A]): Int = {
        val dySchema = DB.ctx.dynamicQuerySchema[A](tname)
        DB.ctx.run(dySchema.filter(_.name == lift(name)).map(_.id)) match {
            case Nil => 
                DB.ctx.run(dySchema.insertValue(quote(lift(maker(name)))).returningGenerated(_.id))
            case iid::_ => 
                iid
        }
    }
    /*
    val dynamicSchema = context.dynamicQuerySchema[MyDBClass](tableNameVar)

    context.transaction {
        myCollection.foreach { p =>
            context.run(dynamicSchema.insertValue(p))
        }
    }
    */
}
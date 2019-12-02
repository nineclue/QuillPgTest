import io.getquill.context.{Context => dbContext}
import scala.reflect.macros.whitebox.{Context => mContext}
// import scala.reflect.macros.{Context => mContext}
import scala.language.experimental.macros
import scala.reflect.runtime.{universe => ru}

trait DBFlexible {
    val id: Int
    val name: String
    def selectOrInsert[A <: DBFlexible](name: String, maker: String => A): Int = macro DBFlexible.selectOrInsertImpl[A]
    /*
    val dynamicSchema = context.dynamicQuerySchema[MyDBClass](tableNameVar)

    context.transaction {
        myCollection.foreach { p =>
            context.run(dynamicSchema.insertValue(p))
        }
    }
    */
}

object DBFlexible {
    def selectOrInsertImpl[A: c.WeakTypeTag](c: mContext)(name: c.Tree, maker: c.Tree): c.Tree = {
        import c.universe._

        q"""
        import DB.ctx._
        DB.ctx.run(dySchema.filter(_.name == lift(name)).map(_.id)) match {
            case Nil => 
                DB.ctx.run(dySchema.insertValue(quote(lift(maker(name)))).returningGenerated(_.id))
            case iid::_ => 
                iid
        }
        """
    }
}
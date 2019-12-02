import io.getquill.context.{Context => dbContext}
import scala.reflect.macros.whitebox.{Context => mContext}
import scala.language.experimental.macros
import scala.reflect.runtime.{universe => ru}

trait HasIdName {
    val id: Int
    val name: String
}

trait DBFlexible {
    def selectOrInsert[A <: HasIdName](name: String, maker: String => A): Int = macro DBFlexible.selectOrInsertImpl[A]
}

object DBFlexible {
    def selectOrInsertImpl[A: c.WeakTypeTag](c: mContext)(name: c.Tree, maker: c.Tree): c.Tree = {
        import c.universe._

        q"""
        import DB.ctx._
        DB.ctx.run(query[A].filter(_.name == lift(name)).map(_.id)) match {
            case Nil => 
                DB.ctx.run(query[A].insertValue(quote(lift(maker(name)))).returningGenerated(_.id))
            case iid::_ => 
                iid
        }
        """
    }
}
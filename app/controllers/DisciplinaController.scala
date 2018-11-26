package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.db.Database
import scala.collection.mutable.MutableList
import models.Disciplina

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class DisciplinaController @Inject()(db: Database, cc: ControllerComponents) extends AbstractController(cc) {

  def lista = Action {
 
    val list = MutableList[Disciplina]()
    //conn representa a conexao de fato com o bd
    db.withConnection { conn =>
      val stm = conn.createStatement()
      val res = stm.executeQuery("""
      select 
         * 
      from 
         disciplina 
      order by 
          disciplina.nome 
      limit 10""")
      while (res.next()) {
        list.+=(Disciplina(res.getInt(1)
               ,res.getString(2)
               ,res.getString(3)
               ,res.getInt(4)))
      }
    }
 
    Ok(views.html.lista(list))
  }
  
  
}

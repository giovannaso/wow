package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.db.Database
import scala.collection.mutable.MutableList
import models.Filme

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class FilmeController @Inject()(db: Database, cc: ControllerComponents) extends AbstractController(cc) {

  def lista = Action {
 
    val list = MutableList[Filme]()
    //conn representa a conexao de fato com o bd
    db.withConnection { conn =>
      val stm = conn.createStatement()
      val res = stm.executeQuery("""
      select 
         * 
      from 
         filmes 
      order by 
          filmes.nome 
      limit 10""")
      while (res.next()) {
        list.+=(Filme(res.getString(2)
               ,res.getInt(3)
               ,res.getString(4)
               ,res.getString(5)
               ,res.getString(6)))
      }
    }
 
    Ok(views.html.filme(list))
  }
}

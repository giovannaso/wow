package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.db.Database
import scala.collection.mutable.MutableList
import models.FilmeDAO
import models.Filme
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class FilmeinController @Inject()(db: Database, cc: ControllerComponents) 
  extends AbstractController(cc) with play.api.i18n.I18nSupport {
  
  val form: Form[Filme] = Form (
        mapping(
            //"id" -> number,
            "nome" -> text,
            "duracao" -> number,
            "genero" -> text,
            "clas" -> text,
            "estudio" -> text,
        )(Filme.apply)(Filme.unapply)
    )
  
  def create = Action {implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.filmForm(formWithErrors))
      },
      filme => {
        FilmeDAO.create(db,filme)
        Redirect("/filmes")
      }
    )
  }
  
  def formfilm = Action {implicit request =>
    Ok(views.html.filmForm(form))
  }
  
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
        list.+=(Filme(//res.getInt(1)
              res.getString(2)
               ,res.getInt(3)
               ,res.getString(4)
               ,res.getString(5)
               ,res.getString(6)))
      }
    }
 
    Ok(views.html.filme(list))
  }
  
  
}

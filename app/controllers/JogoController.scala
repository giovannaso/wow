package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.db.Database
import scala.collection.mutable.MutableList
import models.JogoDAO
import models.Jogo
import play.api.data._
import play.api.data.Forms._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class JogoController @Inject()(db: Database, cc: ControllerComponents) 
  extends AbstractController(cc) with play.api.i18n.I18nSupport {
  
  val form: Form[Jogo] = Form (
        mapping(
            "id" -> number,
            "nome" -> text,
            "genero" -> text,
            "estudio" -> text,
            "qualidade" -> number,
            "loja" -> text
        )(Jogo.apply)(Jogo.unapply)
    )
  
  def create = Action {implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.jogoForm(formWithErrors))
      },
      jogo => {
        JogoDAO.create(db,jogo)
        Redirect("/jogo")
      }
    )
  }
  
  
  def delete = Action {implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => {
        println(formWithErrors)
        BadRequest(views.html.jogoDel(formWithErrors))
      },
      jogo => {
        JogoDAO.delete(db,jogo)
        Redirect("/jogo")
      }
    )
  }
  
  def update = Action {implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => {
        println(formWithErrors)
        BadRequest(views.html.jogoupdate(formWithErrors))
      },
      jogo => {
        JogoDAO.update(db,jogo)
        Redirect("/jogo")
      }
    )
  }
  
  def info(id: Int) = Action {
    val jogo = JogoDAO.getJogo(db,id)
    Ok(views.html.info(jogo))
  }
  
  def formjogo = Action {implicit request =>
    Ok(views.html.jogoForm(form))
  }
  
  def formup = Action {implicit request =>
    Ok(views.html.jogoupdate(form))
  }
  
  def fordel = Action {implicit request =>
    Ok(views.html.jogoDel(form))
  }
  
   def lista = Action {
    val list = MutableList[Jogo]()
    //conn representa a conexao de fato com o bd
    db.withConnection { conn =>
      val stm = conn.createStatement()
      val res = stm.executeQuery("""
      select 
         * 
      from 
         jogo 
      order by 
          jogo.nome 
      limit 10""")
      while (res.next()) {
            list.+=(Jogo(res.getInt(1)
                    ,res.getString(2)
                   ,res.getString(3)
                   ,res.getString(4)
                   ,res.getInt(5)
                   ,res.getString(6)))
          }
    }
 
    Ok(views.html.jogo(list))
  }
  
  
}

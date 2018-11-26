package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.db.Database
import scala.collection.mutable.MutableList
import models.UsuarioDAO
import models.Usuario
import play.api.data._
import play.api.data.Forms._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class UsuarioController @Inject()(db: Database, cc: ControllerComponents) 
  extends AbstractController(cc) with play.api.i18n.I18nSupport {
  
  val usuForm: Form[(String, String, String)] = Form (
        mapping(
            "nome" -> text,
            "email" -> text,
            "senha" -> text
    )(Usuario.apply)(Usuario.unapply))
  
  def form = Action {implicit request =>
    Ok(views.html.usuForm(usuForm))
  }
  
  def cadastro = Action {implicit request =>
    usuForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.usuForm(formWithErrors))
      },
      (nome,email,senha) => {
        UsuarioDAO.create(db,Usuario.criarUsuario(nome,email,senha))
        Redirect("/")
      }
    )
  }

  
}

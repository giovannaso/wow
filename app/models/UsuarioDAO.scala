package models;
import scala.collection.mutable.MutableList;
import play.api.db.Database;

object UsuarioDAO{
    
    def create(db: Database, usu: Usuario): Unit = {
        db.withConnection{ conn =>
            val ps = conn.prepareStatement("insert into usuario(nome,email,senha) values (?,?,?)")
            ps.setString(1,usu.nome)
            ps.setString(2,usu.email)
            ps.setString(3,usu.senha)
            ps.execute()
        }
    }
    
}
package models;
import scala.collection.mutable.MutableList;
import play.api.db.Database;

object FilmeDAO{
    
    def create(db: Database, film:Filme): Unit = {
        db.withConnection{ conn =>
            val ps = conn.prepareStatement("insert into filmes(nome,duracao,genero,clas,estudio) values (?,?,?,?,?)")
            ps.setString(1,film.nome)
            ps.setInt(2,film.duracao)
            ps.setString(3,film.genero)
            ps.setString(4,film.clas)
            ps.setString(5,film.estudio)
            ps.execute()
        }
    }
    
    def listagem(db: Database): MutableList[Filme] = {
    
        val list = MutableList[Filme]()
    
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
        list
    }
}
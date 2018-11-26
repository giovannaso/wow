package models;
import scala.collection.mutable.MutableList;
import play.api.db.Database;

object JogoDAO{
    
    def getJogo(db: Database, id: Int): Jogo = {
        db.withConnection{conn =>
            val ps = conn.prepareStatement("select * from jogo where id=?")
            ps.setInt(1,id)
            val res = ps.executeQuery()
            if(res.next())
                Jogo(res.getInt(1),res.getString(2),res.getString(3),res.getString(4),res.getInt(5),res.getString(6))
            else
                Jogo(0,"","","",0,"")
        }
    }
    
    def create(db: Database, jog: Jogo): Unit = {
        db.withConnection{ conn =>
            val ps = conn.prepareStatement("insert into jogo(nome,genero,estudio,qualidade,loja) values (?,?,?,?,?)")
            ps.setString(1,jog.nome)
            ps.setString(2,jog.genero)
            ps.setString(3,jog.estudio)
            ps.setInt(4,jog.qualidade)
            ps.setString(5,jog.loja)
            ps.execute()
        }
    }
    
    def delete(db: Database, jog: Jogo): Unit = {
     db.withConnection{ conn =>
            val ps = conn.prepareStatement("delete from jogo where id= ?")
            ps.setInt(1,jog.id)
            ps.execute()
        }
}


def update(db: Database, jog: Jogo): Unit = {
        db.withConnection{ conn =>
            val ps = conn.prepareStatement("update jogo set nome= ?, genero= ?, estudio= ?, qualidade= ?, loja= ? where id= ?")
             ps.setString(1,jog.nome)
            ps.setString(2,jog.genero)
            ps.setString(3,jog.estudio)
            ps.setInt(4,jog.qualidade)
            ps.setString(5,jog.loja)
            ps.setInt(6,jog.id)
            ps.execute()
        }
    }

    
    def listagem(db: Database): MutableList[Jogo] = {
    
        val list = MutableList[Jogo]()
    
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
        list
    }
}
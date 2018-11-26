package models

class Usuario(val _id: Int, val _nome: String, val _email: String, val _senha: String){
    def id = _id
    
    def nome = _nome
    
    def email = _email
    
    def senha = _senha
}

object Usuario{
    def criarUsuarioId(id: Int, nome: String, email: String, senha: String) = {
        new Usuario(id,nome,email,senha)
    }
    
    def criarUsuario(nome: String, email: String, senha: String): Usuario = {
        return new Usuario(0,nome,email,senha)
    }
    
    def apply(nome: String, email: String, senha: String): (String,String,String) = {
        return (nome,email,senha)
    }
    //ESPERA UMA VALIDACAO
    def unapply(usu: (String,String,String)): Option[(String,String,String)] = {
        return Some(usu._1,usu._2,usu._3)
    }
}
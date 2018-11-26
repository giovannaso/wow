// PARA CADA TABELA CRIADA DEVE-SE CRIAR UM ARQUIVO EM MODELS. 
//CASO SEJA SEM REGRA DE NEGÓCIOS, CRIAMOS COMO "CASE CLASS". Caso tenha, cria - se uma classe.

package models
 
case class Disciplina(id:Int, nome: String, codigo: String, qtHoras: Int)
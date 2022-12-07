package modelo.clases

/**Data class que nos permite la comunicación entre nuestra app y nuestra BD*/
data class Pelicula(val id: Long, val nombre:String, val comentario:String, val anio:Int, val director:String)
package modelo

import modelo.clases.Pelicula
import modelo.sentencias.Sentencias
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement
import java.util.logging.Logger



class GestorBDD private constructor() {

    //Datos de conexión
    private val url: String = "jdbc:h2:mem:peliculas"
    private val user: String = "admin"
    private val password: String = "admin"

    //BD
    private var conn: Connection? = null


    companion object {
        @Volatile
        private var instance: GestorBDD? = null

        fun getInstance(): GestorBDD {
            if (instance == null) instance = GestorBDD()
            return instance!!
        }
    }

    fun connect() {
        if (conn == null) {
            conn = DriverManager.getConnection(url, user, password)
            println("[Conexión realizada correctamente]")
        } else {
            println("[La conexión ya existe]")
        }
    }

    fun disconnect() {
        if (!conn?.isClosed!!) {
            conn!!.close()
            println("[Desconexión realizada correctamente]")
        } else {
            println("[No existe la conexión]")
        }
    }

    //Funciones de obtención de datos
    fun selectAll(): List<Pelicula> {
        val peliculas = mutableListOf<Pelicula>()

        if (conn != null) {

            val st: Statement = conn!!.createStatement()
            val rs: ResultSet = st.executeQuery(Sentencias.selectAll)

            while (rs.next()) {
                val id = rs.getLong("id_pelicula")
                val nombre = rs.getString("nombre")
                val comentario = rs.getString("comentario")
                val anio = rs.getInt("anio")
                val director = rs.getString("director")
                peliculas.add(Pelicula(id, nombre, comentario, anio, director))
            }

        } else {
            println("No hay conexión")
        }
        return peliculas
    }

    fun selectById(id: Long): Pelicula? {
        var pelicula: Pelicula? = null

        if (conn != null) {

            val ps: PreparedStatement = conn!!.prepareStatement(Sentencias.selectById)
            ps.setLong(1, id)
            val rs: ResultSet = ps.executeQuery()

            while (rs.next()) {
                val nombre = rs.getString("nombre")
                val comentario = rs.getString("comentario")
                val anio = rs.getInt("anio")
                val director = rs.getString("director")

                pelicula = Pelicula(id, nombre, comentario, anio, director)
            }

        } else println("No hay conexión")

        return pelicula
    }

    //Función de insertar pelicula
    fun insertPelicula(pelicula: Pelicula): Boolean {

        if (conn != null && !conn?.isClosed!!) {
            try {
                if (selectById(pelicula.id) == null) {
                    conn!!.autoCommit = false
                    val rows: Int
                    val ps: PreparedStatement = conn!!.prepareStatement(Sentencias.insertPelicula)
                    ps.setLong(1, pelicula.id)
                    ps.setString(2, pelicula.nombre)
                    ps.setString(3, pelicula.comentario)
                    ps.setInt(4, pelicula.anio)
                    ps.setString(5, pelicula.director)
                    rows = ps.executeUpdate()
                    ps.close()

                    if (rows > 0) {
                        conn!!.commit()
                        println("Insertado con éxito")
                        return true
                    } else {
                        conn!!.rollback()
                        println("Error en el insert")
                        return false
                    }

                } else {
                    println("No hay conexión")
                    return false
                }


            } catch (e: Exception) {
                conn!!.rollback()
                e.printStackTrace()
                return false
            }
        }

        return false

    }

    fun updateById(id:Long, pelicula: Pelicula):Boolean{
        if (conn != null && !conn?.isClosed!!) {

            if (selectById(id) != null) {
                try {
                    conn!!.autoCommit = false
                    val rows: Int
                    val ps: PreparedStatement = conn!!.prepareStatement(Sentencias.updateById)
                    ps.setLong(1, pelicula.id)
                    ps.setString(2, pelicula.nombre)
                    ps.setString(3, pelicula.comentario)
                    ps.setInt(4, pelicula.anio)
                    ps.setString(5, pelicula.director)

                    ps.setLong(6, pelicula.id)
                    rows = ps.executeUpdate()
                    ps.close()
                    if (rows > 0) {
                        conn!!.commit()
                        println("Actualizado con éxito")
                        return true
                    } else {
                        conn!!.rollback()
                        println("Error en la actualización")
                        return false
                    }
                } catch (e: Exception){
                    conn!!.rollback()
                    e.printStackTrace()
                    return false
                }
            }
            else{
                println("No existe una película con ese id")
                return false
            }
        }else{
            println("No hay conexión")
            return false
        }
    }

    fun deleteById(id:Long):Boolean{
        if (conn != null && !conn?.isClosed!!) {

            if (selectById(id) != null) {
                try {
                    conn!!.autoCommit = false
                    val rows: Int
                    val ps: PreparedStatement = conn!!.prepareStatement(Sentencias.deleteById)
                    ps.setLong(1, id)
                    rows = ps.executeUpdate()
                    ps.close()
                    if (rows > 0) {
                        conn!!.commit()
                        println("Borrado con éxito")
                        return true
                    } else {
                        conn!!.rollback()
                        println("Error en el borrado")
                        return false
                    }
                } catch (e: Exception){
                    conn!!.rollback()
                    e.printStackTrace()
                    return false
                }
            }
            else{
                println("No existe una película con ese id")
                return false
            }
        }else{
            println("No hay conexión")
            return false
        }
    }


    //Solo para testeo
    fun createTablePeliculas(){
        val ps: PreparedStatement = conn!!.prepareStatement(Sentencias.createTablePeliculas)
        ps.executeUpdate()
        ps.close()
    }

    //
}
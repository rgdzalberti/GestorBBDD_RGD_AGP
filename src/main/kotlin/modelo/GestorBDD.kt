package modelo

import modelo.clases.Pelicula
import modelo.sentencias.Sentencias
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class GestorBDD private constructor() {

    //Datos de conexión
    private val url: String = "jdbc:h2:mem:default"
    private val user: String = "admin"
    private val password: String = "admin"

    //BD
    private var query: String = ""
    private val bd: String = "peliculas"
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
            conn = DriverManager.getConnection(url + bd, user, password)
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
                val titulo = rs.getString("titulo")
                val anio = rs.getInt("anio")
                val director = rs.getString("director")
                val comentario = rs.getString("comentario")
                peliculas.add(Pelicula(id, titulo, anio, director, comentario))
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
                val titulo = rs.getString("titulo")
                val anio = rs.getInt("anio")
                val director = rs.getString("director")
                val comentario = rs.getString("comentario")

                pelicula = Pelicula(id, titulo, anio, director, comentario)
            }

        } else println("No hay conexión")

        return pelicula
    }

    //Función de insertar pelicula
    fun insert(pelicula: Pelicula): Boolean {

        if (conn != null && !conn?.isClosed!!) {
            try {
                if (selectById(pelicula.id) == null) {
                    conn!!.autoCommit = false
                    val rows: Int
                    val ps: PreparedStatement = conn!!.prepareStatement(Sentencias.insert)
                    ps.setLong(1, pelicula.id)
                    ps.setString(2, pelicula.titulo)
                    ps.setInt(3, pelicula.anio)
                    ps.setString(4, pelicula.director)
                    ps.setString(5, pelicula.comentario)
                    rows = ps.executeUpdate()
                    ps.close()
                    if (rows < 1) {
                        conn!!.commit()
                        return true
                    } else {
                        conn!!.rollback()
                        return false
                    }

                } else {
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

    //
}
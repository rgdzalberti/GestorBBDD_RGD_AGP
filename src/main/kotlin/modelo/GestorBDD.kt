package modelo

import modelo.clases.Pelicula
import modelo.sentencias.Sentencias
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement


/**
 * Gestiona una conexión a una base de datos, implementando Singleton para la instanciación de la clase, a la vez que todos los métodos necesarios para la manipulación de datos.
 * */
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


        /**Crea una nueva instancia del gestor de base de datos
         * @return Instancia de [GestorBDD]*/
        fun getInstance(): GestorBDD {
            if (instance == null) instance = GestorBDD()
            return instance!!
        }
    }

    /**Empieza la conexión con la base de datos*/
    fun connect() {
        if (conn == null) {
            conn = DriverManager.getConnection(url, user, password)
            println("[Conexión realizada correctamente]")
        } else {
            println("[La conexión ya existe]")
        }
    }
    /**Termina la conexión con la base de datos*/
    fun disconnect() {
        if (!conn?.isClosed!!) {
            conn!!.close()
            println("[Desconexión realizada correctamente]")
        } else {
            println("[No existe la conexión]")
        }
    }

    //Funciones de obtención de datos

    /**Recupera de la base de datos todas las entradas de la tabla
     * @return [List] de [Pelicula], ordenadas por el orden en el que se encuentran en la base de datos*/
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
    /**Recupera de la base de datos la entrada que coincide con el ID especificado
     * @param id El ID del objeto que se quiere encontrar, en un long
     * @return [Pelicula] cuyo id coincide con el especificado, si no se encuentra, devuelve null*/
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

    /**Permite insertar una pelicula en la BD
     * @param pelicula Objeto que se quiere insertar
     * @return [Boolean], true si se ha completado el insert, false si no*/
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



    //Función de actualizar

    /**Permite actualizar una pelicula en la BD
     * @param id ID que coincida con el del objeto que se quiera actualizar
     * @param pelicula Objeto [Pelicula] con los datos nuevos
     * @return [Boolean], true si se ha completado el insert, false si no*/
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

    //Función de borrado

    /**Permite borrar una entrada de la BD mediante el ID del objeto
     * @param id ID que coincide con el del objeto que se quiere borrar
     * @return [Boolean], que especifica si se pudo completar la operación o no*/
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


    //Solo para testeo con BBDD in-memory.
    /*
    * Buenas Diego. Esta función, como habrás podido leer en la documentación de la función, no debería ser necesario ejecutarla de normal. Sin embargo,
    * nosotros hemos utilizado H2 en memoria, que es una base de datos con host local, que ejecuta y existe durante y hasta el final de la ejecución del programa.
    * Esto supone que cada vez que ejecutemos el MainKt va a dar error al no estar creada esta tabla, así que para facilitarnos el trabajo y tu primera ejecución
    * de la app, hemos creado este método.*/

    /**Función que crea una tabla películas. No debería ser necesario usarla en una base de datos convencional, ya que estaría creada la tabla de antemano*/
    fun createTablePeliculas(){
        val ps: PreparedStatement = conn!!.prepareStatement(Sentencias.createTablePeliculas)
        ps.executeUpdate()
        ps.close()
    }

}
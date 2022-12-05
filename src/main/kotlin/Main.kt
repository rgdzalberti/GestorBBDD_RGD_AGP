import modelo.GestorBDD
import modelo.clases.Pelicula

fun main(args: Array<String>) {


    val pelicula = Pelicula(110112L, "La muerte de Ricardo", 2023, "Klays", "La mejor pelicula del mundo")


    val gestorBDD = GestorBDD.getInstance()

    gestorBDD.connect()

    gestorBDD.createTablePeliculas()

    gestorBDD.insertPelicula(pelicula)

    gestorBDD.selectAll().forEach{
        println(it)
    }


    gestorBDD.disconnect()



}
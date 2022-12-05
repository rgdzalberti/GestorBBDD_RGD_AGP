import modelo.GestorBDD
import modelo.clases.Pelicula

fun main(args: Array<String>) {


    val pelicula = Pelicula(110112L, "La muerte de Ricardo", "La mejor pelicula del mundo", 2023,"Klays" )


    val gestorBDD = GestorBDD.getInstance()

    gestorBDD.connect()

    gestorBDD.createTablePeliculas()

    gestorBDD.insertPelicula(pelicula)

    gestorBDD.selectAll().forEach{
        println(it)
    }

    gestorBDD.updateById(pelicula.id, Pelicula(pelicula.id,"La muerte de Ricardo 2", "Es una mierda", 2024,"Parra"))

    gestorBDD.selectAll().forEach { println(it) }

    gestorBDD.deleteById(pelicula.id)

    gestorBDD.selectAll().forEach { println(it) }

    gestorBDD.disconnect()



}
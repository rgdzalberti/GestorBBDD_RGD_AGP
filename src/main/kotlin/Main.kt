import controlador.Controlador
import modelo.clases.Pelicula
import modelo.GestorBDD
import vista.GUI
import vista.UserInterface

/**
 * Hola Diego. Para facilitarte la lectura de la documentación, te recomendamos que pulses Ctrl + Alt + Q, que activará el modo lectura, y
 * formateará el texto documentado y lo hará mucho más agradable a la vista :).
 * Comentarios:
 *
 * - En [Controlador], en el init, se llama a la función [GestorBDD.createTablePeliculas], que no debería ser necesario si utilizas una base de datos normal,
 * pero nosotros utilizamos una que se guarda solo en tiempo de ejecución, por lo que con cada ejecución del main se borra la tabla Películas.
 * Por eso, si vas a cambiar la base de datos para probar la aplicación, para crear la tabla la puedes crear mediante la consola o mediante la primera ejecución
 * del programa, pero después de eso no sé si dará error al intentar crear una tabla ya existente, por lo que recomiendo que borres la línea 19 de [Controlador]
 * Para más información, haz Ctrl + Click Izquierdo aquí: [GestorBDD.createTablePeliculas]
 *
 * - Los textos que se van a imprimir por pantalla los hemos dividido en una clase aparte, que se encuentra en [vista.strings.Strings],
 * y son los que se usan en [GUI].
 *
 * - La [vista] la hemos dividido en una clase [GUI] y una interfaz [UserInterface], para que en vez de insertar la vista [GUI] directamente a el [Controlador],
 * puedas insertar la que quieras mientras implemente [UserInterface], o lo que es lo mismo, hemos implementado el patrón de diseño de injección de dependencias.
 *
 * @author Alejandro González Parra y Ricardo Gallego Domínguez
* */


fun main() {


    val pelicula = Pelicula(110112L, "La muerte de Ricardo", "La mejor pelicula del mundo", 2023,"Klays" )


    val controlador = Controlador(GUI())

    var bucle = true

    do {

        when(controlador.onStart()){
            1 -> controlador.onBuscarPelicula()
            2 -> controlador.onRegistrarPelicula()
            3 -> controlador.onActualizarPelicula()
            4 -> controlador.onBorrarPelicula()
            5 -> controlador.onMostrarTodasLasPeliculas()
            6 -> {
                controlador.onExit()
                bucle = false
            }
        }


    } while (bucle)


}
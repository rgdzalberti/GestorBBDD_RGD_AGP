package controlador

import modelo.clases.Pelicula
import vista.UserInterface
import modelo.GestorBDD


/**
 *Relaciona una interfaz [UserInterface] y un [GestorBDD] para la ejecución de la aplicación. Es la clase principal de la aplicación
 * @param gui Instancia de una clase que implemente la interfaz [UserInterface]
 * */
class Controlador(private val gui: UserInterface<Pelicula,Long>) {

    private val model = GestorBDD.getInstance()

    init {

        //No se debe usar si tu BD no es in-memory, para que no de error al intentar crear una tabla ya creada
        model.createTablePeliculas()

    }

    /**Comienza la aplicación e imprime el menú principal. Devuelve un [Int] según la opción elegida, del 1 al 6*/
    fun onStart():Int {

        model.connect()
        val intMenu = gui.startMenu()
        if (intMenu != null) return intMenu
        else gui.printIncorrectInput()
        return -1


    }

    /**Realiza una búsqueda de una película según lo introducido por pantalla*/
    fun onBuscarPelicula() {
        val id = gui.getId()
        if (id!= null) {
            val pelicula = model.selectById(id)
            if (pelicula != null){
                gui.printString(pelicula)
            } else gui.dataNotFound()

        } else gui.printIncorrectInput()
    }

    /**Imprime todas las películas de la BD*/
    fun onMostrarTodasLasPeliculas() = model.selectAll().forEach { gui.printString(it)}

    /**Inserta una película nueva según lo introducido por pantalla*/
    fun onRegistrarPelicula(){

        val pelicula = gui.getData()

        if (pelicula != null){
            model.insertPelicula(pelicula)
        } else gui.printIncorrectInput()

    }

    /**Actualiza una película a unos datos nuevos según lo introducido por pantalla*/
    fun onActualizarPelicula(){
        val pelicula = gui.getData()

        if (pelicula != null) model.updateById(pelicula.id,pelicula)
        else gui.printIncorrectInput()
    }

    /**Borra una película según lo introducido por pantalla*/
    fun onBorrarPelicula(){
        val id = gui.getId()

        if (id!=null) model.deleteById(id)
        else gui.printIncorrectInput()
    }

    /**Termina la aplicación*/
    fun onExit(){
        model.disconnect()
        gui.exit()
    }



}
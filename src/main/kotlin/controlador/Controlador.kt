package controlador

import modelo.clases.Pelicula
import vista.GUI
import vista.UserInterface

class Controlador(private val gui: UserInterface<Pelicula>) {

    private val

    fun start() = gui.startMenu()

    fun onBuscarPelicula() {

    }

}
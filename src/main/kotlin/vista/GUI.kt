package vista

import modelo.clases.Pelicula
import vista.strings.Strings

class GUI : UserInterface<Pelicula, Long> {

    override fun startMenu(): Int? {
        println(Strings.mainMenu)

        val input: String = readln()

        try {
            if (input.toInt() in 1..6) {
                return input.toInt()
            } else return null
        } catch (_: Exception) {
            return null
        }

    }

    override fun getId(): Long? {

        println(Strings.insertID)

        val input: String = readln()

        try {
            return input.toLong()
        } catch (_: Exception) {
            return null
        }

    }

    override fun getData(): Pelicula? {

        println(Strings.insertID)
        val inputID: String = readln()

        println(Strings.insertNombre)
        val inputNombre: String = readln()

        println(Strings.insertComentario)
        val inputCometario: String = readln()

        println(Strings.insertAnio)
        val inputAnio: String = readln()

        println(Strings.insertDirector)
        val inputDirector: String = readln()

        try {
            return Pelicula(inputID.toLong(), inputNombre, inputCometario, inputAnio.toInt(), inputDirector)
        } catch (_: Exception) {
            return null
        }


    }


    override fun printIncorrectInput() {
        println(Strings.incorrectInput)
    }


    override fun dataNotFound() {
        println(Strings.dataNotFound)
    }


    override fun <T> printString(data: T) {
        println(data)
    }

    override fun exit() {
        println(Strings.exit)
    }
}
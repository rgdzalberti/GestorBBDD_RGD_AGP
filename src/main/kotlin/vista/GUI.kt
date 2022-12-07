package vista

import modelo.clases.Pelicula
import vista.strings.Strings

class GUI : UserInterface<Pelicula, Long> {

    override fun startMenu(): Int? {
        println(Strings.mainMenu)

        val input: String = readln()

        try {
            if (input.toInt() in 1..4) {
                return input.toInt()
            } else return null
        } catch (_: Exception) {
            return null
        }

    }

    override fun printData(): Long? {

        println(Strings.insertID)

        val input: String = readln()

        try {
            return input.toLong()
        } catch (_: Exception) {
            return null
        }

    }

    override fun insertData(): Pelicula? {

        println(Strings.insertID)
        TODO()
    }

    override fun updateData(): Pelicula? {
        TODO("Not yet implemented")
    }

    override fun deleteData(): Long? {
        TODO("Not yet implemented")
    }


    private fun printIncorrectInput() {
        println("\nLa entrada ha sido incorrecta")
    }

}
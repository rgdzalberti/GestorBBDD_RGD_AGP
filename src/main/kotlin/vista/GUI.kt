package vista

import modelo.clases.Pelicula

class GUI : UserInterface<Pelicula, Long> {

    override fun startMenu(): Int? {

        var bool = false
        var input: String

        println(
            "\nBienvenido a tu Filmoteca. Elija una opción:\n" +
                    "1. Buscar película\n" +
                    "2. Registrar película\n" +
                    "3. Actualizar película\n" +
                    "4. Borrar película\n"
        )

        input = readln()

        try {
            if (input.toInt() in 1..4) {
                bool = true
                return input.toInt()
            } else return null
        } catch (_: Exception) {
            return null
        }

    }

    private fun printPelicula(id: Long) {

        println("\n")
    }

    private fun modifyConfig(config: Map<String, String>): Map<String, String> {

        var bool = false
        var menuNumber = 1
        val newConfMap = mutableMapOf<String, String>()
        val optionMap = mutableMapOf<Int, String>()

        println("\nElija el parámetro a modificar:")

        config.forEach {
            println(menuNumber.toString() + ". " + it.key)
            optionMap[menuNumber] = it.key
            menuNumber++
        }
        optionMap[menuNumber] = "cancelar"
        println("$menuNumber. Cancelar")
        println("\n")

        val input: String = readln()

        try {
            if (input.toInt() in 1..menuNumber) {
                bool = true
            }
        } catch (_: Exception) {
            printIncorrectInput()
        }


        if (bool) {
            val optionName = optionMap[input.toInt()]

            if (!optionName.isNullOrBlank()) {

                if (optionName == "cancelar") {
                    println("Se cancelarán los cambios")
                } else {

                    if (optionName == "password") {

                        val password = changePassword(config)
                        newConfMap["password"] = password
                    } else {
                        val setting = changeSetting(optionName)
                        if (!setting.isNullOrBlank()) newConfMap[optionName] = setting


                    }
                }
            } else printIncorrectInput()

            return newConfMap

        } else {
            printIncorrectInput()
            return emptyMap()
        }
    }

    private fun printIncorrectInput() {
        println("\nLa entrada ha sido incorrecta")
    }

}
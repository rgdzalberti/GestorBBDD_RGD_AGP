package vista


/**Implementa lo que tendría que tener cualquier interfaz de usuario que interactúe también con una base de datos
 * @param T Tipo de dato que se almacena en la base de datos
 * @param I ID del tipo de dato*/
interface UserInterface<T, I> {


    /**Muestra un menú
     * @return La opción seleccionada, o null si ha habido algún error*/
    fun startMenu(): Int?

    /**Pide un ID a través de la interfaz
     * @return El ID introducido, o null si ha habido algún error*/
    fun getId(): I?

    /**Pide un objeto a través de la interfaz
     * @return El objeto introducido, o null si ha habido algún error*/
    fun getData(): T?

    /**Imprime por pantalla que la entrada no ha sido correcta*/
    fun printIncorrectInput()

    /**Imprime por pantalla que no se han encontrado datos*/
    fun dataNotFound()

    /**Imprime el objeto especificado por pantalla.*/
    fun <D> printString(data: D)

    /**Imprime un texto que se despide del usuario*/
    fun exit()


}
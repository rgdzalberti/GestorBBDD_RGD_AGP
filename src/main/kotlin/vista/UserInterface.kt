package vista

interface UserInterface <T,I> {



    abstract fun startMenu():Int?

    abstract fun printData():I?

    abstract fun insertData():T?

    abstract fun updateData():T?

    abstract fun deleteData(): I?

}
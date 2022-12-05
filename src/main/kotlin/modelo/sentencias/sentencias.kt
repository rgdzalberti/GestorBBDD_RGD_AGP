package modelo.sentencias

object Sentencias{
    val insertPelicula = "INSERT INTO PELICULAS VALUES (?,?,?,?,?)"
    val selectById = "select * from PELICULAS where ID_PELICULA = ?"
    val selectAll = "SELECT * FROM PELICULAS"
    val createTablePeliculas = "CREATE TABLE PELICULAS (ID_PELICULA NUMBER(10,0) CONSTRAINT PK_ID_PELICULA PRIMARY KEY, NOMBRE VARCHAR2(50), COMENTARIO VARCHAR2(200), ANIO NUMBER(4,0) CHECK(ANIO>1800), DIRECTOR VARCHAR2(50))"
    val updateById = "UPDATE PELICULAS SET ID_PELICULA = ?, NOMBRE = ?, COMENTARIO = ?, ANIO = ?, DIRECTOR = ? WHERE ID_PELICULA = ?"
    val deleteById = "delete from PELICULAS where ID_PELICULA = ?"
}
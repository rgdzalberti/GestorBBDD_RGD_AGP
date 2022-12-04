package modelo.sentencias

object Sentencias{
    val insert = "INSERT INTO PELICULAS VALUES (?,?,?,?,?)"
    val selectById = "select * from INVENTARIOS where ID_PELICULA = ?"
    val selectAll = "SELECT * FROM PELICULAS"
    val createTablePeliculas = "CREATE TABLE PELICULAS (ID_PELICULA NUMBER(10,0) CONSTRAINT PK_ID_PELICULA PRIMARY KEY, NOMBRE VARCHAR2(50), COMENTARIO VARCHAR2(200), ANIO NUMBER(4,0) CHECK(ANIO>1800), DIRECTOR VARCHAR2(50))"
}
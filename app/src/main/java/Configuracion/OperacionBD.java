    package Configuracion;
    
    public class OperacionBD {
    
        //Nombre de la base de datos
        public static final String DBname = "PM1E1006";
    
        //Creacion de las tablas de base de datos
        public static final String TablePersonas = "contactos";
    
        //Creacion de los campos de base de datos
        public static final String id = "id";
        public static final String pais = "pais";
        public static final String nombreCompleto = "nombreCompleto";
        public static final String telefono = "telefono";
        public static final String nota = "nota";
        public static final String imagen = "imagen";
    
        //DDL Create
    
        public static final String CreateTablePersonas = "Create table " + TablePersonas + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, pais TEXT, nombreCompleto TEXT, telefono TEXT, " +
                "nota TEXT, imagen TEXT )";
    
        public static final String DropTablePersonas = "DROP TABLE IF EXISTS " + TablePersonas;
    
        public static final String SelectAllPersonas = "SELECT * FROM " + TablePersonas;
    
        public static final String BorrarPersonas = "DELETE FROM " + TablePersonas + " WHERE id = '" + id + "'";

    }

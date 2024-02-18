    package Configuracion;

    import android.content.ContentValues;
    import android.database.sqlite.SQLiteDatabase;

    import java.nio.file.Path;

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

        public static int actualizarPersona(SQLiteDatabase db, int id, String pais, String nombreCompleto, String telefono, String nota, String imagen) {
            ContentValues valores = new ContentValues();
            valores.put("pais", pais);
            valores.put("nombreCompleto", nombreCompleto);
            valores.put("telefono", telefono);
            valores.put("nota", nota);
            valores.put("imagen", imagen);
            String whereClause = "id=?";
            String[] whereArgs = new String[]{String.valueOf(id)};
            return db.update("contactos", valores, whereClause, whereArgs);
        }

        public static int eliminarPersona(SQLiteDatabase db, int id) {
            String whereClause = "id=?";
            String[] whereArgs = new String[]{String.valueOf(id)};
            return db.delete("contactos", whereClause, whereArgs);
        }



    }



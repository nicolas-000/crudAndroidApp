package cl.nicolas000.prueba2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConnectionDBHelper extends SQLiteOpenHelper {
    String query = "CREATE TABLE USUARIOS (ID INTEGER PRIMARY KEY, NOMBRE TEXT, APELLIDO TEXT, EMAIL TEXT, PASSWORD TEXT)";
    public ConnectionDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);
        db.execSQL("CREATE TABLE SUPERUSUARIOS (ID INTEGER PRIMARY KEY, NOMBRE TEXT, EMAIL TEXT, PASSWORD TEXT)");
        db.execSQL("INSERT INTO SUPERUSUARIOS (NOMBRE, EMAIL, PASSWORD) VALUES ('ADMIN', 'admin@mail.com', 'Admin123?')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE USUARIOS");
        db.execSQL("DROP TABLE SUPERUSUARIOS");
        db.execSQL(query);
    }
}

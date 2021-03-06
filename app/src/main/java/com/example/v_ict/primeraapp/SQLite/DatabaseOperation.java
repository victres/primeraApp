package com.example.v_ict.primeraapp.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.v_ict.primeraapp.SQLite.TablaInformacion.TablaUsuario;

/**
 * Created by jrguerrerogomez on 1/7/18.
 */

public class DatabaseOperation extends SQLiteOpenHelper {

    public static final String database_name = "primeraApp";
    //TODO: Actualizar version
    public static final int database_version = 2;

    public String CREATE_QUERY_USUARIO = "CREATE TABLE " +
            TablaUsuario.TABLE_NAME + " (" +
            TablaUsuario.USUARIO + " TEXT, " +
            TablaUsuario.CONTRASENIA + " TEXT, " +
            TablaUsuario.EMAIL + " TEXT);";

    public DatabaseOperation(Context context) {
        super(context, database_name, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TablaUsuario.TABLE_NAME);
    }

    // GUARDAR USUARIO
    public void guardarUsuario(DatabaseOperation dop, String name, String password, String email) {
        SQLiteDatabase sdb = dop.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TablaUsuario.USUARIO, name);
        cv.put(TablaUsuario.CONTRASENIA, password);
        cv.put(TablaUsuario.EMAIL, email);
        //Ingresar fecha

        long response = sdb.insert(TablaUsuario.TABLE_NAME, null, cv);
    }

    // CONSULTAR USUARIO
    public Cursor consultarUsuarios(DatabaseOperation dop) {
        SQLiteDatabase sdb = dop.getReadableDatabase();

        String[] colums = {
                TablaUsuario.USUARIO,
                TablaUsuario.CONTRASENIA,
                TablaUsuario.EMAIL
        };

        Cursor cursor = sdb.query(TablaUsuario.TABLE_NAME, colums, null, null, null, null, null);
        return cursor;
    }
    // ELIMINAR ESTATUS
    public boolean eliminarEstatus(DatabaseOperation dop) {
        SQLiteDatabase sdb = dop.getWritableDatabase();
        int result = sdb.delete(TablaUsuario.TABLE_NAME, null, null);
        return result > 0;
    }

    // Validacion
    public Boolean validaSiExisteUsuario(DatabaseOperation dop) {
        SQLiteDatabase sdb = dop.getReadableDatabase();

        Boolean elimino = false;

        String[] colums = {
                TablaUsuario.USUARIO,
                TablaUsuario.CONTRASENIA,
                TablaUsuario.EMAIL
        };

        Cursor cursor = sdb.query(TablaUsuario.TABLE_NAME, colums, null, null, null, null, null);

        if(cursor.getCount()>0){
            sdb.delete(TablaUsuario.TABLE_NAME, null,null);
            elimino = true;
        }

        return elimino;
    }
}

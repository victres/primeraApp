package com.example.v_ict.primeraapp.SQLite;

import android.provider.BaseColumns;

/**
 * Created by jrguerrerogomez on 1/7/18.
 */

public class TablaInformacion {

    public TablaInformacion() {

    }

    public static abstract class TablaUsuario implements BaseColumns {

        public static final String USUARIO = "usuario";
        public static final String CONTRASENIA = "contrasenia";
        public static final String EMAIL = "email";
        //TODO: Agregar Sesion y actualizar version en DatabaseOperation

        public static final String TABLE_NAME = "usuario";

    }

}


package com.example.hospitalsx.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sql extends SQLiteOpenHelper {

    private static final String database = "pacientes";
    private static final int VERSION = 1;
    private final String  tPacientes = "CREATE TABLE PACIENTES (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "AREA TEXT NOT NULL," +
            "DOCTOR TEXT NOT NULL," +
            "NOMBRE TEXT NOT NULL," +
            "SEXO TEXT NOT NULL," +
            "FECHA_INGRESO TEXT NOT NULL," +
            "EDAD TEXT NOT NULL," +
            "ESTATURA TEXT NOT NULL," +
            "PESO TEXT NOT NULL," +
            "IMAGEN TEXT NOT NULL);";

    public sql(Context context){
        super(context, database, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(tPacientes);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if (newVersion > oldVersion){
            db.execSQL("DROP TABLE IF EXISTS PACIENTES");
            db.execSQL(tPacientes);
        }
    }
}

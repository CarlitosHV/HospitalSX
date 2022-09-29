package com.example.hospitalsx.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class sqlite {
    private final sql Sql;
    private SQLiteDatabase db;

    public sqlite(Context context){
        Sql = new sql(context);
    }

    public void abrir(){
        Log.i("SQLite", "Se abre conexión con base de datos " + Sql.getDatabaseName());
        db = Sql.getWritableDatabase();
    }

    public void cerrar(){
        Log.i("SQLite", "Se cierra conexión con base de datos " + Sql.getDatabaseName());
        Sql.close();
    }

    public boolean addRegistroPaciente(
            int id, String area, String  doc, String name, String  sex, String date, String age,
            String heigth, String weigth, String image){

        ContentValues cv = new ContentValues();
        cv.put("ID", id);
        cv.put("AREA", area);
        cv.put("DOCTOR", doc);
        cv.put("NOMBRE", name);
        cv.put("SEXO", sex);
        cv.put("FECHA_INGRESO", date);
        cv.put("EDAD", age);
        cv.put("ESTATURA", heigth);
        cv.put("PESO", weigth);
        cv.put("IMAGEN", image);

        return (db.insert("PACIENTES", null, cv) != 1)?true : false;

    }

    public Cursor getRegistro(){
        return db.rawQuery("SELECT * FROM PACIENTES", null);
    }

    public ArrayList<String> getPacientes(Cursor cursor){
        ArrayList<String> ListData = new ArrayList<>();
        String item = "";

        if (cursor.moveToFirst()){
            do{
                item += "ID :[" + cursor.getString(0) + " \r\n";
                item += "AREA :" + cursor.getString(1) + " \r\n";
                item += "DOCTOR :" + cursor.getString(2) + " \r\n";
                item += "NOMBRE :" + cursor.getString(3) + " \r\n";
                item += "SEXO :" + cursor.getString(4) + " \r\n";
                item += "FECHA_INGRESO :" + cursor.getString(5) + " \r\n";
                item += "EDAD :" + cursor.getString(6) + " \r\n";
                item += "ESTATURA :" + cursor.getString(7) + " \r\n";
                item += "PESO :" + cursor.getString(8) + " ]\r\n";
                ListData.add(item);
                item = "";
            }while(cursor.moveToNext());
        } return ListData;
    }

    public ArrayList<String> getImagenes(Cursor cursor){
        ArrayList<String> ListData = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                ListData.add(cursor.getString(9));
            }while(cursor.moveToNext());
        } return ListData;
    }

    public ArrayList<String> getID(Cursor cursor){
        ArrayList<String> ListData = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                ListData.add(cursor.getString(0));
            }while(cursor.moveToNext());
        } return ListData;
    }

    public String  updateRegistroPaciente(
            int id, String area, String  doc, String name, String  sex, String date, String age,
            String heigth, String weigth, String image){

        ContentValues cv = new ContentValues();
        cv.put("ID", id);
        cv.put("AREA", area);
        cv.put("DOCTOR", doc);
        cv.put("NOMBRE", name);
        cv.put("SEXO", sex);
        cv.put("FECHA_INGRESO", date);
        cv.put("EDAD", age);
        cv.put("ESTATURA", heigth);
        cv.put("PESO", weigth);
        cv.put("IMAGEN", image);

        int valor = db.update("PACIENTES", cv, "ID=" + id, null);

        if (valor == 1){
            return "Paciente actualizado con éxito";
        }else{
            return "Error de actualización";
        }
    }

    public Cursor getValor(int id){
        return db.rawQuery("SELECT * FROM PACIENTES WHERE ID =" + id, null);
    }

    public int Eliminar(Editable id){
        return db.delete("PACIENTES", "ID=" +id, null);
    }
}

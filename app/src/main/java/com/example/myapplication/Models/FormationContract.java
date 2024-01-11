package com.example.myapplication.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class FormationContract extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CV.db";
    public static final String TABLE_NAME = "Formation";
    public static final String col_1 = "ID";
    public static final String col_2 = "Libelle";
    public static final String col_3 = "Annee";
    public static final String col_4 = "Institue";
    public static final String col_5 = "ProfileID"; // Nouvelle colonne pour l'ID du profil

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    col_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    col_2 + " TEXT," +
                    col_3 + " TEXT," +
                    col_4 + " TEXT," +
                    col_5 + " INTEGER," + // Colonne pour l'ID du profil
                    "FOREIGN KEY (" + col_5 + ") REFERENCES " +
                    ProfileContract.TABLE_NAME + "(" + ProfileContract.COL_1 + "))";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public FormationContract(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public boolean insertFormation(String libelle, String annee, String institue, int profilID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2, libelle);
        contentValues.put(col_3, annee);
        contentValues.put(col_4, institue);
        contentValues.put(col_5, profilID);
        long res = db.insert(TABLE_NAME, null, contentValues);
        return res != -1;
    }

    public Cursor getAllFormations() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public Cursor getAllFormationsById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        System.out.println("getProfileById(String id)  :  ***************************************************************************++\n"
        + id);
        String[] columns = { col_2, col_3, col_4}  ;


        return  db.query(TABLE_NAME, columns, "ProfileID=?",
                new String[]{id+""}, null, null, null);

    }


    public boolean updateFormation(String id, String libelle, String annee, String institue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_1, id);
        contentValues.put(col_2, libelle);
        contentValues.put(col_3, annee);
        contentValues.put(col_4, institue);
        db.update(TABLE_NAME, contentValues, col_1 + "=?", new String[]{id});
        return true;
    }

    public Integer deleteFormation(String Libelle,String Annee,String Institue) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] col = new String[]{};
        System.out.println("deleteFormation(String Libelle,String Annee,String Institue) ****************\n");
        return db.delete(TABLE_NAME,  col_2+"=? AND " +col_3+"=? AND " +col_4+"=?", new String[]{Libelle,Annee,Institue});
    }
}

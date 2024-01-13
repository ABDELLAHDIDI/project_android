package com.example.myapplication.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExperienceContract extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CV.db";
    public static final String TABLE_NAME = "Experience";
    public static final String col_1 = "ID";
    public static final String col_2 = "poste";
    public static final String col_3 = "entreprise";
    public static final String col_4 = "duree";
    public static final String col_5 = "ProfileID"; // Nouvelle colonne pour l'ID du profil

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    col_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    col_2 + " TEXT," +
                    col_3 + " TEXT," +
                    col_4 + " TEXT," +  // Modifié pour correspondre au type de duree dans votre template
                    "FOREIGN KEY (" + col_5 + ") REFERENCES " +
                    ProfileContract.TABLE_NAME + "(" + ProfileContract.COL_1 + ") ON DELETE CASCADE )";


    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public ExperienceContract(Context context) {
        super(context, DATABASE_NAME, null, 5);
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

    public boolean insertExperience(String poste, String entreprise, String duree, int profileID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2, poste);
        contentValues.put(col_3, entreprise);
        contentValues.put(col_4, duree);
        contentValues.put(col_5, profileID);
        long res = db.insert(TABLE_NAME, null, contentValues);
        return res != -1;
    }

    public Cursor getAllExperiences() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean updateExperience(String id, String poste, String entreprise, String duree, int profileID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2, poste);
        contentValues.put(col_3, entreprise);
        contentValues.put(col_4, duree);
        contentValues.put(col_5, profileID);
        db.update(TABLE_NAME, contentValues, col_1 + "=?", new String[]{id});
        return true;
    }

    public Integer deleteExperience(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, col_1 + "=?", new String[]{id});
    }

    public Cursor getAllExperiencesById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        System.out.println("getProfileById(String id)  from experience :  ***************************************************************************++\n"
                + id);
        String[] columns = { col_2, col_3, col_4}  ;


        return  db.query(TABLE_NAME, columns, "ProfileID=?",
                new String[]{id+""}, null, null, null);

    }


    public Integer deleteExperience(String poste, String entreprise, String duree) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] col = new String[]{};
        System.out.println("deleteFormation(String Libelle,String Annee,String Institue) ****************\n");
        return db.delete(TABLE_NAME,  col_2+"=? AND " +col_3+"=? AND " +col_4+"=?", new String[]{poste,entreprise,duree});
    }


}

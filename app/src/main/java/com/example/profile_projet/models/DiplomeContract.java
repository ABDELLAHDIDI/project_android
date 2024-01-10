package com.example.profile_projet.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DiplomeContract extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CV.db";
    public static final String TABLE_NAME = "Diplome";
    public static final String col_1 = "ID";
    public static final String col_2 = "Libelle";
    public static final String col_3 = "Annee";
    public static final String col_4 = "EcoleID";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    col_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    col_2 + " TEXT," +
                    col_3 + " TEXT," +
                    col_4 + " INTEGER)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DiplomeContract(Context context) {
        super(context, DATABASE_NAME, null, 4);
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

    public boolean insertDiplome(String libelle, String annee, String ecoleID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2, libelle);
        contentValues.put(col_3, annee);
        contentValues.put(col_4, ecoleID);
        long res = db.insert(TABLE_NAME, null, contentValues);
        return res != -1;
    }

    public Cursor getAllDiplomes() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean updateDiplome(String id, String libelle, String annee, String ecoleID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_1, id);
        contentValues.put(col_2, libelle);
        contentValues.put(col_3, annee);
        contentValues.put(col_4, ecoleID);
        db.update(TABLE_NAME, contentValues, col_1 + "=?", new String[]{id});
        return true;
    }

    public Integer deleteDiplome(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, col_1 + "=?", new String[]{id});
    }
}

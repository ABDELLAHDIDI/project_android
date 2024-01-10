package com.example.profile_projet.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CompetenceLinguistiqueContract extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CV.db";
    public static final String TABLE_NAME = "CompetenceLinguistique";
    public static final String col_1 = "ID";
    public static final String col_2 = "Langue";
    public static final String col_3 = "Niveau";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    col_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    col_2 + " TEXT," +
                    col_3 + " TEXT)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public CompetenceLinguistiqueContract(Context context) {
        super(context, DATABASE_NAME, null, 6);
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

    public boolean insertCompetenceLinguistique(String langue, String niveau) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2, langue);
        contentValues.put(col_3, niveau);
        long res = db.insert(TABLE_NAME, null, contentValues);
        return res != -1;
    }

    public Cursor getAllCompetencesLinguistiques() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean updateCompetenceLinguistique(String id, String langue, String niveau) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_1, id);
        contentValues.put(col_2, langue);
        contentValues.put(col_3, niveau);
        db.update(TABLE_NAME, contentValues, col_1 + "=?", new String[]{id});
        return true;
    }

    public Integer deleteCompetenceLinguistique(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, col_1 + "=?", new String[]{id});
    }
}

package com.example.myapplication.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.Adapter.Profile;


public class ProfileContract extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CV.db";
    public static final String TABLE_NAME = "Profile";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Nom";
    public static final String COL_3 = "Prenom";
    public static final String COL_4 = "Email";
    public static final String COL_5 = "Age";
    public static final String COL_6 = "Filiere";
    public static final String COL_7 = "Nom_Icone";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_2 + " TEXT," +
                    COL_3 + " TEXT," +
                    COL_4 + " TEXT UNIQUE," +
                    COL_5 + " INTEGER," +
                    COL_6 + " TEXT," +
                    COL_7 + " TEXT)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public ProfileContract(Context context) {
        super(context, DATABASE_NAME, null, 2);
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

    public boolean insertProfile(String nom, String prenom, String email, int age, String Filiere) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, nom);
        contentValues.put(COL_3, prenom);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, age);
        contentValues.put(COL_6, Filiere);
        contentValues.put(COL_7, "omar");
        long res = db.insert(TABLE_NAME, null, contentValues);
        return res != -1;
    }
    public boolean addProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, profile.getNom());
        contentValues.put(COL_3, profile.getPrenom());
        contentValues.put(COL_4, profile.getEmail());
        contentValues.put(COL_5, profile.getAge());
        contentValues.put(COL_6, profile.getFiliere());
        contentValues.put(COL_7, profile.getNomIcone());
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllProfiles() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean updateProfile(String nom, String prenom, String email, int age, String Filiere) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, nom);
        contentValues.put(COL_3, prenom);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, age);
        contentValues.put(COL_6, Filiere);
        db.update(TABLE_NAME, contentValues, "Email=?", new String[]{email});
        return true;
    }

    public boolean updateProfile(String id, String nom, String prenom, String email, int age, String Filiere) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, nom);
        contentValues.put(COL_3, prenom);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, age);
        contentValues.put(COL_6, Filiere);
//        contentValues.put(COL_7, Pass);
        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{id});
        return true;
    }

    public Boolean deleteProfile(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID=?", new String[]{id}) != 0;
    }
    public Cursor getProfileById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_2, COL_3, COL_4, COL_5, COL_6};
        String selection = COL_1 + "=?";
        String[] selectionArgs = {id};

        return db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
    }

    public Cursor getProfileByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_2, COL_3, COL_4, COL_5, COL_6};

        return db.query(TABLE_NAME, columns, "Email=?",
                new String[]{email}, null, null, null);
    }


    public boolean getProfilIdByEmail(String string) {
        return true;
    }
}

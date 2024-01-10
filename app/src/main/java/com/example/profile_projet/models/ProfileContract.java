package com.example.profile_projet.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProfileContract extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CV.db";
    public static final String TABLE_NAME = "Profile";
    public static final String col_1 = "ID";
    public static final String col_2 = "Nom";
    public static final String col_3 = "Prenom";
    public static final String col_4 = "Email";
    public static final String col_5 = "Type"; // ingénieur/laureat
    public static final String col_6 = "Filiere";
    public static final String col_7 = "Pass";
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    col_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    col_2 + " TEXT," +
                    col_3 + " TEXT," +
                    col_4 + " TEXT UNIQUE ," +
                    col_5 + " TEXT  ," +
                    col_6 + " TEXT  ," +
                    col_7 + " TEXT)";

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

    public boolean insertProfile(String nom, String prenom, String email, String type,String Filiere,String Pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2, nom);
        contentValues.put(col_3, prenom);
        contentValues.put(col_4, email);
        contentValues.put(col_5, type);
        contentValues.put(col_6, Filiere);
        contentValues.put(col_7, Pass);
        long res = db.insert(TABLE_NAME, null, contentValues);
        return res != -1;
    }

    public Cursor getAllProfiles() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean updateProfile( String nom, String prenom, String email, String type,String Filiere) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(col_2, nom);
        contentValues.put(col_3, prenom);
        contentValues.put(col_4, email);
        contentValues.put(col_5, type);
        contentValues.put(col_6, Filiere);
        db.update(TABLE_NAME, contentValues, "Email=?", new String[]{email});
        return true;
    }

    // pour l'admin

    public boolean updateProfile(String id, String nom, String prenom, String email, String type,String Filiere,String Pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_1, id);
        contentValues.put(col_2, nom);
        contentValues.put(col_3, prenom);
        contentValues.put(col_4, email);
        contentValues.put(col_5, type);
        contentValues.put(col_6, Filiere);
        contentValues.put(col_7, Pass);
        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{id});
        return true;
    }

    public Integer deleteProfile(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID=?", new String[]{id});
    }


    public Cursor getProfileByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { col_2, col_3, col_4, col_5,col_6}  ;


        return  db.query(TABLE_NAME, columns, "Email=?",
                new String[]{email}, null, null, null);

    }

    public Integer getProfilIdByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {col_1};

        Cursor cursor = db.query(TABLE_NAME, columns, "Email=?",
                new String[]{email}, null, null, null);

        if (cursor != null ) {
            if(cursor.getCount() !=0)
            {
//                while (cursor.moveToNext()) {
                cursor.moveToFirst();
                System.out.println("cursor.getColumnIndex(col_1) ************************************+++++++\n"
                        +cursor.getColumnIndex(col_1));
               return cursor.getInt(0);
//                }
            }
        }

        return -1;
    }


}

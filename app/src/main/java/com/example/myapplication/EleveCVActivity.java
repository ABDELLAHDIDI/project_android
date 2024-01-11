package com.example.myapplication;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Models.ProfileContract;

public class EleveCVActivity extends AppCompatActivity {
    private TextView txtId;
    private TextView txtNom;
    private TextView txtPrenom;
    private TextView txtAge;
    private TextView txtEmail;
//    private TextView txtTelephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleve_cv);

        txtId = findViewById(R.id.txtId);
        txtNom = findViewById(R.id.txtNom);
        txtPrenom = findViewById(R.id.txtPrenom);
        txtAge = findViewById(R.id.txtAge);
        txtEmail = findViewById(R.id.txtEmail);

        ProfileContract profileContract = new ProfileContract(this);

        String profileId = getIntent().getStringExtra("profileId");
        System.out.println("++++++++++"+ profileId+"+++++++++++++++");
        Cursor cursor = profileContract.getProfileById(profileId);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String id = cursor.getString(0);
            @SuppressLint("Range") String nom = cursor.getString(1);
            @SuppressLint("Range") String prenom = cursor.getString(2);
            @SuppressLint("Range") String email = cursor.getString(3);
            @SuppressLint("Range") int age = cursor.getInt(4);
//            @SuppressLint("Range") String adresse = cursor.getString(5);
//            @SuppressLint("Range") String telephone = cursor.getString(6);

            txtId.setText(id);
            txtNom.setText(nom);
            txtPrenom.setText(prenom);
            txtAge.setText(String.valueOf(age));
//            txtAdresse.setText(adresse);
            txtEmail.setText(email);
//            txtTelephone.setText(telephone);
        }

        if (cursor != null) {
            cursor.close();
        }
    }
}
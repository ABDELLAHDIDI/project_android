package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Adapter.Adapter_Profile;
import com.example.myapplication.Adapter.Profile;
import com.example.myapplication.Models.ProfileContract;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Adapter_Profile.OnDeleteClickListener,Adapter_Profile.OnUpdateClickListener {
    private ListView lstEleves;
    private ArrayList<Profile> arrayEleves;
    private Adapter_Profile AProfile;
    private ProfileContract profileContract;


    ActivityResultLauncher<Intent> lanceur_profil = registerForActivityResult
            (new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {

                            Toast.makeText(MainActivity.this, "Profile",
                                    Toast.LENGTH_LONG).show();

                            AProfile.notifyDataSetChanged();
                            populateData();


                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstEleves = findViewById(R.id.listEleves);
        arrayEleves = new ArrayList<>();
        profileContract = new ProfileContract(this);

        populateData();

        AProfile = new Adapter_Profile(this, arrayEleves, profileContract, this,this);
        lstEleves.setAdapter(AProfile);

        lstEleves.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click if needed
            }
        });


    }

    private void populateData() {
        Cursor cursor = profileContract.getAllProfiles();

        if (cursor != null && cursor.moveToFirst()) {
            arrayEleves.clear();
            do {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ProfileContract.COL_1));
                @SuppressLint("Range") String nom = cursor.getString(cursor.getColumnIndex(ProfileContract.COL_2));
                @SuppressLint("Range") String prenom = cursor.getString(cursor.getColumnIndex(ProfileContract.COL_3));

                Profile profile = new Profile(id, nom, prenom);
                arrayEleves.add(profile);
            } while (cursor.moveToNext());
        }

        cursor.close();

        if (arrayEleves.isEmpty()) {
            arrayEleves.add(new Profile("1", "elArroud", "Omar", 23, "omar.el@gmail.com", "Ginf"));
            arrayEleves.add(new Profile("2", "Smith", "John", 21, "john.smith@gmail.com", "Computer Science"));
            arrayEleves.add(new Profile("3", "Johnson", "Emily", 24, "emily.johnson@gmail.com", "Mathematics"));

            for (Profile student : arrayEleves) {
                profileContract.addProfile(student);
            }
        }
    }

    @Override
    public void onDeleteClick(int position) {
        Profile profile = arrayEleves.get(position);
        boolean deleted = profileContract.deleteProfile(profile.getId());

        if (deleted) {
            arrayEleves.remove(position);
            AProfile.notifyDataSetChanged();
            Toast.makeText(this, "Profile deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to delete profile", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpdateClick(int position) {

        Profile profile = arrayEleves.get(position);

        Intent intent = new Intent(MainActivity.this,
                Profil.class);
        intent.putExtra("id", Integer.parseInt(profile.getId()));
        lanceur_profil.launch(intent);
    }
    public void add(View view ){

        Intent intent = new Intent(MainActivity.this,
                Profil.class);
        intent.putExtra("id", 0);
             lanceur_profil.launch(intent);

    }

}
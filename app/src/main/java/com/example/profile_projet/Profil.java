package com.example.profile_projet;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.profile_projet.models.ProfileContract;


public class Profil extends AppCompatActivity {

    private EditText tNom;
    private EditText tPrenom;
    private EditText tEmail;
    private EditText tAge;
    private EditText tFiliere;
    private ProfileContract db_Profile ;

    private int id=1;



    ActivityResultLauncher<Intent> lanceur_foramtion = registerForActivityResult
            (new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {

                            Toast.makeText(Profil.this, "Profile",
                                    Toast.LENGTH_LONG).show();

                            if (result != null) {
                                if (result.getData() != null) {
                                    Toast.makeText(Profil.this, "Action validée",
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = result.getData();
                                    id = intent.getIntExtra("id", 1);

                                }


                            }
                        }
                    });





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        // Initialisation des variables de classe avec les éléments de la mise en page
        tNom = findViewById(R.id.t_nom);
        tPrenom = findViewById(R.id.t_prenom);
        tEmail = findViewById(R.id.t_email);
        tAge = findViewById(R.id.t_age);
        tFiliere = findViewById(R.id.t_filiere);
        db_Profile = new ProfileContract(this);


        Toast.makeText(Profil.this, "onCreate",
                Toast.LENGTH_SHORT).show();
      //  AddProfil();
       GetProfil();

    }


    public void AddProfil() {
        boolean
                isInserted = db_Profile.insertProfile(
                "DIDI","ABDELLAH","abdellah@gmail.com",23 ,"GINF");
        if (isInserted) {
            Toast.makeText(Profil.this, "Profil Inserted",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Profil.this, "Profil Not Inserted",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void GetProfil(){

        Toast.makeText(Profil.this, "getProfileByEmail():OnClickListener",
                Toast.LENGTH_SHORT).show();
        Cursor res = db_Profile.getProfileById(id);
        if(res != null){
            if (res.getCount() == 0) {
                Toast.makeText(Profil.this, "No Data Was Found",
                        Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(Profil.this, "there is data",
                        Toast.LENGTH_SHORT).show();

                while (res.moveToNext()) {
                    tNom.setText(res.getString(0));
                    tPrenom.setText(res.getString(1));
                    tEmail.setText(res.getString(2));
                   tAge.setText(res.getInt(3)+"");
                    tFiliere.setText(res.getString(4));
                }

            }
            res.close();
        }

    }

//    int  setType(String type){
//        for (int i = 0; i < tAge.getCount(); i++) {
//            if (tage.getItemAtPosition(i).equals(type)) {
//                return i;
//            }
//        }
//        return -1;
//    }

    public void GetAllProfils(){

        Toast.makeText(Profil.this, "GetAllData():OnClickListener",
                Toast.LENGTH_SHORT).show();
        Cursor res = db_Profile.getAllProfiles();
        if(res.getCount()==0){
            showMessage("Error","No Data Was Found");
            Toast.makeText(Profil.this, "No Data Was Found",
                    Toast.LENGTH_SHORT).show();
            return;
        }else{
            StringBuffer buffer=new StringBuffer();
            while (res.moveToNext()){
                buffer.append(
                        "\nId :"+res.getInt(0)+
                        "\nNom :"+res.getString(1)
                                +"\nPrenom : "+ res.getString(2)
                                +"\nE-mail :"+res.getString(3)
                                +"\nAge :"+res.getInt(4)
                                +"\nFiliere :"+res.getString(5)
                                +"\n");
            }
            showMessage("Data",buffer.toString());
        }
        res.close();

    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder Builder = new AlertDialog.Builder(this);
        Builder.setCancelable(true);
        Builder.setTitle(title);
        Builder.setMessage(Message);
        Builder.show();
    }

    public void Update() {

        boolean isUpdated =
                db_Profile.updateProfile(id,tNom.getText().toString(),tPrenom.getText().toString()
                        ,tEmail.getText().toString(), Integer.parseInt(tAge.getText().toString())
                        ,tFiliere.getText().toString());
        if (isUpdated) {
            Toast.makeText(Profil.this, "Data Updated",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Profil.this, "Data Not Updated",
                    Toast.LENGTH_SHORT).show();
        }
    }





    public void Formations(View view) {
        // Code à exécuter lorsque le bouton "Formations" est cliqué

        Intent intent = new Intent(Profil.this,
                Formation.class);
       intent.putExtra("id",id);
        lanceur_foramtion.launch(intent);
    }


    public void Update(View view) {

//        GetProfil();
//
   // Update();


        GetAllProfils();

    }

}
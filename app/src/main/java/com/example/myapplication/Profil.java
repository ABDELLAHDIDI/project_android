package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Models.ProfileContract;

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




        id=getIntent().getIntExtra("id" , 0);

        Toast.makeText(Profil.this, "onCreate \n"+id,
                Toast.LENGTH_SHORT).show();

    GetProfil();

    }


    public boolean AddProfil() {
        boolean isInserted = false ;

   if( tNom.getText().toString().isEmpty() || tNom.getText().toString().isEmpty()
           ||tPrenom.getText().toString().isEmpty() ||  tEmail.getText().toString().isEmpty()
           ||tAge.getText().toString().isEmpty() || tFiliere.getText().toString().isEmpty()  ){

       Toast.makeText(Profil.this, "il faut remplire tous les champs !",
               Toast.LENGTH_SHORT).show();

   }
   else {
       isInserted = db_Profile.insertProfile(
               tNom.getText().toString(),tPrenom.getText().toString()
               ,tEmail.getText().toString(), Integer.parseInt(tAge.getText().toString())
               ,tFiliere.getText().toString()
       );
   }
        if (isInserted) {
            Toast.makeText(Profil.this, "Profil Inserted",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Profil.this, "Profil Not Inserted",
                    Toast.LENGTH_SHORT).show();
        }
        return isInserted;

    }

    public void GetProfil(){

        Toast.makeText(Profil.this, "getProfileByEmail():OnClickListener",
                Toast.LENGTH_SHORT).show();
        Cursor res = db_Profile.getProfileById(String.valueOf(id));
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

    public void Update() {

        boolean isUpdated =
                db_Profile.updateProfile(String.valueOf(id),tNom.getText().toString(),tPrenom.getText().toString()
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
boolean v=false ;
       if(id==0){
           v=  AddProfil();
           id=db_Profile.getMaxProfileId();
       }
       else {
           Intent intent = new Intent(Profil.this,
                   Formation.class);
           intent.putExtra("id",id);
           lanceur_foramtion.launch(intent);
       }

     if(v){
         Intent intent = new Intent(Profil.this,
                 Formation.class);
         intent.putExtra("id",id);
         lanceur_foramtion.launch(intent);
     }

    }

    public void Retour(View view) {
        // Code à exécuter lorsque le bouton "Formations" est cliquéç

        Intent intent = new Intent();
        intent.putExtra("id", id);
        setResult(RESULT_OK, intent);
        finish();

    }


}
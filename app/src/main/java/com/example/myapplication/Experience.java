package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.myapplication.Adapter.Adapter_Experience;
import com.example.myapplication.Models.ExperienceContract;
import com.example.myapplication.Models.FormationContract;
import com.example.myapplication.Template.Template_Experience;
import com.example.myapplication.Template.Template_Formation;

import java.util.ArrayList;

public class Experience extends AppCompatActivity {

    private EditText edtAddPoste, edtAddEntreprise, edtAddDuree;
    private ListView listViewExperiences;
    private Adapter_Experience adapterExperience;
    private ArrayList<Template_Experience> experiencesList;

    private ExperienceContract db_E;

    private int ProfileID=  0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);

        edtAddPoste = findViewById(R.id.edtAddPoste);
        edtAddEntreprise = findViewById(R.id.edtAddEntreprise);
        edtAddDuree = findViewById(R.id.edtAddDuree);
        listViewExperiences = findViewById(R.id.listViewExperiences);
        experiencesList = new ArrayList<>();
        adapterExperience = new Adapter_Experience(this, experiencesList);
        listViewExperiences.setAdapter(adapterExperience);

        db_E = new ExperienceContract(this);

        ProfileID=getIntent().getIntExtra("id",0);

        GetFromations();
        delete();


    }

    private void ajouterExperience() {
        String poste = edtAddPoste.getText().toString();
        String entreprise = edtAddEntreprise.getText().toString();
        String duree = edtAddDuree.getText().toString();

        if (poste.isEmpty() || entreprise.isEmpty() || duree.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        db_E.insertExperience(poste,entreprise,duree,ProfileID);

        Template_Experience nouvelleExperience = new Template_Experience(poste, entreprise, duree);
        experiencesList.add(nouvelleExperience);
        adapterExperience.notifyDataSetChanged();

        effacerChamps();
        Toast.makeText(this, "Expérience ajoutée avec succès", Toast.LENGTH_SHORT).show();
    }

    private void effacerChamps() {
        edtAddPoste.setText("");
        edtAddEntreprise.setText("");
        edtAddDuree.setText("");
    }

    public void Add(View view) {
        ajouterExperience();
    }

    public void GetFromations(){
        Toast.makeText(Experience.this, "GetAllData():OnClickListener",
                Toast.LENGTH_SHORT).show();
        Cursor res = db_E.getAllExperiencesById(ProfileID);
        if(res.getCount()==0){
            Toast.makeText(Experience.this, "No Data Was Found",
                    Toast.LENGTH_SHORT).show();
            return;
        }else{
            while (res.moveToNext()){
                experiencesList.add(new Template_Experience(res.getString(0), res.getString(1) , res.getString(2)));
            }
            adapterExperience.notifyDataSetChanged();
        }
        res.close();
    }


    public void delete(){

        listViewExperiences.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Créer une boîte de dialogue de confirmation
                AlertDialog.Builder builder = new AlertDialog.Builder(Experience.this);
                builder.setTitle("Confirmation de suppression");
                builder.setMessage("Voulez-vous vraiment supprimer cette experience ?");

                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Supprimer la formation si l'utilisateur clique sur "Oui"
                        db_E.deleteExperience(experiencesList.get(position).getPoste(),
                                experiencesList.get(position).getEntreprise(), experiencesList.get(position).getDuree());

                        // Actualiser l'affichage de la liste après la suppression
                        experiencesList.remove(position);
                        adapterExperience.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Ne rien faire si l'utilisateur clique sur "Non"
                        dialog.dismiss();
                    }
                });

                // Afficher la boîte de dialogue
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }


    public void Retour_Fromation (View view) {

        Intent intent = new Intent();
        intent.putExtra("id", ProfileID);
        setResult(RESULT_OK, intent);
        finish();


    }

    public void competences(View view) {



    }


}

package com.example.profile_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.profile_projet.Adapter.Adapter_Formation;
import com.example.profile_projet.Template.Template_Formation;

import java.util.ArrayList;

public class Formation extends AppCompatActivity {

    private EditText edtAddLibelle, edtAddAnnee, edtAddInstitue;
    private ListView listViewFormations;
    private Adapter_Formation adapterFormation;
    private ArrayList<Template_Formation> formationsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formation);


        edtAddLibelle = findViewById(R.id.edtAddLibelle);
        edtAddAnnee = findViewById(R.id.edtAddAnnee);
        edtAddInstitue = findViewById(R.id.edtAddInstitue);
        listViewFormations = findViewById(R.id.listViewFormations);
        formationsList = new ArrayList<>();



    }

    private void ajouterFormation() {
        String libelle = edtAddLibelle.getText().toString();
        String annee = edtAddAnnee.getText().toString();
        String institue = edtAddInstitue.getText().toString();

        if (libelle.isEmpty() || annee.isEmpty() || institue.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        adapterFormation = new Adapter_Formation(this, formationsList);
        listViewFormations.setAdapter(adapterFormation);
        Template_Formation nouvelleFormation = new Template_Formation(libelle, annee, institue);
        formationsList.add(nouvelleFormation);
        adapterFormation.notifyDataSetChanged();

        effacerChamps();
        Toast.makeText(this, "Formation ajoutée avec succès", Toast.LENGTH_SHORT).show();
    }

    private void effacerChamps() {
        edtAddLibelle.setText("");
        edtAddAnnee.setText("");
        edtAddInstitue.setText("");
    }


    public  void Add(View view ){
        ajouterFormation();
    }



}
package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Adapter.Adapter_Formation;
import com.example.myapplication.Models.FormationContract;
import com.example.myapplication.Template.Template_Formation;

import java.util.ArrayList;

public class Formation extends AppCompatActivity {

    private EditText edtAddLibelle, edtAddAnnee, edtAddInstitue;
    private ListView listViewFormations;
    private Adapter_Formation adapterFormation;
    private ArrayList<Template_Formation> formationsList;
    private FormationContract db_F;
    private int ProfileID=  0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formation);


        edtAddLibelle = findViewById(R.id.edtAddLibelle);
        edtAddAnnee = findViewById(R.id.edtAddAnnee);
        edtAddInstitue = findViewById(R.id.edtAddInstitue);
        listViewFormations = findViewById(R.id.listViewFormations);
        formationsList = new ArrayList<>();
        adapterFormation = new Adapter_Formation(this, formationsList);
        listViewFormations.setAdapter(adapterFormation);

        db_F = new FormationContract(this);

        ProfileID=getIntent().getIntExtra("id",0);

        GetFromations();
        delete();

    }

    private void ajouterFormation() {
        String libelle = edtAddLibelle.getText().toString();
        String annee = edtAddAnnee.getText().toString();
        String institue = edtAddInstitue.getText().toString();

        if (libelle.isEmpty() || annee.isEmpty() || institue.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

db_F.insertFormation(libelle,annee,institue,ProfileID);
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

    public void GetFromations(){

        Toast.makeText(Formation.this, "GetAllData():OnClickListener",
                Toast.LENGTH_SHORT).show();
        Cursor res = db_F.getAllFormationsById(ProfileID);
        if(res.getCount()==0){

            Toast.makeText(Formation.this, "No Data Was Found",
                    Toast.LENGTH_SHORT).show();
            return;
        }else{

            while (res.moveToNext()){
                formationsList.add(new Template_Formation(res.getString(0),
                        res.getString(1) , res.getString(2)));
            }

            adapterFormation.notifyDataSetChanged();

        }
        res.close();



    }

    public void delete(){

        listViewFormations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Créer une boîte de dialogue de confirmation
                AlertDialog.Builder builder = new AlertDialog.Builder(Formation.this);
                builder.setTitle("Confirmation de suppression");
                builder.setMessage("Voulez-vous vraiment supprimer cette formation ?");

                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Supprimer la formation si l'utilisateur clique sur "Oui"
                        db_F.deleteFormation(formationsList.get(position).getLibelle(),
                                formationsList.get(position).getAnnee(), formationsList.get(position).getInstitue());

                        // Actualiser l'affichage de la liste après la suppression
                        formationsList.remove(position);
                        adapterFormation.notifyDataSetChanged();
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


    public void retour(View view) {

        Intent intent = new Intent();
        intent.putExtra("id", ProfileID);
        setResult(RESULT_OK, intent);
        finish();


    }

    public void vers_diplome(View view) {



    }

}
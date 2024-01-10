package com.example.profile_projet.Adapter;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.profile_projet.R;
import com.example.profile_projet.Template.Template_Formation;

import java.util.ArrayList;

public class Adapter_Formation extends BaseAdapter {
    private Context context;
    private ArrayList<Template_Formation> formationsList;

    public Adapter_Formation(Context context, ArrayList<Template_Formation> formationsList) {
        this.context = context;
        this.formationsList = formationsList;
    }

    @Override
    public int getCount() {
        return formationsList.size();
    }

    @Override
    public Object getItem(int position) {
        return formationsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View v = inflater.inflate(R.layout.custom_formation,null);

        TextView txtLibelle = (TextView) v.findViewById(R.id.txtLibelle);
        TextView txtAnnee = (TextView) v.findViewById(R.id.txtAnnee);
        TextView txtInstitue = (TextView) v.findViewById(R.id.txtInstitue);


        txtLibelle.setText(formationsList.get(position).getLibelle());
        txtAnnee.setText(formationsList.get(position).getAnnee());
        txtInstitue.setText(formationsList.get(position).getInstitue());



        return v;

    }



}

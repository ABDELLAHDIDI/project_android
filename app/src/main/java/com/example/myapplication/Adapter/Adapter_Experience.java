package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.myapplication.R;
import com.example.myapplication.Template.Template_Experience;

import java.util.ArrayList;

public class Adapter_Experience extends BaseAdapter {
    private Context context;
    private ArrayList<Template_Experience> experiencesList;

    public Adapter_Experience(Context context, ArrayList<Template_Experience> experiencesList) {
        this.context = context;
        this.experiencesList = experiencesList;
    }

    @Override
    public int getCount() {
        return experiencesList.size();
    }

    @Override
    public Object getItem(int position) {
        return experiencesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.custom_experience, null);

        TextView txtPoste = v.findViewById(R.id.txtPoste);
        TextView txtEntreprise = v.findViewById(R.id.txtEntreprise);
        TextView txtDuree = v.findViewById(R.id.txtDuree);

        txtPoste.setText(experiencesList.get(position).getPoste());
        txtEntreprise.setText(experiencesList.get(position).getEntreprise());
        txtDuree.setText(experiencesList.get(position).getDuree());

        return v;
    }
}


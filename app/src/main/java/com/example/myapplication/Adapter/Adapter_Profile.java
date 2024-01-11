package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.EleveCVActivity;
import com.example.myapplication.Models.ProfileContract;
import com.example.myapplication.R;

import java.util.ArrayList;

public class Adapter_Profile extends BaseAdapter {
    private Context context;
    private ArrayList<Profile> tabEleve;
    private ProfileContract profileContract;
    private OnDeleteClickListener onDeleteClickListener;

    public Adapter_Profile(Context context, ArrayList<Profile> tabEleve, ProfileContract profileContract, OnDeleteClickListener onDeleteClickListener) {
        this.context = context;
        this.tabEleve = tabEleve;
        this.profileContract = profileContract;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @Override
    public int getCount() {
        return tabEleve.size();
    }

    @Override
    public Object getItem(int position) {
        return tabEleve.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater li = LayoutInflater.from(context);
            v = li.inflate(R.layout.custom_profile, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.txtId = v.findViewById(R.id.txtId);
            viewHolder.txtNom = v.findViewById(R.id.txtNom);
            viewHolder.imgIcone = v.findViewById(R.id.imgIcone);
            viewHolder.deleteButton = v.findViewById(R.id.btnDelete1);
            v.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) v.getTag();

        Profile profile = tabEleve.get(position);
        viewHolder.txtId.setText(profile.getId());
        viewHolder.txtNom.setText(profile.getNom() + " " + profile.getPrenom());

        int resId = context.getResources().getIdentifier(profile.getNomIcone(), "drawable", context.getPackageName());
        viewHolder.imgIcone.setImageResource(resId);

        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile profile = tabEleve.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation");
                builder.setMessage("Voulez-vous supprimer l'élève avec l'ID " + profile.getId() + "?");
                builder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onDeleteClickListener != null) {
                            onDeleteClickListener.onDeleteClick(position);
                        }
                    }
                });
                builder.setNegativeButton("Annuler", null);
                builder.show();
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile profile = tabEleve.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation");
                builder.setMessage("Voulez-vous voir le CV de l'élève avec l'ID " + profile.getId() + "?");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Start the EleveCVActivity and pass the necessary data
                        Intent intent = new Intent(context, EleveCVActivity.class);
                        intent.putExtra("profileId", profile.getId());
                        // Add any additional data you want to pass to the EleveCVActivity
                        context.startActivity(intent);
                    }
                });
                builder.setNegativeButton("Non", null);
                builder.show();
            }
        });

        return v;
    }

    static class ViewHolder {
        TextView txtId;
        TextView txtNom;
        ImageView imgIcone;
        Button deleteButton;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }
}
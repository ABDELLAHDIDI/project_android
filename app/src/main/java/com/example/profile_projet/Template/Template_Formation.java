package com.example.profile_projet.Template;

public class Template_Formation {
    String libelle;
    String annee;
    String institue;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getInstitue() {
        return institue;
    }

    public void setInstitue(String institue) {
        this.institue = institue;
    }

    public Template_Formation(String libelle, String annee, String institue) {
        this.libelle = libelle;
        this.annee = annee;
        this.institue = institue;
    }
}

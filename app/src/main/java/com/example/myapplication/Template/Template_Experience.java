package com.example.myapplication.Template;

public class Template_Experience {
    private String poste;
    private String entreprise;
    private String duree;

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public Template_Experience(String poste, String entreprise, String duree) {
        this.poste = poste;
        this.entreprise = entreprise;
        this.duree = duree;
    }
}

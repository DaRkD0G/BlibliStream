package app.cci.com.bliblistream.Model.Class;

/**
 * Created by DaRk-_-D0G on 03/11/14.
 */
public class MonCompte {

    public int id;
    public String titre,description,lienfilm,lienba,lienImage,tarif,dateSortie;
    public boolean nouveaute;
    public Categorie categorie;


    public MonCompte() {
        this.id = 1;
        this.titre = "test";
        this.description = "test";
        this.lienfilm = "test";
        this.lienba = "test";
        this.lienImage = "test";
        this.tarif = "test";
        this.dateSortie = "test";
        this.nouveaute = false;
        this.categorie = new Categorie();
    }

    public MonCompte(int id,
                     String titre,
                     String description,
                     String lienfilm,
                     String lienba,
                     String lienImage,
                     String tarif,
                     String dateSortie,
                     Boolean nouveaute,
                     Categorie unCat) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.lienfilm = lienfilm;
        this.lienba = lienba;
        this.lienImage = lienImage;
        this.tarif = tarif;
        this.dateSortie = dateSortie;
        this.nouveaute = nouveaute;
        this.categorie = unCat;
    }


}

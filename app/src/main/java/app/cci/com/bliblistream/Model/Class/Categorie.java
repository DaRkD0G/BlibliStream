package app.cci.com.bliblistream.Model.Class;

/**
 * Created by DaRk-_-D0G on 03/11/14.
 */
public class Categorie {

    public int id;
    public String titre,description,lienImage;


    public Categorie() {
        this.id = 1;
        this.titre = "test";
        this.description = "test";
        this.lienImage = "http://fs04.androidpit.info/a/8c/1f/duolingo-learn-languages-free-8c1fb0-w144.png";


    }

    public Categorie(int id, String titre, String description) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        // TODO IMPORTE IMG BDD JSON
        this.lienImage = "http://fs04.androidpit.info/a/8c/1f/duolingo-learn-languages-free-8c1fb0-w144.png";

    }


}

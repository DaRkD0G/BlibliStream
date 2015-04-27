package app.cci.com.bliblistream.Model.StrucData;

/**
 * Created by DaRk-_-D0G on 03/11/14.
 */
public class Categorie {
    private Integer id;
    private String titre, description, lienImage;

    public Categorie(int id, String titre, String description, String lienImage) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.lienImage = lienImage;
    }
    /* ################################################################## */
    /*                          GETTER SETTER                             */
    /* ################################################################## */

    public Integer getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getLienImage() {
        return lienImage;
    }

    /* ################################################################## */
    /*                          AUTRE METHOD                              */
    /* ################################################################## */
    @Override
    public String toString() {
        return super.toString();
    }
}

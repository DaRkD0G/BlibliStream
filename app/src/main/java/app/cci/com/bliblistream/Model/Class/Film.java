package app.cci.com.bliblistream.Model.Class;

/**
 * Created by DaRk-_-D0G on 03/11/14.
 */
public class Film {

    private int id;
    private String nom;
    private String description;
    private String sousDescription;
    private String imgLink;
    private String linkFilm;

    public Film() {
        this.id = 1;
        this.nom = "Test";
        this.sousDescription = "SoustestDescription";
        this.description = "Description";
        this.linkFilm = "httpl//ASS.fr";
        this.imgLink = "Http//IMG";
    }
    public Film(String test) {
        this.nom = "ARON MAN";
        this.sousDescription = "Sous Description du film !";
        this.description = "Petit Description";
    }
    public Film(int id, String nom, String sousDescription, String description, String linkFilm, String imgLink) {
        this.id = id;
        this.nom = nom;
        this.sousDescription = sousDescription;
        this.description = description;
        this.linkFilm = linkFilm;
        this.imgLink = imgLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getSousDescription() {
        return sousDescription;
    }

    public void setSousDescription(String sousDescription) {
        this.sousDescription = sousDescription;
    }

    public String getLinkFilm() {
        return linkFilm;
    }

    public void setLinkFilm(String linkFilm) {
        this.linkFilm = linkFilm;
    }
}

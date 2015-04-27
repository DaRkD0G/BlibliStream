package app.cci.com.bliblistream.Model.StrucData;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by DaRk-_-D0G on 03/11/14.
 */
public class Film {
    private Integer id, tarif;
    private ArrayList<Integer> categorie;
    private String titre, description, lienFilm, lienBa, lienImage, dateSortie;
    private boolean nouveaute;

    /**
     * Init
     */
    public Film() {
    }

    /**
     * Init
     * @param id Integer
     * @param titre String
     * @param description String
     * @param lienBa String
     * @param lienImage String
     * @param tarif Integer
     * @param dateSortie String
     * @param categories String
     */
    public Film(int id,
                String titre,
                String description,
                String lienBa,
                String lienImage,
                Integer tarif,
                String dateSortie,
                ArrayList<Integer> categories) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.lienBa = lienBa;
        this.lienImage = lienImage;
        this.tarif = tarif;
        this.dateSortie = dateSortie;
        this.categorie = categories;

        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        if (dateSortie.startsWith(year.toString())) {
            this.nouveaute = true;
        }
    }

    /* ################################################################## */
    /*                          GETTER                                    */
    /* ################################################################## */

    public Integer getId() {
        return id;
    }

    public Integer getTarif() {
        return tarif;
    }

    public ArrayList<Integer> getCategorie() {
        return categorie;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getLienFilm() {
        return lienFilm;
    }

    public String getLienBa() {
        return lienBa;
    }

    public String getLienImage() {
        return lienImage;
    }

    public boolean isNouveaute() {
        return nouveaute;
    }

    public String getDateSortie() {
        return dateSortie;
    }

    /* ################################################################## */
    /*                          AUTRE METHOD                              */
    /* ################################################################## */
    @Override
    public String toString() {
        return super.toString();
    }
}

package app.cci.com.bliblistream.Model.StrucData;

import java.util.ArrayList;

/**
 * Created by DaRk-_-D0G on 03/11/14.
 */
public class CollectionFilm {
    private static CollectionFilm collectionFilmInstance = null;
    private ArrayList<Film> collectionFilm;

    /**
     * Instance Singleton Collection films
     * @return Instance CollectionFilm
     */
    public static CollectionFilm getInstance() {
        if (collectionFilmInstance == null) {
            collectionFilmInstance = new CollectionFilm();
            collectionFilmInstance.collectionFilm = new ArrayList<Film>();
        }
        return collectionFilmInstance;
    }

    /**
     * Obtenir collection de film
     * @return ArrayList<Film>
     */
    public static ArrayList<Film> getCollectionFilm() {
        CollectionFilm instance = CollectionFilm.getInstance();
        return instance.collectionFilm;
    }

    /**
     * Reset la collection de films
     */
    public static void resetCollectionFilms() {
        CollectionFilm instance = CollectionFilm.getInstance();
        instance.collectionFilm.clear();
    }
    /* ################################################################## */
    /*                          AUTRE METHOD                              */
    /* ################################################################## */
    @Override
    public String toString() {
        return super.toString();
    }
}

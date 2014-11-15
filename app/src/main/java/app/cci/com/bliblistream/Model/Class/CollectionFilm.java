package app.cci.com.bliblistream.Model.Class;

import java.util.ArrayList;

/**
 * Created by DaRk-_-D0G on 03/11/14.
 */
public class CollectionFilm {
    private ArrayList<Film> collectionFilm;

    public CollectionFilm() {
        collectionFilm = new ArrayList<Film>();
        collectionFilm.add(new Film());
        collectionFilm.add(new Film());

    }
}

package app.cci.com.bliblistream.Model.StrucData;

import java.util.ArrayList;

/**
 * Created by DaRk-_-D0G on 21/04/15.
 */
public class CollectionCategorie {
    private static CollectionCategorie collectionCategorie = null;
    private ArrayList<Categorie> arraycollectionCategorie;

    public static CollectionCategorie getInstance() {
        if (collectionCategorie == null) {
            collectionCategorie = new CollectionCategorie();
            collectionCategorie.arraycollectionCategorie = new ArrayList<Categorie>();

        }
        return collectionCategorie;
    }

    public static ArrayList<Categorie> getCollectionCategorie() {
        CollectionCategorie instance = CollectionCategorie.getInstance();
        return instance.arraycollectionCategorie;
    }

    public static void resetCollectionCategorie() {
        CollectionCategorie instance = CollectionCategorie.getInstance();
        instance.arraycollectionCategorie.clear();
    }

    /* ################################################################## */
    /*                          AUTRE METHOD                              */
    /* ################################################################## */
    public static String toStringDetail() {
        return getInstance().toString();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

package app.cci.com.bliblistream.Model.StrucData;

import java.util.ArrayList;

/**
 * Class User Singleton
 */
public class User {

    /**
     * Singleton Instance
     */
    private static User userInstance = null;
    /**
     * Attributs
     */
    private static String nom, prenom, password, lienFilmVisionne;
    private static Integer id, jeton;
    private static Film filmChoisi;
    private static ArrayList<Integer> location;
    private static int filtreIdCat = -1;

    /**
     * Init
     */
    public User() {
    }

    public static User getInstance() {
        if (userInstance == null) {
            userInstance = new User();
        }
        return userInstance;
    }

    /* ################################################################## */
    /*                          GETTER SETTER                             */
    /* ################################################################## */

    public static String getLienFilmVisionne() {
        return "http://clips.vorwaerts-gmbh.de/VfE_html5.mp4";//lienFilmVisionne;
    }

    public static void setLienFilmVisionne(String lienFilmVisionne) {
        User.lienFilmVisionne = lienFilmVisionne;
    }

    /**
     * Obtenir l'id filtre
     * @return int
     */
    public static int getFiltreIdCat() {
        return User.filtreIdCat;
    }

    /**
     * Set un Filtre
     * @param filtreIdCat Int
     */
    public static void setFiltreIdCat(int filtreIdCat) {
        User.filtreIdCat = filtreIdCat;
    }

    public static Film getFilmChoisi() {
        return User.filmChoisi;
    }

    public static void setFilmChoisi(Film filmChoisi) {
        User.filmChoisi = filmChoisi;
    }

    public static Integer getId() {
        return User.id;
    }

    public static void setId(Integer id) {
        User.id = id;
    }

    public static String getNom() {
        return User.nom;
    }

    public static void setNom(String name) {
        User.nom = name;
    }

    public static String getPrenom() {
        return User.prenom;
    }

    public static void setPrenom(String prenom) {
        User.prenom = prenom;
    }

    public static ArrayList<Integer> getLocation() {
        return User.location;
    }

    public static void setLocation(ArrayList<Integer> location) {
        User.location = location;
    }

    public static String getPassword() {
        return User.password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

    public static Integer getJeton() {
        return User.jeton;
    }

    public static void setJeton(Integer jeton) {
        User.jeton = jeton;
    }

    public static String toStringDetail() {
        return getInstance().toString();
    }

    /* ################################################################## */
    /*                          AUTRE METHOD                              */
    /* ################################################################## */
    @Override
    public String toString() {
        return super.toString();
    }

}

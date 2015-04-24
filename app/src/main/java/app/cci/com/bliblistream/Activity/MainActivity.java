package app.cci.com.bliblistream.Activity;

import android.app.Activity;
import android.os.Bundle;

import app.cci.com.bliblistream.Controler.ControlerActivity.ControlerMainActivity;
import app.cci.com.bliblistream.Model.DownloadData.ImageData.ImagesCache;
import app.cci.com.bliblistream.Model.DownloadData.JsonData.JsonFeetcher.MyHttpClientListCategorie;
import app.cci.com.bliblistream.Model.DownloadData.JsonData.JsonFeetcher.MyHttpClientListFilm;
import app.cci.com.bliblistream.Model.StrucData.CollectionCategorie;
import app.cci.com.bliblistream.Model.StrucData.CollectionFilm;
import app.cci.com.bliblistream.R;


/**
 * Class de l\u0027activite principale de l\u0027application, Herite de Activity
 */
public class MainActivity extends Activity {

    private ControlerMainActivity controlerMainActivity;
    /**
     * Lancement de l'application
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Initialisation cache image */
        ImagesCache cache = ImagesCache.getInstance();
        cache.initializeCache();
        /* initialisation de lattribut controleur */
        this.controlerMainActivity = new ControlerMainActivity(this);
        /* Init Collection */
        CollectionFilm.getInstance();
        CollectionCategorie.getInstance();
        /* Get Data Json Categorie et films */
        new MyHttpClientListFilm().execute();
        new MyHttpClientListCategorie().execute();
    }
    /**
     * Restauration de l'application
     */
    @Override
    protected void onResume() {
        super.onResume();

        if (this.controlerMainActivity.getListener() == null) {
            this.controlerMainActivity.loadViewAndSetListener(R.layout.view_login);
        }
    }

    /**
     * Mise en pause de l'application
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Fermeture l'application
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() { this.controlerMainActivity.LoadLastViewAndSetListener(); }
}



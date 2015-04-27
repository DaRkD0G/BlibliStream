package app.cci.com.bliblistream.Activity;

import android.app.Activity;
import android.os.Bundle;

import app.cci.com.bliblistream.Controler.ControlerActivity.ControlerMainActivity;
import app.cci.com.bliblistream.Model.DownloadData.ImageData.ImagesCache;
import app.cci.com.bliblistream.Model.DownloadData.JsonData.JsonFeetcher.MyHttpClientListCategorie;
import app.cci.com.bliblistream.Model.DownloadData.JsonData.JsonFeetcher.MyHttpClientListFilm;
import app.cci.com.bliblistream.Model.StrucData.CollectionCategorie;
import app.cci.com.bliblistream.Model.StrucData.CollectionFilm;
import app.cci.com.bliblistream.Outil.ToolKit;
import app.cci.com.bliblistream.R;


/**
 * Class de l\u0027activite principale de l\u0027application, Herite de Activity
 */
public class MainActivity extends Activity {
    // Instance
    private ControlerMainActivity controlerMainActivity;
    // Thread chargement Date
    private Thread threadLoadData = new Thread(new Runnable() {
        @Override
        public void run() {
            while (!ToolKit.isConnectionOK(getApplicationContext())) {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                }
            }
            MyHttpClientListFilm.executeReq();
            MyHttpClientListCategorie.executeReq();

        }
    });

    /**
     * Lancement de l'application
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Init Instance singleton */
        ImagesCache.getInstance().initializeCache();    //Cache Image
        CollectionFilm.getInstance();                   //Collection Films
        CollectionCategorie.getInstance();              //Collection Categories
        MyHttpClientListFilm.getInstance();             //Json Data Films
        MyHttpClientListCategorie.getInstance();        //Json Data Collection Films
        /* Load data Async */
        this.threadLoadData.start();
        /* initialisation de lattribut controleur */
        this.controlerMainActivity = new ControlerMainActivity(this);


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

    /**
     * Click button back
     */
    @Override
    public void onBackPressed() {
        this.controlerMainActivity.LoadLastViewAndSetListener();
    }
}



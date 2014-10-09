package app.cci.com.bliblistream;

import android.app.Activity;
import android.os.Bundle;


import app.cci.com.bliblistream.Controleur.Control;



/**
 * Class de l\u0027activite principale de l\u0027application, Herite de Activity
 *
 */
public class MainActivity extends Activity {

    //Le controleur de l application
    private Control control;

    /**
     * Lancement de l'application
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialisation de lattribut controleur
        this.control =  new Control(this);
    }

    /**
     * Restauration de l'application
     *
     */
    @Override
    protected void onResume() {
        super.onResume();

        //TODO Ici faire un check up avec le control pour savoir on ce situe l application
        this.control.loadViewAndSetListener(R.layout.view_login);
    }
    /**
     * Mise en pause de l'application
     *
     */
    @Override
    protected void onPause() {
        super.onPause();
    }
    /**
     * Fermeture l'application
     *
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}



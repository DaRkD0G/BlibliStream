package app.cci.com.bliblistream;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Animatable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;


import java.util.ArrayList;

import app.cci.com.bliblistream.Controleur.Control;
import app.cci.com.bliblistream.Controleur.ListenerView.ListenerViewAccueil;
import app.cci.com.bliblistream.Controleur.ListenerView.ListenerViewListFilm;
import app.cci.com.bliblistream.Controleur.ListenerView.ListenerViewLogin;
import app.cci.com.bliblistream.Outil.ToolKit;


/**
 * Class de l\u0027activite principale de l\u0027application, Herite de Activity
 */
public class MainActivity extends Activity {

    //Le controleur de l application
    private Control control;


    /**
     * Lancement de l'application
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialisation de lattribut controleur
        this.control = new Control(this);


    }

    /**
     * Restauration de l'application
     */
    @Override
    protected void onResume() {
        super.onResume();

        if(this.control.getListener() == null) {
            this.control.loadViewAndSetListener(R.layout.view_login);
        }
        /* TODO TEMP */



        //TODO Ici faire un check up avec le control pour savoir on ce situe l application
       /* this.animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                );

        // set animation listener
        View btn = (View) findViewById(R.id.layout_login);
        btn.startAnimation(animFadein);*/

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
    public void onBackPressed() {
    //    ToolKit.log("OK");

        this.control.LoadLastViewAndSetListener();


    }
    /**
     * getSize and
     */
    /******/

}



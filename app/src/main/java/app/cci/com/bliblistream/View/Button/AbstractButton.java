package app.cci.com.bliblistream.View.Button;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import app.cci.com.bliblistream.Outil.ToolKit;

/**
 * Class abtrait de l'ecouteur des buttons, Extends de View et implemante OnClickListener
 *
 * @author DaRk-_-D0G on 06/10/2014.
 */
public abstract class AbstractButton extends View implements View.OnClickListener {
    /**
     * Constructeur abstract ListenerButton
     *
     * @param context       Context
     * @param inArrayButton ArrayList<Button>
     */
    public AbstractButton(Context context, ArrayList<Button> inArrayButton) {
        super(context);

        ToolKit.log("Class Init --> ListenerButton");
        /* le tableau de button passer en paramettre ajouter a l ecouteur */
        try {
            for (Button unButton : inArrayButton) {
                unButton.setOnClickListener(this);
            }
        } catch (NullPointerException e) {
            ToolKit.log("ERREUR DANS INITIALISATION DAJOUT DE BOUTON, Le bouton demandander nexiste pas sur cette View");
        }

        this.setOnClickListener(this);


    }


    /**
     * Ecouteur du clic sur un bouton, ici elle est sucharger par les listenerView
     *
     * @param v View
     */
    @Override
    public void onClick(View v) { /* Methode surchager */ }
}


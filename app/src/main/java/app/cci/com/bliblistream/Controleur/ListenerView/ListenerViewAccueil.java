package app.cci.com.bliblistream.Controleur.ListenerView;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import app.cci.com.bliblistream.R;
import app.cci.com.bliblistream.Controleur.Control;
import app.cci.com.bliblistream.Model.AbstractClass.AbstractButton;
import app.cci.com.bliblistream.Outil.ToolKit;

/**
 *
 * Ecouteur de la View Acceuil
 * @author DaRk-_-D0G on 30/09/2014.
 */
public class ListenerViewAccueil {
    private Control control;
    //ArrayList de button de la vue Acceuil
    private ArrayList<Button> arrayButton;

    /**
     * Constructeur
     * @param inControl Control
     */
    public ListenerViewAccueil(Control inControl) {

        ToolKit.log("Class init  --> ListenerViewAccueil");
        this.control = inControl;
        /* ici on ajout tout les buttons de la vue */
        this.arrayButton = new ArrayList<Button>();
        /* Ajoute des boutons de la vue à l attribut */
        this.arrayButton.add((Button) this.control.getActivity().findViewById(R.id.button_nouveaute));
        this.arrayButton.add((Button) this.control.getActivity().findViewById(R.id.button_categorie));

        /* On redefinit l ecouteur du clic sur les boutons */
        new AbstractButton(this.control.getActivity().getBaseContext(), this.arrayButton) {
            /**
             * Methode Sucharge de la class AbstractButton pour redefinir les boutons de la vue
             * @param v View
             */
            @Override
            public void onClick(View v) {
                super.onClick(v);

                switch (v.getId()) {
                    case R.id.button_nouveaute:
                        ToolKit.showMessage(-1,"Bouton Nouveauté ou mot de passe erroné",control.getActivity(),-1,-1);

                        ToolKit.log("clique button_nouveaute");
                        break;

                    case R.id.button_categorie:
                        ToolKit.log("Clique  button_categorie");
                        control.loadViewAndSetListener(R.layout.view_listfilm);
                        break;
                }
            }
        };
    }
}


package app.cci.com.bliblistream.Controleur.ListenerView;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import app.cci.com.bliblistream.Model.CustomClass.CustomEditText;
import app.cci.com.bliblistream.R;
import app.cci.com.bliblistream.Controleur.Control;
import app.cci.com.bliblistream.Model.AbstractClass.AbstractButton;
import app.cci.com.bliblistream.Outil.ToolKit;

/**
 * Ecouteur de la View Acceuil
 * @author DaRk-_-D0G  on 30/09/2014.
 *
 */
public class ListenerViewLogin {
    private Control control;
    private ArrayList<Button> arrayButton;
    private ArrayList<CustomEditText> arrayEditText;

    /**
     * Constructeur
     * @param inControl Control
     */
    public ListenerViewLogin(Control inControl) {

        ToolKit.log("Class init  --> ListenerViewLogin");
        this.control = inControl;
        //TODO ici on ajout tout les buttons de la VIEW
        this.arrayButton = new ArrayList<Button>();
        this.arrayEditText = new ArrayList<CustomEditText>();

        /**
         * 0 = Bouton Connection
         * 1 = Bouton Se connect
         */
        this.arrayButton.add((Button) this.control.getActivity().findViewById(R.id.button_connection));
        this.arrayButton.add((Button) this.control.getActivity().findViewById(R.id.button_se_connect));

        /**
         * 0 = EditTextLogin
         * 1 = EditTextPassWord
         */
        this.arrayEditText.add((CustomEditText) control.getActivity().findViewById(R.id.editTextLogin));
        this.arrayEditText.add((CustomEditText) control.getActivity().findViewById(R.id.ediTextPassword));

        /* On redefinit l ecouteur du clic de la sur les boutons passer en parrametre --> this.arrayButton  */
        new AbstractButton(this.control.getActivity().getBaseContext(), this.arrayButton) {
            /**
             * Methode Sucharge de la class AbstractButton pour redefinir les boutons de la vue
             * @param v View
             */
            @Override
            public void onClick(View v) {
                super.onClick(v);
                switch (v.getId()) {

                    /* Clic sur le bouton connection */
                    case R.id.button_connection:
                        ToolKit.log("Clique sur BTN --> Login");
                        ((RelativeLayout) control.getActivity().findViewById(R.id.linearLayout_acceuil)).setVisibility(INVISIBLE);
                        ((LinearLayout) control.getActivity().findViewById(R.id.linearLayout_fond_trans_login)).setVisibility(VISIBLE);
                        ((LinearLayout) control.getActivity().findViewById(R.id.linearLayout_fond_trans_login_contenu)).setVisibility(VISIBLE);
                        break;

                    /* clic sur le bouton se connecter */
                    case R.id.button_se_connect:
                        ToolKit.log("Clique sur BTN --> Check Login");
                        /* TODO Exemple Pour checked les login est mot de passe */
                        ToolKit.showMessage("Je sais ton SPeudo et MDP"," c'est -->"+arrayEditText.get(0).getText()+"--"+arrayEditText.get(1).getText(),control.getActivity());

                        control.loadViewAndSetListener(R.layout.view_accueil);
                        break;
                }
            }
        };
    }
}


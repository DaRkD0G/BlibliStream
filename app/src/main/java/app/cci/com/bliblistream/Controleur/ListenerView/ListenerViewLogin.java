package app.cci.com.bliblistream.Controleur.ListenerView;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

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
                        //selectionne les vues pour les anime au changement

                        View view1 = control.getActivity().findViewById(R.id.linearLayout_fond_trans_login);
                        View view2 = control.getActivity().findViewById(R.id.linearLayout_fond_trans_login_contenu);
                        (control.getActivity().findViewById(R.id.button_connection)).setVisibility(INVISIBLE);

                        //animation
                        control.animationThis(R.anim.animation_translateenentredroit,view1);
                        control.animationThis(R.anim.animation_translateenentredroit,view2);




                        break;

                    /* clic sur le bouton se connecter */
                    case R.id.button_se_connect:
                        ToolKit.log("Clique sur BTN --> Check Login");
                        /* TODO Exemple Pour checked les login est mot de passe */
                       // ToolKit.showMessage("Je sais ton SPeudo et MDP"," c'est -->"+arrayEditText.get(0).getText()+"--"+arrayEditText.get(1).getText(),control.getActivity());
                        if(arrayEditText.get(0).getText().toString().equals("speudo")   && arrayEditText.get(1).getText().toString().equals("MDP")){
                            control.loadViewAndSetListener(R.layout.view_accueil);
                        } else {
                            /*int[][] sizeScreen = ToolKit.sizeScreen(control.getActivity());
                            int taileTroisQuart = sizeScreen[4][1] + (sizeScreen[2][1]);
                            ToolKit.showMessage(-1,"Identifiant ou mot de passe erroné",control.getActivity(),0,taileTroisQuart);*/
                             animateErrorLogin();
                        }
                        break;
                }
            }
        };
    }

    private boolean activeAnime = false;
    /**
     * Animation du login quand erreur
     */
    public void animateErrorLogin() {
        if(!activeAnime) {
            activeAnime = true;
            final TextView text = (TextView)control.getActivity().findViewById(R.id.layout_login_mauvais_identifiant);
            control.animationThis(R.anim.animation_fadout,text);
            //On creer un timer qui execute un fonction aprés les seconde
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            //execute le code apres le temps
                            //creation dun thrad sur la vue de lutilisation
                            control.getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    /* code pour rendre invisible le btn aprés execution */
                                    control.animationThis(R.anim.animation_fadin,text);
                                    text.setVisibility(View.INVISIBLE);
                                    activeAnime = false;
                                }
                            });
                        }
                    },
                    2000
            );
            text.setVisibility(View.VISIBLE);
            text.setText("Identifiant ou mot de passe erroné");

        }
    }

}


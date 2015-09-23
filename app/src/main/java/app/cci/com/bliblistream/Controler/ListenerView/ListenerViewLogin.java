package app.cci.com.bliblistream.Controler.ListenerView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import app.cci.com.bliblistream.Controler.ControlerActivity.ControlerMainActivity;
import app.cci.com.bliblistream.Model.DownloadData.JsonData.JsonFeetcher.MyHttpClientLogin;
import app.cci.com.bliblistream.Model.StrucData.CollectionCategorie;
import app.cci.com.bliblistream.Model.StrucData.CollectionFilm;
import app.cci.com.bliblistream.Model.StrucData.User;
import app.cci.com.bliblistream.Outil.ToolKit;
import app.cci.com.bliblistream.R;
import app.cci.com.bliblistream.View.Button.AbstractButton;
import app.cci.com.bliblistream.View.EditText.CustomEditText;

/**
 * Ecouteur de la View Acceuil
 *
 * @author DaRk-_-D0G  on 30/09/2014.
 */
public class ListenerViewLogin {
    private ControlerMainActivity controlerMainActivity;
    private ArrayList<Button> arrayButton;
    private ArrayList<CustomEditText> arrayEditText;
    private View view3;
    private boolean activeAnime = false;


    /**
     * Constructeur
     *
     * @param inControlerMainActivity Control
     */
    public ListenerViewLogin(ControlerMainActivity inControlerMainActivity) {
        this.controlerMainActivity = inControlerMainActivity;

        /* Charge Element pour attribut */
        loadButtonListener();

        /* On redefinit l ecouteur du clic de la sur les boutons passer en parrametre --> this.arrayButton  */
        new AbstractButton(this.controlerMainActivity.getActivity().getBaseContext(), this.arrayButton) {
            /**
             * Methode Sucharge de la class AbstractButton pour redefinir les boutons de la vue
             * @param v View
             */
            @Override
            public void onClick(View v) {
                super.onClick(v);
                switch (v.getId()) {

                    /* Clic sur le bouton execute */
                    case R.id.button_connection:
                        checkBTNConnection();
                        break;

                    /* clic sur le bouton se connecter */
                    case R.id.button_se_connect:
                        checkLoginLoad();
                        break;
                }
            }
        };
    }
/*************************************************************************************************/
    // Controleur / loadButtonListener
/*************************************************************************************************/

    /**
     * Charger les elements pour les attributs de la class
     */
    public void loadButtonListener() {
        ToolKit.log("Class init  --> ListenerViewLogin");

        //TODO ici on ajout tout les buttons de la VIEW
        this.arrayButton = new ArrayList<Button>();
        this.arrayEditText = new ArrayList<CustomEditText>();

        /**
         * 0 = Bouton Connection
         * 1 = Bouton Se connect
         */
        this.arrayButton.add((Button) this.controlerMainActivity.getActivity().findViewById(R.id.button_connection));
        this.arrayButton.add((Button) this.controlerMainActivity.getActivity().findViewById(R.id.button_se_connect));

        /**
         * 0 = EditTextLogin
         * 1 = EditTextPassWord
         */
        this.arrayEditText.add((CustomEditText) controlerMainActivity.getActivity().findViewById(R.id.editTextLogin));
        this.arrayEditText.add((CustomEditText) controlerMainActivity.getActivity().findViewById(R.id.ediTextPassword));
    }
/*************************************************************************************************/
    // button_connection / checkBTNConnection

    /**
     * *********************************************************************************************
     */
    public void checkBTNConnection() {
        ToolKit.log("Clique sur BTN --> Login");
        //selectionne les vues pour les anime au changement

        View view1 = controlerMainActivity.getActivity().findViewById(R.id.linearLayout_fond_trans_login);
        View view2 = controlerMainActivity.getActivity().findViewById(R.id.linearLayout_fond_trans_login_contenu);
        (controlerMainActivity.getActivity().findViewById(R.id.button_connection)).setVisibility(View.INVISIBLE);

        ToolKit.animationThis(R.anim.animation_translateenentredroit, view1, controlerMainActivity.getActivity().getBaseContext());
        ToolKit.animationThis(R.anim.animation_translateenentredroit, view2, controlerMainActivity.getActivity().getBaseContext());

    }
/*************************************************************************************************/
    // Check Login / button_se_connect / checkLoginLoad
/*************************************************************************************************/

    /**
     * Check le login avec la connection au serveur
     */
    public void checkLoginLoad() {
        if (ToolKit.isConnectionOK(this.controlerMainActivity.getActivity())) {
            String name = arrayEditText.get(0).getText().toString();
            String pass = arrayEditText.get(1).getText().toString();

            if (name.equals("") || pass.equals("") || name.equals("speudo") || pass.equals("MDP")) {
                animateErrorLogin("Merci de completer les champs login et mot de passe.");
            } else {
                User.setNom(name);
                User.setPassword(pass);
                try {
                    MyHttpClientLogin.getInstance().execute();
                    this.checkValidationAndActiveTranstion();
                } catch (Exception e) {
                }
            }
        } else {
            this.animateErrorLogin("Pas de connection à Internet.");
        }
    }


    /**
     * Check si la connection et valide et attend le retour du webservice
     */
    private void checkValidationAndActiveTranstion() {
        if (ToolKit.isConnectionOK(this.controlerMainActivity.getActivity())) {
            setEnableElement(false);
            view3 = controlerMainActivity.getActivity().findViewById(R.id.linearLayout_fond_trans_login_chargement);
            view3.setVisibility(View.VISIBLE);

            ToolKit.animationThis(R.anim.animation_fadout, view3, controlerMainActivity.getActivity().getBaseContext());

            this.checkLoginInThread();
        } else {
            this.animateErrorLogin("Pas de connection à Internet.");
        }
    }

    /**
     * Check le retour du myHttpClient de la validation du login
     * Dans un Thread et appel de l'animation qui suit
     *
     * @param MyHttpClientLogin myHttpClient
     */
    private void checkLoginInThread() {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                /* Attente de la reponse */
                /* Depassement delais reponse */
                long start = System.currentTimeMillis();
                while (!MyHttpClientLogin.ifLoadFinished() && System.currentTimeMillis() < (start + 10000)) {

                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                    }
                }
                final Boolean validationLogin = MyHttpClientLogin.loadJsonData();
                /* Animation du login */

                if (validationLogin) {
                    controlerMainActivity.getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                        /* Animation fonction login bon ou pas */
                            if (CollectionFilm.getCollectionFilm().size() > 0 && CollectionCategorie.getCollectionCategorie().size() > 0) {
                                checkUiLoad(true);
                            } else {
                                setEnableElement(true);
                                animateErrorLogin("Verifier votre connection internet.");
                                view3.setVisibility(View.INVISIBLE);
                                ToolKit.animationThis(R.anim.animation_fadout, view3, controlerMainActivity.getActivity().getBaseContext());
                            }
                        }
                    });
                } else {
                    controlerMainActivity.getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            checkUiLoad(false);
                        }
                    });
                }

                controlerMainActivity.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        /* Animation fonction login bon ou pas */
                        if (CollectionFilm.getCollectionFilm().size() > 0 && CollectionCategorie.getCollectionCategorie().size() > 0) {
                            checkUiLoad(validationLogin);
                        } else {
                            checkUiLoad(false);
                        }
                    }
                });
            }
        });
        thread.start();
    }

    /**
     * Si la connexion Valide alors animation sinon retour du erreur
     */
    private void checkUiLoad(Boolean validationLogin) {
        if (validationLogin) {
            controlerMainActivity.loadViewAndSetListener(R.layout.view_accueil);
        } else {
            setEnableElement(true);
            animateErrorLogin("Identifiant ou mot de passe erroner.");
            view3.setVisibility(View.INVISIBLE);
            ToolKit.animationThis(R.anim.animation_fadout, view3, controlerMainActivity.getActivity().getBaseContext());
        }
    }
/*************************************************************************************************/
    // Element View
/*************************************************************************************************/
    /**
     * Animation du login quand erreur
     */
    public void animateErrorLogin(String textError) {
        if (!activeAnime) {
            activeAnime = true;
            final TextView text = (TextView) controlerMainActivity.getActivity().findViewById(R.id.layout_login_mauvais_identifiant);
            ToolKit.animationThis(R.anim.animation_fadout, text, controlerMainActivity.getActivity().getBaseContext());
            // control.animationThis(R.anim.animation_fadout, text);
            //On creer un timer qui execute un fonction aprés les seconde
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            //execute le code apres le temps
                            //creation dun thrad sur la vue de lutilisation
                            controlerMainActivity.getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    /* code pour rendre invisible le btn aprés execution */
                                    ToolKit.animationThis(R.anim.animation_fadin, text, controlerMainActivity.getActivity().getBaseContext());
                                    // control.animationThis(R.anim.animation_fadin, text);
                                    text.setVisibility(View.INVISIBLE);
                                    activeAnime = false;
                                }
                            });
                        }
                    },
                    2000
            );
            text.setVisibility(View.VISIBLE);
            text.setText(textError);

        }
    }

    /**
     * Enable Element View
     *
     * @param uBoolean Activation ou Desactivation element
     */
    public void setEnableElement(boolean uBoolean) {
        this.arrayButton.get(0).setEnabled(uBoolean);
        this.arrayButton.get(1).setEnabled(uBoolean);
        this.arrayEditText.get(0).setEnabled(uBoolean);
        this.arrayEditText.get(1).setEnabled(uBoolean);
    }
}


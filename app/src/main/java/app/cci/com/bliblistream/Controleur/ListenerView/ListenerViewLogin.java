package app.cci.com.bliblistream.Controleur.ListenerView;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.cci.com.bliblistream.Model.Class.MCrypt;
import app.cci.com.bliblistream.Model.Class.User;
import app.cci.com.bliblistream.Model.CustomClass.CustomEditText;
import app.cci.com.bliblistream.Model.AbstractClass.AbstractMyHttpClient;

import app.cci.com.bliblistream.Model.Service.MyHttpClientConnection;

import app.cci.com.bliblistream.R;
import app.cci.com.bliblistream.Controleur.Control;
import app.cci.com.bliblistream.Model.AbstractClass.AbstractButton;
import app.cci.com.bliblistream.Outil.ToolKit;

/**
 * Ecouteur de la View Acceuil
 *
 * @author DaRk-_-D0G  on 30/09/2014.
 */
public class ListenerViewLogin {
    private Control control;
    private ArrayList<Button> arrayButton;
    private ArrayList<CustomEditText> arrayEditText;
    private View view3;
    private boolean activeAnime = false;
    private MyHttpClientLogin myHttpClientLogin;

    /**
     * Constructeur
     *
     * @param inControl Control
     */
    public ListenerViewLogin(Control inControl) {
        this.control = inControl;

        /* Charge Element pour attribut */
        loadButtonListener();

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
    public void loadButtonListener(){
        ToolKit.log("Class init  --> ListenerViewLogin");

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
    }
/*************************************************************************************************/
    // button_connection / checkBTNConnection
/*************************************************************************************************/
    public void checkBTNConnection() {
        ToolKit.log("Clique sur BTN --> Login");
        //selectionne les vues pour les anime au changement

        View view1 = control.getActivity().findViewById(R.id.linearLayout_fond_trans_login);
        View view2 = control.getActivity().findViewById(R.id.linearLayout_fond_trans_login_contenu);
        (control.getActivity().findViewById(R.id.button_connection)).setVisibility(View.INVISIBLE);

        //animation
        ToolKit.animationThis(R.anim.animation_translateenentredroit,view1,control.getActivity().getBaseContext());
        //control.animationThis(R.anim.animation_translateenentredroit, view1);
        ToolKit.animationThis(R.anim.animation_translateenentredroit,view2,control.getActivity().getBaseContext());
        //control.animationThis(R.anim.animation_translateenentredroit, view2);
    }
/*************************************************************************************************/
    // Check Login / button_se_connect / checkLoginLoad
/*************************************************************************************************/

    /**
     * Check le login avec la connection au serveur
     */
    public void checkLoginLoad() {
        ToolKit.log("Clique sur BTN --> Check Login");
        String name = arrayEditText.get(0).getText().toString();
        String pass = arrayEditText.get(1).getText().toString();

        if (name.equals("")  || pass.equals("") || name.equals("speudo") || pass.equals("MDP")) {
            animateErrorLogin("Merci de completer les champs login et mot de passe.");
        } else {
            control.setUser(new User(name,pass));
            activeConnectionAndPassParam(control.user);
            checkValidationAndActiveTranstion();
        }
    }

    /**
     * Instancie un connection HttpClient et pass les parametres de connection
     * @param uUser Un User
     */
    private void activeConnectionAndPassParam(User uUser) {
        this.myHttpClientLogin = new MyHttpClientLogin(
                control.getActivity()

        );
        this.myHttpClientLogin.setParams(
                "http://172.16.1.111:8888/bibli/loginpass.php",
                AbstractMyHttpClient.TYPEDEMANDE.GET_SET_URL_PARAM,
                uUser
        );
    }

    /**
     *  Check si la connection et valide et attend le retour du webservice
     */
    private void checkValidationAndActiveTranstion() {
        if(this.myHttpClientLogin.execute()){
            setEnableElement(false);
            view3 = control.getActivity().findViewById(R.id.linearLayout_fond_trans_login_chargement);
            view3.setVisibility(View.VISIBLE);

            ToolKit.animationThis(R.anim.animation_fadout,view3,control.getActivity().getBaseContext());
           // control.animationThis(R.anim.animation_fadout, view3);
            checkLoginInThread();
        } else {
            animateErrorLogin("Pas de connection à Internet.");
        }
    }
    /**
     * Check le retour du myHttpClient de la validation du login
     * Dans un Thread et appel de l'animation qui suit
     * @param MyHttpClientLogin myHttpClient
     */
    private void checkLoginInThread(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                /* Attente de la reponse */
                /* Depassement delais reponse */
                long start = System.currentTimeMillis();
                while (!myHttpClientLogin.getIsFinish() && System.currentTimeMillis() < (start + 10000)) {
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) { }
                }
                /* Animation du login */
                final Boolean validationLogin = myHttpClientLogin.loadJsonWeb();
                control.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        /* Animation fonction login bon ou pas */
                        checkUiLoad(validationLogin);
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
            control.loadViewAndSetListener(R.layout.view_accueil);
        } else {
            setEnableElement(true);
            animateErrorLogin("Identifiant ou mot de passe erroner.");
            view3.setVisibility(View.INVISIBLE);

            ToolKit.animationThis(R.anim.animation_fadout,view3,control.getActivity().getBaseContext());

           // control.animationThis(R.anim.animation_fadin, view3);
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
            final TextView text = (TextView) control.getActivity().findViewById(R.id.layout_login_mauvais_identifiant);
            ToolKit.animationThis(R.anim.animation_fadout,text,control.getActivity().getBaseContext());
           // control.animationThis(R.anim.animation_fadout, text);
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
                                    ToolKit.animationThis(R.anim.animation_fadin,text,control.getActivity().getBaseContext());
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
     * @param uBoolean Activation ou Desactivation element
     */
    public void setEnableElement(boolean uBoolean) {
        this.arrayButton.get(0).setEnabled(uBoolean);
        this.arrayButton.get(1).setEnabled(uBoolean);
        this.arrayEditText.get(0).setEnabled(uBoolean);
        this.arrayEditText.get(1).setEnabled(uBoolean);
    }

/*************************************************************************************************/
    // Override Class MyHttpClient / Connection
/*************************************************************************************************/
    /**
     * Created by DaRk-_-D0G on 13/11/14.
     */
    public class MyHttpClientLogin extends MyHttpClientConnection {

        public MyHttpClientLogin(Activity uActivity) {

            super(uActivity);
        }


        @Override
        public boolean loadJsonWeb() {
            JSONObject jsonObject = null;
            try {
                jsonObject = this.getResult();
                //Obtenir L'objet User
                JSONObject userJsonObject = jsonObject.getJSONObject("user");
                //Dans l'objet user on obtient object coonection si valide
                return userJsonObject.getBoolean("connection");
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        public JSONObject getInfoForUrlByParam() {
            HttpPost httppost = new HttpPost(this.url);
            JSONObject result = null;

            try {
                // Creation du nombre de parametre
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

                if(this.objectClass instanceof User) {
                    //Nombre de parametre

                    // Ajoute des params User
                    User user = (User) this.objectClass;
                    nameValuePairs.add(new BasicNameValuePair("name", user.getName()));
                    nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));
                } else {
                    nameValuePairs = new ArrayList<NameValuePair>(2);
                }

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = this.httpclient.execute(httppost);
                result = jsonResultToJsonObject(response);



            } catch (ClientProtocolException e) {
                ToolKit.log("Erreur -> PostByUrlParamAndReturn");
            } catch (IOException e) {
                ToolKit.log("Erreur -> PostByUrlParamAndReturn");
            }
            return result;
        }
    }

}


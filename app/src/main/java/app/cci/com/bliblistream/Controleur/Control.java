package app.cci.com.bliblistream.Controleur;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import app.cci.com.bliblistream.Model.Class.User;
import app.cci.com.bliblistream.Model.Service.MyHttpClientConnection;
import app.cci.com.bliblistream.R;
import app.cci.com.bliblistream.Controleur.ListenerView.ListenerViewAccueil;
import app.cci.com.bliblistream.Controleur.ListenerView.ListenerViewListFilm;
import app.cci.com.bliblistream.Controleur.ListenerView.ListenerViewLogin;
import app.cci.com.bliblistream.Outil.ToolKit;


/**
 * Class Control, class qui control l'application
 *
 */
public class Control {
    //Attribut de l'activite de l'application
    private Activity activity;
    private Object instantListener;
    private View view;
    private Animation animation;
    private ArrayList<Integer> wayViewID;

    private MyHttpClientConnection myHttpClientConnection;
    public User user;
    //TODO CLASS USER
    public boolean LOGINTEMP = false;
    /**
     * Constructeur
     *
     * @param uActivity Activity
     */
    public Control(Activity uActivity) {
        this.myHttpClientConnection = new MyHttpClientConnection(uActivity);
        this.activity = uActivity;
        this.wayViewID = new ArrayList<Integer>();
    }

    public MyHttpClientConnection getMyHttpClientConnection() {
        return  this.myHttpClientConnection;
    }
    /**
     * Mettre un listener specifique a la view et charger la vue
     *
     * @param view View
     */
    public void loadViewAndSetListener(int inRidLayout) {


        /* selection la vue */
        LayoutInflater inflater = (LayoutInflater) this.activity.getSystemService(android.app.Activity.LAYOUT_INFLATER_SERVICE);
        try {
            //ajout id layout pour le chemin de lutilisateur
            this.wayViewID.add(inRidLayout);

            //on creer un variable temps pour la sauvegarde de la view maintenant pour lanimation
            View saveViewNow = this.view;

            this.view = null;
            this.view = inflater.inflate(this.getLastWayViewID(), null);

            /* Celon la vue il ajoute lecouteur qui lui est specifique */
            switch (this.getLastWayViewID()) {

                case R.layout.view_login:

                        ToolKit.log("Listener view_login  Activer");
                        //Animation
                        if(saveViewNow != null) {
                            this.animationThis(R.anim.animation_fadin,saveViewNow);
                        }

                        //Set a lactivite la view et faire lanimation + listener
                        this.getActivity().setContentView(this.view);
                        this.instantListener = new ListenerViewLogin(this);
                    break;

                case R.layout.view_accueil:

                        ToolKit.log(" view_accueil  Activer");
                        //Animation
                        if(this.instantListener instanceof  ListenerViewLogin) {
                            this.animationThis(R.anim.animation_translateensortiegauche,saveViewNow);
                        } else {
                            this.animationThis(R.anim.animation_fadin,saveViewNow);
                        }
                        //Set a lactivite la view et faire lanimation + listener
                        this.getActivity().setContentView(this.view);
                        this.instantListener = new ListenerViewAccueil(this);
                    break;

                case R.layout.view_listfilm:

                        ToolKit.log("Listener view_listfilm Activer");
                        //Animation
                        this.animationThis(R.anim.animation_fadin,saveViewNow);
                        //Set a lactivite la view et faire lanimation + listener
                        this.getActivity().setContentView(this.view);
                        this.instantListener = new ListenerViewListFilm(this);
                    break;
            }
        } catch (NullPointerException e) {
            ToolKit.log("!! ERREUR VIEW NON EXISTANTE -> inRLayoutId !!");
        }

    }

    /**
     * getActivity courante
     *
     * @return Activity
     */
    public Activity getActivity() {
        return this.activity;
    }

    /**
     * getView courante
     *
     * @return View
     */
    public View getView() {
        return this.view;
    }

    /**
     * getView courante
     *
     * @return Object
     */
    public Object getListener() {
        return this.instantListener;
    }

    public void animationThis(int inTypeAnimation, View inView) {
         this.animation = AnimationUtils.loadAnimation(this.getActivity().getApplicationContext(),
                 inTypeAnimation);

        this.animation.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {

            }
            @Override
            public void onAnimationRepeat(Animation arg0) {

            }
            @Override
            public void onAnimationEnd(Animation arg0) {

            }
        });
        inView.startAnimation(this.animation);

    }

    private int getLastWayViewID(){
        return this.wayViewID.get(this.wayViewID.size() -1);
    }

    private void removeLastWayViewID(){
        this.wayViewID.remove(this.wayViewID.size() -1);
    }

    public void LoadLastViewAndSetListener() {
        if(this.wayViewID.size() > 2) {
            this.removeLastWayViewID();
            this.loadViewAndSetListener(this.getLastWayViewID());
        }
    }

    public void resumeView() {

            this.loadViewAndSetListener(this.getLastWayViewID());

    }




}

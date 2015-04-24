package app.cci.com.bliblistream.Controler.ControlerActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import app.cci.com.bliblistream.Controler.ListenerView.ListenerViewAccueil;
import app.cci.com.bliblistream.Controler.ListenerView.ListenerViewFilm;
import app.cci.com.bliblistream.Controler.ListenerView.ListenerViewListCategorie;
import app.cci.com.bliblistream.Controler.ListenerView.ListenerViewListFilm;
import app.cci.com.bliblistream.Controler.ListenerView.ListenerViewListFilmCat;
import app.cci.com.bliblistream.Controler.ListenerView.ListenerViewListFilmnouveaute;
import app.cci.com.bliblistream.Controler.ListenerView.ListenerViewLogin;
import app.cci.com.bliblistream.Model.DownloadData.ImageData.ImagesCache;
import app.cci.com.bliblistream.Model.DownloadData.JsonData.JsonFeetcher.MyHttpClientListCategorie;
import app.cci.com.bliblistream.Model.DownloadData.JsonData.JsonFeetcher.MyHttpClientListFilm;
import app.cci.com.bliblistream.Model.StrucData.CollectionFilm;
import app.cci.com.bliblistream.Model.StrucData.User;
import app.cci.com.bliblistream.Outil.ToolKit;
import app.cci.com.bliblistream.R;
import app.cci.com.bliblistream.View.ArrayAdapter.MonCompteArrayAdapter;


/**
 * Class Control, class qui control l'application
 */
public class ControlerMainActivity {
    public ArrayList<Integer> filtreFilm;
    private Activity activity;
    private Object instantListener;
    private View view;
    private ArrayList<Integer> wayViewID;
    private int filtreIdCat = -1;
    /**
     * Constructeur
     *
     * @param uActivity Activity
     */
    public ControlerMainActivity(Activity uActivity) {
        this.activity = uActivity;
        this.wayViewID = new ArrayList<Integer>();

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
            ToolKit.log("[ControlerMainActivity] SELECTION ID VIEW :" + inRidLayout);
            //on creer un variable temps pour la sauvegarde de la view maintenant pour lanimation
            View saveViewNow = this.view;

            this.view = null;
            this.view = inflater.inflate(inRidLayout, null);

            /* Celon la vue il ajoute lecouteur qui lui est specifique */
            switch (inRidLayout) {

                case R.layout.view_login:

                    if (saveViewNow != null) {
                        ToolKit.animationThis(R.anim.animation_fadin, saveViewNow, activity.getBaseContext());
                    }
                    this.getActivity().setContentView(this.view);
                    this.instantListener = new ListenerViewLogin(this);
                    break;

                case R.layout.view_accueil:

                    notAllReadyAdd(inRidLayout);
                    ToolKit.animationThis(R.anim.animation_fadin, saveViewNow, activity.getBaseContext());
                    this.getActivity().setContentView(this.view);
                    this.instantListener = new ListenerViewAccueil(this);

                    break;

                case R.layout.view_listfilm:

                    notAllReadyAdd(inRidLayout);
                    ToolKit.animationThis(R.anim.animation_fadin, saveViewNow, activity.getBaseContext());
                    this.getActivity().setContentView(this.view);
                    this.instantListener = new ListenerViewListFilm(this);

                    break;
                case R.layout.view_moncompte:

                    notAllReadyAdd(inRidLayout);
                    ToolKit.animationThis(R.anim.animation_fadin, saveViewNow, activity.getBaseContext());
                    this.getActivity().setContentView(this.view);

                    TextView textView = (TextView) getActivity().findViewById(R.id.TextView_MonCompte_Nom);
                    textView.setText(User.getNom());
                    ToolKit.animationThis(R.anim.animation_translateenentredroit, textView, activity.getBaseContext());


                    textView = (TextView) getActivity().findViewById(R.id.TextView_MonCompte_Monnaie);
                    textView.setText(User.getJeton().toString());
                    textView.setVisibility(View.VISIBLE);
                    ToolKit.animationThis(R.anim.animation_translateenentredroit, textView, activity.getBaseContext());

                    textView = (TextView) getActivity().findViewById(R.id.TextView_MonCompte_Email);
                    textView.setText(User.getPrenom());
                    ToolKit.animationThis(R.anim.animation_translateenentredroit, textView, activity.getBaseContext());
                    ToolKit.animationThis(R.anim.animation_fadin, saveViewNow, activity.getBaseContext());

                    textView = (TextView) getActivity().findViewById(R.id.vos_films);
                    ToolKit.animationThis(R.anim.animation_translateenentredroit, textView, activity.getBaseContext());

                    ArrayList<Integer> location = User.getLocation();

                    ToolKit.logObject(location);
                    ListView listView = (ListView) getActivity().findViewById(R.id.ListeView_MonCompte_liste);

                    MonCompteArrayAdapter adapter = new MonCompteArrayAdapter(getActivity(), R.layout.view_rowtableview_moncompte, location);
                    listView.setAdapter(adapter);

                    break;

                case R.layout.view_film:

                    notAllReadyAdd(inRidLayout);
                    ToolKit.animationThis(R.anim.animation_fadin, saveViewNow, activity.getBaseContext());
                    this.getActivity().setContentView(this.view);
                    this.instantListener = new ListenerViewFilm(this);


                    break;
                case R.layout.view_listcategorie:
                    notAllReadyAdd(inRidLayout);
                    ToolKit.animationThis(R.anim.animation_fadin, saveViewNow, activity.getBaseContext());
                    this.getActivity().setContentView(this.view);
                    this.instantListener = new ListenerViewListCategorie(this);
                    break;

                case R.layout.view_listfilmcat:
                    notAllReadyAdd(inRidLayout);
                    ToolKit.animationThis(R.anim.animation_fadin, saveViewNow, activity.getBaseContext());
                    this.getActivity().setContentView(this.view);
                    this.instantListener = new ListenerViewListFilmCat(this);
                    break;

                case R.layout.view_listfilmnouveaute:
                    notAllReadyAdd(inRidLayout);
                    ToolKit.animationThis(R.anim.animation_fadin, saveViewNow, activity.getBaseContext());
                    this.getActivity().setContentView(this.view);
                    this.instantListener = new ListenerViewListFilmnouveaute(this);
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

    /**
     * Obtenir ID dernier view vu
     *
     * @return int
     */
    private int getLastWayViewID() {
        return this.wayViewID.get(this.wayViewID.size() - 1);
    }

    /**
     * Enlever la derniere view
     */
    private void removeLastWayViewID() {
        this.wayViewID.remove(this.wayViewID.size() - 1);
    }

    /**
     * Filtre pour affichage en list
     *
     * @param uLayout Integer
     */

    /**
     * Obtenir l'id filtre
     * @return int
     */
    public int getFiltreIdCat() { return filtreIdCat; }

    /**
     * Set un Filtre
     * @param filtreIdCat Int
     */
    public void setFiltreIdCat(int filtreIdCat) {
        this.filtreIdCat = filtreIdCat;
    }

    private void notAllReadyAdd(Integer uLayout) {
        if (this.wayViewID.size() > 0) {
            if (!(uLayout == getLastWayViewID())) {
                this.wayViewID.add(uLayout);
            }
        } else {
            this.wayViewID.add(uLayout);
        }
    }
    /**
     * Remettre la dernier view vue
     */
    public void LoadLastViewAndSetListener() { if (this.wayViewID.size() > 1) { removeLastWayViewID(); this.loadViewAndSetListener(this.getLastWayViewID()); } }
}


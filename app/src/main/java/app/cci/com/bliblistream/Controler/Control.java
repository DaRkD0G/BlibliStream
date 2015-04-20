package app.cci.com.bliblistream.Controler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;

import java.util.ArrayList;
import java.util.List;

import app.cci.com.bliblistream.Controler.ListenerView.ListenerViewFilm;

import app.cci.com.bliblistream.Controler.ListenerView.ListenerViewListCategorie;
import app.cci.com.bliblistream.Controler.ListenerView.ListenerViewListFilmCat;
import app.cci.com.bliblistream.Controler.ListenerView.ListenerViewListFilmnouveaute;

import app.cci.com.bliblistream.Controler.ListenerView.ListenerViewListMonCompte;
import app.cci.com.bliblistream.Model.StrucData.Categorie;
import app.cci.com.bliblistream.Model.StrucData.Film;
import app.cci.com.bliblistream.Model.JsonData.MyHttpClientListCategorie;
import app.cci.com.bliblistream.Model.JsonData.MyHttpClientListFilm;
import app.cci.com.bliblistream.Model.StrucData.User;

import app.cci.com.bliblistream.Model.Service.MyHttpClientConnection;
import app.cci.com.bliblistream.R;
import app.cci.com.bliblistream.Controler.ListenerView.ListenerViewAccueil;
import app.cci.com.bliblistream.Controler.ListenerView.ListenerViewListFilm;
import app.cci.com.bliblistream.Controler.ListenerView.ListenerViewLogin;
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
    private List<Film> collectionFilm;
    private int filtreIdCat = -1;
    public ArrayList<Integer> filtreFilm;

    public int getFiltreIdCat() {
        return filtreIdCat;
    }

    public void setFiltreIdCat(int filtreIdCat) {
        this.filtreIdCat = filtreIdCat;
    }

    private Film filmChose;
    private MyHttpClientConnection myHttpClientConnection;
    public User user;
   // private ArrayList<Categorie> collectionCategorie;
    ProgressDialog pDialog;
    //TODO CLASS USER
    public boolean LOGINTEMP = false;

    List<Categorie>  collectionMapCategorie;
    List<Film> collectionFilmMonCompte;
    MyHttpClientListCategorie myHttpClientListCategorie;
    MyHttpClientListFilm myHttpClientListFilm;
    /**
     * Constructeur
     *
     * @param uActivity Activity
     */
    public Control(Activity uActivity) {
        this.myHttpClientConnection = new MyHttpClientConnection(uActivity);
        this.activity = uActivity;
        this.wayViewID = new ArrayList<Integer>();
        this.collectionFilm = null;
        this.collectionMapCategorie = null;

        this.myHttpClientListCategorie =  new MyHttpClientListCategorie(this);
        this.myHttpClientListFilm = new MyHttpClientListFilm(this);
    }



    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
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

            ToolKit.log("--ID VIEW -->"+inRidLayout);
            //on creer un variable temps pour la sauvegarde de la view maintenant pour lanimation
            View saveViewNow = this.view;

            this.view = null;
            this.view = inflater.inflate(inRidLayout, null);

            /* Celon la vue il ajoute lecouteur qui lui est specifique */
            switch (inRidLayout) {

                case R.layout.view_login:

                        //ToolKit.log("Listener view_login  Activer");
                        //Animation
                        if(saveViewNow != null) {
                            ToolKit.animationThis(R.anim.animation_fadin, saveViewNow, activity.getBaseContext());
                        }

                        //Set a lactivite la view et faire lanimation + listener
                        this.getActivity().setContentView(this.view);
                        this.instantListener = new ListenerViewLogin(this);
                    break;

                case R.layout.view_accueil:

                    notAllReadyAdd(inRidLayout);

                      //
                        //Animation
                        if(this.instantListener instanceof  ListenerViewLogin) {
                            ToolKit.animationThis(R.anim.animation_translateensortiegauche,saveViewNow,activity.getBaseContext());

                        } else {
                            ToolKit.animationThis(R.anim.animation_fadin,saveViewNow,activity.getBaseContext());

                        }
                        //Set a lactivite la view et faire lanimation + listener

                        this.getActivity().setContentView(this.view);
                        this.instantListener = new ListenerViewAccueil(this);
                        if(collectionMapCategorie == null && collectionFilm == null) {
                            loadBDD();
                        }

                    break;

                case R.layout.view_listfilm:

                   // if(this.collectionFilm != null && this.collectionFilm.size() > 0) {

                            notAllReadyAdd(inRidLayout);
                            ToolKit.animationThis(R.anim.animation_fadin,saveViewNow,activity.getBaseContext());

                            this.getActivity().setContentView(this.view);
                            this.instantListener = new ListenerViewListFilm(this);

/*
                    } else {
                        ProgressDialog progressDialog= ProgressDialog.show(this.getActivity(), "",
                                "Loading. Please wait...", true);
                        progressDialog.show();
                        loadBDD();
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                /* Attente de la reponse */
                /* Depassement delais reponse*
                                long start = System.currentTimeMillis();

                                while ( System.currentTimeMillis() < (start + 2000)) {
                                    try {
                                        if(collectionFilm != null && collectionFilm.size() >= 1) {
                                           // thread.interrupt();
                                            Thread.currentThread().interrupt();
                                            break;
                                        }
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                    }
                                }
                            }
                        });
                        progressDialog.dismiss();

                    }*/

                    break;
                case R.layout.view_moncompte:
                    notAllReadyAdd(inRidLayout);
                    //this.wayViewID.add(R.layout.view_moncompte);

                    //Animation
                    ToolKit.animationThis(R.anim.animation_fadin,saveViewNow,activity.getBaseContext());
                    //this.animationThis(R.anim.animation_fadin,saveViewNow);
                    //Set a lactivite la view et faire lanimation + listener
                    this.getActivity().setContentView(this.view);
                    this.instantListener = new ListenerViewListMonCompte(this);
                    break;

                case R.layout.view_film:
                    notAllReadyAdd(inRidLayout);
                   // this.wayViewID.add(R.layout.view_film);

                    //Animation
                    ToolKit.animationThis(R.anim.animation_fadin,saveViewNow,activity.getBaseContext());
                    //this.animationThis(R.anim.animation_fadin,saveViewNow);
                    //Set a lactivite la view et faire lanimation + listener
                    //
                    this.getActivity().setContentView(this.view);
                    this.instantListener = new ListenerViewFilm(this);


                    break;
                case R.layout.view_listcategorie:


                        notAllReadyAdd(inRidLayout);
                        ToolKit.animationThis(R.anim.animation_fadin,saveViewNow,activity.getBaseContext());

                        this.getActivity().setContentView(this.view);
                        this.instantListener = new ListenerViewListCategorie(this);



                    break;

                case R.layout.view_listfilmcat:

                    // if(this.collectionFilm != null && this.collectionFilm.size() > 0) {

                    notAllReadyAdd(inRidLayout);
                    ToolKit.animationThis(R.anim.animation_fadin,saveViewNow,activity.getBaseContext());

                    this.getActivity().setContentView(this.view);
                    this.instantListener = new ListenerViewListFilmCat(this);
                    break;
                case R.layout.view_listfilmnouveaute:

                    // if(this.collectionFilm != null && this.collectionFilm.size() > 0) {

                    notAllReadyAdd(inRidLayout);
                    ToolKit.animationThis(R.anim.animation_fadin,saveViewNow,activity.getBaseContext());

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



    private int getLastWayViewID(){
        return this.wayViewID.get(this.wayViewID.size() -1);
    }

    private void removeLastWayViewID(){
        this.wayViewID.remove(this.wayViewID.size() -1);
    }
    private void notAllReadyAdd(Integer uLayout) {
        if(this.wayViewID.size() > 0) {
            if(!(uLayout == getLastWayViewID())) {
                this.wayViewID.add(uLayout);
            }

        } else {
            //if(!(uLayout == getLastWayViewID())) {
                this.wayViewID.add(uLayout);
           // }
        }

    }
    public void LoadLastViewAndSetListener() {
        ToolKit.log("getBACK!");

        if(this.wayViewID.size() > 1) {
        removeLastWayViewID();
            this.loadViewAndSetListener(this.getLastWayViewID());
       }
    }

    public void resumeView() {

            this.loadViewAndSetListener(this.getLastWayViewID());

    }

    public List<Film> getCollectionFilm() {
        return collectionFilm;
    }

    public void setCollectionFilm(List<Film> collectionFilm) {
        this.collectionFilm = collectionFilm;
    }
    public void loadBDD() {
        ToolKit.log("------------> LOAD BDD <-------------> OK");
        if( !myHttpClientListCategorie.executeLoading()) {
            ToolKit.showMessage(-1,"Pas de connection (A6)",this.getActivity(),-1,-1);
        } else {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                /* Attente de la reponse */
                /* Depassement delais reponse*/
                    long start = System.currentTimeMillis();
                    ToolKit.log("------------> Load Categorie <-------------");
                    while (!myHttpClientListCategorie.getIsFinish() && System.currentTimeMillis() < (start + 2000)) {
                        try {

                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            });
            ToolKit.log("------------> Load Categorie <-------------> FINI");
        }
        if(!myHttpClientListFilm.executeLoading()) {
            ToolKit.showMessage(-1,"Pas de connection (A6)",this.getActivity(),-1,-1);
        } else {
            ToolKit.log("------------> LOAD list film <-------------> OK");
        }
    }

    public void setFilmChose(Integer position) {
        this.filmChose = this.collectionFilm.get(position);
    }

    public Film getFilmChose() {
        return this.filmChose;
    }
    public void setCategorie(List<Categorie> collectionMapCategorie) {
         this.collectionMapCategorie = collectionMapCategorie;
    }

    public List<Categorie>  getCategorie() {
        return this.collectionMapCategorie;
    }
}

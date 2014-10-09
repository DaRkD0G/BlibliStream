package app.cci.com.bliblistream.Controleur;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

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

    /**
     * Constructeur
     *
     * @param uActivity Activity
     */
    public Control(Activity uActivity) {
        this.activity = uActivity;
    }

    /**
     * Mettre un listener specifique a la view et charger la vue
     *
     * @param view View
     */
    public void loadViewAndSetListener(int inRLayoutId) {

        /* selection la vue */
        LayoutInflater inflater = (LayoutInflater) this.activity.getSystemService(android.app.Activity.LAYOUT_INFLATER_SERVICE);
        try {
            this.view = null;
            this.instantListener = null;
            /* selectionne la vue */
            this.view = inflater.inflate(inRLayoutId, null);

            /* charge la view */
            this.getActivity().setContentView(this.view);

            /* Celon la vue il ajoute lecouteur qui lui est specifique */
            switch (inRLayoutId) {

                case R.layout.view_login:
                    if (this.instantListener == null || !(this.instantListener instanceof ListenerViewLogin)) {
                        ToolKit.log("Listener view_login  Activer");
                        this.instantListener = new ListenerViewLogin(this);
                    }
                    break;

                case R.layout.view_accueil:
                    if (this.instantListener == null || !(this.instantListener instanceof ListenerViewAccueil)) {
                        ToolKit.log(" view_accueil  Activer");
                        this.instantListener = new ListenerViewAccueil(this);
                    }
                    break;

                case R.layout.view_listfilm:
                    if (this.instantListener == null || !(this.instantListener instanceof ListenerViewListFilm)) {
                        ToolKit.log("Listener view_listfilm Activer");
                        this.instantListener = new ListenerViewListFilm(this);
                    }
                    break;
            }
        } catch (NullPointerException e) { ToolKit.log("!! ERREUR VIEW NON EXISTANTE -> inRLayoutId !!");}
        if(this.instantListener == null) { ToolKit.log("!! instantListener -> NULL !!"); }
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


}

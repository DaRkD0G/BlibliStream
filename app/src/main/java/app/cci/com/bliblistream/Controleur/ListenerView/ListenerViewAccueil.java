package app.cci.com.bliblistream.Controleur.ListenerView;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import app.cci.com.bliblistream.Model.Class.DownloadImageTask;
import app.cci.com.bliblistream.Model.Class.ImagesCache;
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
    private ArrayList<LinearLayout> arrayLinearLayout;

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
        this.arrayButton.add((Button) this.control.getActivity().findViewById(R.id.Button_Acceuil_Nouveaute));
        this.arrayButton.add((Button) this.control.getActivity().findViewById(R.id.Button_Tout_Les_Films));
        this.arrayButton.add((Button) this.control.getActivity().findViewById(R.id.Button_Acceuil_Categorie));
        this.arrayButton.add((Button) this.control.getActivity().findViewById(R.id.Button_Acceuil_MonCompte));



        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                /* Attente de la reponse */
                /* Depassement delais reponse */
                long start = System.currentTimeMillis();
                while (control.getCollectionFilm() == null &&
                        System.currentTimeMillis() < (start + 10000)) {
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) { }
                }
                /* Animation du login */

                control.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        /* Animation fonction login bon ou pas */
                        ImageView imv = (ImageView) control.getActivity().findViewById(R.id.ImageView_Film1);
                        ImageView imv1 = (ImageView) control.getActivity().findViewById(R.id.ImageView_Film2);
                        ImageView imv2 = (ImageView) control.getActivity().findViewById(R.id.ImageView_Film3);

                        ImagesCache cache = ImagesCache.getInstance();//Singleton instance handled in ImagesCache class.
                        cache.initializeCache();

                        Integer random = ToolKit.randInt(0,control.getCollectionFilm().size() -1);
                        String img = control.getCollectionFilm().get(random).lienImage;
                        random = ToolKit.randInt(0,control.getCollectionFilm().size() -1);
                        String img1 = control.getCollectionFilm().get(random).lienImage;
                        random = ToolKit.randInt(0,control.getCollectionFilm().size() -1);
                        String img2 = control.getCollectionFilm().get(random).lienImage;

                        Bitmap bm = cache.getImageFromWarehouse(img);

                        if(bm != null)
                        {
                            imv2.setImageBitmap(bm);
                            imv1.setImageBitmap(bm);
                            imv.setImageBitmap(bm);
                        }
                        else
                        {
                            imv.setImageBitmap(null);
                            imv1.setImageBitmap(null);
                            imv2.setImageBitmap(null);

                            DownloadImageTask imgTask = new DownloadImageTask(cache, imv, 500, 200);//Since you are using it from `Activity` call second Constructor.
                            DownloadImageTask imgTask1 = new DownloadImageTask(cache, imv1, 500, 200);//Since you are using it from `Activity` call second Constructor.
                            DownloadImageTask imgTask2 = new DownloadImageTask(cache, imv2, 500, 200);//Since you are using it from `Activity` call second Constructor.

                            imgTask.execute(img);
                            imgTask1.execute(img1);
                            imgTask2.execute(img2);
                        }





                    }
                });
            }
        });
        thread.start();




        /* On redefinit l ecouteur du clic sur les boutons */
        new AbstractButton(this.control.getActivity().getBaseContext(), this.arrayButton) {
            /**
             * Methode Sucharge de la class AbstractButton pour redefinir les boutons de la vue
             * @param v View
             */
            @Override
            public void onClick(View v) {
                super.onClick(v);
                if(     control.getCollectionFilm() != null &&
                        control.getCategorie() != null &&
                        control.getCategorie().size() > 0 &&
                        control.getCollectionFilm().size() > 0) {
                    switch (v.getId()) {
                        case R.id.Button_Acceuil_Nouveaute:
                            control.loadViewAndSetListener(R.layout.view_listfilmnouveaute);
                            ToolKit.log("clique Button_Acceuil_Nouveaute");
                            break;

                        case R.id.Button_Tout_Les_Films:

                            ToolKit.log("Clique  Button_Tout_Les_Films");
                            control.loadViewAndSetListener(R.layout.view_listfilm);
                            break;
                        case R.id.Button_Acceuil_MonCompte:
                            ToolKit.log("Clique  Button_Acceuil_MonCompte");
                            control.loadViewAndSetListener(R.layout.view_moncompte);
                            break;
                        case R.id.Button_Acceuil_Categorie:
                            ToolKit.log("Clique  Button_Acceuil_Categorie");
                            control.loadViewAndSetListener(R.layout.view_listcategorie);
                            break;
                    }
                } else {
                    ToolKit.showMessage(-1,"Merci de patienter quelque instant pour le chargement de votre compte...",control.getActivity(),-1,-1);
                }

            }
        };
    }
}


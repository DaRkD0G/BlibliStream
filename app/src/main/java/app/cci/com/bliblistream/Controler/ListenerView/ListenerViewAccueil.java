package app.cci.com.bliblistream.Controler.ListenerView;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import app.cci.com.bliblistream.Controler.ControlerActivity.ControlerMainActivity;
import app.cci.com.bliblistream.Model.DownloadData.ImageData.DownloadImageTask;
import app.cci.com.bliblistream.Model.DownloadData.ImageData.ImagesCache;
import app.cci.com.bliblistream.Model.StrucData.CollectionFilm;
import app.cci.com.bliblistream.Outil.ToolKit;
import app.cci.com.bliblistream.R;
import app.cci.com.bliblistream.View.Button.AbstractButton;

/**
 * Ecouteur de la View Acceuil
 *
 * @author DaRk-_-D0G on 30/09/2014.
 */
public class ListenerViewAccueil {
    private ControlerMainActivity controlerMainActivity;
    private ArrayList<Button> arrayButton;
    private ArrayList<LinearLayout> arrayLinearLayout;

    /**
     * Constructeur
     *
     * @param inControlerMainActivity Control
     */
    public ListenerViewAccueil(ControlerMainActivity inControlerMainActivity) {
        this.controlerMainActivity = inControlerMainActivity;
        this.loadAffichage();
    }

    public void loadAffichage() {
        this.loadDataImage();
        /* ici on ajout tout les buttons de la vue */
        this.arrayButton = new ArrayList<Button>();
        /* Ajoute des boutons de la vue à l attribut */
        this.arrayButton.add((Button) this.controlerMainActivity.getActivity().findViewById(R.id.Button_Acceuil_Nouveaute));
        this.arrayButton.add((Button) this.controlerMainActivity.getActivity().findViewById(R.id.Button_Tout_Les_Films));
        this.arrayButton.add((Button) this.controlerMainActivity.getActivity().findViewById(R.id.Button_Acceuil_Categorie));
        this.arrayButton.add((Button) this.controlerMainActivity.getActivity().findViewById(R.id.Button_Acceuil_MonCompte));
        this.addActionOnButtons();
    }

    /**
     * Load les images d'acceuil
     */
    public void loadDataImage() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                /* Attente de la reponse */
                /* Depassement delais reponse */
                long start = System.currentTimeMillis();
                while (CollectionFilm.getCollectionFilm().size() == 0 &&
                        System.currentTimeMillis() < (start + 10000)) {
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                    }
                }
                /* Animation du login */
                controlerMainActivity.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        /* Animation fonction login bon ou pas */
                        ImageView imv1 = (ImageView) controlerMainActivity.getActivity().findViewById(R.id.ImageView_Film1);
                        ImageView imv2 = (ImageView) controlerMainActivity.getActivity().findViewById(R.id.ImageView_Film2);
                        ImageView imv3 = (ImageView) controlerMainActivity.getActivity().findViewById(R.id.ImageView_Film3);

                        ImagesCache cache = ImagesCache.getInstance();//Singleton instance handled in ImagesCache class.

                        Bitmap bm1 = null;
                        Bitmap bm2 = null;
                        Bitmap bm3 = null;
                        String img1 = "";
                        String img2 = "";
                        String img3 = "";
                        if (CollectionFilm.getCollectionFilm().size() > 0) {
                            img1 = CollectionFilm.getCollectionFilm().get(0).getLienImage();
                            img2 = CollectionFilm.getCollectionFilm().get(1).getLienImage();
                            img3 = CollectionFilm.getCollectionFilm().get(2).getLienImage();

                            bm1 = cache.getImageFromWarehouse(img1);
                            bm2 = cache.getImageFromWarehouse(img2);
                            bm3 = cache.getImageFromWarehouse(img3);
                        }
                        if (bm1 != null && bm2 != null && bm3 != null) {
                            imv3.setImageBitmap(bm3);
                            imv2.setImageBitmap(bm2);
                            imv1.setImageBitmap(bm1);
                        } else {
                            imv1.setImageBitmap(null);
                            imv2.setImageBitmap(null);
                            imv3.setImageBitmap(null);

                            DownloadImageTask imgTask1 = new DownloadImageTask(cache, imv1, 500, 200);//Since you are using it from `Activity` call second Constructor.
                            DownloadImageTask imgTask2 = new DownloadImageTask(cache, imv2, 500, 200);//Since you are using it from `Activity` call second Constructor.
                            DownloadImageTask imgTask3 = new DownloadImageTask(cache, imv3, 500, 200);//Since you are using it from `Activity` call second Constructor.


                            imgTask1.execute(img1);
                            imgTask2.execute(img2);
                            imgTask3.execute(img3);
                        }
                    }
                });
            }
        });
        thread.start();
    }

    public void addActionOnButtons() {
        /* On redefinit l ecouteur du clic sur les boutons */
        new AbstractButton(this.controlerMainActivity.getActivity().getBaseContext(), this.arrayButton) {
            /**
             * Methode Sucharge de la class AbstractButton pour redefinir les boutons de la vue
             * @param v View
             */
            @Override
            public void onClick(View v) {
                super.onClick(v);
                switch (v.getId()) {
                    case R.id.Button_Acceuil_Nouveaute:
                        controlerMainActivity.loadViewAndSetListener(R.layout.view_listfilmnouveaute);
                        ToolKit.log("clique Button_Acceuil_Nouveaute");
                        break;

                    case R.id.Button_Tout_Les_Films:

                        ToolKit.log("Clique  Button_Tout_Les_Films");
                        controlerMainActivity.loadViewAndSetListener(R.layout.view_listfilm);
                        break;
                    case R.id.Button_Acceuil_MonCompte:
                        ToolKit.log("Clique  Button_Acceuil_MonCompte");
                        controlerMainActivity.loadViewAndSetListener(R.layout.view_moncompte);
                        break;
                    case R.id.Button_Acceuil_Categorie:
                        ToolKit.log("Clique  Button_Acceuil_Categorie");
                        controlerMainActivity.loadViewAndSetListener(R.layout.view_listcategorie);
                        break;
                }
            }
        };
    }
}


package app.cci.com.bliblistream.Controler.ListenerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import app.cci.com.bliblistream.Activity.VideoViewActivity;
import app.cci.com.bliblistream.Controler.ControlerActivity.ControlerMainActivity;
import app.cci.com.bliblistream.Model.DownloadData.ImageData.DownloadImageTask;
import app.cci.com.bliblistream.Model.DownloadData.ImageData.ImagesCache;
import app.cci.com.bliblistream.Model.DownloadData.JsonData.JsonFeetcher.MyHttpClientListAchatFilm;
import app.cci.com.bliblistream.Model.StrucData.User;
import app.cci.com.bliblistream.Outil.ToolKit;
import app.cci.com.bliblistream.R;
import app.cci.com.bliblistream.View.Button.AbstractButton;
import app.cci.com.bliblistream.View.Dialog.MyDialogFragment;

/**
 * Ecouteur de la View Acceuil
 *
 * @author DaRk-_-D0G on 30/09/2014.
 */
public class ListenerViewFilm {
    private ControlerMainActivity controlerMainActivity;
    private ArrayList<Button> arrayButton;
    private Intent myIntent;
    private Thread threadSendData;
    /// Dialog Fragment sur la location
    private final DialogFragment dialog = new MyDialogFragment() {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(controlerMainActivity.getActivity());
            alertDialogBuilder.setTitle("Location de film");
            alertDialogBuilder.setMessage("voulez vous louer ce film ? Pour " + User.getFilmChoisi().getTarif());

            alertDialogBuilder.setPositiveButton("Non", null);
            alertDialogBuilder.setNegativeButton("Oui", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    final MyHttpClientListAchatFilm listAchat = new MyHttpClientListAchatFilm();
                    ToolKit.showMessage(-1, "Merci de patienter nous effectuons la demande.", controlerMainActivity.getActivity(), -1, -1);

                    threadSendData = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            long start = System.currentTimeMillis();
                            while (!listAchat.ifLoadFinished() &&
                                    System.currentTimeMillis() < (start + 1000)) {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                }
                            }

                            controlerMainActivity.getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                        /* Animation fonction login bon ou pas */
                                    if (listAchat.ifCanSeeVideo()) {
                                        String lienVideo = User.getLienFilmVisionne();
                                        if (lienVideo.endsWith("mp4")) {
                                            myIntent = new Intent(controlerMainActivity.getActivity(), VideoViewActivity.class);
                                            myIntent.putExtra("video", User.getLienFilmVisionne());
                                            controlerMainActivity.getActivity().startActivity(myIntent);
                                        } else {
                                            ToolKit.showMessage(-1, "Une erreur c'est produite (Contacter le service bibliotheque Format non MP4)", controlerMainActivity.getActivity(), -1, -1);
                                        }
                                    } else {
                                        ToolKit.showMessage(-1, "Vous ne diposez plus de jeton pour la location", controlerMainActivity.getActivity(), -1, -1);
                                    }
                                }
                            });
                        }
                    });

                    try {
                        threadSendData.start();
                        listAchat.execute();

                    } catch (IllegalThreadStateException e) {
                    }

                }
            });
            return alertDialogBuilder.create();
        }
    };

    /**
     * Constructeur
     *
     * @param inControlerMainActivity Control
     */
    public ListenerViewFilm(ControlerMainActivity inControlerMainActivity) {
        this.controlerMainActivity = inControlerMainActivity;
        this.loadAffichage();

    }
    /* ############################################################# */
    /*                          AFFICHAGE                            */
    /* ############################################################# */

    /**
     * Call defirente fonction affichage
     */
    public void loadAffichage() {
        this.loadImageFilm();
        this.addElementOnView();
        this.loadActionOnButton();
    }

    /**
     * Load image du film
     */
    public void loadImageFilm() {
        ImageView imv = (ImageView) this.controlerMainActivity.getActivity().findViewById(R.id.ImageView_Film);
        ImagesCache cache = ImagesCache.getInstance();


        String img = User.getFilmChoisi().getLienImage();
        Bitmap bm = cache.getImageFromWarehouse(img);
        if (bm != null) {
            imv.setImageBitmap(bm);
        } else {
            imv.setImageBitmap(null);
            DownloadImageTask imgTask = new DownloadImageTask(cache, imv, 300, 300);//Since you are using it from `Activity` call second Constructor.
            imgTask.execute(img);
        }
    }

    /**
     * add les elements sur la view
     */
    public void addElementOnView() {
        this.arrayButton = new ArrayList<Button>();

        TextView t = (TextView) this.controlerMainActivity.getActivity().findViewById(R.id.TextView_Entete_Titre_Film);
        t.setText(User.getFilmChoisi().getTitre());
        t = (TextView) this.controlerMainActivity.getActivity().findViewById(R.id.TextView_Entete_Description_Film);
        t.setText(User.getFilmChoisi().getDescription());
        t = (TextView) this.controlerMainActivity.getActivity().findViewById(R.id.TextView_Entete_Date_Film);
        t.setText(User.getFilmChoisi().getDateSortie());
        /* ici on ajout tout les buttons de la vue */
        /* Ajoute des boutons de la vue à l attribut */
        this.arrayButton.add((Button) this.controlerMainActivity.getActivity().findViewById(R.id.Button_Entete_Visionner_Film));
        this.arrayButton.add((Button) this.controlerMainActivity.getActivity().findViewById(R.id.Button_Entete_Ba_Film));
    }

    /**
     * Ajoute les actions sur les buttons
     */
    public void loadActionOnButton() {
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
                    case R.id.Button_Entete_Visionner_Film:
                        boolean valide = false;
                        for (Integer idFilm : User.getLocation()) {
                            if (idFilm.equals(User.getFilmChoisi().getId())) {
                                valide = true;
                            }
                        }
                        if (User.getFilmChoisi().getTarif() <= User.getJeton() || valide) {
                            dialog.show(controlerMainActivity.getActivity().getFragmentManager(), "MyDialogFragmentTag");
                        } else {
                            ToolKit.showMessage(-1, "Vous ne diposez plus de jeton pour la location", controlerMainActivity.getActivity(), -1, -1);
                        }
                        break;

                    case R.id.Button_Entete_Ba_Film:

                        String lienBa = User.getFilmChoisi().getLienBa();
                        if (lienBa.endsWith("mp4")) {
                            myIntent = new Intent(controlerMainActivity.getActivity(), VideoViewActivity.class);
                            myIntent.putExtra("video", lienBa);
                            controlerMainActivity.getActivity().startActivity(myIntent);
                        } else {
                            ToolKit.showMessage(-1, "Une erreur c'est produite (Contacter le service bibliotheque Format non MP4)", controlerMainActivity.getActivity(), -1, -1);
                        }
                        break;
                }
            }
        };
    }
}


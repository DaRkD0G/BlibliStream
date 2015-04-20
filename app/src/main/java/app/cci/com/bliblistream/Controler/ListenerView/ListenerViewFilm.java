package app.cci.com.bliblistream.Controler.ListenerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

import app.cci.com.bliblistream.Controler.Control;
import app.cci.com.bliblistream.Controler.VideoViewActivity;
import app.cci.com.bliblistream.View.Button.AbstractButton;
import app.cci.com.bliblistream.Model.DownloadData.DownloadImageTask;
import app.cci.com.bliblistream.Model.DownloadData.ImagesCache;
import app.cci.com.bliblistream.View.Dialog.MyDialogFragment;
import app.cci.com.bliblistream.Model.JsonData.MyHttpClientListAchatFilm;
import app.cci.com.bliblistream.Outil.ToolKit;
import app.cci.com.bliblistream.R;

/**
 *
 * Ecouteur de la View Acceuil
 * @author DaRk-_-D0G on 30/09/2014.
 */
public class ListenerViewFilm {
    private Control control;
    //ArrayList de button de la vue Acceuil
    private ArrayList<Button> arrayButton;
    private ArrayList<LinearLayout> arrayLinearLayout;
    MyHttpClientListAchatFilm mMyHttpClientListAchatFilm;
    ProgressDialog pDialog;
    VideoView videoview;
    Intent myIntent;
    Bundle b;
    Integer mLastPosition;
    Integer limit;
    Context mContext;
    int inTextViewResourceId;


    /**
     * Constructeur
     * @param inControl Control
     */
    public ListenerViewFilm(Control inControl) {


        ToolKit.log("Class init  --> ListenerViewFilm");
        this.control = inControl;
        this.arrayButton = new ArrayList<Button>();



        ImageView imv = (ImageView) this.control.getActivity().findViewById(R.id.ImageView_Film);
        ImagesCache cache = ImagesCache.getInstance();//Singleton instance handled in ImagesCache class.
        cache.initializeCache();
        String img = control.getFilmChose().lienImage;


        Bitmap bm = cache.getImageFromWarehouse(img);

        if(bm != null)
        {
            imv.setImageBitmap(bm);
        }
        else
        {
            imv.setImageBitmap(null);

            DownloadImageTask imgTask = new DownloadImageTask(cache, imv, 300, 300);//Since you are using it from `Activity` call second Constructor.

            imgTask.execute(img);
        }


        TextView t = (TextView) this.control.getActivity().findViewById(R.id.TextView_Entete_Titre_Film);
        t.setText(control.getFilmChose().titre);
        t = (TextView) this.control.getActivity().findViewById(R.id.TextView_Entete_Description_Film);
        t.setText(control.getFilmChose().description);
        t = (TextView) this.control.getActivity().findViewById(R.id.TextView_Entete_Date_Film);
        t.setText(control.getFilmChose().dateSortie);
        /* ici on ajout tout les buttons de la vue */
        //this.arrayButton = new ArrayList<Button>();
        /* Ajoute des boutons de la vue à l attribut */
        this.arrayButton.add((Button) this.control.getActivity().findViewById(R.id.Button_Entete_Visionner_Film));
        this.arrayButton.add((Button) this.control.getActivity().findViewById(R.id.Button_Entete_Ba_Film));
       // this.arrayButton.add((Button) this.control.getActivity().findViewById(R.id.button_categorie));
        //this.arrayButton.add((Button) this.control.getActivity().findViewById(R.id.button_moncompte));
        /* On redefinit l ecouteur du clic sur les boutons */
        new AbstractButton(this.control.getActivity().getBaseContext(), this.arrayButton) {
            /**
             * Methode Sucharge de la class AbstractButton pour redefinir les boutons de la vue
             * @param v View
             */
            @Override
            public void onClick(View v) {
                super.onClick(v);

                ToolKit.log("hey !");
                switch (v.getId()) {
                   case R.id.Button_Entete_Visionner_Film:

                       final DialogFragment dialog = new MyDialogFragment() {
                           @Override
                           public Dialog onCreateDialog(Bundle savedInstanceState) {
                               AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(control.getActivity());
                               alertDialogBuilder.setTitle("Location de film");
                               alertDialogBuilder.setMessage("voulez vous louer ce film ? Pour "+control.getFilmChose().tarif);
                               //null should be your on click listener
                               alertDialogBuilder.setPositiveButton("Non", null);
                               alertDialogBuilder.setNegativeButton("Oui", new DialogInterface.OnClickListener() {

                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {

                                       myIntent = new Intent(control.getActivity(),VideoViewActivity.class);
                                       b = new Bundle();
                                       b.putString("key", control.getFilmChose().lienfilm); //Your id
                                       myIntent.putExtras(b); //Put your id to your next Intent
                                       mMyHttpClientListAchatFilm = new MyHttpClientListAchatFilm(control);
                                       mMyHttpClientListAchatFilm.executeLoading();

                                       Thread thread = new Thread(new Runnable() {
                                           @Override
                                           public void run() {
                /* Attente de la reponse */
                /* Depassement delais reponse */
                                               long start = System.currentTimeMillis();
                                               while (!mMyHttpClientListAchatFilm.getIsFinish() &&
                                                       System.currentTimeMillis() < (start + 10000)
                                                      ) {
                                                   try {
                                                       Thread.sleep(800);
                                                   } catch (InterruptedException e) { }
                                               }
                /* Animation du login */
                                               final Boolean validationLogin = mMyHttpClientListAchatFilm.loadJsonWeb();
                                               control.getActivity().runOnUiThread(new Runnable() {
                                                   public void run() {
                        /* Animation fonction login bon ou pas */
                                                       if(validationLogin) {
                                                           if(mMyHttpClientListAchatFilm.validation) {
                                                               control.getActivity().startActivity(myIntent);
                                                                ToolKit.showMessage(-1,"Bon visionnage",control.getActivity(),-1,-1);
                                                           } else {
                                                               ToolKit.showMessage(-1,"Vous ne diposez plus de jeton pour la location",control.getActivity(),-1,-1);
                                                           }

                                                       }
                                                   }
                                               });
                                           }
                                       });
                                       thread.start();



                                   }
                               });


                               return alertDialogBuilder.create();
                           }
                       };


                      dialog.show(control.getActivity().getFragmentManager(), "MyDialogFragmentTag");

                        break;

                    case R.id.Button_Entete_Ba_Film:
                       // ToolKit.log("clique ----------clique -------------------clique -------------------clique -------------------clique -------------------clique -------------------clique -------------------clique -------------------clique ----------------------------");
                        myIntent = new Intent(control.getActivity(),VideoViewActivity.class);
                        b = new Bundle();
                        b.putString("key", control.getFilmChose().lienba); //Your id
                        myIntent.putExtras(b); //Put your id to your next Intent
                        control.getActivity().startActivity(myIntent);
                       // control.loadViewAndSetListener(R.layout.view_listfilm);
                     break;
                }
            }
        };
    }
}


package app.cci.com.bliblistream.Controleur.ListenerView;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

import app.cci.com.bliblistream.Model.AbstractClass.AbstractMyHttpClient;
import app.cci.com.bliblistream.Model.AbstractClass.AbstractTableViewListFilm;
import app.cci.com.bliblistream.Model.Class.CollectionFilm;
import app.cci.com.bliblistream.Model.Class.Film;
import app.cci.com.bliblistream.Model.Class.User;
import app.cci.com.bliblistream.Model.Service.MyHttpClientConnection;
import app.cci.com.bliblistream.R;
import app.cci.com.bliblistream.Controleur.Control;
import app.cci.com.bliblistream.Outil.ToolKit;

/**
 *
 * Ecouteur de la View Acceuil
 * @author DaRk-_-D0G on 30/09/2014.
 */
public class ListenerViewListFilm {
    private Control control;
    int valeurClic = -2;
    private MyHttpClientListFilm myHttpClientListFilm;
    /**
     * Constructeur
     * @param inControl Control
     */
    public ListenerViewListFilm(Control inControl) {
        ToolKit.log("Class init  --> ListenerViewListFilm");


        this.control = inControl;

        // TODO exemple de valeur pour une liste
        String[] values = new String[] { "Recherche", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };


        /* creation dun ecouteur de la listView avec les methodes qui peuvent etre sucharger */
        ListView listView = ((ListView) this.control.getActivity().findViewById(R.id.list_film));

        // inflater = (LayoutInflater) this.control.getActivity()
               // .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        //ArrayAdapter adapter=new ArrayAdapter<String>(this.control.getActivity(),
              //  R.layout.view_toast);
        //setListAdapter(adapter);
        //listView.addView(adapter);

     //   listView.addView(rowView);
        //TODO EXEMPLE DE FILM;

        //http://yannickstephan.com/json/film.json
         ArrayList<Film> collectionFilm;


            collectionFilm = new ArrayList<Film>();
            collectionFilm.add(null);
            collectionFilm.add(new Film());
            collectionFilm.add(new Film("test"));
            collectionFilm.add(new Film("test"));
            collectionFilm.add(new Film("test"));
            collectionFilm.add(new Film("test"));
            collectionFilm.add(new Film());
            collectionFilm.add(new Film());
            collectionFilm.add(new Film());
            collectionFilm.add(new Film("test"));
            collectionFilm.add(new Film("test"));
            collectionFilm.add(new Film());
            collectionFilm.add(new Film());



        listView.setAdapter(new AbstractTableViewListFilm(this.control.getActivity().getBaseContext(),
                R.layout.view_rowtableview, collectionFilm) {
            /**
             * Surcharge de la methode dans AbstractTableView
             * Permet de retourne la view_RowTableView.xml
             * @param position int
             * @param convertView View
             * @param parent ViewGroup
             * @return
             */
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return super.getView(position, convertView, parent);
            }

            /**
             * Surchage de la methode dans AbstractTableView
             * Permet obtenir l item courant
             * @param position int
             * @return int
             */
            @Override
            public long getItemId(int position) {
                if (valeurClic == position) {
                    ToolKit.showMessage(-1, "Clic", control.getActivity(), -1, -1);
                } else {
                    valeurClic = position;
                }
                return super.getItemId(position);
            }

            /**
             * ?
             * @return boolean
             */
            @Override
            public boolean hasStableIds() {
                return super.hasStableIds();
            }
        });

        this.myHttpClientListFilm = new MyHttpClientListFilm(control.getActivity());
        this.myHttpClientListFilm.setParams(
                "http://yannickstephan.com/json/user.json",
                AbstractMyHttpClient.TYPEDEMANDE.GET_SET_URL_PARAM,
                null
        );
       if( this.myHttpClientListFilm.execute() ){
           ToolKit.log("-------------> ok");
           Thread thread = new Thread(new Runnable() {
               @Override
               public void run() {
                /* Attente de la reponse */
                /* Depassement delais reponse */
                   long start = System.currentTimeMillis();
                   while (!myHttpClientListFilm.getIsFinish() && System.currentTimeMillis() < (start + 10000)) {
                       try {
                           Thread.sleep(800);
                       } catch (InterruptedException e) { }
                   }

                   ToolKit.log("-------------> FINI OK");
                /* Animation du login */
                   final Boolean validationLogin = myHttpClientListFilm.loadJsonWeb();
                   control.getActivity().runOnUiThread(new Runnable() {
                       public void run() {
                           if(validationLogin) {
                            //TODO CHARGER LA LIST DE FILM
                           }
                        /* Animation fonction login bon ou pas */
                           //checkUiLoad(validationLogin);
                       }
                   });
               }
           });
           thread.start();
        }

        // Ici ajout de code si des boutons ou autre coposant
    }



/*************************************************************************************************/
    // Override Class MyHttpClient / Connection
/*************************************************************************************************/
    /**
     * Created by DaRk-_-D0G on 13/11/14.
     */
    public class MyHttpClientListFilm extends MyHttpClientConnection {

        public MyHttpClientListFilm(Activity uActivity) {
            super(uActivity);
        }

        @Override
        public boolean loadJsonWeb() {
            JSONObject jsonObject = null;
            try {
                jsonObject = this.getResult();
                /* Obtenir le tableau film contenant la liste des films */
                JSONObject userJsonObject = jsonObject.getJSONObject("Film");
                //Dans l'objet user on obtient object coonection si valide
                return true;
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
                List<NameValuePair> nameValuePairs;

                if(this.objectClass instanceof User) {
                    //Nombre de parametre
                    nameValuePairs = new ArrayList<NameValuePair>(2);
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


package app.cci.com.bliblistream.Model.Class;

/**
 * Created by DaRk-_-D0G on 12/12/14.
 */

import android.app.Activity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.cci.com.bliblistream.Controleur.Control;
import app.cci.com.bliblistream.Model.AbstractClass.AbstractMyHttpClient;
import app.cci.com.bliblistream.Model.Service.MyHttpClientConnection;
import app.cci.com.bliblistream.Outil.ToolKit;

/**
 * Created by DaRk-_-D0G on 13/11/14.
 */

public class MyHttpClientListFilm extends AbstractMyHttpClient {
    public JSONObject jsonObject = null;
    private Control control;
    public MyHttpClientListFilm(Control control) {
        super(control.getActivity());
        this.control = control;
    }

    public Boolean executeLoading() {
        this.setParams(
                "http://172.16.1.111:8888/bibli/conec.php",
                AbstractMyHttpClient.TYPEDEMANDE.GET_SET_URL_PARAM,
                null
        );
        if (this.execute()) {
            return  true;
        }
            return  false;


    }
    @Override
    public boolean loadJsonWeb() {

        jsonObject = null;
        try {
            this.jsonObject = this.getResult();



            if (control.getCollectionFilm() == null) {
                control.setCollectionFilm(new ArrayList<Film>());
                ToolKit.log("-------------->>>>LIST FILM LOAD JSON <<<<<");

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {


                                   /* Animation du login */
                            //final Boolean validationLogin = loadJsonWeb();


                                // Getting JSON Array node
                                JSONArray jsONArray = null;
                                try {
                                    jsONArray = jsonObject.getJSONArray("film");

                                    // looping through All Contacts
                                    for (int i = 0; i < jsONArray.length(); i++) {

                                        JSONObject c = jsONArray.getJSONObject(i);

                                        Integer id = c.getInt("id");
                                        String titre = c.getString("titre");
                                        String description = c.getString("description");
                                        String lienfilm = c.getString("lienfilm");
                                        String lienba = c.getString("lienBa");
                                        String lienImage = c.getString("lienImage");
                                        String tarif = c.getString("tarif");
                                        String dateSortie = c.getString("datesortie");
                                        Integer boolNewString = c.getInt("nouveaute");
                                        Boolean boolNew = false;
                                        if(boolNewString == 1) {
                                            boolNew = true;
                                        }

                                        int idCat = c.getInt("idCategorie");

                                        Categorie unCat = null;
                                        try {
                                            unCat = control.getCategorie().get(idCat);
                                        } catch (IndexOutOfBoundsException e){
                                           unCat  = new Categorie();
                                        }

                                        Film unFilm = new Film(id, titre, description, lienfilm, lienba, lienImage, tarif, dateSortie,boolNew,unCat);
                                        control.getCollectionFilm().add(unFilm);
                                      //  ToolKit.log("ADD BDD >>>>" + unFilm.toString());
                                    }
                                } catch (JSONException e) {
                                    control.setCollectionFilm(null);
                                    ToolKit.log("Exception ListFilm LoadJSonWeb" + e.toString());
                                } catch (NullPointerException e) {
                                    control.setCollectionFilm(null);
                                    ToolKit.log("Exception ListFilm LoadJSonWeb" + e.toString());
                                }
                               /// loadFilmListView();
                            }



                    });
                    thread.start();
                }



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

            nameValuePairs = new ArrayList<NameValuePair>(2);
                User user = (User) this.control.getUser();
                nameValuePairs.add(new BasicNameValuePair("name", user.getName()));
                nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));
                nameValuePairs.add(new BasicNameValuePair("param", "getfilm"));


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

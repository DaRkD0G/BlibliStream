package app.cci.com.bliblistream.Model.JsonData;

/**
 * Created by DaRk-_-D0G on 12/12/14.
 */

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
import java.util.List;

import app.cci.com.bliblistream.Controler.Control;
import app.cci.com.bliblistream.Model.StrucData.User;
import app.cci.com.bliblistream.Model.StrucData.Categorie;
import app.cci.com.bliblistream.Outil.ToolKit;

/**
 * Created by DaRk-_-D0G on 13/11/14.
 */

public class MyHttpClientListCategorie extends AbstractMyHttpClient {
    public JSONObject jsonObject = null;
    private Control control;
    public MyHttpClientListCategorie(Control control) {
        super(control.getActivity());
        this.control = control;
    }

    public Boolean executeLoading() {
        this.setParams(
                "http://localhost:8888/bibli/cat.php",
                TYPEDEMANDE.GET_SET_URL_PARAM,
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



            if (control.getCategorie() == null) {
                control.setCategorie(new ArrayList<Categorie>());

                ToolKit.log("-------------->>>>LIST CAT LOAD JSON <<<<<");

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {

                                   /* Animation du login */
                            //final Boolean validationLogin = loadJsonWeb();


                                // Getting JSON Array node
                                JSONArray jsONArray = null;
                                try {
                                    jsONArray = jsonObject.getJSONArray("categorie");
                                    ToolKit.log("-------------->>>>ICI<<<<<");

                                    // looping through All Contacts
                                    for (int i = 0; i < jsONArray.length(); i++) {

                                        JSONObject c = jsONArray.getJSONObject(i);

                                        int id = c.getInt("id");
                                        String titre = c.getString("titre");
                                        String description = c.getString("description");
                                        ToolKit.log(" cat id ->"+id);
                                        ToolKit.log(" cat titre ->"+titre);
                                        ToolKit.log(" cat descitp ->"+description);
                                        Categorie unCat = new Categorie(id, titre, description);
                                        try {
                                            control.getCategorie().add(unCat);
                                        } catch (IndexOutOfBoundsException e) {

                                        }

                                       // ToolKit.log("ADD BDD >>>>" + unCat.toString());
                                    }
                                } catch (JSONException e) {
                                    control.setCollectionFilm(null);
                                    ToolKit.log("Exception ListCat LoadJSonWeb" + e.toString());
                                } catch (NullPointerException e) {
                                    control.setCollectionFilm(null);
                                    ToolKit.log("Exception ListCat LoadJSonWeb" + e.toString());
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
                nameValuePairs.add(new BasicNameValuePair("param", "getCategorie"));


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

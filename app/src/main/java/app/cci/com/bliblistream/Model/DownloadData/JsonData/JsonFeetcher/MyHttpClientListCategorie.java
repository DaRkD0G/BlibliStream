package app.cci.com.bliblistream.Model.DownloadData.JsonData.JsonFeetcher;

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

import app.cci.com.bliblistream.Model.DownloadData.JsonData.ModeJsonData.AbstractMyHttpClient;
import app.cci.com.bliblistream.Model.StrucData.Categorie;
import app.cci.com.bliblistream.Model.StrucData.CollectionCategorie;
import app.cci.com.bliblistream.Model.StrucData.CollectionFilm;
import app.cci.com.bliblistream.Model.StrucData.User;
import app.cci.com.bliblistream.Outil.ToolKit;

/**
 * Created by DaRk-_-D0G on 13/11/14.
 */

public class MyHttpClientListCategorie extends AbstractMyHttpClient {

    public MyHttpClientListCategorie() {
        super();
        this.setParams(
                "http://yannickstephan.com/cci/categorie_true.json",
                TYPEDEMANDE.GET_SET_URL_PARAM,
                null
        );
    }


    @Override
    public boolean loadJsonWeb() {

        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    JSONArray jsONArray = null;
                    try {
                        jsONArray = getResult().getJSONArray("categorie");
                        for (int i = 0; i < jsONArray.length(); i++) {

                            JSONObject c = jsONArray.getJSONObject(i);

                            int id = c.getInt("id");
                            String titre = c.getString("titre");
                            String description = c.getString("description");
                            String lienImage = c.getString("lienImage");
                            Categorie unCat = new Categorie(id, titre, description, lienImage);
                            try {
                                CollectionCategorie.getCollectionCategorie().add(unCat);
                            } catch (IndexOutOfBoundsException e) {

                            }
                        }
                    } catch (JSONException e) {
                        CollectionFilm.resetCollectionFilms();
                        ToolKit.log("[MyHttpClientListCategorie] Exception ListCat LoadJSonWeb" + e.toString());
                    } catch (NullPointerException e) {
                        CollectionFilm.resetCollectionFilms();
                        ToolKit.log("[MyHttpClientListCategorie] Exception ListCat LoadJSonWeb" + e.toString());
                    }
                }


            });
            thread.start();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public JSONObject executeRequeteWithUrlByParam() {
        HttpPost httppost = new HttpPost(this.getUrl());
        JSONObject result = null;

        try {
            // Creation du nombre de parametre
            List<NameValuePair> nameValuePairs;

            nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("name", User.getNom()));
            nameValuePairs.add(new BasicNameValuePair("password", User.getPassword()));
            nameValuePairs.add(new BasicNameValuePair("param", "getCategorie"));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = this.getHttpclient().execute(httppost);
            result = jsonResultToJsonObject(response);


        } catch (ClientProtocolException e) {
            ToolKit.log("[MyHttpClientListCategorie] Erreur -> PostByUrlParamAndReturn");
        } catch (IOException e) {
            ToolKit.log("[MyHttpClientListCategorie] Erreur -> PostByUrlParamAndReturn");
        }
        return result;
    }
}

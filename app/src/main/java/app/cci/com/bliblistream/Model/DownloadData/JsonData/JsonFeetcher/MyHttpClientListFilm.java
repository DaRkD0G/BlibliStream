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
import app.cci.com.bliblistream.Model.StrucData.CollectionFilm;
import app.cci.com.bliblistream.Model.StrucData.Film;
import app.cci.com.bliblistream.Outil.ToolKit;

/**
 * Created by DaRk-_-D0G on 13/11/14.
 */

public class MyHttpClientListFilm extends AbstractMyHttpClient {
    private static MyHttpClientListFilm listFilmInstance;

    public MyHttpClientListFilm() {
        super();
        this.setParams(
                "http://yannickstephan.com/cci/collectionfilm_true.json",
                AbstractMyHttpClient.TYPEDEMANDE.GET_SET_URL_PARAM
        );
    }

    /**
     * Init Instance  MyHttpClientListFilm
     * @return MyHttpClientLogin
     */
    public static MyHttpClientListFilm getInstance() {
        if (listFilmInstance == null) {
            listFilmInstance = new MyHttpClientListFilm();
        }
        return listFilmInstance;
    }

    /**
     * Le chargement de la data fini
     * @return boolean
     */
    public static boolean ifLoadFinished() {
        return MyHttpClientListFilm.getInstance().getIsFinish();
    }

    /**
     * Le chargement de la data fini
     * @return boolean
     */
    public static void executeReq() {
        MyHttpClientListFilm.getInstance().execute();
    }

    /**
     * Load le retour du json en data
     *
     * @return boolean
     */
    @Override
    public boolean loadJsonWeb() {

        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray jsONArray = getResult().getJSONArray("films");

                        for (int i = 0; i < jsONArray.length(); i++) {

                            JSONObject c = jsONArray.getJSONObject(i);

                            ArrayList<Integer> collectionCategorie = new ArrayList<Integer>();

                            JSONArray colCat = c.getJSONArray("categorie");
                            for (int index = 0; index < colCat.length(); ++index) {
                                collectionCategorie.add(colCat.getInt(index));
                            }
                            if (collectionCategorie.size() == 0) {
                                CollectionFilm.resetCollectionFilms();
                                return;
                            }
                            Film film = new Film(
                                    c.getInt("id"),
                                    c.getString("titre"),
                                    c.getString("description"),
                                    c.getString("lienBa"),
                                    c.getString("lienImage"),
                                    c.getInt("tarif"),
                                    c.getString("dateSortie"),
                                    collectionCategorie
                            );

                            CollectionFilm.getCollectionFilm().add(film);

                        }

                        // MainActivity.barriere.countDown();
                    } catch (JSONException e) {
                        CollectionFilm.resetCollectionFilms();
                        ToolKit.log("[MyHttpClientListFilm] Exception ajout film" + e.toString());
                    } catch (NullPointerException e) {
                        CollectionFilm.resetCollectionFilms();
                        ToolKit.log("[MyHttpClientListFilm] Exception ListFilm LoadJSonWeb" + e.toString());
                    }
                }
            });
            thread.start();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Exectute la requete  JSon
     *
     * @return JSONObject
     */
    @Override
    public JSONObject executeRequeteWithUrlByParam() {
        HttpPost httppost = new HttpPost(this.getUrl());
        JSONObject result = null;
        try {
            List<NameValuePair> nameValuePairs;
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("params", "films"));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = this.getHttpclient().execute(httppost);
            result = jsonResultToJsonObject(response);


        } catch (ClientProtocolException e) {
            ToolKit.log("[MyHttpClientListFilm] Erreur -> PostByUrlParamAndReturn" + e.getStackTrace());
        } catch (IOException e) {
            ToolKit.log("[MyHttpClientListFilm] Erreur -> PostByUrlParamAndReturn" + e.getStackTrace());
        }
        return result;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

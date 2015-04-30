package app.cci.com.bliblistream.Model.DownloadData.JsonData.JsonFeetcher;

/**
 * Created by DaRk-_-D0G on 20/04/15.
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

import app.cci.com.bliblistream.Model.DownloadData.JsonData.ModeJsonData.AbstractMyHttpClient;
import app.cci.com.bliblistream.Model.StrucData.User;
import app.cci.com.bliblistream.Outil.ToolKit;

/**
 * Class gerant l'autothetification
 */
public class MyHttpClientLogin extends AbstractMyHttpClient {

    private static MyHttpClientLogin loginInstance;

    /**
     * Init Instance
     *
     */
    public MyHttpClientLogin() {
        super();
        this.setParams(
                "http://yannickstephan.com/cci/connection_true.json",
                AbstractMyHttpClient.TYPEDEMANDE.GET_SET_URL_PARAM
        );
    }

    /**
     * Init Instance  MyHttpClientLogin
     * @return MyHttpClientLogin
     */
    public static MyHttpClientLogin getInstance() {
        if (loginInstance == null) {
            loginInstance = new MyHttpClientLogin();
        }
        return loginInstance;
    }

    /**
     * Le chargement de la data fini
     * @return boolean
     */
    public static boolean ifLoadFinished() {
        return MyHttpClientLogin.getInstance().getIsFinish();
    }

    /**
     * Charger la data Json en object
     * @return boolean
     */
    public static boolean loadJsonData() {
        return MyHttpClientLogin.getInstance().loadJsonWeb();
    }

    /**
     * Charge les donnes de retour de la requete JSON
     *
     * @return boolean
     */
    @Override
    public boolean loadJsonWeb() {
        JSONObject jsonObject = null;
        try {
            jsonObject = this.getResult();
            /* TODO Detail JSON */
            ToolKit.log(this.toString());

            try {
                User.setId(jsonObject.getInt("userId"));
                User.setNom(jsonObject.getString("userNom"));
                User.setPrenom(jsonObject.getString("userPrenom"));
                User.setJeton(jsonObject.getInt("jeton"));

                /* Parse json array */
                ArrayList<Integer> list = new ArrayList<Integer>();
                JSONArray jsonArray = jsonObject.getJSONArray("location");
                if (jsonArray != null) {
                    int len = jsonArray.length();
                    for (int i = 0; i < len; i++) {
                        try {
                            Integer value = jsonArray.getInt(i);
                            list.add(value);
                        } catch (NumberFormatException e) {
                            ToolKit.log("[MyHttpClientLogin] Erreur parseInt location (MyHttpClientLogin)");
                        } catch (JSONException e) {
                            /* Nothing */
                        }
                    }
                }
                User.setLocation(list);
                return true;
            } catch (JSONException e) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Exectute la requete avec des params a URL
     *
     * @return JSONObject
     */
    @Override
    public JSONObject executeRequeteWithUrlByParam() {
        HttpPost httppost = new HttpPost(this.getUrl());
        JSONObject result = null;

        try {
            // Creation du nombre de parametre
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("name", User.getNom()));
            nameValuePairs.add(new BasicNameValuePair("password", User.getPassword()));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = this.getHttpclient().execute(httppost);
            result = jsonResultToJsonObject(response);

        } catch (ClientProtocolException e) {
            ToolKit.log("[MyHttpClientLogin] Erreur -> executeRequeteWithUrlByParam : ClientProtocolException");
        } catch (IOException e) {
            ToolKit.log("[MyHttpClientLogin] Erreur -> executeRequeteWithUrlByParam : IOException");
        }
        return result;
    }

    /**
     * Get le string de la class
     *
     * @return String
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
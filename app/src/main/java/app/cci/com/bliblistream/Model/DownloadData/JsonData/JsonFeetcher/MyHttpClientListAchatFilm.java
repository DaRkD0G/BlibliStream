package app.cci.com.bliblistream.Model.DownloadData.JsonData.JsonFeetcher;

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

import app.cci.com.bliblistream.Model.DownloadData.JsonData.ModeJsonData.AbstractMyHttpClient;
import app.cci.com.bliblistream.Model.StrucData.User;
import app.cci.com.bliblistream.Outil.ToolKit;

/**
 * Created by DaRk-_-D0G on 13/11/14.
 */

public class MyHttpClientListAchatFilm extends AbstractMyHttpClient {
    private Boolean validation = false;

    public MyHttpClientListAchatFilm() {
        super();
        this.setParams(
                "http://yannickstephan.com/cci/script.php",
                AbstractMyHttpClient.TYPEDEMANDE.GET_SET_URL_PARAM
        );
    }

    public boolean ifLoadFinished() {
        return this.getIsFinish();
    }

    public boolean ifCanSeeVideo() {
        return this.validation;
    }

    @Override
    public boolean loadJsonWeb() {
        JSONObject jsonObject = null;
        try {
            jsonObject = this.getResult();
            try {
                Integer idUser = jsonObject.getInt("userId");

                if (idUser.equals(User.getId())) {
                    User.setJeton(jsonObject.getInt("jeton"));
                    User.setLienFilmVisionne(jsonObject.getString("filmLien"));
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
                                ToolKit.log("[MyHttpClientListAchatFilm] Erreur parseInt location (MyHttpClientListAchatFilm)");
                            } catch (JSONException e) {
                            /* Nothing */
                            }
                        }
                        this.validation = true;
                        User.setLocation(list);
                    }
                }
                return true;
            } catch (JSONException e) {
                ToolKit.log("[MyHttpClientListAchatFilm] Erreur JSONException location (MyHttpClientListAchatFilm)");
                this.validation = false;
                return false;
            }
        } catch (Exception e) {
            ToolKit.log("[MyHttpClientListAchatFilm] Erreur Exception location (MyHttpClientListAchatFilm)");
            this.validation = false;
            return false;
        }
    }


    @Override
    public JSONObject executeRequeteWithUrlByParam() {
        HttpPost httppost = new HttpPost(this.getUrl());
        JSONObject result = null;

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("login", User.getNom()));
            ToolKit.log("------>" + User.getNom());
            // nameValuePairs.add(new BasicNameValuePair("password", User.getPassword()));
            nameValuePairs.add(new BasicNameValuePair("param", "location"));
            nameValuePairs.add(new BasicNameValuePair("id", User.getFilmChoisi().getId().toString()));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = this.getHttpclient().execute(httppost);
            result = jsonResultToJsonObject(response);
            ToolKit.log("------>"+result.toString());
        } catch (ClientProtocolException e) {
            ToolKit.log("Erreur -> PostByUrlParamAndReturn");
        } catch (IOException e) {
            ToolKit.log("Erreur -> PostByUrlParamAndReturn");
        }
        return result;
    }
}

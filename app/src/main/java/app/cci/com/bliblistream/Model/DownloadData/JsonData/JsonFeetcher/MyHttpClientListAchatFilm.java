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
    private static MyHttpClientListAchatFilm achatInstance = null;
    private Boolean validation = false;
    private Integer filmId = 0;

    public MyHttpClientListAchatFilm() {
        super();
        this.setParams(
                "http://yannickstephan.com/cci/location_false.json",
                TYPEDEMANDE.GET_SET_URL_PARAM,
                null
        );
    }

    public static MyHttpClientListAchatFilm getInstance() {
        if (achatInstance == null) {
            achatInstance = new MyHttpClientListAchatFilm();
        }
        return achatInstance;
    }

    public static boolean ifLoadFinished() {
        return MyHttpClientListAchatFilm.getInstance().getIsFinish();
    }

    public static boolean loadJsonData() {
        return MyHttpClientListAchatFilm.getInstance().loadJsonWeb();
    }

    public static boolean ifCanSeeVideo() {
        return MyHttpClientListAchatFilm.getInstance().validation;
    }

    @Override
    public boolean loadJsonWeb() {

        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    JSONArray jsONArray = null;
                    try {
                        JSONObject jsonUser = getResult().getJSONObject("location");
                        String validationString = jsonUser.getString("validation");
                                /* TODO GERER LA LOCATION */
                        if (validationString.equals("1")) {
                            validation = true;
                            User.getLocation().add(1);
                        } else {
                            validation = false;
                        }

                    } catch (JSONException e) {
                        validation = false;
                        ToolKit.log("exepction" + e.toString());
                    } catch (NullPointerException e) {
                        validation = false;
                        ToolKit.log("exepction" + e.toString());
                    }

                }
            });
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public void setFilmId(Integer filmId) {
        MyHttpClientListAchatFilm.getInstance().filmId = filmId;
    }

    @Override
    public JSONObject executeRequeteWithUrlByParam() {
        HttpPost httppost = new HttpPost(this.url);
        JSONObject result = null;

        try {
            // Creation du nombre de parametre
            List<NameValuePair> nameValuePairs;

            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("name", User.getNom()));
            nameValuePairs.add(new BasicNameValuePair("password", User.getPassword()));
            nameValuePairs.add(new BasicNameValuePair("param", "achat"));
            nameValuePairs.add(new BasicNameValuePair("filmid", "" + filmId));

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

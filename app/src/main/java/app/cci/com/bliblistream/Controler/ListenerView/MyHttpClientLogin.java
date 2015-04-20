package app.cci.com.bliblistream.Controler.ListenerView;

/**
 * Created by DaRk-_-D0G on 20/04/15.
 */

import android.app.Activity;

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

import app.cci.com.bliblistream.Model.JsonData.AbstractMyHttpClient;
import app.cci.com.bliblistream.Model.StrucData.User;
import app.cci.com.bliblistream.Outil.ToolKit;

/**
 * Created by DaRk-_-D0G on 13/11/14.
 */
class MyHttpClientLogin extends AbstractMyHttpClient {

    public MyHttpClientLogin(Activity uActivity) {

        super(uActivity);
    }


    @Override
    public boolean loadJsonWeb() {
        JSONObject jsonObject = null;
        try {
            jsonObject = this.getResult();
            ToolKit.log(jsonObject.toString());
            //Obtenir L'objet User
            JSONObject userJsonObject = jsonObject.getJSONObject("user");

            //Dans l'objet user on obtient object coonection si valide
            return userJsonObject.getBoolean("connection");
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
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            if(this.objectClass instanceof User) {
                //Nombre de parametre

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
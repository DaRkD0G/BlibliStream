package app.cci.com.bliblistream.Model.Class;

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

import app.cci.com.bliblistream.Controleur.Control;
import app.cci.com.bliblistream.Model.AbstractClass.AbstractMyHttpClient;
import app.cci.com.bliblistream.Outil.ToolKit;

/**
 * Created by DaRk-_-D0G on 13/11/14.
 */

public abstract  class MyHttpClientMonCompte222 extends AbstractMyHttpClient {
    public JSONObject jsonObject = null;
    private Control control;

    public MyHttpClientMonCompte222(Control control) {
        super(control.getActivity());
        this.control = control;
    }

    public Boolean executeLoading() {
        this.setParams(
                "http://172.16.1.111:8888/bibli/user.php",
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

       return false;
    }

    @Override
    public JSONObject getInfoForUrlByParam() {
        HttpPost httppost = new HttpPost(this.url);
        JSONObject result = null;

        try {
            // Creation du nombre de parametre
            List<NameValuePair> nameValuePairs;



                nameValuePairs = new ArrayList<NameValuePair>(2);
                // Ajoute des params User
                User user = (User) this.control.getUser();
                nameValuePairs.add(new BasicNameValuePair("name", user.getName()));
                nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));
                nameValuePairs.add(new BasicNameValuePair("param", "user"));

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

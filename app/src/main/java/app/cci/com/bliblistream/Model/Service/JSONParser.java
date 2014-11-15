package app.cci.com.bliblistream.Model.Service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import app.cci.com.bliblistream.Outil.ToolKit;


/**
 * Created by DaRk-_-D0G
 * Class Parser du JSON
 */
public class JSONParser {
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    /**
     * Constructeur
     */
    public JSONParser() {}


    /**
     * Obtenir l'URL
     * @param url String
     * @return jSONObject JSONObject
     */
    public JSONObject getJSONFromUrl(String url) {


        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch  (UnsupportedEncodingException  e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                ToolKit.log(line);
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
          /*  ToolKit.log("");*/
        }

        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            /*Log.e( e.toString());*/
        }

        return jObj;
    }
}

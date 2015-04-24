package app.cci.com.bliblistream.Model.DownloadData.JsonData.ModeJsonData;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import app.cci.com.bliblistream.Outil.ToolKit;

/**
 * Class pour la connection au WebService pour recuperer les données ou envoyer ou les deux
 */
public abstract class AbstractMyHttpClient {

    public HttpClient httpclient;
    public String url;
    public Object objectClass;
    private HttpAsyncTask httpAsyncTask;

    /**
     * Constructeur
     *
     * @param uActivity l'activité de l'application
     */
    public AbstractMyHttpClient() {
        this.httpclient = new DefaultHttpClient();
        this.httpAsyncTask = new HttpAsyncTask();

    }

    /**
     * Convertit InputStream en String
     *
     * @param inputStream Flux entrée
     * @return La convertion flux entrée en String
     * @throws IOException
     */
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;

    }

    /**
     * Setter les paramettres d'execution
     *
     * @param uUrlInformation Url vers les informations
     * @param uTypeDemande    Type de demande
     * @param uObjectsClass   Object à traiter
     */
    public void setParams(String uUrlInformation, TYPEDEMANDE uTypeDemande, Object uObjectsClass) {
        this.url = uUrlInformation;
        this.objectClass = uObjectsClass;
        this.httpAsyncTask.setTypeDemande(uTypeDemande);
    }

    /**
     * Execute la demande de connection pour chercher les informations d'un lien avec des param URL / Json
     */
    public void execute() {
        this.httpAsyncTask.execute(this.url);
    }

    /**
     * Execute la demande de connection pour chercher les informations d'un lien avec des param URL / Json
     *
     *
     public boolean check() {
     //Execute
     if(this.url.equals("")) {
     ToolKit.log("Erreur -> Manque URL MyHttpClient");
     return false;
     } else if(this.objectClass == null)  {
     ToolKit.log("Erreur -> Manque URL MyHttpClient");
     return false;
     }
     return true;
     }*/
    /**
     * Donne ce que retourne une URL en format STRING
     */
    private String getInfoForUrl() {
       /* InputStream inputStream = null;
        String result = "";
        try {
            // Fabrique le retour d'un requete
            HttpResponse httpResponse = this.httpclient.execute(new HttpGet(this.url));
            // Resois la reponse de L'url
            try {
                inputStream = httpResponse.getEntity().getContent();
                result = convertInputStreamToString(inputStream);
            } catch (NullPointerException e) {
                result = null;
            }
        } catch (Exception e) {
            ToolKit.logWTF("InputStream ------>" + e.getLocalizedMessage());
        }*/
        return null;
    }

    /**
     * Envoi d'un objet au formation JSON
     */
    public String getInfoUrlByJsonAndReturn() {
/*
        InputStream inputStream = null;
        String result = "";

        try {

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            if(objectClass instanceof User) {
                User user = (User) objectClass;

                // TODO JsonObjet
               /* JSONObject jsonObjectContenu = new JSONObject();
                jsonObjectContenu.put("password", user.getPassword());
                jsonObjectContenu.put("name", user.getName());*/
               /* JSONArray ja = new JSONArray();
                ja.put(jsonObjectContenu);


               // jsonObject.put("user", jsonObjectContenu);
            } else {

            }


            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
            ToolKit.log(json);
            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);



            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = this.httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            ToolKit.logWTF("InputStream" +e.getLocalizedMessage());
        }

        // 11. return result*/
        return null;
    }

    public boolean getIsFinish() {
        return this.httpAsyncTask.isFinish;
    }

    public JSONObject getResult() throws NullPointerException {
        return this.httpAsyncTask.result;
    }

    /**
     * StringJson to JsonObject
     *
     * @param uResult String au format Json
     * @return ObjectJson
     */
    public JSONObject jsonResultToJsonObject(HttpResponse uResult) {

        // transforme le resultalt en format String
        String resultJsonString = null;
        try {
            InputStream inputStream = uResult.getEntity().getContent();
            resultJsonString = convertInputStreamToString(inputStream);

        } catch (IOException e) {
            //ToolKit.showMessage(-1, "Pas de connection (A1)", activity, -1, -1);
            ToolKit.log("ERREUR -> jsonStringToObject :" + e.toString());
        }

        //String To json
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(resultJsonString);
        } catch (JSONException e) {
            // ToolKit.showMessage(-1, "Pas de connection (A2)", activity, -1, -1);
            ToolKit.log("ERREUR -> jsonStringToObject :" + e.toString());
        }
        return jObj;
    }

    /**
     * Envoi dans URL au format GET les demandes dans retourne
     *
     * @param url Une URL
     *            TODO DEFINIT LES PARAMATRES EST LES OBJECT
     */
    public JSONObject executeRequeteWithUrlByParam() {
        // Create httpost
        return null;
    }

    /**
     * Traitement du fichier Json
     *
     * @return Boolean une validation du traitement
     */
    public boolean loadJsonWeb() {
        return false;
    }

    public enum TYPEDEMANDE {
        GET_URL_INFO, GET_SET_URL_PARAM, GET_SET_JSON_PARAM
    }

    /**
     * Class qui gere l'execution en InSynchrone de demande
     */
    private class HttpAsyncTask extends AsyncTask<String, Void, JSONObject> {
        public JSONObject result = null;
        public boolean isFinish = false;
        private TYPEDEMANDE typeDemande;

        /**
         * La demande d'execution en InSynchrone
         *
         * @param typeDemande type de demande d'execution
         */
        public void setTypeDemande(TYPEDEMANDE typeDemande) {
            this.typeDemande = typeDemande;
        }

        /**
         * Execution InSynchrone
         *
         * @param urls Url
         * @return Le retour du lien
         */
        @Override
        protected JSONObject doInBackground(String... urls) {
            //postData();

            if (typeDemande == TYPEDEMANDE.GET_URL_INFO) {
                // return getInfoForUrl();
            } else if (typeDemande == TYPEDEMANDE.GET_SET_JSON_PARAM) {
                // return getInfoUrlByJsonAndReturn();
            } else if (typeDemande == TYPEDEMANDE.GET_SET_URL_PARAM) {

                return executeRequeteWithUrlByParam();
            }

            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(JSONObject result) {
            // Toast.makeText(activity.getBaseContext(), , Toast.LENGTH_LONG).show();

            this.result = result;
            ToolKit.log("---> Received!");
            if (!loadJsonWeb()) {
                ToolKit.log("Erreur onPostExecute pas loadJsonWeb <----");
            }

            this.isFinish = true;


        }
    }


}

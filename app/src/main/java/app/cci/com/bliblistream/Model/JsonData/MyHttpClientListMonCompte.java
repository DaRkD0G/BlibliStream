package app.cci.com.bliblistream.Model.JsonData;

/**
 * Created by DaRk-_-D0G on 12/12/14.
 */

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

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
import app.cci.com.bliblistream.View.TableView.AbstractTableViewListFilmMonCompte;
import app.cci.com.bliblistream.Model.StrucData.User;
import app.cci.com.bliblistream.Outil.ToolKit;
import app.cci.com.bliblistream.R;

/**
 * Created by DaRk-_-D0G on 13/11/14.
 */

public class MyHttpClientListMonCompte extends AbstractMyHttpClient {
    public JSONObject jsonObject = null;
    private Control control;
    public MyHttpClientListMonCompte(Control control) {
        super(control.getActivity());
        this.control = control;
    }

    public Boolean executeLoading() {
        this.setParams(
                "http://localhost:8888/bibli/moncompte.json",
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





                ToolKit.log("-------------->>>>LIST CAT LOAD JSON <<<<<");

            control.getActivity().runOnUiThread(new Runnable() {
                public void run() {

                                   /* Animation du login */
                            //final Boolean validationLogin = loadJsonWeb();


                                // Getting JSON Array node
                                JSONArray jsONArray = null;
                            try {

                                JSONObject jsonUser = jsonObject.getJSONObject("user");

                                String name = jsonUser.getString("userPrenom");
                                String monnaie = jsonUser.getString("jeton");
                                String mail = jsonUser.getString("mail");



                                TextView textView = (TextView) control.getActivity().findViewById(R.id.TextView_MonCompte_Nom);
                                textView.setText(name);
                                ToolKit.animationThis(R.anim.animation_translateenentredroit, textView, control.getActivity());


                                textView = (TextView) control.getActivity().findViewById(R.id.TextView_MonCompte_Monnaie);
                                textView.setText(monnaie);
                                textView.setVisibility(View.VISIBLE);
                                ToolKit.animationThis(R.anim.animation_translateenentredroit, textView, control.getActivity());



                                textView = (TextView) control.getActivity().findViewById(R.id.TextView_MonCompte_Email);
                                textView.setText(mail);
                                ToolKit.animationThis(R.anim.animation_translateenentredroit, textView, control.getActivity());
                                //  ((TextView) control.getActivity().findViewById(R.id.TextView_MonCompte_Email)).setText(mail);



                                //JSONArray filmLouer = c.getJSONArray("location");





                            } catch (JSONException e) {
                                // control.setCollectionFilm(null);
                                ToolKit.log("exepction" + e.toString());
                            } catch (NullPointerException e) {
                                //   control.setCollectionFilm(null);
                                ToolKit.log("exepction" + e.toString());
                            }
                               /// loadFilmListView();
                            }



                    });


            control.filtreFilm = new ArrayList<Integer>();
            JSONArray jsonArrayFilm = jsonObject.getJSONArray("film");
            ToolKit.log("--OKKKK-->");
            for (int i = 0; i < jsonArrayFilm.length(); i++) {
                JSONObject c = jsonArrayFilm.getJSONObject(i);
                ToolKit.log("--OKKKK-->");
                Integer idFilm = c.getInt("id");
                control.filtreFilm.add(idFilm);


            }


            ListView listView = ((ListView) control.getActivity().findViewById(R.id.ListeView_MonCompte_liste));
            AbstractTableViewListFilmMonCompte a = new AbstractTableViewListFilmMonCompte(control, R.layout.view_rowtableview_moncompte, 5);
            listView.setAdapter(a);

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
                nameValuePairs.add(new BasicNameValuePair("userPrenom", user.getName()));
                nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));
                nameValuePairs.add(new BasicNameValuePair("param", "getmoncompte"));


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

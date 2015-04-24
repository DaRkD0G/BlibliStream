package app.cci.com.bliblistream.Controler.ListenerView;

import android.widget.ListView;

import app.cci.com.bliblistream.Controler.ControlerActivity.ControlerMainActivity;
import app.cci.com.bliblistream.R;
import app.cci.com.bliblistream.View.TableView.AbstractTableViewListCategorie;

/**
 * Ecouteur de la View Acceuil
 *
 * @author DaRk-_-D0G on 30/09/2014.
 */
public class ListenerViewListCategorie {
    private ControlerMainActivity controlerMainActivity;
    private ListView listView;

    /**
     * Constructeur
     *
     * @param inControlerMainActivity Control
     */
    public ListenerViewListCategorie(ControlerMainActivity inControlerMainActivity) {
        this.controlerMainActivity = inControlerMainActivity;
        /* creation dun ecouteur de la listView avec les methodes qui peuvent etre sucharger */
        listView = ((ListView) this.controlerMainActivity.getActivity().findViewById(R.id.list_film));
        this.loadFilmListView();
    }


    public void loadFilmListView() {
        controlerMainActivity.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                AbstractTableViewListCategorie abstractTableViewListCategorie = new AbstractTableViewListCategorie(controlerMainActivity,
                        R.layout.view_rowtableview, 5) {

                };

                listView.setAdapter(abstractTableViewListCategorie);
                listView.setOnItemClickListener(abstractTableViewListCategorie);
            }


            // ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            // imageView.setImageResource(R.drawable.ic_launcher);
                        /* Animation fonction login bon ou pas */
            //checkUiLoad(validationLogin);

        });
    }
}


package app.cci.com.bliblistream.Controler.ListenerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import app.cci.com.bliblistream.Controler.ControlerActivity.ControlerMainActivity;
import app.cci.com.bliblistream.Model.StrucData.CollectionFilm;
import app.cci.com.bliblistream.Model.StrucData.User;
import app.cci.com.bliblistream.Outil.ToolKit;
import app.cci.com.bliblistream.R;
import app.cci.com.bliblistream.View.TableView.AbstractTableViewListFilm;

/**
 * Ecouteur de la View Acceuil
 *
 * @author DaRk-_-D0G on 30/09/2014.
 */
public class ListenerViewListFilm {
    private ControlerMainActivity controlerMainActivity;
    private ListView listView;

    /**
     * Constructeur
     *
     * @param inControlerMainActivity Control
     */
    public ListenerViewListFilm(ControlerMainActivity inControlerMainActivity) {
        this.controlerMainActivity = inControlerMainActivity;
        /* creation dun ecouteur de la listView avec les methodes qui peuvent etre sucharger */
        listView = ((ListView) this.controlerMainActivity.getActivity().findViewById(R.id.list_film));

        this.controlerMainActivity.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                AbstractTableViewListFilm abstractTableViewListFilm = new AbstractTableViewListFilm(controlerMainActivity,
                        R.layout.view_rowtableview, 5) {
                };
                listView.setAdapter(abstractTableViewListFilm);
            }


            // ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            // imageView.setImageResource(R.drawable.ic_launcher);
                        /* Animation fonction login bon ou pas */
            //checkUiLoad(validationLogin);

        });

        this.listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {
                        User.setFilmChoisi(CollectionFilm.getCollectionFilm().get(position));
                        controlerMainActivity.loadViewAndSetListener(R.layout.view_film);
                    }
                }
        );
    }
}


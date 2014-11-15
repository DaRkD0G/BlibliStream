package app.cci.com.bliblistream.Controleur.ListenerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import app.cci.com.bliblistream.Model.AbstractClass.AbstractTableViewListFilm;
import app.cci.com.bliblistream.Model.Class.CollectionFilm;
import app.cci.com.bliblistream.Model.Class.Film;
import app.cci.com.bliblistream.R;
import app.cci.com.bliblistream.Controleur.Control;
import app.cci.com.bliblistream.Outil.ToolKit;

/**
 *
 * Ecouteur de la View Acceuil
 * @author DaRk-_-D0G on 30/09/2014.
 */
public class ListenerViewListFilm {
    private Control control;
    int valeurClic = -2;
    /**
     * Constructeur
     * @param inControl Control
     */
    public ListenerViewListFilm(Control inControl) {

        ToolKit.log("Class init  --> ListenerViewListFilm");
        this.control = inControl;

        // TODO exemple de valeur pour une liste
        String[] values = new String[] { "Recherche", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };


        /* creation dun ecouteur de la listView avec les methodes qui peuvent etre sucharger */
        ListView listView = ((ListView) this.control.getActivity().findViewById(R.id.list_film));

        // inflater = (LayoutInflater) this.control.getActivity()
               // .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        //ArrayAdapter adapter=new ArrayAdapter<String>(this.control.getActivity(),
              //  R.layout.view_toast);
        //setListAdapter(adapter);
        //listView.addView(adapter);

     //   listView.addView(rowView);
        //TODO EXEMPLE DE FILM;

        //http://yannickstephan.com/json/film.json
         ArrayList<Film> collectionFilm;


            collectionFilm = new ArrayList<Film>();
            collectionFilm.add(null);
            collectionFilm.add(new Film());
            collectionFilm.add(new Film("test"));
            collectionFilm.add(new Film("test"));
            collectionFilm.add(new Film("test"));
            collectionFilm.add(new Film("test"));
            collectionFilm.add(new Film());
            collectionFilm.add(new Film());
            collectionFilm.add(new Film());
            collectionFilm.add(new Film("test"));
            collectionFilm.add(new Film("test"));
            collectionFilm.add(new Film());
            collectionFilm.add(new Film());



        listView.setAdapter(new AbstractTableViewListFilm(this.control.getActivity().getBaseContext(),
                R.layout.view_rowtableview, collectionFilm) {
            /**
             * Surcharge de la methode dans AbstractTableView
             * Permet de retourne la view_RowTableView.xml
             * @param position int
             * @param convertView View
             * @param parent ViewGroup
             * @return
             */
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return super.getView(position, convertView, parent);
            }

            /**
             * Surchage de la methode dans AbstractTableView
             * Permet obtenir l item courant
             * @param position int
             * @return int
             */
            @Override
            public long getItemId(int position) {
                if (valeurClic == position) {
                    ToolKit.showMessage(-1, "Clic", control.getActivity(), -1, -1);
                } else {
                    valeurClic = position;
                }
                return super.getItemId(position);
            }

            /**
             * ?
             * @return boolean
             */
            @Override
            public boolean hasStableIds() {
                return super.hasStableIds();
            }
        });

        // Ici ajout de code si des boutons ou autre coposant
    }
}


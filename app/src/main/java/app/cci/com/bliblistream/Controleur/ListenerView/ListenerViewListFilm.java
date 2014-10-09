package app.cci.com.bliblistream.Controleur.ListenerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import app.cci.com.bliblistream.Model.AbstractClass.AbstractTableView;
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
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };


        /* creation dun ecouteur de la listView avec les methodes qui peuvent etre sucharger */
        ListView listView = ((ListView) this.control.getActivity().findViewById(R.id.list_film));
        listView.setAdapter(new AbstractTableView(this.control.getActivity().getBaseContext(),
                R.layout.view_rowtableview, values) {
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
                if(valeurClic == position) {
                    ToolKit.showMessage("Double CLic !","Bien ta cliquer",control.getActivity());
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


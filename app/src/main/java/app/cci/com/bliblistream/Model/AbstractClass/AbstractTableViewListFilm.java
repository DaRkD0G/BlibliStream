package app.cci.com.bliblistream.Model.AbstractClass;

import android.content.Context;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import app.cci.com.bliblistream.Model.Class.Film;
import app.cci.com.bliblistream.Vue.ViewRowTableViewListFilm;

/**
 *
 * Listener Abstrait de la TableView
 * @author DaRk-_-D0G on 06/10/2014.
 */
public abstract class AbstractTableViewListFilm extends ArrayAdapter<Film> {
    private Context context;
    private ArrayList<Film> ArrayListFilm;
    /* ce tableau trace le clic */
   // private HashMap<Integer, Integer> mIdMap = new HashMap<Integer, Integer>();



    /**
     * Constructeur du Listener des lignes de la TableView
     * @param inContext Context
     * @param inTextViewResourceId int
     * @param inObjects String[]
     */
    public AbstractTableViewListFilm(Context inContext, int inTextViewResourceId,
                                     ArrayList<Film> inObjects) {
        super(inContext, inTextViewResourceId, inObjects);

        this.ArrayListFilm = inObjects;
        this.context = inContext;
        /* Pour chaque ligne on ajoute un tableau de pointeur */
       /* for (int i = 0; i < inObjects.size() -1; ++i) {
            mIdMap.put(i, i);
        }*/
    }

    /**
     * Methode qui serre a charger chaque ligne d'une viewRow et la personnaliser
     * @param position int
     * @param convertView View
     * @param parent ViewGroup
     * @return View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewRowTableViewListFilm view = new ViewRowTableViewListFilm(context,position,convertView,parent,this.ArrayListFilm);
        return view.getViewRow();
    }

    /**
     * Obtenir un Item
     * @param position int
     * @return long
     */
    @Override
    public long getItemId(int position) {
        Log.i("test","Position de l'item --->"+position);
       // int item = getItem(position);
        //return mIdMap.get(position);
        return position;
    }

    /**
     * ?
     * @return boolean
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }


}

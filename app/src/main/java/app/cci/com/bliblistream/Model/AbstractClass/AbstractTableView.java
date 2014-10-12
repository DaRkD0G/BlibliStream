package app.cci.com.bliblistream.Model.AbstractClass;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.HashMap;

import app.cci.com.bliblistream.Vue.ViewRow;

/**
 *
 * Listener Abstrait de la TableView
 * @author DaRk-_-D0G on 06/10/2014.
 */
public abstract class AbstractTableView extends ArrayAdapter<String> {
    private Context context;
    private String[] ArrayListText;
    /* ce tableau trace le clic */
    private HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();


    /**
     * Constructeur du Listener des lignes de la TableView
     * @param inContext Context
     * @param inTextViewResourceId int
     * @param inObjects String[]
     */
    public AbstractTableView(Context inContext, int inTextViewResourceId,
                             String[] inObjects) {
        super(inContext, inTextViewResourceId, inObjects);

        this.ArrayListText = inObjects;
        this.context = inContext;
        /* Pour chaque ligne on ajoute un tableau de pointeur */
        for (int i = 0; i < inObjects.length -1; ++i) {
            mIdMap.put(inObjects[i], i);
        }
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
        ViewRow view = new ViewRow(context,position,convertView,parent,this.ArrayListText);
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
        String item = getItem(position);
        return mIdMap.get(item);
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

package app.cci.com.bliblistream.View.ArrayAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.cci.com.bliblistream.Model.StrucData.CollectionFilm;
import app.cci.com.bliblistream.Model.StrucData.Film;
import app.cci.com.bliblistream.R;

/**
 * Adapter de la listview de mon compte
 */
public class MonCompteArrayAdapter extends ArrayAdapter<Integer> {
    private final Context context;
    private final int resourceID;

    public MonCompteArrayAdapter(Context context, int resource, ArrayList<Integer> bah) {
        super(context, resource, bah);

        this.context = context;
        this.resourceID = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(resourceID, parent, false);

        /* ID Film */
        Integer item = getItem(position);
        /* Ajout description */
        if (position < CollectionFilm.getCollectionFilm().size() - 1) {
            Film collectionFilm = CollectionFilm.getCollectionFilm().get(item);
            TextView textView = (TextView) rowView.findViewById(R.id.titreFilm);
            textView.setText(collectionFilm.getTitre());

            textView = (TextView) rowView.findViewById(R.id.descriptionFilm);
            textView.setText(collectionFilm.getDescription());
        }
        return rowView;
    }
}

package app.cci.com.bliblistream.Vue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.cci.com.bliblistream.Model.Class.Film;
import app.cci.com.bliblistream.R;

/**
 *
 * Class qui genere la Row de la tableView personnaliser
 * @author DaRk-_-D0G on 30/09/2014.
 */
public class ViewRowTableViewListFilm {

    Context context;
    int position;
    View convertView;
    ViewGroup parent;
    ArrayList<Film> arrayString;

    /**
     * Constructeur
     * @param inContext Context
     * @param inPosition int
     * @param inConvertView View
     * @param inParent ViewGroup
     * @param inArrayString String[]
     */
    public ViewRowTableViewListFilm(Context inContext, int inPosition, View inConvertView, ViewGroup inParent, ArrayList<Film> inArrayString) {
        //inActivity.
        this.context = inContext;
        this.position = inPosition;
        this.convertView = inConvertView;
        this.parent = inParent;
        this.arrayString = inArrayString;
    }

    /**
     * Obtenir la Row personnaliser de la tableView
     * @return View
     */
    public View getViewRow() {
        LayoutInflater inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView =   inflater.inflate(R.layout.view_rowtableview, this.parent, false);
        try {

        TextView titreFilmView = (TextView) rowView.findViewById(R.id.titreFilm);
        TextView descriptionView = (TextView) rowView.findViewById(R.id.descriptionFilm);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

            String nom = this.arrayString.get(this.position).getNom();
            String description = this.arrayString.get(this.position).getDescription();

            titreFilmView.setText(nom);
            descriptionView.setText(description);
            imageView.setImageResource(R.drawable.ic_launcher);
        } catch (NullPointerException e) {

        }


        // Change the icon for Windows and iPhone

        if (this.arrayString.get(this.position) == null) {

             rowView =   inflater.inflate(R.layout.view_recherche_tableview, this.parent, false);



        } else {

        }
        return (View)rowView;
    }
}

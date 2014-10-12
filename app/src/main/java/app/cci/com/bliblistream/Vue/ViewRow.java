package app.cci.com.bliblistream.Vue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import app.cci.com.bliblistream.R;

/**
 *
 * Class qui genere la Row de la tableView personnaliser
 * @author DaRk-_-D0G on 30/09/2014.
 */
public  class ViewRow {

    Context context;
    int position;
    View convertView;
    ViewGroup parent;
    String[] arrayString;

    /**
     * Constructeur
     * @param inContext Context
     * @param inPosition int
     * @param inConvertView View
     * @param inParent ViewGroup
     * @param inArrayString String[]
     */
    public ViewRow(Context inContext,int inPosition, View inConvertView, ViewGroup inParent, String[] inArrayString) {
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
        View rowView = inflater.inflate(R.layout.view_rowtableview, this.parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.titre);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(this.arrayString[this.position]);
        // Change the icon for Windows and iPhone
        String s = this.arrayString[this.position];
        if (s.startsWith("Windows7") || s.startsWith("iPhone")
                || s.startsWith("Solaris")) {
            imageView.setImageResource(R.drawable.ic_launcher);
        } else {
            imageView.setImageResource(R.drawable.ic_launcher);
        }
        return rowView;
    }
}

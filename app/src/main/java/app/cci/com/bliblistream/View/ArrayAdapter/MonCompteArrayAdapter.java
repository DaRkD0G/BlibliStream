package app.cci.com.bliblistream.View.ArrayAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import app.cci.com.bliblistream.Model.DownloadData.ImageData.DownloadImageTask;
import app.cci.com.bliblistream.Model.DownloadData.ImageData.ImagesCache;
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
        View v = convertView;
        CompleteListViewHolder viewHolder;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.view_rowtableview, null);
        viewHolder = new CompleteListViewHolder(v, this);
        v.setTag(viewHolder);

        /* ID Film */ /*  R.layout.view_rowtableview */
        Integer item = getItem(position);
        /* Ajout description */
        if (position < CollectionFilm.getCollectionFilm().size() - 1) {
            try {


        /* Load img */
                String img = CollectionFilm.getCollectionFilm().get(position).getLienImage();
                Bitmap bm = ImagesCache.getInstance().getImageFromWarehouse(img);

                if (bm != null) {
                    viewHolder.imageView.setImageBitmap(bm);

                } else {

                    viewHolder.imageView.setImageBitmap(null);
                    viewHolder.imgTask.execute(img);
                }

                ArrayList<Film> col = CollectionFilm.getCollectionFilm();
                String nom = col.get(position).getTitre();
                String description = col.get(position).getDescription();
                String id = col.get(position).getId().toString();

                viewHolder.titreFilmView.setText(nom);
                viewHolder.descriptionView.setText(description);
                viewHolder.idRecuperationFilm.setText(id);
            } catch (IndexOutOfBoundsException e) {
                //ToolKitanimateErrorLogin("Chargement, relancer votre demande.");
            }

        } else {
            return inflater.inflate(R.layout.blank_layout, parent,
                    false);
        }
        return v;
    }

    class CompleteListViewHolder {
        public TextView titreFilmView;
        public TextView idRecuperationFilm;
        public TextView descriptionView;
        public ImageView imageView;
        public DownloadImageTask imgTask;
        public ProgressBar progressBar;

        public CompleteListViewHolder(View base, BaseAdapter baseAdapter) {
            imgTask = new DownloadImageTask(baseAdapter, 500, 200);
            imageView = (ImageView) base.findViewById(R.id.icon);
            progressBar = (ProgressBar) base.findViewById(R.id.progressBar);
            titreFilmView = (TextView) base.findViewById(R.id.titreFilm);
            descriptionView = (TextView) base.findViewById(R.id.descriptionFilm);
            idRecuperationFilm = (TextView) base.findViewById(R.id.idFilm);
        }
    }
}

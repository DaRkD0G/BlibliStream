package app.cci.com.bliblistream.View.TableView;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import app.cci.com.bliblistream.Controler.ControlerActivity.ControlerMainActivity;
import app.cci.com.bliblistream.Model.DownloadData.ImageData.DownloadImageTask;
import app.cci.com.bliblistream.Model.DownloadData.ImageData.ImagesCache;
import app.cci.com.bliblistream.Model.StrucData.CollectionFilm;
import app.cci.com.bliblistream.Model.StrucData.Film;
import app.cci.com.bliblistream.R;


/**
 * Listener Abstrait de la TableView
 *
 * @author DaRk-_-D0G on 06/10/2014.
 */
public class AbstractTableViewListFilmNouveaute extends BaseAdapter {
    private Context mContext;

    /**
     * Constructeur du Listener des lignes de la TableView
     *
     * @param inContext            Context
     * @param inTextViewResourceId int
     * @param inObjects            String[]
     */
    public AbstractTableViewListFilmNouveaute(ControlerMainActivity controlerMainActivity, int inTextViewResourceId, Integer limit) {
        this.mContext = controlerMainActivity.getActivity().getApplicationContext();
    }

    @Override
    public Object getItem(int position) {
        return CollectionFilm.getCollectionFilm().get(position);
    }

    @Override
    public int getCount() {
        return CollectionFilm.getCollectionFilm().size();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    /**
     * Methode qui serre a charger chaque ligne d'une viewRow et la personnaliser
     *
     * @param position    int
     * @param convertView View
     * @param parent      ViewGroup
     * @return View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        CompleteListViewHolder viewHolder;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.view_rowtableview, null);
        viewHolder = new CompleteListViewHolder(v, this);
        v.setTag(viewHolder);

        if (CollectionFilm.getCollectionFilm().get(position).isNouveaute()) {
            v.setVisibility(View.VISIBLE);

        } else {
            return inflater.inflate(R.layout.blank_layout, parent,
                    false);
        }


        String img = CollectionFilm.getCollectionFilm().get(position).getLienImage();
        Bitmap bm = ImagesCache.getInstance().getImageFromWarehouse(img);

        if (bm != null) {
            viewHolder.imageView.setImageBitmap(bm);

        } else {
            viewHolder.imageView.setImageBitmap(null);
            viewHolder.imgTask.execute(img);
            viewHolder.imageView.setImageBitmap(null);

        }

        ArrayList<Film> col = CollectionFilm.getCollectionFilm();
        String nom = col.get(position).getTitre();
        String description = col.get(position).getDescription();
        String id = col.get(position).getId().toString();

        viewHolder.titreFilmView.setText(nom);
        viewHolder.descriptionView.setText(description);
        viewHolder.idRecuperationFilm.setText(id);

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

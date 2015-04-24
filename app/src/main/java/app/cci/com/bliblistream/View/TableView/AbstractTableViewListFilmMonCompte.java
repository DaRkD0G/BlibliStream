package app.cci.com.bliblistream.View.TableView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import app.cci.com.bliblistream.Controler.ControlerActivity.ControlerMainActivity;
import app.cci.com.bliblistream.Model.DownloadData.ImageData.DownloadImageTask;
import app.cci.com.bliblistream.Model.DownloadData.ImageData.ImagesCache;
import app.cci.com.bliblistream.Model.StrucData.CollectionFilm;
import app.cci.com.bliblistream.Outil.ToolKit;
import app.cci.com.bliblistream.R;


/**
 * Listener Abstrait de la TableView
 *
 * @author DaRk-_-D0G on 06/10/2014.
 */
public class AbstractTableViewListFilmMonCompte extends BaseAdapter {
    private Context mContext;
    private int inTextViewResourceId;
    private ControlerMainActivity controlerMainActivity;

    /**
     * Constructeur du Listener des lignes de la TableView
     *
     * @param inContext            Context
     * @param inTextViewResourceId int
     * @param inObjects            String[]
     */
    public AbstractTableViewListFilmMonCompte(ControlerMainActivity controlerMainActivity, int inTextViewResourceId, Integer limit) {
        ToolKit.log("--POSE-->");
        this.mContext = controlerMainActivity.getActivity().getApplicationContext();
        this.controlerMainActivity = controlerMainActivity;
        this.inTextViewResourceId = inTextViewResourceId;

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
        v = inflater.inflate(inTextViewResourceId, null);
        viewHolder = new CompleteListViewHolder(v, this);
        v.setTag(viewHolder);

        try {
            Integer id = controlerMainActivity.filtreFilm.get(position);
            v.setVisibility(View.VISIBLE);
        } catch (IndexOutOfBoundsException e) {
            return inflater.inflate(R.layout.blank_layout, parent,
                    false);
        }


        String nom = CollectionFilm.getCollectionFilm().get(position).getTitre();
        String description = CollectionFilm.getCollectionFilm().get(position).getDescription();

        viewHolder.titreFilmView.setText(nom);
        viewHolder.descriptionView.setText(description);

        return v;
    }

    /**
     * Obtenir un Item
     *
     * @param position int
     * @return long
     */
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

    /**
     * ?
     *
     * @return boolean
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    class CompleteListViewHolder {
        public TextView titreFilmView;
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
        }
    }
}

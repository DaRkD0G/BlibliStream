package app.cci.com.bliblistream.View.TableView;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import app.cci.com.bliblistream.Controler.ControlerActivity.ControlerMainActivity;
import app.cci.com.bliblistream.Model.DownloadData.ImageData.DownloadImageTask;
import app.cci.com.bliblistream.Model.DownloadData.ImageData.ImagesCache;
import app.cci.com.bliblistream.Model.StrucData.Categorie;
import app.cci.com.bliblistream.Model.StrucData.CollectionCategorie;
import app.cci.com.bliblistream.Outil.ToolKit;
import app.cci.com.bliblistream.R;


/**
 * Listener Abstrait de la TableView
 *
 * @author DaRk-_-D0G on 06/10/2014.
 */
public class AbstractTableViewListCategorie extends BaseAdapter implements AdapterView.OnItemClickListener {
    private Context mContext;
    int inTextViewResourceId;
    private ControlerMainActivity controlerMainActivity;

    /**
     * Constructeur du Listener des lignes de la TableView
     *
     * @param inContext            Context
     * @param inTextViewResourceId int
     * @param inObjects            String[]
     */
    public AbstractTableViewListCategorie(ControlerMainActivity controlerMainActivity, int inTextViewResourceId, Integer limit) {
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
        v = inflater.inflate(R.layout.view_rowtableview, null);
        viewHolder = new CompleteListViewHolder(v, this);
        v.setTag(viewHolder);
        String img = CollectionCategorie.getCollectionCategorie().get(position).getLienImage();
        Bitmap bm =  ImagesCache.getInstance().getImageFromWarehouse(img);

        if (bm != null) {
            viewHolder.imageView.setImageBitmap(bm);
        } else {
            viewHolder.imageView.setImageBitmap(null);
            viewHolder.imgTask.execute(img);
            viewHolder.imageView.setImageBitmap(null);
        }

        String nom = CollectionCategorie.getCollectionCategorie().get(position).getTitre();
        String description = CollectionCategorie.getCollectionCategorie().get(position).getDescription();
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
    public Categorie getItem(int position) { return CollectionCategorie.getCollectionCategorie().get(position); }

    @Override
    public int getCount() {
        return CollectionCategorie.getCollectionCategorie().size();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view,
                            int position, long id) {
        controlerMainActivity.setFiltreIdCat(CollectionCategorie.getCollectionCategorie().get(position).getId());
        controlerMainActivity.loadViewAndSetListener(R.layout.view_listfilmcat);
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


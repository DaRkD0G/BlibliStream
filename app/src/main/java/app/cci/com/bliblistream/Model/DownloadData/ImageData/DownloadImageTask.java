package app.cci.com.bliblistream.Model.DownloadData.ImageData;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import app.cci.com.bliblistream.Outil.ToolKit;


/**
 * Created by DaRk-_-D0G on 13/12/14.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private int inSampleSize = 0;

    private String imageUrl;

    private BaseAdapter adapter;

    private ImagesCache cache;

    private int desiredWidth, desiredHeight;

    private Bitmap image = null;

    private ImageView ivImageView;

    public DownloadImageTask(BaseAdapter adapter, int desiredWidth, int desiredHeight) {
        this.adapter = adapter;

        this.cache = ImagesCache.getInstance();

        this.desiredWidth = desiredWidth;

        this.desiredHeight = desiredHeight;
    }

    public DownloadImageTask(ImagesCache cache, ImageView ivImageView, int desireWidth, int desireHeight) {
        this.cache = cache;

        this.ivImageView = ivImageView;

        this.desiredHeight = desireHeight;

        this.desiredWidth = desireWidth;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        imageUrl = params[0];

        return getImage(imageUrl);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);

        if (result != null) {
            cache.addImageToWarehouse(imageUrl, result);

            if (ivImageView != null) {
                ivImageView.setImageBitmap(result);
            } else {

            }

            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    private Bitmap getImage(String imageUrl) {
        if (cache.getImageFromWarehouse(imageUrl) == null) {
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inJustDecodeBounds = true;

            options.inSampleSize = inSampleSize;

            try {
                URL url = new URL(imageUrl);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                InputStream stream = connection.getInputStream();

                image = BitmapFactory.decodeStream(stream, null, options);

                int imageWidth = options.outWidth;

                int imageHeight = options.outHeight;

                if (imageWidth > desiredWidth || imageHeight > desiredHeight) {
                    System.out.println("imageWidth:" + imageWidth + ", imageHeight:" + imageHeight);

                    inSampleSize = inSampleSize + 2;

                    getImage(imageUrl);
                } else {
                    options.inJustDecodeBounds = false;

                    connection = (HttpURLConnection) url.openConnection();

                    stream = connection.getInputStream();

                    image = BitmapFactory.decodeStream(stream, null, options);

                    return image;
                }
            } catch (Exception e) {
                //Log.e("getImage", e.toString());
            }
        }

        return image;
    }
}
//http://stackoverflow.com/questions/1945201/android-image-caching
/* ACTIVITY */
/*
ImageView imv = (ImageView) findViewById(R.id.imageView);
ImagesCache cache = ImagesCache.getInstance();//Singleton instance handled in ImagesCache class.
cache.initializeCache();

String img = "your_image_url_here";

Bitmap bm = cache.getImageFromWareHouse(img);

if(bm != null)
{
  imv.setImageBitmap(bm);
}
else
{
  imv.setImageBitmap(null);

  DownloadImageTask imgTask = new DownloadImageTask(cache, imv, 300, 300);//Since you are using it from `Activity` call second Constructor.

  imgTask.execute(img);
}
*/
/* Adapater*/
/*
ImageView imv = (ImageView) rowView.findViewById(R.id.imageView);
ImagesCache cache = ImagesCache.getInstance();
cache.initializeCache();

String img = "your_image_url_here";

Bitmap bm = cache.getImageFromWareHouse(img);

if(bm != null)
{
  imv.setImageBitmap(bm);
}
else
{
  imv.setImageBitmap(null);

  DownloadImageTask imgTask = new DownloadImageTask(this, 300, 300);//Since you are using it from `Adapter` call first Constructor.

  imgTask.execute(img);
}
*/



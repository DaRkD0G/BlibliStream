package app.cci.com.bliblistream.Model.AbstractClass;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.util.Objects;

import app.cci.com.bliblistream.Outil.ToolKit;
import app.cci.com.bliblistream.R;

/**
 * Created by DaRk-_-D0G on 12/12/14.
 */

public class AsyncExecuteWeb extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    ProgressBar progressBar;
    Context context;
    public AsyncExecuteWeb(ImageView bmImage,ProgressBar progressBar, Context context) {
        this.bmImage = bmImage;
        this.progressBar = progressBar;
        this.context = context;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            ToolKit.log("Error" + e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {

        ToolKit.animationThis(R.anim.animation_fadin,progressBar,context);
        ToolKit.animationThis(R.anim.animation_fadout,bmImage,context);
        progressBar.setVisibility(View.INVISIBLE);
        bmImage.setImageBitmap(result);
    }
}
package app.cci.com.bliblistream.Activity;

/**
 * Created by DaRk-_-D0G on 13/12/14.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.net.URI;

import app.cci.com.bliblistream.Model.StrucData.User;
import app.cci.com.bliblistream.R;

/**
 * Lecteur de video
 */
public class VideoViewActivity extends Activity {

    /**
     * Lancement de l'activity
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_video);

        getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setTitle("Charge Video Streaming");
        pDialog.setMessage("Chargement ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        final VideoView videoview = (VideoView) findViewById(R.id.VideoView);

        MediaController mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(videoview);

        videoview.setMediaController(mediacontroller);
        videoview.requestFocus();
        videoview.setVideoURI(Uri.parse(getIntent().getStringExtra("video")));
        videoview.requestFocus();
        videoview.start();

        /**
         * En cas d'erreur
         */
        videoview.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                pDialog.dismiss();
                finish();
                return false;
            }
        });
        /**
         * Lecture de video
         */
        videoview.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                videoview.start();
                pDialog.dismiss();
            }
        });
        /**
         * Video fini
         */
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });
    }
}
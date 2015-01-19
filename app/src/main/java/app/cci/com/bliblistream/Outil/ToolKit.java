package app.cci.com.bliblistream.Outil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.media.Image;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

import app.cci.com.bliblistream.R;

/**
 *
 * Class des tools
 * @author DaRk-_-D0G on 06/10/2014.
 */
public class ToolKit {

    public static void log(String inParam) {
        Log.i("test1", "##################################");
        Log.i("test1", inParam);
        Log.i("test1", "##################################");
    }
    public static void logWTF(String inParam) {
        Log.w("test1", "##################################");
        Log.w("test1", inParam);
        Log.w("test1", "##################################");
    }
    public static void showMessage(int inImage, String inMessage, final Activity inActivity, int x, int y) {

        LayoutInflater inflater = inActivity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.view_toast, (ViewGroup) inActivity.findViewById(R.id.custom_toast_layout_id));

        try {
            ImageView image = (ImageView) layout.findViewById(R.id.toast_image);
            if(inImage != -1) {

                image.setImageResource(inImage);
            }
            // set a message
            TextView text = (TextView) layout.findViewById(R.id.toast_text);
            text.setText(inMessage);
        } catch (NullPointerException e) {
            logWTF("<!> TOAST erreur image ou Text vide");
        }



        // Toast...
        Toast toast = new Toast(inActivity.getBaseContext());
        if(x == -1 || y == -1){
            x=0;
            y=0;
        }

        toast.setGravity(Gravity.TOP, x, y);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        toast.show();
    }

    /**
     * Obtenir la taille de lecran
     * @param inActivity Activity
     * @return Point
     */
    public static Point getSize(Activity inActivity) {
        Display display = inActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    /**
     * Tableau de la taille ecran
     * @return Interger[]
     */
    public static int[][] sizeScreen(Activity inActivity) {
        Point point = ToolKit.getSize(inActivity);

        int[][] relations= new int[5][2];

        relations[0][0] = point.x;
        relations[0][1] = point.y;


        relations[1][0] = point.x/8;
        relations[1][1] = point.y/8;

        relations[2][0] = point.x/4;
        relations[2][1] = point.y/4;

        relations[3][0] = (point.x)*3/8;
        relations[3][1] = (point.y)*3/8;

        relations[4][0] = point.x/2;
        relations[4][1] = point.y/2;



        return relations;
    }


    /**
     * Enables/Disables all child views in a view group.
     *
     * @param viewGroup the view group
     * @param enabled <code>true</code> to enable, <code>false</code> to disable
     * the views.
     */
    public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }

    static public void animationThis(int inTypeAnimation, View inView, Context context) {
        Animation animation = AnimationUtils.loadAnimation(context,
                inTypeAnimation);

        animation.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {

            }
            @Override
            public void onAnimationRepeat(Animation arg0) {

            }
            @Override
            public void onAnimationEnd(Animation arg0) {

            }
        });
        inView.startAnimation(animation);

    }
  /*  static public void uiTrhead(Activity uActivity,final Object objectReception, final Method uMethod,final Object uParameters) throws Exception {
        uActivity.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    uMethod.invoke(objectReception, uParameters);
                } catch (IllegalAccessException e) {

                } catch (InvocationTargetException e) {

                }

               /* if(validationLogin) {
                    control.loadViewAndSetListener(R.layout.view_accueil);
                } else {
                    setEnableElement(true);
                    animateErrorLogin();
                    view3.setVisibility(View.INVISIBLE);
                    control.animationThis(R.anim.animation_fadin,view3);
                }
            }
        });

    }*/
    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}

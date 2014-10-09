package app.cci.com.bliblistream.Outil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

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

    public static void showMessage(String inTitle, String inMessage, final Context context) {
        new AlertDialog.Builder(context)
                .setTitle(inTitle)
                .setMessage(inMessage)
                .setIcon(android.R.drawable.presence_online)
                .show();
            int saucisse = 55;
                     /*                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })*/
    }

}

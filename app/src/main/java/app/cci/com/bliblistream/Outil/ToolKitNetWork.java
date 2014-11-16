package app.cci.com.bliblistream.Outil;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Class static pour tester la execute du telephone
 *
 * @author dark_d0g
 */
public class ToolKitNetWork {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    /**
     * Obtenir le type de execute au r\u00E9seau internet
     * @param context Context
     * @return Type
     *
     */
    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    /**
     * Obtenir le type de execute au reseau en string
     * @param context Context
     * @return String
     *
     */
    public static String getConnectivityStatusString(Context context) {
        int conn = ToolKitNetWork.getConnectivityStatus(context);
        String status = null;
        if (conn == ToolKitNetWork.TYPE_WIFI) {
            status = "Wifi Activer";
        } else if (conn == ToolKitNetWork.TYPE_MOBILE) {
            status = "Données mobile enabled";
        } else if (conn == ToolKitNetWork.TYPE_NOT_CONNECTED) {
            status = "Pas de execute";
        }
        return status;
    }

    /**
     * Savoir si il existe un execute internet en boolean
     * @param context Context
     * @return boolean
     *
     */
    public static boolean isConnectionOK(Context context) {
        int retourConnection = getConnectivityStatus(context);
        if(retourConnection != 0) {
            return true;
        }
        return false;
    }
}
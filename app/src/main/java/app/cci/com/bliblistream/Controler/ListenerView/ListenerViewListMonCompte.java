package app.cci.com.bliblistream.Controler.ListenerView;

import android.widget.ListView;

import app.cci.com.bliblistream.Controler.Control;
import app.cci.com.bliblistream.View.TableView.AbstractTableViewListFilmNouveaute;
import app.cci.com.bliblistream.Model.JsonData.MyHttpClientListMonCompte;
import app.cci.com.bliblistream.Outil.ToolKit;

/**
 * Ecouteur de la View Acceuil
 *
 * @author DaRk-_-D0G on 30/09/2014.
 */
public class ListenerViewListMonCompte {
    private Control control;
    int valeurClic = -2;
    AbstractTableViewListFilmNouveaute a;
    private ListView listView;

    private MyHttpClientListMonCompte myHttpClientListMonCompte;

    /**
     * Constructeur
     *
     * @param inControl Control
     */
    public ListenerViewListMonCompte(Control inControl) {
        ToolKit.log("Class init  --> ListenerViewListMonCompte");




        this.control = inControl;





        /* creation dun ecouteur de la listView avec les methodes qui peuvent etre sucharger */

        myHttpClientListMonCompte = new MyHttpClientListMonCompte(control);
        myHttpClientListMonCompte.executeLoading();






    }


}


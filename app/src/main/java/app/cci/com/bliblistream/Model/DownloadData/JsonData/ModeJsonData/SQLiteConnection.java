package app.cci.com.bliblistream.Model.DownloadData.JsonData.ModeJsonData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;


/**
 * Created by DaRk-_-D0G on 27/10/14.
 * Class BDD
 */
public class SQLiteConnection {

    public static final String KEY_BOOK = "book";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_ISBN = "isbn";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_RATING = "rating";
    public static final String KEY_STATUS = "status";
    private static final String DATABASE_NAME = " nba";
    private static final String DATABASE_TABLE = "bookList";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE =
            " create table  " + DATABASE_TABLE + " ("
                    + KEY_ROWID + " integer primary key autoincrement,  "
                    + KEY_AUTHOR + " text not null, "
                    + KEY_BOOK + " text not null, "
                    + KEY_RATING + " text not null, "
                    + KEY_STATUS + " text not null, "
                    + KEY_ISBN + " text not null); ";
    private static String TAG = "Upgrading Database!";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private Context mCtx;

    public void SQLiteConnection(Context uContext) {
        this.mCtx = uContext;

    }

    public SQLiteConnection open() throws SQLException {

        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long createBook(String book, String author, String isbn, float rating, String status) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_BOOK, book);
        initialValues.put(KEY_AUTHOR, author);
        initialValues.put(KEY_ISBN, isbn);
        initialValues.put(KEY_RATING, rating);
        initialValues.put(KEY_STATUS, status);


        return mDb.insert(DATABASE_TABLE, null, initialValues);

    }

    public boolean deleteBook(long rowId) {
        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;

    }

    public Cursor fetchAllBooks() {
        return mDb.query(DATABASE_TABLE, new String[]{KEY_BOOK, KEY_ISBN, KEY_AUTHOR, KEY_ROWID, KEY_RATING, KEY_STATUS}, null, null, null, null, null);
    }

    public Cursor fetchCustom(String uRequete) throws SQLException {
        Cursor mCursor =
                mDb.query(DATABASE_TABLE, new String[]{KEY_BOOK, KEY_AUTHOR, KEY_ROWID, KEY_ISBN, KEY_RATING, KEY_STATUS}, uRequete, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor fetchBook(long rowId) throws SQLException {
        Cursor mCursor =
                mDb.query(DATABASE_TABLE, new String[]{KEY_BOOK, KEY_AUTHOR, KEY_ROWID, KEY_ISBN, KEY_RATING, KEY_STATUS}, KEY_ROWID + "=" +
                        rowId, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
        /* PARCOURS
            Cursor cursor = dbWritable.rawQuery(query, null);


            Contact contact = null;
            if (cursor.moveToFirst()) {
                do {
                    contact = new Contact();
                    contact.setId(Integer.parseInt(cursor.getString(0)));
                    contact.setTitre(cursor.getString(1));
                    contact.setNom(cursor.getString(2));
                    contact.setNumero(cursor.getString(3));



                    contactsAll.add(contact);
                } while (cursor.moveToNext());
            }
         */

    }

    public boolean updateBook(long rowId, String book, String author, String isbn, float rating, String status) {
        ContentValues args = new ContentValues();
        args.put(KEY_BOOK, book);
        args.put(KEY_AUTHOR, author);
        args.put(KEY_ISBN, isbn);
        args.put(KEY_RATING, rating);
        args.put(KEY_STATUS, status);


        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;


    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);


        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("ALTER TABLE bookList ADD COLUMN String status");

        }
    }
}
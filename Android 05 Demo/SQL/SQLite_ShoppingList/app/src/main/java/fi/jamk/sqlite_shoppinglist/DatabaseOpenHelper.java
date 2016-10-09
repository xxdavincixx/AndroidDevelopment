package fi.jamk.sqlite_shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Leo on 09.10.2016.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PTM_database";
    private final String DATABASE_TABLE = "shoppingList";
    private final String PRODUCT = "itemName";
    private final String COUNT = "itemAmount";
    private final String PRICE = "itemPrice";

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+DATABASE_TABLE+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+PRODUCT+" TEXT, "+COUNT+" REAL, " + PRICE+" FLOAT);");
        ContentValues values = new ContentValues();
        values.put(PRODUCT, "Chocolate");
        values.put(COUNT, 1);
        values.put(PRICE,1.09);
        db.insert(DATABASE_TABLE, null, values);
        values.put(PRODUCT, "Pasta");
        values.put(COUNT, 2);
        values.put(PRICE, 1.35);
        db.insert(DATABASE_TABLE, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
        onCreate(db);
    }

}

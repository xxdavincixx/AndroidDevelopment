package fi.jamk.sqlite_shoppinglist;

    import android.app.DialogFragment;
    import android.content.ContentValues;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.os.Bundle;
    import android.support.v7.app.AppCompatActivity;
    import android.view.ContextMenu;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ListAdapter;
    import android.widget.ListView;
    import android.widget.SimpleCursorAdapter;
    import android.widget.Toast;

/**
 * Created by Leo on 27/09/15.
 */
public class MainActivity extends AppCompatActivity implements AddItemDialogFragment.DialogListener {
    private final String DATABASE_TABLE = "shoppingList";
    private final int DELETE_ID = 0;
    private SQLiteDatabase db;
    private Cursor cursor;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)  findViewById(R.id.listView);
        registerForContextMenu(listView);

        db = (new DatabaseOpenHelper(this)).getWritableDatabase();
        queryData();

        float totalPrice = 0f;
        if (cursor.moveToFirst()) {
            do {
                float price = cursor.getFloat(3);
                int count = cursor.getInt(2);
                totalPrice += count * price;
            } while(cursor.moveToNext());
            Toast.makeText(getBaseContext(), "Total price: " + totalPrice, Toast.LENGTH_SHORT).show();
        }
    }

    // query data from database
    public void queryData() {
        cursor = db.rawQuery("SELECT _id, itemName, itemAmount, itemPrice FROM "+DATABASE_TABLE+/*"shoppingList*/" ORDER BY itemPrice DESC", null);
        //String[] resultColumns = new String[]{"_id","itemName","itemAmount","itemPrice"};
        //cursor = db.query(DATABASE_TABLE,resultColumns,null,null,null,null,"score DESC",null);

        ListAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.list_item, cursor,
                new String[] {"itemName", "itemAmount","itemPrice"},
                new int[] {R.id.productItemName, R.id.productItemCount, R.id.productItemPrice}
                ,0);

        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                AddItemDialogFragment eDialog = new AddItemDialogFragment();
                eDialog.show(getFragmentManager(), "Add a new item");
        }
        return false;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String item, int amount, float price) {
        ContentValues values=new ContentValues(3);
        values.put("itemName", item);
        values.put("itemAmount", amount);
        values.put("itemPrice", price);
        db.insert("shoppingList", null, values);
        queryData();
        float totalPrice = 0f;
        if (cursor.moveToFirst()) {
            do {
                float score = cursor.getFloat(3); // columnIndex
                int count = cursor.getInt(2);
                totalPrice += count * score;
            } while(cursor.moveToNext());
            Toast.makeText(getBaseContext(), "Total price: " + totalPrice, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, DELETE_ID, Menu.NONE, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                String[] args = {String.valueOf(info.id)};
                db.delete("shoppingList", "_id=?", args);
                queryData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}

package com.example.android.simpleshoppinglist.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.simpleshoppinglist.ShoppingListAdapter;

/**
 * Created by User on 22/08/17.
 */

public class DatabaseOperations {

    public static Cursor getCursor(SQLiteDatabase db){
        return db.query(ShoppingListContract.ShoppingListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ShoppingListContract.ShoppingListEntry._ID);
    }

    public static long addNewItem (SQLiteDatabase db, ShoppingListAdapter adapter, String item) {
        ContentValues cv = new ContentValues();
        cv.put(ShoppingListContract.ShoppingListEntry.COLUMN_ITEM,item);
        cv.put(ShoppingListContract.ShoppingListEntry.COLUMN_CROSSED,0);
        adapter.updateList(getCursor(db));
        return db.insert(ShoppingListContract.ShoppingListEntry.TABLE_NAME,null,cv);
    }

    public static boolean removeItem (SQLiteDatabase db, long id) {
        return db.delete(ShoppingListContract.ShoppingListEntry.TABLE_NAME, ShoppingListContract.ShoppingListEntry._ID+"="+id,null)>0;
    }
}

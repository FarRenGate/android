package com.example.android.simpleshoppinglist.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.simpleshoppinglist.ShoppingListAdapter;
import com.example.android.simpleshoppinglist.data.ShoppingListContract.ShoppingListEntry;

import static com.example.android.simpleshoppinglist.data.ShoppingListContract.ShoppingListEntry.*;

/**
 * Created by User on 22/08/17.
 */

public class DatabaseOperations {

    public static Cursor getCursor(SQLiteDatabase db){
        return db.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                _ID);
    }

    public static long addNewItem (SQLiteDatabase db, ShoppingListAdapter adapter, String item) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ITEM,item);
        cv.put(COLUMN_CROSSED,0);
        adapter.updateList(getCursor(db));
        return db.insert(TABLE_NAME,null,cv);
    }

    public static boolean removeItem (SQLiteDatabase db, long id) {
        return db.delete(TABLE_NAME, _ID+"="+id,null)>0;
    }

    public static void crossItem (SQLiteDatabase db, ShoppingListAdapter adapter, long id) {
        ContentValues cv = new ContentValues();
        String whereClause = _ID+"=?";
        String[] whereArgs = new String[] {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_NAME,
                new String[] {COLUMN_CROSSED},
                whereClause,
                whereArgs,
                null,
                null,
                null);
        cursor.moveToFirst();
        int crossedValue = cursor.getInt(cursor.getColumnIndex(COLUMN_CROSSED));
        if (crossedValue==0) crossedValue=1;
        else crossedValue=0;
        cv.put(COLUMN_CROSSED, crossedValue);
        db.update(TABLE_NAME,cv, whereClause,whereArgs);
        adapter.updateList(getCursor(db));
        cursor.close();
    }

}

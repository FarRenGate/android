package com.example.android.simpleshoppinglist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.simpleshoppinglist.data.DatabaseOperations;
import com.example.android.simpleshoppinglist.data.ShoppingListHelper;

public class MainActivity extends AppCompatActivity implements ShoppingListAdapter.ListItemClickListener{

    private ShoppingListAdapter mShoppingListAdapter;
    private SQLiteDatabase mDb;
    private EditText mItemEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView shoppingListView;

        shoppingListView = (RecyclerView)  this.findViewById(R.id.rv_ShoppingList);
        mItemEditText = (EditText) this.findViewById(R.id.et_addItem);

        shoppingListView.setLayoutManager(new LinearLayoutManager(this));

        ShoppingListHelper shoppingListDbHelper = new ShoppingListHelper(this);
        mDb = shoppingListDbHelper.getWritableDatabase();

        Cursor cursor = DatabaseOperations.getCursor(mDb);

        mShoppingListAdapter = new ShoppingListAdapter(this,cursor,this);

        shoppingListView.setAdapter(mShoppingListAdapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long) viewHolder.itemView.getTag();
                DatabaseOperations.removeItem(mDb,id);
                mShoppingListAdapter.updateList(DatabaseOperations.getCursor(mDb));
            }
        }

        ).attachToRecyclerView(shoppingListView);


    }

    public void addToShoppingList(View view) {
        if (mItemEditText.getText().length()==0) return;
        DatabaseOperations.addNewItem(mDb, mShoppingListAdapter, mItemEditText.getText().toString());
        mItemEditText.setText("");
    }



    @Override
    public void onItemClick(int clickedItem) {
        Toast.makeText(this,"item clicked",Toast.LENGTH_SHORT).show();
    }
}

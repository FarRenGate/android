package com.example.android.simpleshoppinglist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.simpleshoppinglist.data.ShoppingListContract;

/**
 * Created by User on 22/08/17.
 */

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onItemClick (int clickedItem);
    }


    public ShoppingListAdapter(Context context, Cursor cursor, ListItemClickListener listener) {
        mContext=context;
        mCursor=cursor;
        mOnClickListener=listener;
    }

    @Override
    public ShoppingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.shopping_list_item,parent,false);
        return new ShoppingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShoppingListViewHolder holder, int position) {

        if (!mCursor.moveToPosition(position))
            return;

        String listItem=mCursor.getString(mCursor.getColumnIndex(ShoppingListContract.ShoppingListEntry.COLUMN_ITEM));
        int crossed = mCursor.getInt(mCursor.getColumnIndex(ShoppingListContract.ShoppingListEntry.COLUMN_CROSSED));
        long id = mCursor.getLong(mCursor.getColumnIndex(ShoppingListContract.ShoppingListEntry._ID));

        holder.itemTextView.setText(listItem);
        holder.itemView.setTag(id);

        if (crossed==1) {
            holder.itemTextView.setPaintFlags(holder.itemTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.itemTextView.setPaintFlags(holder.itemTextView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class ShoppingListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView itemTextView;

        public ShoppingListViewHolder(View itemView) {
            super(itemView);
            itemTextView = (TextView) itemView.findViewById(R.id.tv_list_element);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedItem = getAdapterPosition();
            mOnClickListener.onItemClick(clickedItem);
        }
    }



    public void updateList (Cursor newCursor) {
        if (mCursor!=null) mCursor.close();
        mCursor=newCursor;
        if (newCursor!=null)
            this.notifyDataSetChanged();
    }

}

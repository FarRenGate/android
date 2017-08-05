package com.example.android.minesweeper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Oleg on 28.07.2017.
 */

public class GridCellAdapter extends RecyclerView.Adapter<GridCellAdapter.CellViewHolder> {

    final private GridCellClickListener mOnClickListener;

    private String[] mCellState;
    private int mNumberCells;

    public GridCellAdapter(int numberOfCells, GridCellClickListener listener) {
        mNumberCells=numberOfCells;
        mOnClickListener=listener;
        mCellState=new String[mNumberCells];
    }

    @Override
    public CellViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int idOfCellLayout = R.layout.grid_cell;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(idOfCellLayout,viewGroup,shouldAttachToParentImmediately);

        CellViewHolder viewHolder = new CellViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CellViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberCells;
    }

    public interface GridCellClickListener {
        void onCellClick(int clickedItemIndex);
    }


    public class CellViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textCellNumber;

        public CellViewHolder(View itemView) {
            super(itemView);
            textCellNumber = (TextView) itemView.findViewById(R.id.cell_text);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            //textCellNumber.setText(String.valueOf(position));
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onCellClick(clickedPosition);
        }
    }
}


package com.example.android.minesweeper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Oleg on 28.07.2017.
 */

public class GridCellAdapter extends RecyclerView.Adapter<GridCellAdapter.CellViewHolder> {

    final private GridCellClickListener mOnClickListener;
    final private GridCellLongClickListener mOnLongClickListener;

    private Game game;
    private int mNumberCells;

    public GridCellAdapter(int numberOfCells, GridCellClickListener listener, GridCellLongClickListener longClickListener) {
        mNumberCells=numberOfCells;
        mOnClickListener=listener;
        mOnLongClickListener=longClickListener;
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

    public interface GridCellLongClickListener {
        void onCellLongClick(int clickedItemIndex);
    }

    public void startNewGame(int width, int height, int numberOfMines) {
        game = new Game(width,height,numberOfMines);
        notifyDataSetChanged();
    }

    public String bombsLeft(){
        if (game!=null) {
        return game.toString();
        } else {
            return "";
        }
    }



    public class CellViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView textCellNumber;

        public CellViewHolder(View itemView) {
            super(itemView);
            textCellNumber = (TextView) itemView.findViewById(R.id.cell_text);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bind(int position) {
            if (game!=null) {
                textCellNumber.setText(game.getCellState(position));
            } else {
                textCellNumber.setText("");
            }
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();

            mOnClickListener.onCellClick(clickedPosition);
            if (game==null) return;
            game.click(clickedPosition);
            notifyDataSetChanged();


        /*    if (!game.isInGame()) {
                game=null;
            }*/

        }

        @Override
        public boolean onLongClick(View v) {
            int clickedPosition=getAdapterPosition();
            mOnLongClickListener.onCellLongClick(clickedPosition);
            if (game==null) {
                return true;
            }
            game.rightClick(clickedPosition);
            notifyDataSetChanged();
           /* if (!game.isInGame()) {
                game=null;
            }*/
            return true;
        }
    }
}


package com.example.android.sunshine.utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.sunshine.R;

/**
 * Created by User on 24/06/17.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

    private String[] mWeatherData;

    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.forecast_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem,parent,false);
        ForecastAdapterViewHolder forecastAdapterViewHolder =  new ForecastAdapterViewHolder(view);
        return forecastAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder holder, int position) {
        if (mWeatherData.length>position) {
            holder.bind(mWeatherData[position]);
        }
        else {
            holder.bind("error");
        }
    }

    @Override
    public int getItemCount() {
        if (mWeatherData==null) {
            return 0;
        }
        else {
            return mWeatherData.length;
        }
    }

    public void setWeatherData(String[] weatherData){
        mWeatherData=weatherData;
        notifyDataSetChanged();
    }

    public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView mWeatherTextView;

        public ForecastAdapterViewHolder(View itemView) {
            super(itemView);
            mWeatherTextView = (TextView) itemView.findViewById(R.id.tv_weather_data);
        }

        public void bind (String s) {
            mWeatherTextView.setText(s);
        }
    }
}

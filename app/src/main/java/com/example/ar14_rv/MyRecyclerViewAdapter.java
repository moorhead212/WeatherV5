package com.example.ar14_rv;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private final ArrayList<String> mv_data;
    private WeatherInfo weatherInfo;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView cv_tvCity, cv_tvTemp, cv_tvIcon;
        private LinearLayout cv_container;

        public MyViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            cv_tvCity = view.findViewById(R.id.tv_city);
            cv_tvTemp = view.findViewById(R.id.tv_temp);
            cv_tvIcon = view.findViewById(R.id.tv_icon);
            cv_container = view.findViewById(R.id.container);

        }
    }

    public MyRecyclerViewAdapter(ArrayList<String> data) {
        mv_data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View lv_view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_row, parent, false);
        return new MyViewHolder(lv_view);
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.MyViewHolder holder, int position) {
        //// HERE

        OpenWeatherMapAPI api = new OpenWeatherMapAPI();
        weatherInfo = api.getWeatherInfo(mv_data.get(position));

        holder.cv_tvCity.setText(weatherInfo.getCity());
        holder.cv_tvTemp.setText(String.valueOf((int) weatherInfo.getCurrentTemp()) + "°F");
        holder.cv_container.setBackgroundColor(backgroundColor((int) weatherInfo.getCurrentTemp()));


        Map<String, Integer> conditionToIconMap = new HashMap<>();
        conditionToIconMap.put("thunderstorm", R.string.icon_thunderstorm);
        conditionToIconMap.put("drizzle", R.string.icon_drizzle);
        conditionToIconMap.put("rain", R.string.icon_rain);
        conditionToIconMap.put("snow", R.string.icon_snow);
        conditionToIconMap.put("fog", R.string.icon_fog);
        conditionToIconMap.put("clear", R.string.icon_clear);
        conditionToIconMap.put("clouds", R.string.icon_clouds);

        holder.cv_tvIcon.setText(conditionToIconMap.getOrDefault(weatherInfo.getCurrentCondition().toLowerCase(), R.string.icon_unknown));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewPage.class);
                intent.putExtra("zipcode", mv_data.get(position));
                view.getContext().startActivity(intent);
            }
        });
    }

    public int backgroundColor(int temp) {
        if (temp >= 120) return Color.rgb(250, 0, 0); //0xFA0000;
        else if (temp >= 100) return Color.rgb(250, 85, 0); //0xFA5500;
        else if (temp >= 80) return Color.rgb(240, 250, 0); //0xF0FA00;
        else if (temp >= 60) return Color.rgb(40, 250, 0); //0x28FA00;
        else if (temp >= 32) return Color.rgb(0, 250, 165); //0x00FAA5;
        else if (temp >= 0) return Color.rgb(0, 195, 250); //0x00C3FA;
        else if (temp >= -20) return Color.rgb(0, 115, 250); //0x0073FA;
        else return 0x0000FA;
    }

    @Override
    public int getItemCount() {
        //// HERE
        return mv_data.size();
    }
}
package com.example.ar14_rv;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WeatherFragment extends Fragment {

    private String zipCode, cityName, currentCondition, forecast1, forecast2, forecast3, units;
    private int currentTemp, currentCTemp, lowTemp, currentCLow, highTemp, currentCHigh;
    private TextView cityTextView, currentConditionTextView, currentTempTextView, lowTempTextView,
            highTempTextView, currentIcon, forecast24TextView, forecast24Icon, forecast48TextView,
            forecast48Icon, forecast96TextView, forecast96Icon;
    //private Button changeUnits;

    private View view;

    private OpenWeatherMapAPI api;
    private WeatherInfo weatherInfo;


    public static WeatherFragment newInstance(String zipCode) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString("zipCode", zipCode);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            zipCode = getArguments().getString("zipCode");
        }

        api = new OpenWeatherMapAPI();
        weatherInfo = api.getWeatherInfo(zipCode);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main, container, false);
        units = "F";
        // Get the current day of the week
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        String dayOfWeekString24 = new SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        String dayOfWeekString48 = new SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        String dayOfWeekString96 = new SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.getTime());
/*
        cityTextView = view.findViewById(R.id.location);
        currentConditionTextView = view.findViewById(R.id.todayText);
        currentTempTextView = view.findViewById(R.id.todayTemp);
        lowTempTextView = view.findViewById(R.id.todayLow);
        highTempTextView = view.findViewById(R.id.todayHigh);
        currentIcon = view.findViewById(R.id.todayClimacon);

        forecast24TextView = view.findViewById(R.id.nxtDay01);
        forecast24Icon = view.findViewById(R.id.weather_01);
        forecast48TextView = view.findViewById(R.id.nxtDay02);
        forecast48Icon = view.findViewById(R.id.weather_02);
        forecast96TextView = view.findViewById(R.id.nxtDay03);
        forecast96Icon = view.findViewById(R.id.weather_03);
        */

        switch(units) {
            case "F":
                currentTemp = (int) weatherInfo.getCurrentTemp();
                lowTemp = (int) weatherInfo.getLowTemp();
                highTemp = (int) weatherInfo.getHighTemp();
                break;
            case "C":
                currentTemp = (int) FtoC(weatherInfo.getCurrentTemp());
                lowTemp = (int) FtoC(weatherInfo.getLowTemp());
                highTemp = (int) FtoC(weatherInfo.getHighTemp());
        }

        view.setBackgroundColor(backgroundColor(currentTemp));

        currentCondition = weatherInfo.getCurrentCondition();

        forecast1 = weatherInfo.getForecast1();
        forecast2 = weatherInfo.getForecast2();
        forecast3 = weatherInfo.getForecast3();

        forecast24TextView.setText(String.valueOf(dayOfWeekString24.substring(0, 3)));
        forecast48TextView.setText(String.valueOf(dayOfWeekString48.substring(0, 3)));
        forecast96TextView.setText(String.valueOf(dayOfWeekString96.substring(0, 3)));

        currentTempTextView.setText(String.valueOf(currentTemp + "°" + units));
        lowTempTextView.setText(String.valueOf("L " + lowTemp + "°"));
        highTempTextView.setText(String.valueOf("H " + highTemp + "°"));


        Map<String, Integer> conditionToIconMap = new HashMap<>();
        conditionToIconMap.put("thunderstorm", R.string.icon_thunderstorm);
        conditionToIconMap.put("drizzle", R.string.icon_drizzle);
        conditionToIconMap.put("rain", R.string.icon_rain);
        conditionToIconMap.put("snow", R.string.icon_snow);
        conditionToIconMap.put("fog", R.string.icon_fog);
        conditionToIconMap.put("clear", R.string.icon_clear);
        conditionToIconMap.put("clouds", R.string.icon_clouds);

        currentIcon.setText(conditionToIconMap.getOrDefault(currentCondition.toLowerCase(), R.string.icon_unknown));
        forecast24Icon.setText(conditionToIconMap.getOrDefault(forecast1.toLowerCase(), R.string.icon_unknown));
        forecast48Icon.setText(conditionToIconMap.getOrDefault(forecast2.toLowerCase(), R.string.icon_unknown));
        forecast96Icon.setText(conditionToIconMap.getOrDefault(forecast3.toLowerCase(), R.string.icon_unknown));

        cityName = weatherInfo.getCity();

        cityTextView.setText(cityName);
        currentConditionTextView.setText(currentCondition);


        return view;
    }

    private double FtoC(double temp) {
        return (temp - 32) * (5.0 / 9.0);
    }

    private double CtoF(double temp) {
        return (temp * (9.0 / 5.0)) + 32;
    }

    public void setUnits(String chUnits) {
        units = chUnits;
    }

    public int backgroundColor(int temp) {
        if (units == "F") {
            if (temp >= 120) return Color.rgb(250, 0, 0); //0xFA0000;
            else if (temp >= 100) return Color.rgb(250, 85, 0); //0xFA5500;
            else if (temp >= 80) return Color.rgb(240, 250, 0); //0xF0FA00;
            else if (temp >= 60) return Color.rgb(40, 250, 0); //0x28FA00;
            else if (temp >= 32) return Color.rgb(0, 250, 165); //0x00FAA5;
            else if (temp >= 0) return Color.rgb(0, 195, 250); //0x00C3FA;
            else if (temp >= -20) return Color.rgb(0, 115, 250); //0x0073FA;
            else return 0x0000FA;
        } else {
            if (temp >= 48) return Color.rgb(250, 0, 0); //0xFA0000;
            else if (temp >= 37) return Color.rgb(250, 85, 0); //0xFA5500;
            else if (temp >= 26) return Color.rgb(240, 250, 0); //0xF0FA00;
            else if (temp >= 15) return Color.rgb(40, 250, 0); //0x2AFA00;
            else if (temp >= 0) return Color.rgb(0, 250, 165); //0x00FAA5;
            else if (temp >= -17) return Color.rgb(0, 195, 250); //0x00C3FA;
            else if (temp >= -28) return Color.rgb(0, 115, 250); //0x0073FA;
            else return 0x0000FA;
        }
    }
}
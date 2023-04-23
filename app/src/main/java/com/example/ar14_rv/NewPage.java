package com.example.ar14_rv;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class NewPage extends AppCompatActivity {
    RelativeLayout relativeLayout;
    private TextView tv_City, tv_Icon, tv_Temp, tv_nxtDay1, tv_nxtDay2, tv_nxtDay3,
            tv_nextForecast1, tv_nextForecast2, tv_nextForecast3, tv_nextHigh1, tv_nextHigh2, tv_nextHigh3,
            tv_nextLow1, tv_nextLow2, tv_nextLow3;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_page);
        Intent intent = getIntent();
        String zipCode = intent.getStringExtra("zipcode");

        OpenWeatherMapAPI api = new OpenWeatherMapAPI();
        WeatherInfo weatherInfo = api.getWeatherInfo(zipCode);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        String dayOfWeekString24 = new SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        String dayOfWeekString48 = new SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        String dayOfWeekString96 = new SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.getTime());

        ConstraintLayout rl = (ConstraintLayout) findViewById(R.id.new_page);

        tv_City = findViewById(R.id.tv_cityBig);
        tv_Icon = findViewById(R.id.tv_iconBig);
        tv_Temp = findViewById(R.id.tv_tempBig);

        tv_nxtDay1 = findViewById(R.id.nxtDay01);
        tv_nxtDay2 = findViewById(R.id.nxtDay02);
        tv_nxtDay3 = findViewById(R.id.nxtDay03);

        tv_nextHigh1 = findViewById(R.id.nxtHigh01);
        tv_nextHigh2 = findViewById(R.id.nxtHigh02);
        tv_nextHigh3 = findViewById(R.id.nxtHigh03);

        tv_nextLow1 = findViewById(R.id.nxtLow01);
        tv_nextLow2 = findViewById(R.id.nxtLow02);
        tv_nextLow3 = findViewById(R.id.nxtLow03);


        tv_nextForecast1 = findViewById(R.id.nxtForecast01);
        tv_nextForecast2 = findViewById(R.id.nxtForecast02);
        tv_nextForecast3 = findViewById(R.id.nxtForecast03);

        tv_nxtDay1.setText(String.valueOf(dayOfWeekString24.substring(0, 3)));
        tv_nxtDay2.setText(String.valueOf(dayOfWeekString48.substring(0, 3)));
        tv_nxtDay3.setText(String.valueOf(dayOfWeekString96.substring(0, 3)));

        tv_nextHigh1.setText(String.valueOf((int) weatherInfo.getHigh1()));
        tv_nextHigh2.setText(String.valueOf((int) weatherInfo.getHigh2()));
        tv_nextHigh3.setText(String.valueOf((int) weatherInfo.getHigh3()));

        tv_nextLow1.setText(String.valueOf((int) weatherInfo.getLow1()));
        tv_nextLow2.setText(String.valueOf((int) weatherInfo.getLow2()));
        tv_nextLow3.setText(String.valueOf((int) weatherInfo.getLow3()));



        tv_City.setText(weatherInfo.getCity());
        tv_Temp.setText(String.valueOf((int)weatherInfo.getCurrentTemp()) + "°F");

        Map<String, Integer> conditionToIconMap = new HashMap<>();
        conditionToIconMap.put("thunderstorm", R.string.icon_thunderstorm);
        conditionToIconMap.put("drizzle", R.string.icon_drizzle);
        conditionToIconMap.put("rain", R.string.icon_rain);
        conditionToIconMap.put("snow", R.string.icon_snow);
        conditionToIconMap.put("fog", R.string.icon_fog);
        conditionToIconMap.put("clear", R.string.icon_clear);
        conditionToIconMap.put("clouds", R.string.icon_clouds);

        tv_Icon.setText(conditionToIconMap.getOrDefault(weatherInfo.getCurrentCondition().toLowerCase(), R.string.icon_unknown));
        tv_nextForecast1.setText(conditionToIconMap.getOrDefault(weatherInfo.getForecast1().toLowerCase(), R.string.icon_unknown));
        tv_nextForecast2.setText(conditionToIconMap.getOrDefault(weatherInfo.getForecast2().toLowerCase(), R.string.icon_unknown));
        tv_nextForecast3.setText(conditionToIconMap.getOrDefault(weatherInfo.getForecast3().toLowerCase(), R.string.icon_unknown));
        rl.setBackgroundColor(backgroundColor((int) weatherInfo.getCurrentTemp()));
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
    public void onBackPressed() {
        super.onBackPressed();
        // Go back to the previous activity
        finish();
    }
}

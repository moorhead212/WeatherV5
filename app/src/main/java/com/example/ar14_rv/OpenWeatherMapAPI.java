package com.example.ar14_rv;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class OpenWeatherMapAPI {

    private static final String API_KEY = "e75cefd930a6958282ae449c5f10ca6b";
    private static final String API_ENDPOINT = "https://api.openweathermap.org/data/2.5/forecast";

    public static WeatherInfo getWeatherInfo(String zipCode) {
        try {
            String apiUrl = API_ENDPOINT + "?zip=" + zipCode + "&appid=" + API_KEY + "&units=imperial";
            JSONObject response = new GetWeatherTask().execute(apiUrl).get();
            WeatherInfo weatherInfo = parseWeatherInfo(response);
            return weatherInfo;
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class GetWeatherTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... urls) {
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String responseString = stringBuilder.toString();
                JSONObject response = new JSONObject(responseString);
                return response;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }
    }

    private static WeatherInfo parseWeatherInfo(JSONObject response) throws JSONException {
        WeatherInfo weatherInfo = new WeatherInfo();
        JSONObject city = response.getJSONObject("city");
        weatherInfo.setCity(city.getString("name"));
        JSONArray forecast = response.getJSONArray("list");
        JSONObject currentForecast = forecast.getJSONObject(0);
        JSONObject weather = currentForecast.getJSONArray("weather").getJSONObject(0);
        weatherInfo.setCurrentCondition(weather.getString("main"));
        JSONObject main = currentForecast.getJSONObject("main");
        weatherInfo.setCurrentTemp(main.getDouble("temp"));
        weatherInfo.setLowTemp(main.getDouble("temp_min"));
        weatherInfo.setHighTemp(main.getDouble("temp_max"));

        JSONObject forecast1 = forecast.getJSONObject(8);
        JSONObject forecast2 = forecast.getJSONObject(16);
        JSONObject forecast3 = forecast.getJSONObject(32);

        // Extracting weather information for each forecast
        JSONObject weather1 = forecast1.getJSONArray("weather").getJSONObject(0);
        JSONObject weather2 = forecast2.getJSONArray("weather").getJSONObject(0);
        JSONObject weather3 = forecast3.getJSONArray("weather").getJSONObject(0);

        double high1 = forecast1.getJSONObject("main").getDouble("temp_max");
        double high2 = forecast2.getJSONObject("main").getDouble("temp_max");
        double high3 = forecast3.getJSONObject("main").getDouble("temp_max");

        double low1 = forecast1.getJSONObject("main").getDouble("temp_min");
        double low2 = forecast2.getJSONObject("main").getDouble("temp_min");
        double low3 = forecast3.getJSONObject("main").getDouble("temp_min");

        weatherInfo.setHigh1((int) high1);
        weatherInfo.setHigh2((int) high2);
        weatherInfo.setHigh3((int) high3);

        weatherInfo.setLow1(low1);
        weatherInfo.setLow2(low2);
        weatherInfo.setLow3(low3);

        // Assigning forecast data to variables
        weatherInfo.setForecast1(weather1.getString("main"));
        weatherInfo.setForecast2(weather2.getString("main"));
        weatherInfo.setForecast3(weather3.getString("main"));



        return weatherInfo;
    }
}
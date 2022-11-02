package com.example.firebaseapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity2 extends AppCompatActivity {

    EditText cityInput;
    TextView tvCityAndCountry, tvTemp, tvFeelsLike, tvDescription;
    Button getWeatherBtn;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "beb6e0b3168d890265d204cb9f18c34b";
    DecimalFormat df = new DecimalFormat("#");
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    String cityText, countryText, tempText, descriptionText, feelsLikeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        cityInput = findViewById(R.id.cityInput);
        tvCityAndCountry = findViewById(R.id.tvCityAndCountry);
        tvTemp = findViewById(R.id.tvTemp);
        tvFeelsLike = findViewById(R.id.tvFeelsLike);
        tvDescription = findViewById(R.id.tvDescription);
        getWeatherBtn = findViewById(R.id.getWeatherBtn);

        getWeatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = cityInput.getText().toString().trim();
                if (city.equals("")) {
                    Toast.makeText(MainActivity2.this, "You must enter a city", Toast.LENGTH_SHORT).show();
                } else {

                    String tempUrl = url + "?q=" + city + "&appid=" + appid;

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                                JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                                String description = jsonObjectWeather.getString("description");

                                JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                                double temp = jsonObjectMain.getDouble("temp") - 273.15;
                                double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;

                                JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                                String countryName = jsonObjectSys.getString("country");

                                String cityName = jsonResponse.getString("name");


                                tvCityAndCountry.setText(cityName + "("+countryName+")");
                                tvTemp.setText(df.format(temp) + "C");
                                tvFeelsLike.setText(df.format(feelsLike) + "C");
                                tvDescription.setText(description);


                                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString("description", description);
                                editor.putString("temp", df.format(temp));
                                editor.putString("feelsLike", df.format(feelsLike));
                                editor.putString("countryName", countryName);
                                editor.putString("cityName", cityName);

                                editor.apply();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }
            }
        });

        loadData();
        updateViews();

    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        countryText = sharedPreferences.getString("countryName", "");
        descriptionText = sharedPreferences.getString("description", "");
        feelsLikeText = sharedPreferences.getString("feelsLike", "");
        cityText = sharedPreferences.getString("cityName", "");
        tempText = sharedPreferences.getString("temp", "");
    }

    public void updateViews() {

        tvCityAndCountry.setText(cityText + "("+countryText+")");
        tvTemp.setText(tempText + "C");
        tvFeelsLike.setText(feelsLikeText + "C");
        tvDescription.setText(descriptionText);
    }
}
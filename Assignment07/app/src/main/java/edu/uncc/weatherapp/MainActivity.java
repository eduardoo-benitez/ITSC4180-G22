package edu.uncc.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import edu.uncc.weatherapp.fragments.WeatherForecastFragment;
import edu.uncc.weatherapp.models.City;

import edu.uncc.weatherapp.fragments.CitiesFragment;

public class MainActivity extends AppCompatActivity implements CitiesFragment.CitiesListener {

    //in build.gradle.kts (Module :app):
    //implementation("com.squareup.okhttp3:okhttp:4.9.0")
    //implementation("com.squareup.picasso:picasso:2.71828")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new CitiesFragment())
                .commit();
    }

    @Override
    public void goToCity(City city) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, WeatherForecastFragment.newInstance(city))
                .addToBackStack(null)
                .commit();
    }
}

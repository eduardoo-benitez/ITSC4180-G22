package edu.uncc.weatherapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.weatherapp.R;
import edu.uncc.weatherapp.databinding.ForecastListItemBinding;
import edu.uncc.weatherapp.databinding.FragmentWeatherForecastBinding;
import edu.uncc.weatherapp.models.City;
import edu.uncc.weatherapp.models.Forecast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class WeatherForecastFragment extends Fragment {
    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private City mCity;


    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    public static WeatherForecastFragment newInstance(City city) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (City) getArguments().getSerializable(ARG_PARAM_CITY);
        }
    }

    FragmentWeatherForecastBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherForecastBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    //TODO: declare the Forecasts ArrayList and CUSTOM adapter for Forecasts
    ForecastAdapter adapter;
    ArrayList<Forecast> forecasts = new ArrayList<>();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("WeatherForcastFragment");
        getForecastUrl();
        //TODO: set the text at the top of the fragment to: city, state
        binding.textViewCityName.setText(mCity.getName() + ", " + mCity.getState());
        //TODO: initialize the custom array adapter and set the layout manager and adapter for the recycler view.
        adapter = new ForecastAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        getForecastUrl();
    }
    private final OkHttpClient client = new OkHttpClient();
    public void getForecastUrl() {
        //TODO: Build a URL (with variables) that goes to a URL based on the city provided to make a GET request. EX: "https://api.weather.gov/points/{cityLat},{cityLng}"
        Request request = new Request.Builder()
                .url("https://api.weather.gov/points/" + mCity.getLat() + "," + mCity.getLng())
                .build();
        //TODO: Make a call with the above request that retrieves the "forecastURL" string within the "properties" object. onRun, pass that string result into getCityForecast()
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String body = response.body().string(); //pull EVERYTHING @ the URL into a string named "body".
                    try {
                        JSONObject jsonObject = new JSONObject(body); //convert the body string into a JSONObject (curly braces)
                        JSONObject properties = jsonObject.getJSONObject("properties"); //pull the properties object from the jsonObject object (curly braces)
                        String forecastUrl = properties.getString("forecast"); //pull the forecast string from the properties object
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getCityForecast(forecastUrl);
                            }
                        });

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }


    public void getCityForecast(String url) {
        //TODO: Build a URL that goes to the correct URL to make a GET request. no variables needed, just use the "url" argument. EX: "https://api.weather.gov/gridpoints/GSP/113,63/forecast"
        Request request = new Request.Builder()
                .url(url)
                .build();
        //TODO: Make a call with the above request that retrieves the "periods" array within the "properties" object. Loop through this array to create a Forecast object for each entry and add it to the Forecast ArrayList. onRun, notify the adapter of a change.
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String body = response.body().string();
                    try{
                        forecasts.clear();
                        JSONObject jsonObject = new JSONObject(body);
                        JSONObject properties = jsonObject.getJSONObject("properties");
                        JSONArray periods = properties.getJSONArray("periods");

                        for (int i = 0; i < periods.length(); i++){
                            Forecast forecast = new Forecast(periods.getJSONObject(i));
                            forecasts.add(forecast);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });


                    }
                    catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    //TODO: Create a RecyclerView for the Forecast array. See A06. NOTE: Picasso library will have to be used to load the image into the imageView.
    class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>{
        @NonNull
        @Override
        public ForecastAdapter.ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ForecastListItemBinding itemBinding = ForecastListItemBinding.inflate(getLayoutInflater(), parent, false);
            return new ForecastViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ForecastAdapter.ForecastViewHolder holder, int position) {
            holder.setupUI(forecasts.get(position));
        }

        @Override
        public int getItemCount() {
            return forecasts.size();
        }

        class ForecastViewHolder extends RecyclerView.ViewHolder{
            ForecastListItemBinding itemBinding;
            public ForecastViewHolder(ForecastListItemBinding itemBinding){
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }
            public void setupUI(Forecast forecast){
                itemBinding.textViewDateTime.setText(forecast.getStartTime());
                itemBinding.textViewTemperature.setText(forecast.getTemp() + " " + forecast.getTempUnit());
                itemBinding.textViewForecast.setText(forecast.getShortForecast());
                itemBinding.textViewHumidity.setText("Humidity: " + forecast.getRelativeHumid() + "%");
                itemBinding.textViewWindSpeed.setText("Wind Speed: " + forecast.getWindSpeed());
                Picasso.get().load(forecast.getIconUrl()).into(itemBinding.imageView);
            }
        }
    }
}
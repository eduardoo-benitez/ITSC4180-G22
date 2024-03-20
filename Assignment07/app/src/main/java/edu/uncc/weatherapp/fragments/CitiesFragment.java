package edu.uncc.weatherapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.weatherapp.databinding.FragmentCitiesBinding;
import edu.uncc.weatherapp.models.City;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CitiesFragment extends Fragment {
    private final OkHttpClient client = new OkHttpClient();

    public CitiesFragment() {
        // Required empty public constructor
    }

    FragmentCitiesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCitiesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayList<City> cities = new ArrayList<>();
    ArrayAdapter<City> adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("CitiesFragment");
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, cities);
        binding.listView.setAdapter(adapter);

        getCities();

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.goToCity(cities.get(position));
            }
        });
    }

    CitiesListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CitiesListener) {
            mListener = (CitiesListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + "must implement CitiesListener");
        }
    }

    public interface CitiesListener {
        void goToCity(City city);
    }

    public void getCities() {
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/cities")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String body = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        JSONArray jsonArray = jsonObject.getJSONArray("cities");
                        cities.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject citiesJsonObject = jsonArray.getJSONObject(i);
                            City city = new City(citiesJsonObject.getString("name"), citiesJsonObject.getString("state"), citiesJsonObject.getDouble("lat"), citiesJsonObject.getDouble("lng"));
                            cities.add(city);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}
package edu.uncc.assignment12.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.assignment12.databinding.FragmentSleepQualityBinding;

public class SleepQualityFragment extends Fragment {

    public SleepQualityFragment() {
        // Required empty public constructor
    }

    FragmentSleepQualityBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSleepQualityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("SleepQualityFragment");

        //TODO: Somehow display the sleep quality options and pass the result back to the AddLogFragment (by going through the MainActivity first). Could be another listview or some radio buttons in a group?

        binding.buttonSleepQualityCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.back();
            }
        });

        //TODO: Replace with a submit on listview option selection if that is used.
        binding.buttonSleepQualitySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    SleepQualityListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (SleepQualityListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement SleepQualityListener");
        }
    }

    public interface SleepQualityListener {
        void back();
        void selectedSleepQuality(int sleepQuality);
    }
}

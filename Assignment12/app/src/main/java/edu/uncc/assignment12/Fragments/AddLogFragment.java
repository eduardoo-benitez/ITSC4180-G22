package edu.uncc.assignment12.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.assignment12.databinding.FragmentAddLogBinding;

public class AddLogFragment extends Fragment {

    public AddLogFragment() {
        // Required empty public constructor
    }

    FragmentAddLogBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddLogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("AddLogFragment");

        binding.buttonAddDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToDateTime();
            }
        });

        binding.buttonAddHoursSlept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToHoursSlept();
            }
        });

        binding.buttonAddSleepQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToSleepQuality();
            }
        });

        binding.buttonAddHoursExercised.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToHoursExercised();
            }
        });

        binding.buttonAddCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.back();
            }
        });

        binding.buttonAddSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    AddLogListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (AddLogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement AddLogListener");
        }
    }

    public interface AddLogListener {
        void goToDateTime();
        void goToHoursSlept();
        void goToSleepQuality();
        void goToHoursExercised();
        void back();
    }
}
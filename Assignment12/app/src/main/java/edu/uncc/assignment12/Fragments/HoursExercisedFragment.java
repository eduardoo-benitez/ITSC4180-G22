package edu.uncc.assignment12.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import edu.uncc.assignment12.databinding.FragmentHoursExercisedBinding;

public class HoursExercisedFragment extends Fragment {

    public HoursExercisedFragment() {
        // Required empty public constructor
    }

    FragmentHoursExercisedBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHoursExercisedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("HoursExercisedFragment");

        //TODO: Make an adapter and set the listview to an array containing the options for hours exercised.

        binding.listViewHoursExcercised.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: On selection of an item, pass the corresponding value back to the MainActivity.

            }
        });

        binding.buttonHoursExercisedCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.back();
            }
        });
    }

    HoursExercisedListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (HoursExercisedListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement HoursExercisedListener");
        }
    }

    public interface HoursExercisedListener {
        void back();
        void selectedHoursExercised(double hoursExercised);
    }
}

package edu.uncc.assignment12.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.assignment12.databinding.FragmentVisualizeBinding;

public class VisualizeFragment extends Fragment {

    //TODO: I have no idea. We will figure it out tho, trust.

    public VisualizeFragment() {
        // Required empty public constructor
    }

    FragmentVisualizeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVisualizeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("VisualizeFragment");

        binding.buttonVisualizeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.back();
            }
        });
    }

    VisualizeListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (VisualizeListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement VisualizeListener");
        }
    }

    public interface VisualizeListener {
        void back();
    }
}
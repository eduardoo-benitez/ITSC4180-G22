package edu.uncc.assignment11;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import edu.uncc.assignment11.databinding.FragmentMailboxBinding;
import edu.uncc.assignment11.databinding.FragmentNewMessageBinding;

public class NewMessageFragment extends Fragment {

    public NewMessageFragment() {
        // Required empty public constructor
    }

    FragmentNewMessageBinding binding;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewMessageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.nmSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Post message to Firebase

                mListener.back();
            }
        });

        binding.nmCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.back();
            }
        });

        getActivity().setTitle("NewMessageFragment");
    }

    NewMessageListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (NewMessageListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement NewMessageListener");
        }
    }
    public interface NewMessageListener {
        void back();
    }
}
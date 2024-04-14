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

import edu.uncc.assignment11.databinding.FragmentReplyBinding;
import edu.uncc.assignment11.databinding.FragmentUserListBinding;

public class ReplyFragment extends Fragment {

    public ReplyFragment() {
        // Required empty public constructor
    }

    FragmentReplyBinding binding;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReplyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.reSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Post message to Firebase in a similar way to NewMessage, but with the receiver and title field filled in

                mListener.back();
            }
        });

        binding.reCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.back();
            }
        });

        getActivity().setTitle("ReplyFragment");
    }

    ReplyListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (ReplyListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement ReplyListener");
        }
    }
    public interface ReplyListener {
        void back();
    }
}
package edu.uncc.assignment11;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

import edu.uncc.assignment11.databinding.FragmentReplyBinding;
import edu.uncc.assignment11.models.User;

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
    User sender, recipient;
    Message message;
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        binding.reSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String body = binding.reEditTextText.getText().toString();
                //Set the title to the replying message title
                String title = binding.textViewTitle.getText().toString();
                //Set the sender name to the person you are replying to
                String senders = binding.textViewSender.getText().toString();

                if (body.isEmpty()) {
                    Toast.makeText(getActivity(), "Missing input!!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    //TODO: Post message to Firebase in a similar way to NewMessage, but with the receiver and title field filled in

                    mListener.back();
                }
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
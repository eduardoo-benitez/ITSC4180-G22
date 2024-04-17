package edu.uncc.assignment11;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

import edu.uncc.assignment11.databinding.FragmentNewMessageBinding;
import edu.uncc.assignment11.models.User;

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

    User sender, recipient;
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        binding.nmSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.nmEditTextTitle.getText().toString();
                String body = binding.nmEditTextText.getText().toString();
                String recipientEmail = binding.editTextRecipient.getText().toString();
                if (title.isEmpty() || body.isEmpty() || recipientEmail.isEmpty()) {
                    Toast.makeText(getActivity(), "Missing input!!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    db.collection("users")
                            .whereEqualTo("userId", mAuth.getCurrentUser().getUid())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document: task.getResult()) {
                                            sender = document.toObject(User.class);
                                        }
                                        db.collection("users")
                                                .whereEqualTo("email", recipientEmail)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        int count = 0;
                                                        if (task.isSuccessful()) {
                                                            for (QueryDocumentSnapshot document: task.getResult()) {
                                                                recipient = document.toObject(User.class);
                                                                count++;
                                                            }
                                                        }
                                                        if (count == 1 && !(sender.getUserId()).equals(recipient.getUserId()) && !(recipient.getBlocked().contains(sender.getUserId()))) {
                                                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                            DocumentReference senderRef = db.collection("users").document(sender.getDocId()).collection("messages").document();
                                                            DocumentReference recipientDef = db.collection("users").document(recipient.getDocId()).collection("messages").document();

                                                            HashMap<String, Object> data = new HashMap<>();
                                                            data.put("docId", senderRef.getId());
                                                            data.put("title", title);
                                                            data.put("body", body);
                                                            data.put("sentAt", FieldValue.serverTimestamp());
                                                            data.put("sender", sender.getEmail());
                                                            data.put("recipient", recipient.getEmail());
                                                            data.put("read", false);

                                                            senderRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    recipientDef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            recipientDef.update("docId", recipientDef.getId()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    mListener.back();
                                                                                }
                                                                            });
                                                                        }
                                                                    });
                                                                }
                                                            });
                                                            recipientDef.set(data);
                                                            recipientDef.update("docId", recipientDef.getId());
                                                            mListener.back();
                                                        }
                                                        else {
                                                            Toast.makeText(getActivity(), "Recipient email blocked/not found!!!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                }
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
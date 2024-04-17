package edu.uncc.assignment11;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import edu.uncc.assignment11.databinding.FragmentMailboxBinding;
import edu.uncc.assignment11.databinding.MailboxRowItemBinding;
import edu.uncc.assignment11.models.Message;
import edu.uncc.assignment11.models.User;

public class MailboxFragment extends Fragment {

    public MailboxFragment() {
        // Required empty public constructor
    }

    FragmentMailboxBinding binding;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMailboxBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    User mUser;
    MailboxAdapter mailboxAdapter;
    ArrayList<Message> mMessages = new ArrayList<>();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.mailboxRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mailboxAdapter = new MailboxAdapter();
        binding.mailboxRecyclerView.setAdapter(mailboxAdapter);
        mMessages.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("userId", mAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document: task.getResult()) {
                                mUser = document.toObject(User.class);
                                display();
                            }
                        }
                    }
                });

        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.logout();
            }
        });

        binding.buttonNewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToNewMessage();
            }
        });

        binding.buttonUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToUserList();
            }
        });

        getActivity().setTitle("MailboxFragment");
    }

    ListenerRegistration listenerRegistration;
    void display() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        listenerRegistration = db.collection("users").document(mUser.getDocId()).collection("messages").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                mMessages.clear();
                for (QueryDocumentSnapshot document: value) {
                    Message message = document.toObject(Message.class);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("users")
                            .whereEqualTo("email", message.getSender())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document: task.getResult()) {
                                            User user = document.toObject(User.class);
                                            if (!(mUser.getBlocked().contains(user.getUserId()))) {
                                                mMessages.add(message);
                                                Log.d("whateveryourtagis", mMessages.toString());
                                            }
                                        }
                                        mailboxAdapter.notifyDataSetChanged();
                                    }
                                }
                            });
                }
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
    }
    MailboxListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (MailboxListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement MailboxListener");
        }
    }

    public interface MailboxListener{
        void logout();
        void goToNewMessage();
        void goToUserList();
        void goToReply();
    }

    class MailboxAdapter extends RecyclerView.Adapter<MailboxAdapter.MailboxViewHolder> {
        @NonNull
        @Override
        public MailboxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            MailboxRowItemBinding binding = MailboxRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new MailboxViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull MailboxViewHolder holder, int position) {
            Message message = mMessages.get(position);
            holder.setupUI(message);
        }

        public int getItemCount() {
            return mMessages.size();
        }

        class MailboxViewHolder extends RecyclerView.ViewHolder {
            MailboxRowItemBinding mBinding;
            Message mMessage;

            public MailboxViewHolder(MailboxRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(Message message) {
                mMessage = message;
                mBinding.textViewMessageTitle.setText(message.getTitle());
                mBinding.textViewMessageBody.setText("Please Click to Open!");
                mBinding.textViewMessageReciever.setText(message.getRecipient());
                mBinding.textViewMessageSender.setText(message.getSender());
                if (mMessage.isRead()) {
                    //has been read
                    mBinding.getRoot().setBackgroundColor(Color.LTGRAY);
                    mBinding.textViewMessageBody.setText(message.getBody());
                }
                else {
                    mBinding.getRoot().setBackgroundColor(Color.WHITE);
                }


                if (mMessage.getSentAt() == null) {
                    mBinding.textViewDate.setText("N/A");
                } else {
                    Date date = mMessage.getSentAt().toDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                    mBinding.textViewDate.setText(sdf.format(date));
                }
                mBinding.textViewMessageBody.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("users").document(mUser.getDocId()).collection("messages").document(mMessage.getDocId()).update("read", true);

                    }
                });
                mBinding.replyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String body = mBinding.insertReply.getText().toString();
                        if (!(body.isEmpty())){
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            DocumentReference bodyReply = db.collection("users").document(mUser.getDocId()).collection("messages").document();
                            HashMap<String, Object> data = new HashMap<>();
                            data.put("body", body);
                            data.put("title", "re: "  + message.getTitle());
                            data.put("sender", message.getRecipient());
                            data.put("docId", message.getDocId());
                            data.put("sentAt", FieldValue.serverTimestamp());
                            data.put("recipient", message.getSender());
                            data.put("read", message.isRead());

                            bodyReply.set(data);
                        }
                        else {
                            Toast.makeText(getActivity(), "Please fill in a reply!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                    mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("users").document(mUser.getDocId()).collection("messages").document(mMessage.getDocId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    mailboxAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });
            }
        }
    }
}
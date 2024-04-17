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
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ListenerRegistration listenerRegistration;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.mailboxRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mailboxAdapter = new MailboxAdapter();
        binding.mailboxRecyclerView.setAdapter(mailboxAdapter);
        mMessages.clear();

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

        binding.buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = binding.searchEditText.getText().toString();
                Log.d("A11q", search);
                if (search.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a search!!!", Toast.LENGTH_SHORT).show();
                    mMessages.clear();
                    db.collection("users").document(mUser.getDocId()).collection("messages")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for (QueryDocumentSnapshot document: task.getResult()) {
                                        Message message = document.toObject(Message.class);
                                        mMessages.add(message);
                                    }
                                    mailboxAdapter.notifyDataSetChanged();
                                }
                            });
                }
                else {
                    mMessages.clear();
                    char end = search.charAt(search.length() - 1);
                    char newEnding = ++end;

                    StringBuilder newString = new StringBuilder(search);
                    newString.deleteCharAt(search.length() - 1);
                    newString.append(newEnding);

                    db.collection("users").document(mUser.getDocId()).collection("messages")
                            .whereGreaterThanOrEqualTo("title", search)
                            .whereLessThan("title", newString.toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for (QueryDocumentSnapshot document: task.getResult()) {
                                        Message message = document.toObject(Message.class);
                                        mMessages.add(message);
                                    }
                                    mailboxAdapter.notifyDataSetChanged();
                                }
                            });
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

    void display() {
        listenerRegistration = db.collection("users").document(mUser.getDocId()).collection("messages").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                mMessages.clear();
                for (QueryDocumentSnapshot document: value) {
                    Message message = document.toObject(Message.class);
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
            mMessages.clear();
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
    }

    User recipient = new User();
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
                mBinding.getRoot().setBackgroundColor(Color.WHITE);

                mBinding.textViewMessageTitle.setText(message.getTitle());
                mBinding.textViewMessageBody.setText("Please Click to Open!");
                mBinding.textViewMessageReciever.setText(message.getRecipient());
                mBinding.textViewMessageSender.setText(message.getSender());

                if (mMessage.isRead()) {
                    mBinding.getRoot().setBackgroundColor(Color.LTGRAY);
                    mBinding.textViewMessageBody.setText(message.getBody());
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
                        db.collection("users").document(mUser.getDocId()).collection("messages").document(mMessage.getDocId()).update("read", true);
                    }
                });
                mBinding.replyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String body = mBinding.insertReply.getText().toString();
                        if (!(body.isEmpty())) {
                            db.collection("users")
                                    .whereEqualTo("email", mMessage.getSender())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    recipient = document.toObject(User.class);
                                                }
                                                if (mUser.getUserId().equals(recipient.getUserId())) {
                                                    Toast.makeText(getActivity(), "Do not reply to yourself!!!", Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    DocumentReference senderRef = db.collection("users").document(mUser.getDocId()).collection("messages").document();
                                                    DocumentReference recipientRef = db.collection("users").document(recipient.getDocId()).collection("messages").document();

                                                    HashMap<String, Object> data = new HashMap<>();
                                                    data.put("body", body);
                                                    data.put("title", "re: " + message.getTitle());
                                                    data.put("sender", mUser.getEmail());
                                                    data.put("docId", senderRef.getId());
                                                    data.put("sentAt", FieldValue.serverTimestamp());
                                                    data.put("recipient", message.getSender());
                                                    data.put("read", false);

                                                    senderRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            recipientRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    recipientRef.update("docId", recipientRef.getId()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            mBinding.insertReply.setText("");
                                                                        }
                                                                    });
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    });
                        }
                        else {
                            Toast.makeText(getActivity(), "Please fill in a reply!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
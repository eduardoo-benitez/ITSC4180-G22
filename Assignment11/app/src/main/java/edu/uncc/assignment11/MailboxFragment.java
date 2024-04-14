package edu.uncc.assignment11;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import edu.uncc.assignment11.databinding.FragmentLoginBinding;
import edu.uncc.assignment11.databinding.FragmentMailboxBinding;
import edu.uncc.assignment11.databinding.MailboxRowItemBinding;
import edu.uncc.assignment11.models.Message;

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

    MailboxAdapter mailboxAdapter;
    ArrayList<Message> mMessages = new ArrayList<Message>();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO: Pull data from firebase and properly display messages

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
                //TODO: Properly display messages in the recyclerView

                mBinding.textViewReply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.goToReply();
                    }
                });

                mBinding.textViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: Delete message from firebase & update recyclerView

                    }
                });
            }
        }
    }
}
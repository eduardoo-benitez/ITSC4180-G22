package edu.uncc.assignment11;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

import edu.uncc.assignment11.databinding.FragmentUserListBinding;
import edu.uncc.assignment11.databinding.UserRowItemBinding;

public class UserListFragment extends Fragment {

    public UserListFragment() {
        // Required empty public constructor
    }

    FragmentUserListBinding binding;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    UserListAdapter userAdapter;
    ArrayList<User> mUsers = new ArrayList<>();
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.uRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter = new UserListAdapter();
        binding.uRecyclerView.setAdapter(userAdapter);

        binding.buttonBlocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Use button (or radio button, or some other component) that toggles the recyclerView to show either blocked or not blocked users

            }
        });

        binding.uButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.back();
            }
        });

        getActivity().setTitle("UserListFragment");
    }

    UserListListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (UserListListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement UserListListener");
        }
    }
    public interface UserListListener {
        void back();
    }

    class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {
        @NonNull
        @Override
        public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            UserRowItemBinding binding = UserRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new UserListViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull UserListAdapter.UserListViewHolder holder, int position) {
            User user = mUsers.get(position);
            holder.setupUI(user);
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }

        class UserListViewHolder extends RecyclerView.ViewHolder {
            UserRowItemBinding mBinding;
            User mUser;

            public UserListViewHolder(UserRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(User user) {
                mUser = user;
                //TODO: Properly display users in the recyclerView

            }
        }
    }
}
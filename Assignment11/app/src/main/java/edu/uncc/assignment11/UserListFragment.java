package edu.uncc.assignment11;

import android.content.Context;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.uncc.assignment11.databinding.FragmentUserListBinding;
import edu.uncc.assignment11.databinding.UserRowItemBinding;
import edu.uncc.assignment11.models.User;

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
    ArrayList<User> mDisplayedUsers = new ArrayList<>();
    ArrayList<User> mHiddenUsers = new ArrayList<>();
    ArrayList<String> blockedIds = new ArrayList<>();
    String docId = "";
    boolean blockedView = false;
    ListenerRegistration listenerRegistration;
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.uRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter = new UserListAdapter();
        binding.uRecyclerView.setAdapter(userAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .whereEqualTo("userId", mAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document: task.getResult()) {
                                docId = document.getId();
                                blockedIds = (ArrayList<String>)document.get("blocked");
                                display();
                            }
                        }
                    }
                });

        binding.buttonBlocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blockedView = !blockedView;
                if (blockedView) {
                    binding.buttonBlocked.setText("Un-Blocked");
                }
                else {
                    binding.buttonBlocked.setText("Blocked");
                }
                ArrayList<User> temp = new ArrayList<>(mDisplayedUsers);
                mDisplayedUsers= new ArrayList<>(mHiddenUsers);
                mHiddenUsers = new ArrayList<>(temp);
                userAdapter.notifyDataSetChanged();
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

    public void display() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        listenerRegistration = db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("A11", "Listen Failed", error);
                    return;
                }
                mDisplayedUsers.clear();
                mHiddenUsers.clear();
                for (QueryDocumentSnapshot document: value) {
                    User user = document.toObject(User.class);
                    if (blockedIds.contains(user.getUserId())) {
                        mHiddenUsers.add(user);
                    }
                    else if (!(mAuth.getCurrentUser().getUid().equals(user.getUserId()))) {
                        mDisplayedUsers.add(user);
                    }
                }
                if (blockedView) {
                    ArrayList<User> temp = new ArrayList<>(mDisplayedUsers);
                    mDisplayedUsers= new ArrayList<>(mHiddenUsers);
                    mHiddenUsers = new ArrayList<>(temp);
                }
                userAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(listenerRegistration != null){
            listenerRegistration.remove();
        }
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
            User user = mDisplayedUsers.get(position);
            holder.setupUI(user);
        }

        @Override
        public int getItemCount() {
            return mDisplayedUsers.size();
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
                mBinding.textViewUserEmail.setText(user.getName());
                mBinding.textViewUserName.setText(user.getEmail());
                if (blockedView) {
                    mBinding.buttonBlock.setText("Un-block");
                }
                else {
                    mBinding.buttonBlock.setText("Block");
                }
                mBinding.buttonBlock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        DocumentReference docRef = db.collection("users").document(docId);
                        if (blockedView) {
                            blockedIds.remove(user.getUserId());
                        }
                        else {
                            blockedIds.add(user.getUserId());
                        }
                        docRef.update("blocked", blockedIds);
                    }
                });
            }
        }
    }
}
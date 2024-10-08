package edu.uncc.assignment05.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Comparator;

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.UserAdapter;
import edu.uncc.assignment05.databinding.FragmentUsersBinding;
import edu.uncc.assignment05.models.User;

public class UsersFragment extends Fragment {

    public void setSortText(String sortText) {
        this.sortText = sortText;
    }

    private static final String ARG_USERS = "USERS";
    private ArrayList<User> mUsers; //we need an adapter for each fragment that has a ListView that is to display an array. this one
                                    //is a custom one that is made specifically for an array of Users. defined in UserAdapter.java.
    private String sortText = "Sort by Name (ASC)";

    UserAdapter adapter;

    public UsersFragment() {
        // Required empty public constructor
    }

    public static UsersFragment newInstance(ArrayList<User> mUsers) {
        UsersFragment fragment = new UsersFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USERS, mUsers);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mUsers = (ArrayList<User>) getArguments().getSerializable(ARG_USERS);
        }
    }

    FragmentUsersBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUsersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("UsersFragment");

        binding.textViewSortIndicator.setText(sortText);

        adapter = new UserAdapter(getActivity(), R.layout.user_list_item, mUsers);
        binding.listView.setAdapter(adapter);

        binding.buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsers.clear();
                adapter.notifyDataSetChanged();
            }
        });

        binding.buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToSort();
            }
        });

        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToAddUser();
            }
        });

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.goToUserDetails(mUsers.get(position), position); //pass both the user to be displayed and it's index so it can be deleted later, if that option is selected.
            }
        });
    }

    UsersListener mListener;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (UsersListener) context;
    }

    public interface UsersListener {
        public void goToAddUser();
        public void goToUserDetails(User user, int position);
        public void goToSort();
    }
}
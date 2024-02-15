package edu.uncc.assignment05.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.UserAdapter;
import edu.uncc.assignment05.databinding.FragmentUsersBinding;
import edu.uncc.assignment05.models.User;

public class UsersFragment extends Fragment {

    public void addUser(User newUser) {
        mUsers.add(newUser);
    }
    private static final String ARG_USER = "USER";
    private ArrayList<User> mUsers;

    ListView listview;
    UserAdapter adapter;
    public UsersFragment() {
        // Required empty public constructor
    }

    public static UsersFragment newInstance(ArrayList<User> mUsers) {
        UsersFragment fragment = new UsersFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, mUsers);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mUsers = (ArrayList<User>) getArguments().getSerializable(ARG_USER);
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

        listview = binding.listView.findViewById(R.id.listView);
        adapter = new UserAdapter(getActivity(), R.layout.user_list_item, mUsers);
        listview.setAdapter(adapter);

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
                mUsers.sort(new Comparator<User>() {
                    @Override
                    public int compare(User u1, User u2) {
                        return u1.getName().compareTo(u2.getName());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });

        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToAddUser();
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
    }
}
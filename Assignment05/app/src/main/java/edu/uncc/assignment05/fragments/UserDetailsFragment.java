package edu.uncc.assignment05.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.assignment05.databinding.FragmentUserDetailsBinding;
import edu.uncc.assignment05.models.User;

public class UserDetailsFragment extends Fragment {
    private static final String ARG_USER = "USER";
    private static final String ARG_POS = "POSITION";
    private User user;
    private int position;

    public UserDetailsFragment() {
        // Required empty public constructor
    }

    public static UserDetailsFragment newInstance(User user, int position) {
        UserDetailsFragment fragment = new UserDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        args.putInt(ARG_POS, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.user = (User) getArguments().getSerializable(ARG_USER);
            this.position = getArguments().getInt(ARG_POS);
        }
    }

    FragmentUserDetailsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("UsersFragment");

        binding.textViewName.setText(user.getName());
        binding.textViewEmail.setText(user.getEmail());
        binding.textViewGender.setText(user.getGender());
        binding.textViewAge.setText(String.valueOf(user.getAge()));
        binding.textViewState.setText(user.getState());
        binding.textViewGroup.setText(user.getGroup());

        binding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.deleteUserDetails(position);
            }
        });

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.userDetailsBack();
            }
        });
    }

    UserDetailsListener mListener;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (UserDetailsListener) context;
    }
    public interface UserDetailsListener {
        public void deleteUserDetails(int position);
        public void userDetailsBack();
    }
}
package edu.uncc.assignment05.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.databinding.FragmentAddUserBinding;
import edu.uncc.assignment05.models.User;

public class AddUserFragment extends Fragment {

    private User newUser;

    public AddUserFragment() {
        // Required empty public constructor
    }

    public static AddUserFragment newInstance(String param1, String param2) {
        AddUserFragment fragment = new AddUserFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentAddUserBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAddUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("AddUserFragment");

        binding.buttonSelectGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToGender();
            }
        });

        binding.buttonSelectAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToAge();
            }
        });

        binding.buttonSelectState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToState();
            }
        });

        binding.buttonSelectGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToGroup();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = binding.editTextName.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                String gender = binding.textViewGender.getText().toString();
                int age = Integer.valueOf(binding.textViewGender.getText().toString());
                String state = binding.textViewGender.getText().toString();
                String group = binding.textViewGender.getText().toString();

                newUser = new User(name, email, gender, age, state, group);
                mListener.userUpdate(newUser);
            }
        });
    }

    AddUserListener mListener;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddUserFragment.AddUserListener)context;
    }
    public interface AddUserListener {
        public void goToGender();
        public void goToAge();
        public void goToState();
        public void goToGroup();
        public void userUpdate(User newUser);
    }
}
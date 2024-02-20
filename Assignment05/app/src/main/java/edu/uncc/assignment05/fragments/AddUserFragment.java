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

import edu.uncc.assignment05.databinding.FragmentAddUserBinding;
import edu.uncc.assignment05.models.User;

public class AddUserFragment extends Fragment {

    private User newUser;
    private String gender;
    private int age = -1;
    private String state;
    private String group;

    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setGroup(String group) {
        this.group = group;
    }

    public AddUserFragment() {
        // Required empty public constructor
    }

    FragmentAddUserBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("AddUserFragment");

        binding.textViewGender.setText(this.gender != null ? this.gender : "N/A");
        binding.textViewAge.setText(this.age != -1 ? String.valueOf(age) : "N/A");
        binding.textViewState.setText(this.state != null ? this.state : "N/A");
        binding.textViewGroup.setText(this.group!= null ? this.group: "N/A");

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

                if (binding.editTextName.getText().toString().equals("N/A")
                        || binding.editTextEmail.getText().toString().equals("N/A")
                        || binding.textViewGender.getText().toString().equals("N/A")
                        || binding.textViewAge.getText().toString().equals("N/A")
                        || binding.textViewState.getText().toString().equals("N/A")
                        || binding.textViewGroup.getText().toString().equals("N/A")) {
                    Toast.makeText(getActivity(), "Missing input!!!!!", Toast.LENGTH_LONG).show();
                }
                else {
                    newUser = new User(name, email, gender, age, state, group);
                    mListener.userUpdate(newUser);
                }
            }
        });
    }

    AddUserListener mListener;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddUserListener)context;
    }
    public interface AddUserListener {
        void goToGender();
        void goToAge();
        void goToState();
        void goToGroup();
        void userUpdate(User newUser);
    }
}
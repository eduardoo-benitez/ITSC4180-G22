package edu.uncc.assignment06.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentAddTaskBinding;
import edu.uncc.assignment06.models.Task;

public class AddTaskFragment extends Fragment {

    private Task newTask;
    private String priority;
    private String category;

    public void setPriority(String priority) {
        this.priority = priority;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public AddTaskFragment() {
        // Required empty public constructor
    }

    FragmentAddTaskBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("AddTaskFragment");

        binding.textViewPriority.setText(this.priority != null ? this.priority : "N/A");
        binding.textViewCategory.setText(this.category != null ? this.category : "N/A");

        binding.buttonSelectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToCategory();
            }
        });

        binding.buttonSelectPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToPriority();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextName.getText().toString();
                String category = binding.textViewCategory.getText().toString();
                String priorityStr = binding.textViewPriority.getText().toString();
                int priority = -1;

                if (binding.editTextName.getText().toString().equals("") || binding.textViewCategory.getText().toString().equals("N/A") || binding.textViewPriority.getText().toString().equals("N/A")) {
                    Toast.makeText(getActivity(), "Missing input!!!!!", Toast.LENGTH_LONG).show();
                }
                else {
                    if (priorityStr.equals("Very High")) {
                        priority = 5;
                    }
                    else if (priorityStr.equals("High")) {
                        priority = 4;
                    }
                    else if (priorityStr.equals("Medium")) {
                        priority = 3;
                    }
                    else if (priorityStr.equals("Low")) {
                        priority = 2;
                    }
                    else if (priorityStr.equals("Very Low")) {
                        priority = 1;
                    }
                    newTask = new Task(name, category, priorityStr, priority);
                    mListener.tasksUpdate(newTask);
                }
            }
        });
    }
    AddTaskListener mListener;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddTaskListener) context;
    }
    public interface AddTaskListener {
        void goToCategory();
        void goToPriority();
        void tasksUpdate(Task newTask);
    }
}
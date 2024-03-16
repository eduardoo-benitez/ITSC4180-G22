package edu.uncc.assignment06.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentTaskDetailsBinding;
import edu.uncc.assignment06.databinding.FragmentTasksBinding;
import edu.uncc.assignment06.models.Task;

public class TaskDetailsFragment extends Fragment {

    private static final String ARG_TASK = "TASK";
    private static final String ARG_POS = "POSITION";

    // TODO: Rename and change types of parameters
    private Task task;
    private int position;

    public TaskDetailsFragment() {
        // Required empty public constructor
    }

    public static TaskDetailsFragment newInstance(Task task, int position) {
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK, task);
        args.putSerializable(ARG_POS, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            task = (Task) getArguments().getSerializable(ARG_TASK);
            position = getArguments().getInt(ARG_POS);
        }
    }

    FragmentTaskDetailsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTaskDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("TaskDetailsFragment");

        binding.textViewName.setText(task.getName());
        binding.textViewCategory.setText(task.getCategory());
        binding.textViewPriority.setText(task.getPriorityStr());

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.detailsBack();
            }
        });
        binding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.deleteFromDetails(position);
            }
        });
    }

    TaskDetailsListener mListener;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (TaskDetailsListener) context;
    }

    //TODO: The interface for the TasksFragment
    public interface TaskDetailsListener{
        void detailsBack();
        void deleteFromDetails(int position);
    }
}
package edu.uncc.assignment06.fragments;

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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentTasksBinding;
import edu.uncc.assignment06.models.Task;

public class TasksFragment extends Fragment {
    private ArrayList<Task> mTasks = new ArrayList<>();

    public TasksFragment() {
        // Required empty public constructor
    }

    FragmentTasksBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    TasksAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("TasksFragment");

        adapter = new TasksAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        mTasks.clear();
        mTasks.addAll(mListener.getAllTasks());

        binding.buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTasks.clear();
                adapter.notifyDataSetChanged();
                mListener.clearAllTasks();
            }
        });

        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddTask();
            }
        });

        binding.imageViewSortAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textViewSortIndicator.setText("Sort by Priority (ASC)");
                mTasks = mListener.sortTasks(new Comparator<Task>() {
                    @Override
                    public int compare(Task t1, Task t2) {
                        return Integer.compare(t1.getPriority(), t2.getPriority());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });
        binding.imageViewSortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textViewSortIndicator.setText("Sort by Priority (DESC)");
                mTasks = mListener.sortTasks(new Comparator<Task>() {
                    @Override
                    public int compare(Task t1, Task t2) {
                        return Integer.compare(t2.getPriority(), t1.getPriority());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });
    }

    TasksListener mListener;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (TasksListener) context;
    }

    //TODO: The interface for the TasksFragment
    public interface TasksListener{
        ArrayList<Task> getAllTasks();
        void clearAllTasks();
        void gotoAddTask();
        ArrayList<Task> sortTasks(Comparator<Task> comparator);
        void gotoTaskDetails(Task task, int position);
        void deleteTask(int position);
    }

    class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {
        @NonNull
        @Override
        public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.task_list_item, parent, false);
            return new TasksViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.setupUI(task, position);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }

        class TasksViewHolder extends RecyclerView.ViewHolder {
            TextView textViewName, textViewCategory, textViewPriority;
            Task mTask;
            int position;
            public TasksViewHolder(@NonNull View itemView) {
                super(itemView);

                textViewName = itemView.findViewById(R.id.textViewName);
                textViewCategory = itemView.findViewById(R.id.textViewCategory);
                textViewPriority = itemView.findViewById(R.id.textViewPriority);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoTaskDetails(mTask, position);
                    }
                });

                itemView.findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.deleteTask(position);
                    }
                });
            }

            void setupUI(Task task, int position) {
                mTask = task;
                this.position = position;
                textViewName.setText(task.getName());
                textViewCategory.setText(task.getCategory());
                textViewPriority.setText(task.getPriorityStr());
            }
        }
    }
}
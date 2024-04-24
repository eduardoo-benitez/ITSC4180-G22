package edu.uncc.assignment12.Fragments;

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

import java.util.ArrayList;

import edu.uncc.assignment12.Models.LogEntry;
import edu.uncc.assignment12.databinding.FragmentLogsBinding;
import edu.uncc.assignment12.databinding.LogsRowItemBinding;

public class LogsFragment extends Fragment {

    public LogsFragment() {
        // Required empty public constructor
    }

    private static final String ARG_LOGS= "LOGS";
    private ArrayList<LogEntry> mLogEntries;
    public static LogsFragment newInstance(ArrayList<LogEntry> mLogEntries) {
        LogsFragment fragment = new LogsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOGS, mLogEntries);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mLogEntries = (ArrayList<LogEntry>) getArguments().getSerializable(ARG_LOGS);
        }
    }

    FragmentLogsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLogsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    LogsAdapter logsAdapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("LogsFragment");

        binding.logsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        logsAdapter = new LogsAdapter();
        binding.logsRecyclerView.setAdapter(logsAdapter);

        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToAddLog();
            }
        });

        binding.buttonVisualize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToVisualize();
            }
        });
    }

    LogsListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (LogsListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement LogsListener");
        }
    }

    public interface LogsListener {
        void goToAddLog();
        void goToVisualize();
    }

    class LogsAdapter extends RecyclerView.Adapter<LogsAdapter.LogsViewHolder> {

        @NonNull
        @Override
        public LogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LogsRowItemBinding binding = LogsRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new LogsViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull LogsAdapter.LogsViewHolder holder, int position) {
            LogEntry logEntry = mLogEntries.get(position);
            holder.setupUI(logEntry);
        }

        @Override
        public int getItemCount() {
            return mLogEntries.size();
        }

        class LogsViewHolder extends RecyclerView.ViewHolder {
            LogsRowItemBinding mBinding;
            LogEntry mLogEntry;

            public LogsViewHolder(LogsRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(LogEntry logEntry) {
                mLogEntry = logEntry;

                //TODO: Make recyclerview show each LogEntry in the mLogEntries array.

                mBinding.buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: Delete an entry.

                    }
                });
            }
        }
    }
}
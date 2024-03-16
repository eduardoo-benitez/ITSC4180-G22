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

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentSelectPriorityBinding;
import edu.uncc.assignment06.models.Data;

public class SelectPriorityFragment extends Fragment {
    private String[] mPriorities;

    public SelectPriorityFragment() {
        // Required empty public constructor
    }

    FragmentSelectPriorityBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectPriorityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    SelectPriorityAdapter adapter;
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("SelectPriorityFragment");

        mPriorities = Data.priorities;
        adapter = new SelectPriorityAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelSelectPriority();
            }
        });
    }

    SelectPriorityListener mListener;

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectPriorityListener) context;
    }

    public interface SelectPriorityListener {
        void cancelSelectPriority();
        void selectPriority(String priority);
    }

    class SelectPriorityAdapter extends RecyclerView.Adapter<SelectPriorityAdapter.SelectPriorityViewHolder> {
        @NonNull
        @Override
        public SelectPriorityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.selection_list_item, parent, false);
            return new SelectPriorityViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SelectPriorityViewHolder holder, int position) {
            String categories = mPriorities[position];
            holder.setupUI(categories, position);
        }

        @Override
        public int getItemCount() {
            return mPriorities.length;
        }

        class SelectPriorityViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            String mPriority;
            int position;

            public SelectPriorityViewHolder(@NonNull View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.textView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.selectPriority(mPriority);
                    }
                });
            }

            void setupUI(String priority, int position) {
                mPriority = priority;
                this.position = position;
                textView.setText(priority);
            }
        }
    }
}

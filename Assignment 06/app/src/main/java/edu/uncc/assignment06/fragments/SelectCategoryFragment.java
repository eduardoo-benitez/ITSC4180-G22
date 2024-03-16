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

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentSelectCategoryBinding;
import edu.uncc.assignment06.models.Data;

public class SelectCategoryFragment extends Fragment {
    private String[] mCategories;

    public SelectCategoryFragment() {
        // Required empty public constructor
    }

    FragmentSelectCategoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    SelectCategoryAdapter adapter;
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("SelectCategoryFragment");

        mCategories = Data.categories;
        adapter = new SelectCategoryAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelSelectCategory();
            }
        });
    }

    SelectCategoryListener mListener;

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectCategoryListener) context;
    }

    public interface SelectCategoryListener {
        void cancelSelectCategory();
        void selectCategory(String category);
    }

    class SelectCategoryAdapter extends RecyclerView.Adapter<SelectCategoryAdapter.SelectCategoryViewHolder> {
        @NonNull
        @Override
        public SelectCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.selection_list_item, parent, false);
            return new SelectCategoryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SelectCategoryViewHolder holder, int position) {
            String categories = mCategories[position];
            holder.setupUI(categories, position);
        }

        @Override
        public int getItemCount() {
            return mCategories.length;
        }

        class SelectCategoryViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            String mCategory;
            int position;

            public SelectCategoryViewHolder(@NonNull View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.textView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.selectCategory(mCategory);
                    }
                });
            }

            void setupUI(String category, int position) {
                mCategory = category;
                this.position = position;
                textView.setText(category);
            }
        }
    }
}
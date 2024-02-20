package edu.uncc.assignment05.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Comparator;

import edu.uncc.assignment05.databinding.FragmentSelectSortBinding;
import edu.uncc.assignment05.models.User;

public class SelectSortFragment extends Fragment implements View.OnClickListener {

    public SelectSortFragment() {
        // Required empty public constructor
    }

    FragmentSelectSortBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectSortBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("SelectSortFragment");

        binding.imageViewNameAscending.setOnClickListener(this);
        binding.imageViewNameDescending.setOnClickListener(this);

        binding.imageViewEmailAscending.setOnClickListener(this);
        binding.imageViewEmailDescending.setOnClickListener(this);

        binding.imageViewGenderAscending.setOnClickListener(this);
        binding.imageViewGenderDescending.setOnClickListener(this);

        binding.imageViewAgeAscending.setOnClickListener(this);
        binding.imageViewAgeDescending.setOnClickListener(this);

        binding.imageViewStateAscending.setOnClickListener(this);
        binding.imageViewStateDescending.setOnClickListener(this);

        binding.imageViewGroupAscending.setOnClickListener(this);
        binding.imageViewGroupDescending.setOnClickListener(this);

        binding.buttonCancel.setOnClickListener(this);
    }

    SelectSortListener mListener;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectSortListener) context;
    }

    @Override
    public void onClick(View v) {
        Comparator<User> comparator = null;

        if (v == binding.imageViewNameAscending || v == binding.imageViewNameDescending) {
            comparator = Comparator.comparing(User::getName);
        }
        else if (v == binding.imageViewEmailAscending || v == binding.imageViewEmailDescending) {
            comparator = Comparator.comparing(User::getEmail);
        }
        else if (v == binding.imageViewGenderAscending || v == binding.imageViewGenderDescending) {
            comparator = Comparator.comparing(User::getGender);
        }
        else if (v == binding.imageViewAgeAscending || v == binding.imageViewAgeDescending) {
            comparator = Comparator.comparingInt(User::getAge);
        }
        else if (v == binding.imageViewStateAscending || v == binding.imageViewStateDescending) {
            comparator = Comparator.comparing(User::getState);
        }
        else if (v == binding.imageViewGroupAscending || v == binding.imageViewGroupDescending) {
            comparator = Comparator.comparing(User::getGroup);
        }
        else if (v == binding.buttonCancel) {
            mListener.sortToUsersCancel();
            return;
        }

        if (v == binding.imageViewNameDescending ||
                v == binding.imageViewEmailDescending ||
                v == binding.imageViewGenderDescending ||
                v == binding.imageViewAgeDescending ||
                v == binding.imageViewStateDescending ||
                v == binding.imageViewGroupDescending) {
            comparator = comparator.reversed();
        }

        mListener.sortToUsers(comparator);
    }

    public interface SelectSortListener {
        public void sortToUsers(Comparator<User> comparator);
        public void sortToUsersCancel();

    }
}
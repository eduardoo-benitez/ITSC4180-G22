package edu.uncc.assignment04.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import edu.uncc.assignment04.R;
import edu.uncc.assignment04.databinding.FragmentSelectIncomeBinding;
import edu.uncc.assignment04.databinding.FragmentSelectLivingStatusBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectIncomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectIncomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String income = "$50k to < $100k";

    public SelectIncomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectIncomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectIncomeFragment newInstance(String param1, String param2) {
        SelectIncomeFragment fragment = new SelectIncomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentSelectIncomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectIncomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("SelectIncomeFragment");


        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0 ){
                    binding.textViewHouseHoldIncome.setText("<$25K");
                    income = "<$25K";
                }
                else if (progress == 1){
                    binding.textViewHouseHoldIncome.setText("$25K to < $50K");
                    income = "$25K to < $50K";
                }
                else if (progress == 2 ){
                    binding.textViewHouseHoldIncome.setText("$50K to < $100K");
                    income = "$50K to < $100K";
                }
                else if (progress == 3) {
                    binding.textViewHouseHoldIncome.setText("$100K to < $200K");
                    income = "$100K to < $200K";
                }
                else if (progress == 4 ){
                    binding.textViewHouseHoldIncome.setText("<$200K");
                    income = "<$200K";
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.incomeUpdate(income);
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelIncomeUpdate();
            }
        });
    }
    SelectIncomeFragment.SelectIncomeListener mListener;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectIncomeFragment.SelectIncomeListener)context;
    }
    public interface SelectIncomeListener {
        public void incomeUpdate(String income);
        public void cancelIncomeUpdate();
    }
}
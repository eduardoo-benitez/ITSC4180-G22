package edu.uncc.assignment04.fragments;

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
import android.widget.Toast;

import edu.uncc.assignment04.R;
import edu.uncc.assignment04.databinding.FragmentSelectEducationBinding;
import edu.uncc.assignment04.databinding.FragmentSelectLivingStatusBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectLivingStatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectLivingStatusFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String livingStatus;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SelectLivingStatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectLivingStatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectLivingStatusFragment newInstance(String param1, String param2) {
        SelectLivingStatusFragment fragment = new SelectLivingStatusFragment();
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

    FragmentSelectLivingStatusBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectLivingStatusBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("SelectLivingStatusFragment");

        RadioGroup radioGroup = binding.radioGroup.findViewById(R.id.radioGroup);

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = binding.radioGroup.findViewById(checkedId);
                if (checkedRadioButton != null) {
                    livingStatus = checkedRadioButton.getText().toString();
                }
            }
        });
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (livingStatus == null) {
                    Toast.makeText(getActivity(), "Select your living status", Toast.LENGTH_SHORT).show();
                }
                else {
                    mListener.livingStatusUpdate(livingStatus);
                }
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelLivingStatusUpdate();
            }
        });
    }
    SelectLivingStatusFragment.SelectLivingStatusListener mListener;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectLivingStatusFragment.SelectLivingStatusListener)context;
    }
    public interface SelectLivingStatusListener {
        public void livingStatusUpdate(String livingStatus);
        public void cancelLivingStatusUpdate();
    }
}
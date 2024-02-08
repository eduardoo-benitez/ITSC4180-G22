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

import edu.uncc.assignment04.R;
import edu.uncc.assignment04.databinding.FragmentIdentificationBinding;
import edu.uncc.assignment04.databinding.FragmentMainBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IdentificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IdentificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String name;
    String email;
    String role = "";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IdentificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IdentificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IdentificationFragment newInstance(String param1, String param2) {
        IdentificationFragment fragment = new IdentificationFragment();
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

    FragmentIdentificationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIdentificationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("IdentificationFragment");

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = binding.editTextName.getText().toString();
                email = binding.editTextEmail.getText().toString();

                RadioGroup radioGroup = binding.radioGroup.findViewById(R.id.radioGroup);

                binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton checkedRadioButton = binding.radioGroup.findViewById(checkedId);
                        if (checkedRadioButton != null) {
                            role = checkedRadioButton.getText().toString();
                        }
                    }
                });
                mListener.goToDemographic(name, email, role);
            }
        });
    }

    IdentificationListener mListener;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (IdentificationListener)context;
    }
    public interface IdentificationListener {
        public void goToDemographic(String name, String email, String role);
    }
}
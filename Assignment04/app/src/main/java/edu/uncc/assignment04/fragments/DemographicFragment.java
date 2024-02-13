package edu.uncc.assignment04.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.uncc.assignment04.Response;
import edu.uncc.assignment04.databinding.FragmentDemographicBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DemographicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DemographicFragment extends Fragment {
    private String selectEducation;
    private String selectMaritalStatus;
    private String setSelectLivingStatus;
    private String setSelectIncome;

    public void setSelectEducation(String selectEducation) {
        this.selectEducation = selectEducation;
        response.setEducation(selectEducation);
    }
    public void setSelectMaritalStatus(String selectMaritalStatus) {
        this.selectMaritalStatus = selectMaritalStatus;
        response.setMaritalStatus(selectMaritalStatus);
    }

    public void setSelectLivingStatus(String selectLivingStatus) {
        this.setSelectLivingStatus = selectLivingStatus;
        response.setLivingStatus(selectLivingStatus);
    }
    public void setSelectIncome(String selectIncome) {
        this.setSelectIncome = selectIncome;
        response.setIncomeStatus(selectIncome);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_RESPONSE = "RESPONSE";

    // TODO: Rename and change types of parameters
    private Response response;

    public DemographicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param response Parameter 1.
     * @return A new instance of fragment DemographicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DemographicFragment newInstance(Response response) {
        DemographicFragment fragment = new DemographicFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESPONSE, response);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.response = (Response) getArguments().getSerializable(ARG_RESPONSE);
        }
    }

    FragmentDemographicBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDemographicBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("DemographicFragment");

        binding.textViewEducation.setText(this.response.getEducation() != null ? this.response.getEducation() : "N/A");
        binding.textViewMaritalStatus.setText(this.response.getMaritalStatus() != null ? this.response.getMaritalStatus() : "N/A");
        binding.textViewIncomeStatus.setText(this.response.getIncomeStatus() != null ? this.response.getIncomeStatus() : "N/A");
        binding.textViewLivingStatus.setText(this.response.getLivingStatus() != null ? this.response.getLivingStatus() : "N/A");

        binding.buttonSelectEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToEducation();
            }
        });

        binding.buttonSelectMarital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToMaritalStatus();
            }
        });

        binding.buttonSelectLiving.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v) { mListener.goToLivingStatus();}
        });
        binding.buttonSelectIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { mListener.goToIncome();}
        });
        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.textViewEducation.getText() != "N/A"
                        && binding.textViewMaritalStatus.getText() != "N/A"
                        && binding.textViewLivingStatus.getText() != "N/A"
                        && binding.textViewIncomeStatus.getText() != "N/A") {
                    mListener.goToProfile();
                }
                else {
                    Toast.makeText(getActivity(), "Missing input!!!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    DemographicListener mListener;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (DemographicListener) context;
    }

    public interface DemographicListener {
        public void goToEducation();
        public void goToMaritalStatus();
        public void goToProfile();
        public void goToLivingStatus();
        public void goToIncome();
    }
}
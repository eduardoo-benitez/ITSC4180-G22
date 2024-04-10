package edu.uncc.gradesapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.local.QueryEngine;

import java.util.ArrayList;

import edu.uncc.gradesapp.R;
import edu.uncc.gradesapp.databinding.FragmentMyGradesBinding;
import edu.uncc.gradesapp.databinding.GradeRowItemBinding;
import edu.uncc.gradesapp.models.Grade;

public class MyGradesFragment extends Fragment {
    public MyGradesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_add){
            mListener.gotoAddGrade();
            return true;
        } else if(item.getItemId() == R.id.action_logout) {
            mListener.logout();
            return true;
        } else if(item.getItemId() == R.id.action_reviews){
            mListener.gotoViewReviews();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    FragmentMyGradesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyGradesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    MyGradesAdapter myGradesAdapter;
    ArrayList<Grade> mGrades = new ArrayList<>();
    ListenerRegistration listenerRegistration;
    int totalHours = 0;
    String gpaString = "";
    double totalGPA = 0;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("My Grades");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myGradesAdapter = new MyGradesAdapter();
        binding.recyclerView.setAdapter(myGradesAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //FirebaseFirestore call to initialize grade information in mGrades.
        listenerRegistration = db.collection("grades").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("A10", "Listen Failed", error);
                    return;
                }
                //reset the below variables each time the collection changes in order to update them correctly
                mGrades.clear();
                totalHours = 0;
                gpaString = "";
                totalGPA = 0;
                //loops through each document inside of of the collection.
                for (QueryDocumentSnapshot document: value) {
                    Grade grade = document.toObject(Grade.class);
                    //Only adds to grades if the grade object retreived from the grades collection has a user id that matches the logged in user.
                    if (mAuth.getCurrentUser().getUid().equals(grade.getCreatedByUId())) {
                        mGrades.add(grade);
                        //adds hours to a variable. Must parse an int from a string and remove the .00 at the end.
                        int hours = Integer.parseInt(grade.getCreditHours().substring(0, grade.getCreditHours().length() - 2));
                        //increments totalHours by the above
                        totalHours += hours;
                        //very scuffed.
                        //takes the letter grade and adds it to a gpa string. This is done once for each credit hour a class is worth.
                        //If you have two classes, one worth 3 credits with an A and another worth 4 with a B, the final GPA string would be:
                        //AAABBBB
                        for (int i = 0; i < hours; i++) {
                            gpaString += grade.getLetterGrade();
                        }
                    }
                }
                //for each char in the gpaString, the totalGPA is incremented depending on the values.
                //for the above example, AAABBBB -> 4+4+4+3+3+3+3
                for (char c: gpaString.toCharArray()) {
                    switch (c) {
                        case 'A':
                            totalGPA += 4;
                            break;
                        case 'B':
                            totalGPA += 3;
                            break;
                        case 'C':
                            totalGPA += 2;
                            break;
                        case 'D':
                            totalGPA += 1;
                            break;
                    }
                }
                //divide the total by total credit hours
                //(4+4+4+3+3+3+3)/7
                //only divided if credit hours is not 0 ofc
                if (totalHours != 0) {
                    totalGPA = totalGPA/totalHours;
                }
                else {
                    totalGPA = 0;
                }
                binding.textViewHours.setText("Hours: " + totalHours + ".0");
                binding.textViewGPA.setText(String.format("GPA: %.2f", totalGPA));

                myGradesAdapter.notifyDataSetChanged();
            }
        });
    }

    MyGradesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (MyGradesListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement MyGradesListener");
        }
    }

    public interface MyGradesListener {
        void gotoAddGrade();
        void logout();
        void gotoViewReviews();
    }

    class MyGradesAdapter extends RecyclerView.Adapter<MyGradesAdapter.MyGradesViewHolder> {
        @NonNull
        @Override
        public MyGradesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            GradeRowItemBinding binding = GradeRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new MyGradesViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull MyGradesViewHolder holder, int position) {
            Grade grade = mGrades.get(position);
            holder.setupUI(grade);
        }

        public int getItemCount() {
            return mGrades.size();
        }

        class MyGradesViewHolder extends RecyclerView.ViewHolder {
            GradeRowItemBinding mBinding;
            Grade mGrade;

            public MyGradesViewHolder(GradeRowItemBinding binding) {
                super (binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(Grade grade) {
                mGrade = grade;
                mBinding.textViewCourseName.setText(grade.getCourseName().toString());
                mBinding.textViewLetterGrade.setText(grade.getLetterGrade().toString());
                mBinding.textViewCreditHours.setText(grade.getCreditHours().toString());
                mBinding.textViewSemester.setText(grade.getSemester().toString());
                mBinding.textViewCourseNumber.setText(grade.getCourseCode().toString());
                //if the active user is the owner of grade on the current row, enable them to delete it by displaying the image.
                if (mAuth.getCurrentUser().getUid().equals(grade.getCreatedByUId())) {
                    mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("grades").document(mGrade.getDocId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                        }
                    });
                    mBinding.imageViewDelete.setVisibility(View.VISIBLE);
                }
                else {
                    mBinding.imageViewDelete.setVisibility(View.INVISIBLE);
                }
            }
        }
    }
}
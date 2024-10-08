package edu.uncc.gradesapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import edu.uncc.gradesapp.R;
import edu.uncc.gradesapp.databinding.FragmentAddGradeBinding;
import edu.uncc.gradesapp.models.Course;
import edu.uncc.gradesapp.models.LetterGrade;
import edu.uncc.gradesapp.models.Semester;

public class AddGradeFragment extends Fragment {
    public AddGradeFragment() {
        // Required empty public constructor
    }

    FragmentAddGradeBinding binding;
    public LetterGrade selectedLetterGrade;
    public Semester selectedSemester;
    public Course selectedCourse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddGradeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    //Retrieves the FirebaseAuth instance of the current project, GradesApp, so we can use .getCurrentUser().getUid()
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Grade");

        if(selectedCourse != null){
            binding.textViewCourse.setText(selectedCourse.getNumber());
        }

        if(selectedLetterGrade != null){
            binding.textViewGrade.setText(selectedLetterGrade.getLetterGrade());
        }

        if(selectedSemester != null){
            binding.textViewSemester.setText(selectedSemester.getName());
        }

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelAddGrade();
            }
        });

        binding.buttonSelectCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectCourse();
            }
        });

        binding.buttonSelectLetterGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectLetterGrade();
            }
        });

        binding.buttonSelectSemester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectSemester();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semesterText = binding.textViewSemester.getText().toString();
                String courseText = binding.textViewCourse.getText().toString();
                String gradeText = binding.textViewGrade.getText().toString();
                if(semesterText.equalsIgnoreCase("N/A") || courseText.equalsIgnoreCase("N/A") || gradeText.equalsIgnoreCase("N/A")){
                    Toast.makeText(getActivity(), "Please fill in all the available fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Retrieves the FirebaseFirestore instance so we can manipulate collections/documents
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    //DocumentReference is an object that is used to point to a specific, document.
                    //As such, in db.collection().document() the collection argument is the name of the collection and
                    //the document argument is the docId of the document within that collection we want to reference. In this case however,
                    //we are leaving the argument of document empty. This causes a new document with a random docId to be
                    //created, instead of referencing an existing one.
                    DocumentReference docRef = db.collection("grades").document();
                    //a hash map that maps the name of a document field to a value we can retrieve with code. Can even be hardcoded values.
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("letterGrade", gradeText);
                    data.put("courseName", selectedCourse.getName().toString());
                    data.put("courseNumber", courseText);
                    data.put("semester", semesterText);
                    data.put("creditHours",  String.valueOf(selectedCourse.getHours()));
                    data.put("createdByUId", mAuth.getCurrentUser().getUid());
                    data.put("docId", docRef.getId());

                    //using set overwrites whatever may already be in the document we are referencing.
                    docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mListener.completedAddGrade();
                            }
                        }
                    });

                }

            }
        });
        getActivity().setTitle(R.string.create_post_label);
    }

    AddGradeListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddGradeListener) {
            mListener = (AddGradeListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AddGradeListener");
        }
    }

    public interface AddGradeListener {
        void cancelAddGrade();
        void completedAddGrade();
        void gotoSelectSemester();
        void gotoSelectCourse();
        void gotoSelectLetterGrade();
    }

}
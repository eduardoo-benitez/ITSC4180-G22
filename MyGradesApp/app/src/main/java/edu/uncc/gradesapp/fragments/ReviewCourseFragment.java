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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uncc.gradesapp.R;
import edu.uncc.gradesapp.databinding.FragmentReviewCourseBinding;
import edu.uncc.gradesapp.databinding.ReviewRowItemBinding;
import edu.uncc.gradesapp.models.Course;
import edu.uncc.gradesapp.models.CourseReview;
import edu.uncc.gradesapp.models.Grade;
import edu.uncc.gradesapp.models.Review;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReviewCourseFragment extends Fragment {
    private static final String ARG_PARAM_COURSE= "ARG_PARAM_COURSE";
    private Course mCourse;
    ArrayList<Review> mReviews = new ArrayList<>();
    ReviewsAdapter adapter;

    public ReviewCourseFragment() {
        // Required empty public constructor
    }

    public static ReviewCourseFragment newInstance(Course course) {
        ReviewCourseFragment fragment = new ReviewCourseFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_COURSE, course);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCourse = (Course) getArguments().getSerializable(ARG_PARAM_COURSE);
        }
        setHasOptionsMenu(true);
    }

    FragmentReviewCourseBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReviewCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ListenerRegistration listenerRegistration;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Review Course");

        binding.textViewCourseName.setText(mCourse.getName());
        binding.textViewCourseNumber.setText(mCourse.getNumber());
        binding.textViewCreditHours.setText(mCourse.getHours() + " Credit Hours");

        adapter = new ReviewsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //FirebaseFirestore call to initialize reviewCourse information in mReviews.
        listenerRegistration = db.collection("reviewCourse").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("A10", "Listen Failed", error);
                    return;
                }
                mReviews.clear();
                for (QueryDocumentSnapshot document: value) {
                    Review review = document.toObject(Review.class);
                    //only add a review to mReviews if it matches the course that a user selected.
                    if (review.getCourse().equals(binding.textViewCourseNumber.getText().toString())) {
                        mReviews.add(review);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reviewText = binding.editTextReview.getText().toString();
                if(reviewText.isEmpty()){
                    Toast.makeText(getActivity(), "Review cannot be empty", Toast.LENGTH_SHORT).show();
                } else {

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference docRef = db.collection("reviewCourse").document();

                    //a hash map that maps the name of a document field to a value we can retrieve with code. Can even be hardcoded values.
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("createdAt", FieldValue.serverTimestamp());
                    data.put("createdByName", mAuth.getCurrentUser().getDisplayName());
                    data.put("postText", reviewText);
                    data.put("course", binding.textViewCourseNumber.getText().toString());
                    data.put("createdByUId", mAuth.getCurrentUser().getUid());
                    data.put("docId", docRef.getId());

                    docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //when a review is successfully submitted, we call changeNumReviews(1). This will increment the numReviews field
                            //of the correct courseReview by 1.
                            if (task.isSuccessful()) {
                                changeNumReviews(1);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        });
    }

    private void changeNumReviews(int inc) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //finds the specific courseReview document that has a course field that equals the course that we just wrote a review for/deleted's
        //course number. (we were not taught this)
        db.collection("courseReview")
                .whereEqualTo("course", mCourse.getNumber())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //if the query is successful and is not empty, we look through the "task" received (basically a collection with only documents that fit our
                            //criteria defined in the query) and for each document, we simply update the numReviews filed with an increment.
                            //There shold be only one document in each "task" since there should only be one course with a given course number.
                            if (task.getResult() != null && !(task.getResult().isEmpty())) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String documentId = document.getId();
                                    DocumentReference courseRef = db.collection("courseReview").document(documentId);
                                    courseRef.update("numReviews", FieldValue.increment(inc));
                                }
                            }
                            //if the query is not successful, meaning there is no courseReview document for the course we just reviewed,
                            //we must make a new one. Similar to what we do at the bottom CourseReview, except we initialize numReviews to 1.
                            //this should ONLY happen when there are no reviews for a course so this will never execute if we are trying to delete a review,
                            //since it needs to be there for us to delete it in the first place, hence the initialization to 1.
                            else {
                                DocumentReference docRef = db.collection("courseReview").document();
                                Map<String, Object> data = new HashMap<>();
                                List<String> favoredBy = new ArrayList<>();
                                data.put("course", mCourse.getNumber());
                                data.put("favoredBy", favoredBy);
                                data.put("numReviews", 1);
                                data.put("docId", docRef.getId());
                                docRef.set(data);
                            }
                        }
                    }
                });
    }


    class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {
        @NonNull
        @Override
        public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ReviewRowItemBinding itemBinding = ReviewRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new ReviewViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
            Review review = mReviews.get(position);
            holder.setupUI(review);
        }

        @Override
        public int getItemCount() {
            return mReviews.size();
        }

        class ReviewViewHolder extends RecyclerView.ViewHolder {
            ReviewRowItemBinding itemBinding;
            Review mReview;
            public ReviewViewHolder(ReviewRowItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }


            private void setupUI(Review review){
                mReview = review;

                itemBinding.textViewReview.setText(review.getPostText());
                itemBinding.textViewUserName.setText(review.getCreatedByName());

                //do the below when we need to display a date in a textview. we have to do this
                //because the getCreatedAt should be of a weird date datatype in order to satisfy FirebaseFirestore,
                //see Review model.
                if (review.getCreatedAt() == null) {
                    itemBinding.textViewCreatedAt.setText("N/A");
                } else {
                    Date date = this.mReview.getCreatedAt().toDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                    itemBinding.textViewCreatedAt.setText(sdf.format(date));
                }

                //if the currentyl logged in user is the author of a review, we may display the trashcan icon.
                if (mAuth.getCurrentUser().getUid().equals(mReview.getCreatedByUId())) {
                    itemBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("reviewCourse").document(mReview.getDocId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //when a trash can icon is pressed and a review is successfully deleted from reviewCourse, we call changeNumReviews(-1) on line 169
                                    //in order to decrement the numReviews in the correct courseReview document.
                                    changeNumReviews(-1);
                                }
                            });
                        }
                    });
                    itemBinding.imageViewDelete.setVisibility(View.VISIBLE);
                }
                else {
                    itemBinding.imageViewDelete.setVisibility(View.INVISIBLE);
                }

            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.cancel_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_cancel){
            mListener.onSelectionCanceled();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ReviewCourseListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (ReviewCourseListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ReviewCourseListener");
        }
    }

    public interface ReviewCourseListener{
        void onSelectionCanceled();
    }
}
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
import com.google.android.gms.tasks.Task;
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
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uncc.gradesapp.R;
import edu.uncc.gradesapp.databinding.CourseReviewRowItemBinding;
import edu.uncc.gradesapp.databinding.FragmentCourseReviewsBinding;
import edu.uncc.gradesapp.models.Course;
import edu.uncc.gradesapp.models.CourseReview;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CourseReviewsFragment extends Fragment {
    public CourseReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    FragmentCourseReviewsBinding binding;
    //holds data for each course, but the data is retrieved from FirebaseFirestore (number, favoredBy, numReviews).
    ArrayList<CourseReview> mCourseReviews = new ArrayList<>();
    //holds data for each course, retrieved via API. (name, creditHours, number, courseId)
    ArrayList<Course> mCourses = new ArrayList<>();
    CourseReviewsAdapter adapter;

    //necessary for when we need to populate the arraylist on line 97.
    ListenerRegistration listenerRegistration;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCourseReviewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Course Reviews");
        adapter = new CourseReviewsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        //API call to initialize all the course information in mCourses.
        getCourses();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //FirebaseFirestore call to initialize course information in mCourseReview
        listenerRegistration = db.collection("courseReview").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("A10", "Listen Failed", error);
                    return;
                }
                for (QueryDocumentSnapshot document: value) {
                    CourseReview courseReview = document.toObject(CourseReview.class);
                    mCourseReviews.add(courseReview);
                }
            }
        });
    }

    private final OkHttpClient client = new OkHttpClient();

    private void getCourses(){
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/cci-courses")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();
                    try {
                        mCourses.clear();
                        JSONObject json = new JSONObject(body);
                        JSONArray courses = json.getJSONArray("courses");
                        for (int i = 0; i < courses.length(); i++) {
                            Course course = new Course(courses.getJSONObject(i));
                            mCourses.add(course);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Failed to get courses", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    //RecyclerView for displaying course information. Is set to use mCourses for each of its rows.
    class CourseReviewsAdapter extends RecyclerView.Adapter<CourseReviewsAdapter.CourseReviewsViewHolder> {

        @NonNull
        @Override
        public CourseReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CourseReviewRowItemBinding itemBinding = CourseReviewRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new CourseReviewsViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull CourseReviewsViewHolder holder, int position) {
            holder.setupUI(mCourses.get(position));
        }

        @Override
        public int getItemCount() {
            return mCourses.size();
        }

        class CourseReviewsViewHolder extends RecyclerView.ViewHolder {
            CourseReviewRowItemBinding itemBinding;
            Course mCourse;
            public CourseReviewsViewHolder(CourseReviewRowItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
                this.itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoReviewCourse(mCourse);
                    }
                });
            }

            public void setupUI(Course course){
                this.mCourse = course;
                itemBinding.textViewCourseName.setText(course.getName());
                itemBinding.textViewCreditHours.setText(course.getHours() + " Credit Hours");
                itemBinding.textViewCourseNumber.setText(course.getNumber());

                //since RecyclerViews setup their UI row-wise there is a single object within the mCourse array selected each time the code
                //in setupUI is run. (line 195) we can loop through the mCourseReviews array to find a CourseReview with a matching course id.
                //if a matching id is found, we then check to see if the current user, found through mAuth.getCurrentUser().getUid(), is present
                //within the favoredBy array field of the CourseReview object. If it is, we initialize the heart to full, otherwise it is empty.
                for (CourseReview cr: mCourseReviews) {
                    if (course.getNumber().equals(cr.getCourse())) {
                        itemBinding.textViewCourseReviews.setText(cr.getNumReviews() + " Reviews");
                        if (cr.getFavoredBy().contains(mAuth.getCurrentUser().getUid())) {
                            itemBinding.imageViewHeart.setImageResource(R.drawable.ic_heart_full);
                        }
                        else {
                            itemBinding.imageViewHeart.setImageResource(R.drawable.ic_heart_empty);
                        }
                    }
                }
                itemBinding.imageViewHeart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        String userId = mAuth.getCurrentUser().getUid();

                        //first we check if a user (again, found through mAuth.getCurrentUser().getUid(), which in this case is
                        //set to the userId string above) is present within the CourseReview object that has a matching course id
                        //with the currently selected course object. If a user is present we set a flag to true.
                        boolean isFavored = false;
                        for (CourseReview cr: mCourseReviews) {
                            if (mCourse.getNumber().equals(cr.getCourse())) {
                                if (cr.getFavoredBy().contains(userId)) {
                                    isFavored = true;
                                }
                            }
                        }

                        //if the flag is true, after the user presses it, the image resource is set to empty and we once again find the CourseReview object
                        //matching the active course. We set a new List object (this is the datatype that FirebaseFirestore likes for arrays) to the selected CourseReview objects
                        //favoredBy field. We reflect the change we want to happen in FirebaseFirestore (removing the selected user) here. We then reference the
                        //the correct document within the collection courseReview by retrieving the docId of the CourseReview object with the course id
                        //matching the selected course. Note the usage of update (not set) and the arguments: the field name in FirebaseFirestore, and the thing we need that field to be
                        //(the List object we made and updated earlier).
                        if (isFavored) {
                            itemBinding.imageViewHeart.setImageResource(R.drawable.ic_heart_empty);
                            for (CourseReview cr: mCourseReviews) {
                                if (mCourse.getNumber().equals(cr.getCourse())) {
                                    List<String> favoredBy = cr.getFavoredBy();
                                    favoredBy.remove(userId);
                                    db.collection("courseReview").document(cr.getDocId()).update("favoredBy", favoredBy);
                                }
                            }
                        }
                        //if the flag is false, we do the same thing as above, but we add the user instead of removing it.
                        else {
                            itemBinding.imageViewHeart.setImageResource(R.drawable.ic_heart_full);
                            boolean courseExists = false;
                            for (CourseReview cr : mCourseReviews) {
                                if (mCourse.getNumber().equals(cr.getCourse())) {
                                    courseExists = true;
                                    List<String> favoredBy = cr.getFavoredBy();
                                    favoredBy.add(userId);
                                    db.collection("courseReview").document(cr.getDocId()).update("favoredBy", favoredBy);
                                }
                            }
                            //there may not be a document for all courses within the CourseReview collection
                            //so we need to make sure we create a document in the case that there is not one.
                            //this happens when a heart is clicked of a course that does not have a document in courseReviews.
                            //this means that the course cannot have anything in the favoredBy array when we click it, so we need to
                            //add the logged in user to the List and then initialize the values of the new document.
                            if (!courseExists) {
                                DocumentReference docRef = db.collection("courseReview").document();
                                Map<String, Object> data = new HashMap<>();
                                List<String> favoredBy = new ArrayList<>();
                                favoredBy.add(userId);
                                data.put("course", mCourse.getNumber());
                                data.put("favoredBy", favoredBy);
                                data.put("numReviews", 0);
                                data.put("docId", docRef.getId());
                                docRef.set(data);
                            }
                        }
                    }
                });
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

    CourseReviewsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (CourseReviewsListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement CourseReviewsListener");
        }
    }

    public interface CourseReviewsListener{
        void onSelectionCanceled();
        void gotoReviewCourse(Course course);
    }
}
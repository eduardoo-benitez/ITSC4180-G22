package edu.uncc.gradesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import edu.uncc.gradesapp.auth.LoginFragment;
import edu.uncc.gradesapp.auth.SignUpFragment;
import edu.uncc.gradesapp.fragments.AddGradeFragment;
import edu.uncc.gradesapp.fragments.CourseReviewsFragment;
import edu.uncc.gradesapp.fragments.MyGradesFragment;
import edu.uncc.gradesapp.fragments.ReviewCourseFragment;
import edu.uncc.gradesapp.fragments.SelectCourseFragment;
import edu.uncc.gradesapp.fragments.SelectLetterGradeFragment;
import edu.uncc.gradesapp.fragments.SelectSemesterFragment;
import edu.uncc.gradesapp.models.Course;
import edu.uncc.gradesapp.models.CourseReview;
import edu.uncc.gradesapp.models.LetterGrade;
import edu.uncc.gradesapp.models.Semester;

//Select*Fragment are left uncommented since we were provided those and they are mainly just dealing with
//the data from the API calls.
//When drag and dropping the google-services.json file press the drop down
//that says Android in the top left and choose Project. From there drag and drop the .json into the app dir.
//Make sure the correct things are imported in the build.grade.kts files and note which is which, since there are two.
//Firebase should tell you but I think these should be what you need:

//for build.grade.kts (Module :app):
    //plugins {
    //        id("com.android.application")
    //        id("com.google.gms.google-services")
    //        }
    //dependencies {
    //        ...
    //        implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    //        implementation("com.google.firebase:firebase-firestore")
    //        implementation("com.google.firebase:firebase-auth")
    //        }
//for build.grade.kts (Project :GradesApp):
    //plugins {
    //        ...
    //        id("com.google.gms.google-services") version "4.4.1" apply false
    //        }

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener,
        SignUpFragment.SignUpListener, MyGradesFragment.MyGradesListener, AddGradeFragment.AddGradeListener,
        SelectLetterGradeFragment.LetterGradeListener, SelectSemesterFragment.SemestersListener, SelectCourseFragment.CoursesListener,
        CourseReviewsFragment.CourseReviewsListener, ReviewCourseFragment.ReviewCourseListener {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(mAuth.getCurrentUser() == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new LoginFragment())
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new MyGradesFragment())
                    .commit();
        }
    }

    @Override
    public void createNewAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new SignUpFragment())
                .commit();
    }

    @Override
    public void authCompleted() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new MyGradesFragment())
                .commit();
    }

    @Override
    public void login() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();
    }


    @Override
    public void gotoAddGrade() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new AddGradeFragment(), "add-grade-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void logout() {
        mAuth.signOut();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();
    }

    @Override
    public void gotoViewReviews() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new CourseReviewsFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancelAddGrade() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void completedAddGrade() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoSelectSemester() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new SelectSemesterFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectCourse() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new SelectCourseFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectLetterGrade() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new SelectLetterGradeFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onLetterGradeSelected(LetterGrade letterGrade) {
        AddGradeFragment fragment = (AddGradeFragment) getSupportFragmentManager().findFragmentByTag("add-grade-fragment");
        if(fragment != null){
            fragment.selectedLetterGrade = letterGrade;
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSemesterSelected(Semester semester) {
        AddGradeFragment fragment = (AddGradeFragment) getSupportFragmentManager().findFragmentByTag("add-grade-fragment");
        if(fragment != null){
            fragment.selectedSemester = semester;
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCourseSelected(Course course) {
        AddGradeFragment fragment = (AddGradeFragment) getSupportFragmentManager().findFragmentByTag("add-grade-fragment");
        if(fragment != null){
            fragment.selectedCourse = course;
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSelectionCanceled() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoReviewCourse(Course course) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, ReviewCourseFragment.newInstance(course))
                .addToBackStack(null)
                .commit();
    }
}
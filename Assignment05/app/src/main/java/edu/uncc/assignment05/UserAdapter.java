package edu.uncc.assignment05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import edu.uncc.assignment05.models.User;

public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_list_item, parent, false); //inflate the layout used for the custom adapter.
        }

        User user = getItem(position);

        //create TextViews for each of the field. pulled from the user_list_item.xml
        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewEmail = convertView.findViewById(R.id.textViewEmail);
        TextView textViewGender = convertView.findViewById(R.id.textViewGender);
        TextView textViewAge = convertView.findViewById(R.id.textViewAge);
        TextView textViewState = convertView.findViewById(R.id.textViewState);
        TextView textViewGroup = convertView.findViewById(R.id.textViewGroup);

        //set text of the newly created TextViews
        textViewName.setText(user.getName());
        textViewEmail.setText(user.getEmail());
        textViewGender.setText(user.getGender());
        textViewAge.setText(String.valueOf(user.getAge()));
        textViewState.setText(user.getState());
        textViewGroup.setText(user.getGroup());

        return convertView;
    }
}

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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_list_item, parent, false);
        }

        User user = getItem(position);

        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewEmail = convertView.findViewById(R.id.textViewEmail);
        TextView textViewAge = convertView.findViewById(R.id.textViewAge);
        TextView textViewGender = convertView.findViewById(R.id.textViewGender);
        TextView textViewGroup = convertView.findViewById(R.id.textViewGroup);

        textViewName.setText(user.getName());
        textViewEmail.setText(user.getEmail());
        textViewAge.setText(String.valueOf(user.getAge()));
        textViewGender.setText(user.getGender());
        textViewGroup.setText(user.getGroup());

        return convertView;
    }
}

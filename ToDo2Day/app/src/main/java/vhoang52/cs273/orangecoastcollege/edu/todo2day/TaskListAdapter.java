package vhoang52.cs273.orangecoastcollege.edu.todo2day;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.List;

/**
 * Adapter class to inflate the layout of the task list view with a custom xml layout
 *
 * @author Vincent Hoang
 */

public class TaskListAdapter extends ArrayAdapter<Task> {
    private Context mContext;
    private int mResourceId;
    private List<Task> mTaskList;

    /**
     * Constructor for the adapter
     * @param context Context from the activity creating an instance of the adapter
     * @param resource The resource id to inflate the view with
     * @param objects The objects to pull information from
     */
    public TaskListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Task> objects) {
        super(context, resource, objects);

        mContext = context;
        mResourceId = resource;
        mTaskList = objects;
    }

    /**
     * Manages the position and index of the objects inflated by the adapter (well, it actually does the work of inflating the objects in order from the list provided in the
     * constructor)
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        Task selectedTask = mTaskList.get(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(mResourceId, null);

        CheckBox selectedCheckBox = (CheckBox) view.findViewById(R.id.isDoneCheckBox);
        selectedCheckBox.setChecked(selectedTask.isDone());
        selectedCheckBox.setText(selectedTask.getDescription());

        selectedCheckBox.setTag(selectedTask);
        return view;
    }
}

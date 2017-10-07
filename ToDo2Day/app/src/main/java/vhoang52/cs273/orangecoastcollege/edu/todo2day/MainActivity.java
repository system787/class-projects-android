package vhoang52.cs273.orangecoastcollege.edu.todo2day;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * This is the one and only user-facing activity of the application.
 *
 * All that occurs in this activity is assignment of Views to their associated reference IDs defined in the layout xml,
 * and a few handler methods to handle the self-explanatory methods.
 *
 * @author Vincent Hoang
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    Button mAddTaskButton;
    Button mClearAllTasksButton;
    EditText mInputEditText;
    ListView mTasksListView;
    DatabaseController dbc;
    TaskListAdapter mAdapter;

    List<Task> mTaskArrayList;

    /**
     * onCreate calls the database, assigns the appropriate views to their layout IDs, assigns onClickListeners, and assigns listview adapters
     *
     * A few quick notes about how the database model used in this application works.
     *  - The database methods are synchronized, so if this were to be deployed as a RESTful api or something similar, having many concurrent
     *      users will not lock the database with simultaneous commands
     *  - The database instance must be called within the onCreate, not as an instance variable.
     *      (e.g. DatabaseController dbc = DatabaseController.getInstance() will not work as an instance variable. Instead, declare it as a null
     *      instance variable first, such as Database dbc;, then immediately call dbc = DatabaseController.getInstance() early within onCreate)
     *  - Database commands are integrated into the models themselves. To get a List of all entries of a particular model, call ModelName.getAll(dbc).
     *      To create a new entry in the database, create an object, then call objectName.save(dbc) where dbc is an instance of DatabaseController
     *  - Before reading or writing to the database, call dbc.openDatabase() to query the DatabaseController to give the user the rights to the next database
     *      actions. Then, pass the instance of DatabaseController into the object calling a database method. (e.g. book1.save(dbc)). Always close the database
     *      after finishing your actions. You can call more than one database action within one openDatabase window, but always call dbc.close() after to let the
     *      database controller know you're done reading from or writing to the database.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate() before calling DatabaseController.getInstance()");
        dbc = DatabaseController.getInstance(this);

        Log.d(TAG, "onCreate() before first database call");

        dbc.openDatabase();
        mTaskArrayList = Task.getAll(dbc);
        dbc.close();

        mAddTaskButton = (Button) findViewById(R.id.addTaskButton);
        mAddTaskButton.setOnClickListener(this);

        mClearAllTasksButton = (Button) findViewById(R.id.clearAllTasksButton);
        mClearAllTasksButton.setOnClickListener(this);

        mInputEditText = (EditText) findViewById(R.id.taskEditText);

        mTasksListView = (ListView) findViewById(R.id.taskListView);
        mTasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                checkTask(view);
            }
        });

        Log.d(TAG, "onCreate(0 before creating and assigning list adapter");
        mAdapter = new TaskListAdapter(this, R.layout.task_item, mTaskArrayList);
        mTasksListView.setAdapter(mAdapter);
    }

    /**
     * onClick handler. directs traffic toward the correct layout id
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addTaskButton:
                addTask();
                break;
            case R.id.clearAllTasksButton:
                deleteAllTasks();
                break;
        }
    }

    /**
     * Adds a task to the list in both the activity's list as well as saving it to the database.
     */
    private void addTask() {
        if (mInputEditText.getText().toString().length() > 0) {
            Task newTask = new Task(mInputEditText.getText().toString());

            dbc.openDatabase();
            newTask.save(dbc);
            dbc.close();

            mTaskArrayList.add(newTask);
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Please enter a description for the task", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * deletes all tasks present onscreen as well as their entries in the database
     */
    private void deleteAllTasks() {
        if (mTaskArrayList.size() == 0) {
            return;
        }

        dbc.openDatabase();
        Task.deleteAllTasks(dbc);
        dbc.close();

        mTaskArrayList.clear();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Updates the isChecked property of the items in the list
     */
    private void checkTask(View view) {
        CheckBox selectedCheckBox = (CheckBox) view;
        Task selectedTask = (Task) selectedCheckBox.getTag();

        selectedTask.setDone(selectedCheckBox.isChecked());

        dbc.openDatabase();
        selectedTask.update(dbc);
        mTaskArrayList = Task.getAll(dbc);
        dbc.close();
    }
}

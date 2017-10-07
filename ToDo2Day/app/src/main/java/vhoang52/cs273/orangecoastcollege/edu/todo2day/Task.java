package vhoang52.cs273.orangecoastcollege.edu.todo2day;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class for Task objects.
 * Also handles the database methods and hosts the LocalDatabaseModel() for Task objects
 *
 * @author Vincent Hoang
 */

public final class Task {
    private static final String TAG = "Task";
    private static final String TABLE_NAME = "tasks";
    private static final String ID_FIELD = "id";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String DONE_FIELD = "is_done";

    private int mId = -1;
    private String mDescription;
    private boolean mIsDone;

    /**
     * Default constructor with no parameters
     */
    public Task() {
        mId = -1;
        mDescription = "";
        mIsDone = false;
    }

    /**
     * 1 Parameter constructor
     *
     * @param description The user input from the EditText
     */
    public Task(String description) {
        mDescription = description;
        mIsDone = false;
    }

    /**
     * 2 Parameter constructor
     *
     * @param description The user input from the EditText
     * @param done        Boolean value representing the checkbox
     */
    public Task(String description, boolean done) {
        mDescription = description;
        mIsDone = done;
    }

    /**
     * 3 Parameter constructor
     *
     * @param id          the id of the object created by SQLite (for querying)
     * @param description The user input from the EditText
     * @param done        Boolean value representing the checkbox
     */
    public Task(int id, String description, boolean done) {
        mId = id;
        mDescription = description;
        mIsDone = done;
    }

    // non static methods (getters, setters, database methods) here


    /**
     * Gets the id of the object
     */
    public int getId() {
        return mId;
    }

    /**
     * Sets the id of the object
     */
    public void setId(int id) {
        mId = id;
    }

    /**
     * Gets the description of the object
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * Sets the description of the object
     */
    public void setDescription(String description) {
        mDescription = description;
    }

    /**
     * Gets the value of isDone
     */
    public boolean isDone() {
        return mIsDone;
    }

    /**
     * Sets the value of isDone
     */
    public void setDone(boolean done) {
        mIsDone = done;
    }

    /**
     * Saves the object to the database
     *
     * @param dbc instance of DatabaseController called by calling DatabaseController.getInstance() from the parent activity
     */
    public void save(DatabaseController dbc) {
        Log.d(TAG, "save() called");
        SQLiteDatabase database = dbc.database();
        ContentValues values = new ContentValues();
        values.put(DESCRIPTION_FIELD, mDescription);
        values.put(DONE_FIELD, (mIsDone ? 1 : 0));
        database.insert(TABLE_NAME, null, values);
    }

    /**
     * Deletes the object from the database.
     *
     * @param dbc instance of DatabaseController called by calling DatabaseController.getInstance() from the parent activity
     */
    public void delete(DatabaseController dbc) {
        Log.d(TAG, "delete() called");
        SQLiteDatabase database = dbc.database();
        database.delete(TABLE_NAME, "id" + " = ?", new String[]{String.valueOf(mId)});
    }

    /**
     * Updates the object in the database
     *
     * @param dbc instance of DatabaseController called by calling DatabaseController.getInstance() from the parent activity
     */
    public void update(DatabaseController dbc) {
        Log.d(TAG, "update() called");
        SQLiteDatabase database = dbc.database();
        ContentValues values = new ContentValues();
        values.put(ID_FIELD, mId);
        values.put(DESCRIPTION_FIELD, mDescription);
        values.put(DONE_FIELD, (mIsDone ? 1 : 0));

        database.update(TABLE_NAME, values, "id" + "=" + mId, null);
    }

    /**
     * To string..
     */
    @Override
    public String toString() {
        return "Task{" +
                "mId=" + mId +
                ", mDescription='" + mDescription + '\'' +
                ", mIsDone=" + mIsDone +
                '}';
    }

    /**
     * Queries the database to get a specific Task object by it's id
     *
     * @param dbc instance of DatabaseController called by calling DatabaseController.getInstance() from the parent activity
     * @param id  the id of the object to retrieve from the database
     * @return the Task object
     */
    // static methods here
    public static Task getById(DatabaseController dbc, int id) {
        Log.d(TAG, "getById() called; id to get: " + id);
        SQLiteDatabase database = dbc.database();
        Cursor cursor = database.query(
                "tasks",
                new String[]{"id", "description", "is_done"},
                "id" + "=" + id,
                null,
                null,
                null,
                null);

        Task task = null;
        if (cursor.moveToFirst()) {
            task = new Task(cursor.getInt(0), cursor.getString(1), cursor.getInt(2) == 1);
        }
        cursor.close();
        return task;
    }

    /**
     * Deletes a specific Task object from the database by its id
     * @param dbc instance of DatabaseController called by calling DatabaseController.getInstance() from the parent activity
     * @param id the id of the Task to delete
     */
    public static void deleteById(DatabaseController dbc, int id) {
        Log.d(TAG, "deleteById() called; id to delete: " + id);
        SQLiteDatabase database = dbc.database();
        database.delete(TABLE_NAME, "id" + " = ?", new String[]{String.valueOf(id)});
    }

    /**
     * Updates a specific Task object in the database by its id
     * @param dbc instance of DatabaseController called by calling DatabaseController.getInstance() from the parent activity
     * @param task the id of the Task to update
     */
    public static void updateTaskById(DatabaseController dbc, Task task) {
        Log.d(TAG, "updateTaskById() called");
        SQLiteDatabase database = dbc.database();
        ContentValues values = new ContentValues();
        values.put(ID_FIELD, task.getId());
        values.put(DESCRIPTION_FIELD, task.getDescription());
        values.put(DONE_FIELD, task.isDone() ? 1 : 0);

        database.update(TABLE_NAME, values, "id" + "=" + task.getId(), null);
    }

    /**
     * Gets a List containing all of the reconstructed Task objects stored in the database
     * @param dbc instance of DatabaseController called by calling DatabaseController.getInstance() from the parent activity
     * @return ArrayList of type Task
     */
    public static List<Task> getAll(DatabaseController dbc) {
        Log.d(TAG, "getAll() called");
        SQLiteDatabase database = dbc.database();

        ArrayList<Task> allTasksList = new ArrayList<>();
        Cursor cursor = database.query(
                TABLE_NAME,
                new String[]{ID_FIELD, DESCRIPTION_FIELD, DONE_FIELD},
                null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task(cursor.getInt(0), cursor.getString(1), cursor.getInt(2) == 1);
                allTasksList.add(task);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return allTasksList;
    }

    /**
     * Deletes all entries from the Tasks table in the database (without deleting the database);
     * @param dbc instance of DatabaseController called by calling DatabaseController.getInstance() from the parent activity
     */
    public static void deleteAllTasks(DatabaseController dbc) {
        Log.d(TAG, "deleteAllTasks() called");
        SQLiteDatabase db = dbc.database();
        db.delete(TABLE_NAME, null, null);
    }

    /**
     * LocalDataBaseModel() as defined in DatabaseController
     * Contains constructor, onUpgrade and onCreate methods
     */
    public static class Model extends DatabaseController.LocalDatabaseModel {

        public Model() {
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
            Log.d(TAG, "LocalDatabaseModel.onUpgrade() called");
            database.execSQL("DROP TABLE IF EXISTS tasks");
            onCreate(database);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            Log.d(TAG, "LocalDatabaseModel.onCreate() called");
            String table = "CREATE TABLE tasks(id INTEGER PRIMARY KEY, description TEXT, is_done INTEGER)";
            database.execSQL(table);
        }
    }
}

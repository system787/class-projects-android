package vhoang52.cs273.orangecoastcollege.edu.todo2day;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

/**
 * Database Controller
 * <p>
 * All methods available to the user are synchronized to prevent SQLite hiccups
 * See documentation above certain methods to see which methods to use or not use
 */

public final class DatabaseController extends SQLiteOpenHelper {

    /**
     * Abstract class used for object models to create a database model for that object class
     */
    public static abstract class LocalDatabaseModel {

        public LocalDatabaseModel() {

        }

        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        }

        public abstract void onCreate(SQLiteDatabase database);
    }

    private SQLiteDatabase database;
    private int openConnections = 0;

    private static final String TAG = "DatabaseController";

    private static final String DATABASE = "cs273.db";
    private static final int VERSION = 1;
    private static DatabaseController instance = null;

    // Add LocalDatabaseModels here
    private final LocalDatabaseModel[] models = new LocalDatabaseModel[]{
            new Task.Model()};

    /**
     * Use this method to get an instance of DatabaseController
     * getInstance() cannot be called to initialize an instance variable.
     * For an example of correct usage, create a DatabaseController object dbc as a null instance variable
     * Then, call dbc = DatabaseController.getInstance(*context* e.g. *this*) in onCreate before any database calls
     *
     * @param context from the activity calling DatabaseController.getInstance()
     * @return an instance of DatabaseController
     */
    public synchronized static DatabaseController getInstance(Context context) {
        Log.d(TAG, "getInstance() called");
        if (instance == null) {
            instance = new DatabaseController(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseController(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    /**
     * Must be called from the same thread as the original openDatabase call.
     */
    @Override
    public synchronized void close() {
        Log.d(TAG, "close() called; Attempting to close database; OpenConnections: " + openConnections);
        if (database == null || openConnections == 0) {
            throw new IllegalStateException("Database already closed or has never been opened.");
        }
        openConnections--;
        if (openConnections != 0) {
            return;
        }
        database = null;
        super.close();
    }

    /**
     * Do not manually call this method! Use openDatabase(), database() and close()!
     * <p>
     * Opens the SQLiteDatabase if not already opened.
     * This implementation does the exact same thing as getWritableDatabase and thus will return a writable database.
     *
     * @return the newly opened database or the existing database.
     */
    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        return getWritableDatabase();
    }

    /**
     * Do not manually call this method! Use openDatabase(), database() and close()!
     * <p>
     * Opens the SQLiteDatabase if not already opened.
     *
     * @return the newly opened database or the existing database.
     */
    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        if (database == null) {
            database = super.getWritableDatabase();
        }
        openConnections++;
        return database;
    }

    /**
     * Open the database. Always pair this call with close() and use database() to get the opened database!
     */
    public synchronized void openDatabase() {
        Log.d(TAG, "openDatabase() called; Open Connections: " + openConnections);
        getWritableDatabase();
    }

    /**
     * Returns the opened database. Throws an exception if the database has not been opened yet!
     *
     * @return the database.
     */
    public synchronized SQLiteDatabase database() {
        Log.d(TAG, "database() called");
        if (database == null) {
            throw new IllegalStateException("Database has not been opened yet!");
        }
        return database;
    }

    /**
     * Sets foreign key constraints for older versions of Android, then calls onCreate in each model
     */
    @Override
    public synchronized void onCreate(SQLiteDatabase db) {
        setForeignKeyConstraintsEnabled(db);
        for (LocalDatabaseModel model : models) {
            model.onCreate(db);
        }
    }

    /**
     * Sets foreign key constraints for older versions of Android, then calls onUpgrade in each model
     */
    @Override
    public synchronized void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        setForeignKeyConstraintsEnabled(db);
        for (LocalDatabaseModel model : models) {
            model.onUpgrade(db, oldVersion, newVersion);
        }
    }

    /**
     * Sets foreign key constraints for older versions of Android
     */
    @Override
    public synchronized void onOpen(SQLiteDatabase db) {
        setForeignKeyConstraintsEnabled(db);
    }

    /**
     * Sets foreign key constraints for older versions of Android
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public synchronized void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        //Skip for Android 4.1 and newer as this is already handled in onConfigure
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && !db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    /**
     * Gets the number of entries in the specified table
     */
    public long getCount(String table) {
        return DatabaseUtils.queryNumEntries(database(), table);
    }
}

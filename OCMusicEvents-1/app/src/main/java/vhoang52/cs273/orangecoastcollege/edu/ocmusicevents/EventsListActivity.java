package vhoang52.cs273.orangecoastcollege.edu.ocmusicevents;

/**
 * @author Vincent Hoang
 */

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * List of music events around OC
 */
public class EventsListActivity extends ListActivity {

    /**
     * Sets list adapter to display information found in MusicEvent
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_events_list);

        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, MusicEvent.titles));
    }

    /**
     * Grabs the position the user clicked in, the details of the associated item, and generates an intent and hands it off to the operating system
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //super.onListItemClick(l, v, position, id);

        String title = MusicEvent.titles[position];
        String details = MusicEvent.details[position];

        Intent intent = new Intent(this, EventDetailsActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("details", details);
        startActivity(intent);
    }
}

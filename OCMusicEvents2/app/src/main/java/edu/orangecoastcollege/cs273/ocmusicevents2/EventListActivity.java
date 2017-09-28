package edu.orangecoastcollege.cs273.ocmusicevents2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EventListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, MusicEvent.titles));

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Intent detailsIntent = new Intent(this, EventDetailsActivity.class);
        String title = MusicEvent.titles[position];
        String details = MusicEvent.details[position];
        detailsIntent.putExtra("Title", title);
        detailsIntent.putExtra("Details", details);

        startActivity(detailsIntent);
    }
}

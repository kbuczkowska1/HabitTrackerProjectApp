package com.example.android.habittrackerapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.habittrackerapp.data.SwimmingContact;
import com.example.android.habittrackerapp.data.SwimmingContact.SwimmingEntry;
import com.example.android.habittrackerapp.data.SwimmingDbHelper;

import static com.example.android.habittrackerapp.data.SwimmingContact.SwimmingEntry.TABLE_NAME;

public class CatalogActivity extends AppCompatActivity {

    //Database helper that will provide us access to the database.
    private SwimmingDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup imagebutton to open EditorActivity.
        ImageButton image = (ImageButton) findViewById(R.id.icon);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new SwimmingDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    //Temporary helper method to display information in the onscreen TextView about the state of
    // the swimming day database.
    private Cursor displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                SwimmingEntry._ID,
                SwimmingEntry.COLUMN_DAY,
                SwimmingEntry.COLUMN_STYLE,
                SwimmingEntry.COLUMN_TIME,
                SwimmingEntry.COLUMN_NOTE,};
        // Perform a query on the swimming table.
        Cursor cursor = db.query(
                TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                 // The sort order

        TextView displayView = (TextView) findViewById(R.id.text);

        try {
            // _id - day - style - time - note
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The number of your entries: " + cursor.getCount() + ".\n\n");
            displayView.append(SwimmingEntry._ID + " - " +
                    SwimmingEntry.COLUMN_DAY + " - " +
                    SwimmingEntry.COLUMN_STYLE + " - " +
                    SwimmingEntry.COLUMN_TIME + " - " +
                    SwimmingEntry.COLUMN_NOTE + "\n");

            // Figure out the index of each column.
            int idColumnIndex = cursor.getColumnIndex(SwimmingEntry._ID);
            int dayColumnIndex = cursor.getColumnIndex(SwimmingEntry.COLUMN_DAY);
            int styleColumnIndex = cursor.getColumnIndex(SwimmingEntry.COLUMN_STYLE);
            int timeColumnIndex = cursor.getColumnIndex(SwimmingEntry.COLUMN_TIME);
            int noteColumnIndex = cursor.getColumnIndex(SwimmingEntry.COLUMN_NOTE);

            // Iterate through all the returned rows in the cursor.
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentStyle = cursor.getString(styleColumnIndex);
                String currentNote = cursor.getString(noteColumnIndex);
                int currentTime = cursor.getInt(timeColumnIndex);
                int currentDay = cursor.getInt(dayColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView.
                displayView.append(("\n" + currentID + " - " +
                        currentDay + " - " +
                        currentStyle + " - " +
                        currentTime + " - " +
                        currentNote));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

        return cursor;
    }

    //Helper method to insert hardcoded swimming data into the database. For debugging purposes only.
    private void insertSwimming() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object <dummy data>.
        ContentValues values = new ContentValues();
        values.put(SwimmingEntry.COLUMN_DAY, SwimmingContact.SwimmingEntry.DAY_FRIDAY);
        values.put(SwimmingEntry.COLUMN_STYLE, "Breaststroke ");
        values.put(SwimmingEntry.COLUMN_TIME, 60);
        values.put(SwimmingEntry.COLUMN_NOTE, "Correct");

        long newRowId = db.insert(TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "dummy data" menu option.
            case R.id.dummy_data:
                insertSwimming();
                displayDatabaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

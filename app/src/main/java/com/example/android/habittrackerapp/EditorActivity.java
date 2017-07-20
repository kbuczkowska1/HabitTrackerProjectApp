package com.example.android.habittrackerapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.habittrackerapp.data.SwimmingContact;
import com.example.android.habittrackerapp.data.SwimmingContact.SwimmingEntry;
import com.example.android.habittrackerapp.data.SwimmingDbHelper;

public class EditorActivity extends AppCompatActivity {

    // EditText field to enter day.
    private Spinner mDaySpinner;

    // EditText field to enter style.
    private EditText mStyleEditText;

    // EditText field to enter time.
    private EditText mTimeEditText;

    // EditText field to enter note.
    private EditText mNoteEditText;

    private int mDay = SwimmingContact.SwimmingEntry.DAY_SUNDAY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from.
        mDaySpinner = (Spinner) findViewById(R.id.spinner_day);
        mStyleEditText = (EditText) findViewById(R.id.edit_text_style);
        mTimeEditText = (EditText) findViewById(R.id.edit_text_time);
        mNoteEditText = (EditText) findViewById(R.id.edit_text_note);

        setupSpinner();
    }

    //Setup the dropdown spinner that allows the user to select the day of the swimming.
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout.
        ArrayAdapter daySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_day, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line.
        daySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner.
        mDaySpinner.setAdapter(daySpinnerAdapter);

        // Set the integer mSelected to the constant values.
        mDaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.MONDAY))) {
                        mDay = SwimmingEntry.DAY_MONDAY;
                    } else if (selection.equals(getString(R.string.TUESDAY))) {
                        mDay = SwimmingEntry.DAY_TUESDAY;
                    } else if (selection.equals(getString(R.string.WEDNESDAY))) {
                        mDay = SwimmingEntry.DAY_WEDNESDAY;
                    } else if (selection.equals(getString(R.string.THURSDAY))) {
                        mDay = SwimmingEntry.DAY_THURSDAY;
                    } else if (selection.equals(getString(R.string.FRIDAY))) {
                        mDay = SwimmingEntry.DAY_FRIDAY;
                    } else if (selection.equals(getString(R.string.SATURDAY))) {
                        mDay = SwimmingEntry.DAY_SATURDAY;
                    } else if (selection.equals(getString(R.string.SUNDAY))) {
                        mDay = SwimmingEntry.DAY_SUNDAY;
                    } else {
                        mDay = SwimmingEntry.DAY_SUNDAY;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDay = SwimmingEntry.DAY_SUNDAY;
            }
        });
    }

    //Get user input from editor and save new swimming day into database.
    private void insertSwimming() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String styleString = mStyleEditText.getText().toString().trim();
        String noteString = mNoteEditText.getText().toString().trim();

        String timeString = mTimeEditText.getText().toString().trim();
        int time = Integer.parseInt(timeString);

        // Create database helper.
        SwimmingDbHelper mDbHelper = new SwimmingDbHelper(this);

        // Gets the database in write mode.
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and swimming attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(SwimmingEntry.COLUMN_DAY, mDay);
        values.put(SwimmingEntry.COLUMN_STYLE, styleString);
        values.put(SwimmingEntry.COLUMN_TIME, time);
        values.put(SwimmingEntry.COLUMN_NOTE, noteString);


        // Insert a new row for swimming in the database, returning the ID of that new row.
        long newRowId = db.insert(SwimmingEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving activity", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Activity saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.editor_save:
                // Save pet to database
                insertSwimming();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

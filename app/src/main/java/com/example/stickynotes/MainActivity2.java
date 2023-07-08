package com.example.stickynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private EditText editTextNote;
    private Button buttonAddNote;
    private ListView listViewNotes;

    private SQLiteDatabase database;
    private NotesAdapter notesAdapter;
    private List<String> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editTextNote = findViewById(R.id.editTextNote);
        buttonAddNote = findViewById(R.id.buttonAddNote);
        listViewNotes = findViewById(R.id.listViewNotes);


        // Create or open the database
        database = openOrCreateDatabase("NotesDB", MODE_PRIVATE, null);
        createTable();

        // Fetch notes from the database and display them
        notesList = getNotes();
        notesAdapter = new NotesAdapter(this, R.layout.note_item, notesList);
        listViewNotes.setAdapter(notesAdapter);

        // Add note button click listener
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteText = editTextNote.getText().toString();
                updateWidget();
                addNoteToDatabase(noteText);
                editTextNote.setText("");
                refreshNotesList();
                Toast.makeText(MainActivity2.this, "Note added", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createTable() {
        database.execSQL("CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY AUTOINCREMENT, note TEXT)");
    }

    private void addNoteToDatabase(String noteText) {
        String insertQuery = "INSERT INTO notes (note) VALUES ('" + noteText + "')";
        database.execSQL(insertQuery);

    }

    private List<String> getNotes() {
        List<String> notes = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM notes", null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String noteText = cursor.getString(cursor.getColumnIndex("note"));
                notes.add(noteText);
                cursor.moveToNext();
            }
        }


        cursor.close();
        return notes;

    }


    private void refreshNotesList() {
        notesList.clear();
        notesList.addAll(getNotes());
        notesAdapter.notifyDataSetChanged();
    }
    private void updateWidget(){
        AppWidgetManager appWidgetManager= AppWidgetManager.getInstance(this);
        RemoteViews remoteViews = new RemoteViews(this.getPackageName(),R.layout.widget_layout);
        ComponentName thisWidget = new ComponentName(this, AppWidget.class);
        remoteViews.setTextViewText(R.id.idTVWidget,editTextNote.getText().toString());
        appWidgetManager.updateAppWidget(thisWidget,remoteViews);
    }
}

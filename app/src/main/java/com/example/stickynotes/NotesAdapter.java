package com.example.stickynotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class NotesAdapter extends ArrayAdapter<String> {

    private Context context;
    private int resource;
    private List<String> notesList;

    public NotesAdapter(@NonNull Context context, int resource, @NonNull List<String> notesList) {
        super(context, resource, notesList);
        this.context = context;
        this.resource = resource;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, null);
        }

        String noteText = notesList.get(position);

        if (noteText != null) {
            TextView textViewNote = view.findViewById(R.id.textViewNote);
            textViewNote.setText(noteText);
        }

        return view;
    }
}


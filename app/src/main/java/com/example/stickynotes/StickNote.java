package com.example.stickynotes;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class StickNote {
    String getStick(Context context) {
//get the file from path
        File fileEvents = new File(context.getFilesDir().getPath() + "/stickynote.txt");
//create a StringBuilder to store the text from file
        StringBuilder text = new StringBuilder();
        try {
            //use the BufferedReader to Read the file efficiently
            BufferedReader br = new BufferedReader(new FileReader(fileEvents));
            String line;
            //read a single line at a time
            //append newline character after each line
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
           //close the BufferedReader
            br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        //finally return the string i.e. the retrieved data from file
        return text.toString();
    }
    //this function saves the new content in the file if it exists
    //or will create a new one
    void setStick(String textToBeSaved, Context context) {
        String text = textToBeSaved;
        //create the FileOutputStream to efficiently write the file
        FileOutputStream fos = null;
        try {
            //get the file from storage
            fos = context.getApplicationContext().openFileOutput("stickynote.txt", MODE_PRIVATE);
            //write to the file at once
            fos.write(text.getBytes());
        } catch (IOException e) {
           e.printStackTrace();
       } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
           }

    }
}

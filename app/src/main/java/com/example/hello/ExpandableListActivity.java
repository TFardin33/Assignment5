package com.example.hello;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    List<String> groupData;
    HashMap<String, List<String>> childData;
    int lastExpandedGroup = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list);

        expandableListView = findViewById(R.id.expandableListView);
        data();
        ExpandableListAdapter adapter = new ExpandableListAdapter(this, groupData, childData);
        expandableListView.setAdapter(adapter);

        expandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            String groupText = groupData.get(groupPosition);
            Toast.makeText(getApplicationContext(), groupText, Toast.LENGTH_SHORT).show();
            return false;
        });

        expandableListView.setOnGroupCollapseListener(groupPosition -> {
            String groupText = groupData.get(groupPosition);
            Toast.makeText(getApplicationContext(), groupText + " is Collapsed", Toast.LENGTH_SHORT).show();
        });

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            String childText = childData.get(groupData.get(groupPosition)).get(childPosition);
            showDetailsDialog(groupData.get(groupPosition), groupPosition);
            return false;
        });

        expandableListView.setOnGroupExpandListener(groupPosition -> {
            if (lastExpandedGroup != -1 && lastExpandedGroup != groupPosition) {
                expandableListView.collapseGroup(lastExpandedGroup);
            }
            lastExpandedGroup = groupPosition;
        });
    }

    private void data() {
        String[] headerString = getResources().getStringArray(R.array.tech_array);
        String[] childString = getResources().getStringArray(R.array.tech_array_subtitle);
        groupData = new ArrayList<>();
        childData = new HashMap<>();

        for (int i = 0; i < headerString.length; i++) {
            groupData.add(headerString[i]);
            List<String> child = new ArrayList<>();
            child.add(childString[i]);
            childData.put(groupData.get(i), child);
        }
    }

    void showDetailsDialog(String groupName, int groupPosition) {
        // Get the string array from resources
        String[] subtitles = getResources().getStringArray(R.array.tech_array_subtitle);

        // Parse the specific subtitle for the current groupPosition
        String subtitle = subtitles[groupPosition];

        // Extract ID and Name from the subtitle using simple string operations
        String studentId = subtitle.split("\n")[0].replace("Student ID: ", "").trim();
        String studentName = subtitle.split("\n")[1].replace("Name: ", "").trim();
        String cGPA = subtitle.split("\n")[2].replace("CGPA: ", "").trim();
        String address = subtitle.split("\n")[3].replace("Address: ", "").trim();
        String university = "University: Leading University";

        // Inflate dialog layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_info_layout, null);
        TextView idTextView = dialogView.findViewById(R.id.id_text);
        TextView nameTextView = dialogView.findViewById(R.id.name_text);
        TextView cgpaTextView = dialogView.findViewById(R.id.cgpa_text);
        TextView addressTextView = dialogView.findViewById(R.id.address_text);
        TextView universityTextView = dialogView.findViewById(R.id.university_text);

        // Set dialog data
        idTextView.setText("Student ID: " + studentId);
        nameTextView.setText("Name: " + studentName);
        cgpaTextView.setText("CGPA: " + cGPA);
        addressTextView.setText("Address: " + address);
        universityTextView.setText(university);

        // Create and display the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        // OK button to dismiss the dialog
        Button okButton = dialogView.findViewById(R.id.ok_button);
        okButton.setOnClickListener(v -> dialog.dismiss());
    }

}

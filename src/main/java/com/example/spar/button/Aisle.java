package com.example.spar.button;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spar.MainActivity;
import com.example.spar.R;

public class Aisle extends AppCompatActivity {
    public String left;
    public String right;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aisle);

        Intent intent = getIntent();
        left = intent.getStringExtra(Activity2.EXTRA_TEXT);
        right = intent.getStringExtra(Activity2.EXTRA_TEXT_2);

        TextView text = findViewById(R.id.text);
        TextView text2 = findViewById(R.id.text2);
        TextView text3 = findViewById(R.id.text3);
        String leftD = getDescription(left);
        String rightD = getDescription(right);
        text.setText("You are on an intersection of aisles");
        text2.setText("Left aisle contains: " + leftD);
        text3.setText("Right aisle contains: " + rightD);
    }

    public void buttonClicked(View view) {
        Intent intent;
        if (view.getId() == R.id.left) {
            intent = new Intent(this, AisleDetails.class);
            intent.putExtra(Activity2.EXTRA_TEXT, left);
            intent.putExtra(Activity2.EXTRA_TEXT_2, right);
        } else if (view.getId() == R.id.right) {
            intent = new Intent(this, AisleDetails.class);
            intent.putExtra(Activity2.EXTRA_TEXT, right);
            intent.putExtra(Activity2.EXTRA_TEXT_2, left);
        } else if (view.getId() == R.id.scan){
            intent = new Intent(this, Activity2.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
    }

    public String getDescription(String curr) {
        SQLiteDatabase myDB = openOrCreateDatabase("deneme.db", MODE_PRIVATE, null);
        Cursor cursor = myDB.rawQuery("select * from aisle where id = " + curr, null);
        String description = "";

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                description = cursor.getString(cursor.getColumnIndex("description"));
                cursor.moveToNext();
            }
        }
        return description;
    }
}
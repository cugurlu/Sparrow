package com.example.spar.swipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spar.MainActivity;
import com.example.spar.R;

public class Intersection extends AppCompatActivity {
    public String left;
    public String right;

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intersection);

        Intent intent = getIntent();
        left = intent.getStringExtra(Scan.EXTRA_TEXT);
        right = intent.getStringExtra(Scan.EXTRA_TEXT_2);

        TextView text = findViewById(R.id.text);
        TextView leftAisle = findViewById(R.id.left);
        TextView rightAisle = findViewById(R.id.right);
        TextView swipe = findViewById(R.id.swipe);
        String leftD = getDescription(left);
        String rightD = getDescription(right);
        text.setText("You are on an intersection of aisles");
        leftAisle.setText("Left aisle contains: " + leftD);
        rightAisle.setText("Right aisle contains: " + rightD);
        swipe.setText("swipe left to hear left aisle \n" +
                "swipe right to hear right aisle \n" +
                "swipe up to scan new code \n" +
                "swipe down to select new mode");

        RelativeLayout relativeLayout = findViewById(R.id.intersection);
        relativeLayout.setOnTouchListener(new OnSwipeTouchListener(Intersection.this) {
            public void onSwipeTop() {
                Intent intent = new Intent(Intersection.this, Scan.class);
                startActivity(intent);
            }

            public void onSwipeBottom() {
                Intent intent = new Intent(Intersection.this, MainActivity.class);
                startActivity(intent);
            }

            public void onSwipeLeft() {
                Intent intent = new Intent(Intersection.this, Single.class);
                intent.putExtra(Scan.EXTRA_TEXT, left);
                intent.putExtra(Scan.EXTRA_TEXT_2, right);
                startActivity(intent);
            }

            public void onSwipeRight() {
                Intent intent = new Intent(Intersection.this, Single.class);
                intent.putExtra(Scan.EXTRA_TEXT, right);
                intent.putExtra(Scan.EXTRA_TEXT_2, left);
                startActivity(intent);
            }

        });
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
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

public class Single extends AppCompatActivity {
    String curr;
    String other;
    String content;
    String left;
    String right;

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        Intent intent = getIntent();
        curr = intent.getStringExtra(Scan.EXTRA_TEXT);
        other = intent.getStringExtra(Scan.EXTRA_TEXT_2);

        getContent(curr);

        TextView title = findViewById(R.id.title);
        TextView leftSwipe = findViewById(R.id.left);
        TextView rightSwipe = findViewById(R.id.right);
        TextView swipe = findViewById(R.id.swipe);

        title.setText("Aisle " + curr);
        leftSwipe.setText("left side: " + left);
        rightSwipe.setText("right side: " + right);
        swipe.setText("swipe up to scan new code \n" +
                "swipe down to select new mode");

        RelativeLayout relativeLayout = findViewById(R.id.single);
        relativeLayout.setOnTouchListener(new OnSwipeTouchListener(Single.this) {
            public void onSwipeTop() {
                Intent intent = new Intent(Single.this, Scan.class);
                startActivity(intent);
            }

            public void onSwipeBottom() {
                Intent intent = new Intent(Single.this, MainActivity.class);
                startActivity(intent);
            }

        });
    }

    public void getContent(String curr) {
        SQLiteDatabase myDB = openOrCreateDatabase("deneme.db", MODE_PRIVATE, null);
        Cursor cursor = myDB.rawQuery("select * from aisle where id = " + curr, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                content = cursor.getString(cursor.getColumnIndex("description"));
                left = cursor.getString(cursor.getColumnIndex("leftAisle"));
                right = cursor.getString(cursor.getColumnIndex("rightAisle"));
                cursor.moveToNext();
            }
        }
    }
}
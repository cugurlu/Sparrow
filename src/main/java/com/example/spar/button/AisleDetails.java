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

public class AisleDetails extends AppCompatActivity {
    String curr;
    String other;
    String content;
    String left;
    String right;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aisle_details);

        Intent intent = getIntent();
        curr = intent.getStringExtra(Activity2.EXTRA_TEXT);
        other = intent.getStringExtra(Activity2.EXTRA_TEXT_2);

        getContent(curr);

        TextView name = findViewById(R.id.name);
        name.setText("Aisle " + curr);
        TextView detail = findViewById(R.id.content);
        TextView detail2 = findViewById(R.id.content2);
        detail.setText("left side: " + left);
        detail2.setText("right side: " + right);
    }

    public void buttonClicked(View view) {
        if (view.getId() == R.id.scan) {
            Intent intent = new Intent(this, Activity2.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
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
package com.example.spar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spar.button.Activity2;
import com.example.spar.swipe.Scan;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDatabase();
    }

    public void buttonClicked(View view) {
        Intent intent;
        if (view.getId() == R.id.button) {
            intent = new Intent(this, Activity2.class);
        } else {
            intent = new Intent(this, Scan.class);
        }
        startActivity(intent);
    }

    public void createDatabase() {
        try {
            SQLiteDatabase myDB = this.openOrCreateDatabase("deneme.db", MODE_PRIVATE, null);

            myDB.execSQL("CREATE TABLE IF NOT EXISTS aisle (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "description TEXT," +
                    "leftAisle TEXT," +
                    "rightAisle TEXT);");

            myDB.execSQL("CREATE TABLE IF NOT EXISTS qr (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "type TEXT," +
                    "curr TEXT," +
                    "other TEXT);");

            myDB.execSQL("INSERT INTO aisle (description, leftAisle, rightAisle)"
                    + " VALUES " +
                    "('Bread, Eggs, Breakfast, Tea, Sugar', 'Baguettes, Breakfast cereals, Rice crackers, Knackebrod, Toasts, Peanut butter, Apple syrup, Chocolate spread, Honey, Tea, Sugar, Brown sugar, Powdered sugar', 'Whole grain, casino, white, tiger bread in whole and half loafs. Buns, Currant buns, Croisssants, Eggs, Free range, Biologic, quail eggs'), " +
                    "('Candy, Cleaning, Hygiene', 'Licorice, Gummy bears, Chocolate, Candy bars, M&M s', 'Laundry detergent, Fabric softener, Deodorant, Toothpaste'), " +
                    "('Cookies. Coffee', 'Cofffe in cups, Ground coffee, Coffee beans, Coffee milk', 'Chocolate cookies, Stroopwafels, Oreo, Coffee cookies, Caramel cookies'), " +
                    "('Foreign Kitchen', 'White rice, Brow rice, Sushi rice, Saffron rice, Spaghetti, Macaroni, Tagliatelle, Noodles, Mie', 'Cooking packages: Chinese dishes, Thai curries, Japanese dishes, Italian dishes'), " +
                    "('Wine, Frozen Goods', 'Fish, Frozen vegetables, Frozen fruits', 'Wines, Red wine, White wine, Rose wine, Alcohol free wine'), " +
                    "('Beer, Frozen goods', 'Pizzas, Ice cream, Fries, Frikadellen, Krokketten', 'Beer, Crates, Cans, Bottles, Belgian beer, Specialty beer, Pilsner, IPA, Dark lager')");

            myDB.execSQL("INSERT INTO qr (type, curr, other)"
                    + " VALUES " +
                    "('0', '1', ''), ('0', '3', ''), ('0', '5', '')," +
                    "('1', '1', '2'), ('1', '3', '4'), ('1', '5', '6')," +
                    "('0', '2', ''), ('0', '4', ''), ('0', '6', '');");

        } catch (Exception NullPointerException) {
            System.out.println("not working");
        }
    }
}
package com.example.android.gridview.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;


import com.example.android.gridview.R;
import com.example.android.gridview.adapter.CustomGrid;
import com.example.android.gridview.dialog.AboutDialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    GridView grid;
    ArrayList<String> web = new ArrayList<>();
    int[] imageId = {
            R.drawable.turkishcoffee, R.drawable.espresso,  R.drawable.macchiato, R.drawable.nescafe,
            R.drawable.cappucino,  R.drawable.icedcoffee};

    String[] gridColor ={"#FFF9C4","#f5ce85","#d6c3bc","#FFAB40","#F5F5F5","#8D6E63"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        web.add(getString(R.string.traditional_coffee));
        web.add(getString(R.string.espresso));
        web.add(getString(R.string.macchiato));
        web.add(getString(R.string.nescafe));
        web.add(getString(R.string.cappuccino));
        web.add(getString(R.string.icedCoffe));
        CustomGrid adapter = new CustomGrid(MainActivity.this, web, imageId, gridColor);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(this);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.drawable.coffeeshop);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimpSlifiableIfStatement
        if (id == R.id.about) {
            AlertDialog alertDialog = new AboutDialog(MainActivity.this).prepareDialog();
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawableResource(R.color.colorDialog);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent;
            switch (i) {
                case 0:
                    intent = new Intent(this, TraditionalCoffeeActivity.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(this, EspressoActivity.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(this, MacchiatoActivity.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(this, NescafeActivity.class);
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(this, CappuccinoActivity.class);
                    startActivity(intent);
                    break;
                case 5:
                    intent = new Intent(this, IcedCoffeeActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }



}
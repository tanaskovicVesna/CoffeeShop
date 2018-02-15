package com.example.android.gridview.activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.gridview.filter.CustomInputFilter;
import com.example.android.gridview.R;

import java.text.NumberFormat;


public class TraditionalCoffeeActivity extends AppCompatActivity {

    int quantity =  1;
    int quantity1 = 1;
    int quantity2 = 1;
    EditText nameField;
    EditText waterQuantity;
    EditText delightQuantity;

    CustomInputFilter check = new CustomInputFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traditional_coffee);

        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#FFF9C4"));
        //hide soft keyboard on EditText
        nameField = (EditText) findViewById(R.id.name_field);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        displayPricePerCup(50,30,20);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.drawable.turkishcoffee);
        }

        waterQuantity = (EditText)findViewById(R.id.minearal_water_quantity);
        check.checkQuantityInput(waterQuantity);

        delightQuantity = (EditText)findViewById(R.id. delight_quantity);
        check.checkQuantityInput(delightQuantity);
    }



    private void displayPricePerCup(int number, int water, int delight) {
        TextView priceTextView = (TextView) findViewById(R.id.traditional_coffee_price);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));

        TextView waterTextView = (TextView) findViewById(R.id.mineral_water_price);
        waterTextView.setText(NumberFormat.getCurrencyInstance().format(water));

        TextView delightTextView = (TextView) findViewById(R.id.delight_price);
        delightTextView.setText(NumberFormat.getCurrencyInstance().format(delight));
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, getString(R.string.increment), Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity <=0) {
            quantity = 0;
            // Show an error message as a toast
            Toast.makeText(this,getString(R.string.decrement), Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }

        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */


    public void submitOrder(View view) {


        nameField = (EditText) findViewById(R.id.name_field);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        String name = nameField.getText().toString();


        // Figure out if the user wants mineral water
        CheckBox mineralWaterCheckBox = (CheckBox) findViewById(R.id.mineral_water_checkbox);
        boolean hasMineralWater = mineralWaterCheckBox.isChecked();

        // Figure out if the user wants delight
        CheckBox delightCheckBox = (CheckBox) findViewById(R.id.delight_checkbox);
        boolean hasDelight = delightCheckBox.isChecked();

        waterQuantity = (EditText)findViewById(R.id.minearal_water_quantity);


        delightQuantity =(EditText)findViewById(R.id.delight_quantity);

        if(hasMineralWater){
            if (waterQuantity.getText().toString().matches("")) {
                Toast.makeText(this, getString(R.string.checkInputWater), Toast.LENGTH_SHORT).show();
                return;
            }
            quantity1 = Integer.parseInt(waterQuantity.getText().toString());
        }
        if(hasDelight){
            if (delightQuantity.getText().toString().matches("")) {
                Toast.makeText(this, getString(R.string.checkInputDelight), Toast.LENGTH_SHORT).show();
                return;
            }
            quantity2 = Integer.parseInt(delightQuantity.getText().toString());
        }
        // Calculate the price
        int price = calculatePrice(hasMineralWater,hasDelight,quantity1,quantity2);

        // Display the order summary on the screen
        String message = createOrderSummary(name, price, hasMineralWater, hasDelight);

        displayTotal(price);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    private int calculatePrice(boolean addMineralWater, boolean addDelight, int quantityWater, int quantityDelight) {
        int basePrice = 50;
        if(addMineralWater) {

            if (quantityWater == 0) {
                quantityWater = 0;
            }
        } else {
                quantityWater = 0;
                waterQuantity.setText(R.string.quantity);
        }

        if(addDelight){

            if (quantityDelight == 0) {
                quantityDelight = 0;
            }
        }else{
            quantityDelight = 0;
            delightQuantity.setText(R.string.quantity);
        }

        int totalPrice = quantity *basePrice + quantityWater*30 + quantityDelight*20;
        return  totalPrice;
    }

    public void showTotal(View view) {

        // Figure out if the user wants mineral water
        CheckBox mineralWaterCheckBox = (CheckBox) findViewById(R.id.mineral_water_checkbox);
        boolean hasMineralWater = mineralWaterCheckBox.isChecked();

        // Figure out if the user wants delight
        CheckBox delightCheckBox = (CheckBox) findViewById(R.id.delight_checkbox);
        boolean hasDelight = delightCheckBox.isChecked();

        waterQuantity = (EditText)findViewById(R.id.minearal_water_quantity);


        delightQuantity =(EditText)findViewById(R.id.delight_quantity);

        if(hasMineralWater){
            if (waterQuantity.getText().toString().matches("")) {
                Toast.makeText(this, getString(R.string.checkInputWater), Toast.LENGTH_SHORT).show();
                return;
            }
            quantity1 = Integer.parseInt(waterQuantity.getText().toString());
        }
        if(hasDelight){
            if (delightQuantity.getText().toString().matches("")) {
                Toast.makeText(this, getString(R.string.checkInputDelight), Toast.LENGTH_SHORT).show();
                return;
            }
            quantity2 = Integer.parseInt(delightQuantity.getText().toString());
        }
        // Calculate the price
        int price = calculatePrice(hasMineralWater,hasDelight,quantity1,quantity2);


        // Display the price on the screen
        displayTotal(price);
    }

    /**
     * Create summary of the order.
     *
     * @param price           of the order
     * @param addMineralWater is whether or not to add whipped cream to the coffee
     * @param addDelight    is whether or not to add chocolate to the coffee
     * @return text summary
     */
    @SuppressLint("StringFormatInvalid")
    private String createOrderSummary(String name, int price, boolean addMineralWater, boolean addDelight) {
        String priceMessage = getString(R.string.order_summary_name,name);
        priceMessage +="\n" + getString(R.string.traditional_coffee);
        priceMessage +="\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage +="\n" + getString(R.string.order_summary_mineral_water, addMineralWater);
        if(addMineralWater){priceMessage +="\n" + getString(R.string.order_summary_quantity_water, quantity1);}
        priceMessage +="\n" + getString(R.string.order_summary_delight, addDelight);
        if(addDelight){ priceMessage +="\n" + getString(R.string.order_summary_quantity_water, quantity2);}
        priceMessage +="\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage +="\n" + getString(R.string.thanks);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price value on the screen.
     */
    private void displayTotal(int number) {
        TextView totalTextView = (TextView) findViewById(R.id.total_text_view);
        totalTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }


}

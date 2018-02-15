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

import com.example.android.gridview.R;
import com.example.android.gridview.filter.CustomInputFilter;

import java.text.NumberFormat;

import static com.example.android.gridview.R.string.quantity;

public class EspressoActivity extends AppCompatActivity {
    int quantity =  1;
    int quantity1 = 1;
    int quantity2 = 1;
    EditText nameField;
    EditText cookiesQuantity;
    EditText donutsQuantity;

    CustomInputFilter check = new CustomInputFilter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espresso);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#e5cb99"));
        //hide soft keyboard on EditText
        nameField = (EditText) findViewById(R.id.name_field);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        displayPricePerCup(70,30,50,10);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.drawable.espresso);
        }
        cookiesQuantity = (EditText)findViewById(R.id.cookies_quantity);
        check.checkQuantityInput(cookiesQuantity);

        donutsQuantity = (EditText)findViewById(R.id. donuts_quantity);
        check.checkQuantityInput(donutsQuantity);
    }

    private void displayPricePerCup(int number, int cookies, int donuts, int toppings) {

        TextView priceTextView = (TextView) findViewById(R.id.espresso_price);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));

        TextView cookiesTextView = (TextView) findViewById(R.id.cookies_price);
        cookiesTextView.setText(NumberFormat.getCurrencyInstance().format(cookies));

        TextView donutsTextView = (TextView) findViewById(R.id.donuts_price);
        donutsTextView.setText(NumberFormat.getCurrencyInstance().format(donuts));

        TextView toppingsTextView = (TextView) findViewById(R.id.toppings_price);
        toppingsTextView.setText(NumberFormat.getCurrencyInstance().format(toppings));
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

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        CheckBox cookiesCheckBox = (CheckBox) findViewById(R.id.cookies_checkbox);
        boolean hasCookies = cookiesCheckBox.isChecked();

        CheckBox donutsCheckBox = (CheckBox) findViewById(R.id.donuts_checkbox);
        boolean hasDonuts = donutsCheckBox.isChecked();

        cookiesQuantity = (EditText)findViewById(R.id.cookies_quantity);

        donutsQuantity = (EditText)findViewById(R.id.donuts_quantity);

        if(hasCookies){
            if (cookiesQuantity.getText().toString().matches("")) {
                Toast.makeText(this, getString(R.string.checkInputCookies), Toast.LENGTH_SHORT).show();
                return;
            }
            quantity1 = Integer.parseInt(cookiesQuantity.getText().toString());
        }
        if(hasDonuts){
            if (donutsQuantity.getText().toString().matches("")) {
                Toast.makeText(this, getString(R.string.checkInputDonuts), Toast.LENGTH_SHORT).show();
                return;
            }
            quantity2 = Integer.parseInt(donutsQuantity.getText().toString());
        }

        // Calculate the price
        int price = calculatePrice(hasWhippedCream,hasChocolate,hasCookies,hasDonuts,quantity1,quantity2);
        displayTotal(price);
        // Display the order summary on the screen
        String message = createOrderSummary(name, price, hasWhippedCream, hasChocolate,hasCookies,hasDonuts);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        // displayMessage(message);
    }
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate, boolean withCookies, boolean withDonuts,int quantityCookies, int quantityDonuts) {
        int basePrice = 70;
        if(addWhippedCream){
            basePrice = basePrice + 10;
        }
        if(addChocolate){
            basePrice = basePrice + 10;
        }
        if(withCookies) {

            if (quantityCookies == 0) {
                quantityCookies = 0;
            }
        } else {
            quantityCookies= 0;
            cookiesQuantity.setText(R.string.quantity);
        }

        if(withDonuts){

            if (quantityDonuts == 0) {
                quantityDonuts = 0;
            }
        }else{
            quantityDonuts = 0;
            donutsQuantity.setText(R.string.quantity);
        }

        int totalPrice = quantity *basePrice + quantityCookies*30 + quantityDonuts*50;
        return  totalPrice;
    }


    public void showTotal(View view) {

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        CheckBox cookiesCheckBox = (CheckBox) findViewById(R.id.cookies_checkbox);
        boolean hasCookies = cookiesCheckBox.isChecked();

        CheckBox donutsCheckBox = (CheckBox) findViewById(R.id.donuts_checkbox);
        boolean hasDonuts = donutsCheckBox.isChecked();
        cookiesQuantity = (EditText)findViewById(R.id.cookies_quantity);

        donutsQuantity = (EditText)findViewById(R.id.donuts_quantity);

        if(hasCookies){
            if (cookiesQuantity.getText().toString().matches("")) {
                Toast.makeText(this, getString(R.string.checkInputCookies), Toast.LENGTH_SHORT).show();
                return;
            }
            quantity1 = Integer.parseInt(cookiesQuantity.getText().toString());
        }
        if(hasDonuts){
            if (donutsQuantity.getText().toString().matches("")) {
                Toast.makeText(this, getString(R.string.checkInputDonuts), Toast.LENGTH_SHORT).show();
                return;
            }
            quantity2 = Integer.parseInt(donutsQuantity.getText().toString());
        }

        // Calculate the price
        int price = calculatePrice(hasWhippedCream,hasChocolate,hasCookies,hasDonuts,quantity1,quantity2);

        // Display the price on the screen
        displayTotal(price);
    }

    /**
     * Create summary of the order.
     *
     * @param price           of the order
     * @param addWhippedCream is whether or not to add whipped cream to the coffee
     * @param addChocolate    is whether or not to add chocolate to the coffee
     * @param withCookies
     * @param  withDonuts
     * @return text summary
     */
    @SuppressLint("StringFormatInvalid")
    private String createOrderSummary(String name, int price, boolean addWhippedCream,boolean addChocolate, boolean withCookies, boolean withDonuts) {
        String priceMessage = getString(R.string.order_summary_name,name);
        priceMessage +="\n" + getString(R.string.espresso);
        priceMessage +="\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage +="\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage +="\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage +="\n" + getString(R.string.order_summary_cookies, withCookies);
        if(withCookies){priceMessage +="\n" + getString(R.string.order_summary_quantity_cookies, quantity1);}
        priceMessage +="\n" + getString(R.string.order_summary_donuts, withDonuts);
        if(withDonuts){priceMessage +="\n" + getString(R.string.order_summary_quantity_donuts, quantity1);}
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

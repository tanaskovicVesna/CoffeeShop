package com.example.android.gridview.activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.gridview.R;

import java.text.NumberFormat;

public class IcedCoffeeActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iced_coffee);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#A1887F"));

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.drawable.icedcoffee);
        }
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
        if (quantity == 1) {
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


        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        // Figure out if the user wants whipped cream topping
        CheckBox lemonpieCheckBox = (CheckBox) findViewById(R.id.lemonpie_checkbox);
        boolean hasLemonpie = lemonpieCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox pancakesCheckBox = (CheckBox) findViewById(R.id.pancakes_checkbox);
        boolean hasPancakes = pancakesCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasLemonpie,hasPancakes);

        // Display the order summary on the screen
        String message = createOrderSummary(name, price, hasLemonpie, hasPancakes);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        // displayMessage(message);
    }
    private int calculatePrice(boolean addLemonpie, boolean addPancakes) {
        int basePrice = 5;
        if(addLemonpie){
            basePrice = basePrice + 1;
        }
        if(addPancakes){
            basePrice = basePrice + 2;
        }
        return quantity *basePrice;
    }

    public void showTotal(View view) {

        // Figure out if the user wants whipped cream topping
        CheckBox lemonpieCheckBox = (CheckBox) findViewById(R.id.lemonpie_checkbox);
        boolean hasLemonpie = lemonpieCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox pancakesCheckBox = (CheckBox) findViewById(R.id.pancakes_checkbox);
        boolean hasPancakes = pancakesCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasLemonpie,hasPancakes);


        // Display the price on the screen
        displayTotal(price);
    }

    /**
     * Create summary of the order.
     *
     * @param price           of the order
     * @param addLemonpie is whether or not to add whipped cream to the coffee
     * @param addPancakes    is whether or not to add chocolate to the coffee
     * @return text summary
     */
    @SuppressLint("StringFormatInvalid")
    private String createOrderSummary(String name, int price, boolean addLemonpie, boolean addPancakes) {
        String priceMessage = getString(R.string.order_summary_name,name);
        priceMessage +="\n" + getString(R.string.icedCoffe);
        priceMessage +="\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage +="\n" + getString(R.string.order_summary_lemonpie, addLemonpie);
        priceMessage +="\n" + getString(R.string.order_summary_pancakes, addPancakes);
        priceMessage +="\n" + getString(R.string.order_summary_quantity, quantity);
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

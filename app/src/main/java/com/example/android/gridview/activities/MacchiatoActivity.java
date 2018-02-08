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

public class MacchiatoActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_macchiato);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#ccb5ad"));

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.drawable.macchiato);
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
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        CheckBox muffinsCheckBox = (CheckBox) findViewById(R.id.muffins_checkbox);
        boolean hasMuffins = muffinsCheckBox.isChecked();

        CheckBox moonpieCheckBox = (CheckBox) findViewById(R.id.moonpie_checkbox);
        boolean hasMoonpie = moonpieCheckBox.isChecked();


        // Calculate the price
        int price = calculatePrice(hasWhippedCream,hasChocolate,hasMuffins,hasMoonpie);
        // Display the order summary on the screen
        String message = createOrderSummary(name, price, hasWhippedCream, hasChocolate,hasMuffins,hasMoonpie);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        // displayMessage(message);
    }
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate, boolean withMuffins, boolean withMoonpie) {
        int basePrice = 5;
        if(addWhippedCream){
            basePrice = basePrice + 1;
        }
        if(addChocolate){
            basePrice = basePrice + 2;
        }
        if(withMuffins){
            basePrice = basePrice + 3;
        }
        if(withMoonpie){
            basePrice = basePrice + 4;
        }
        return quantity *basePrice;
    }


    public void showTotal(View view) {

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        CheckBox muffinsCheckBox = (CheckBox) findViewById(R.id.muffins_checkbox);
        boolean hasMuffins = muffinsCheckBox.isChecked();

        CheckBox moonpieCheckBox = (CheckBox) findViewById(R.id.moonpie_checkbox);
        boolean hasMoonpie = moonpieCheckBox.isChecked();


        // Calculate the price
        int price = calculatePrice(hasWhippedCream,hasChocolate,hasMuffins,hasMoonpie);

        // Display the price on the screen
        displayTotal(price);
    }

    /**
     * Create summary of the order.
     *
     * @param price           of the order
     * @param addWhippedCream is whether or not to add whipped cream to the coffee
     * @param addChocolate    is whether or not to add chocolate to the coffee
     * @param withMuffins
     * @param  withMoonpie
     * @return text summary
     */
    @SuppressLint("StringFormatInvalid")
    private String createOrderSummary(String name, int price, boolean addWhippedCream,boolean addChocolate, boolean withMuffins, boolean withMoonpie) {
        String priceMessage = getString(R.string.order_summary_name,name);
        priceMessage +="\n" + getString(R.string.macchiato);
        priceMessage +="\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage +="\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage +="\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage +="\n" + getString(R.string.order_summary_muffins, withMuffins);
        priceMessage +="\n" + getString(R.string.order_summary_moonpie, withMoonpie);
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

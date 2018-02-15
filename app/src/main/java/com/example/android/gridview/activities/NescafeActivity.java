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

public class NescafeActivity extends AppCompatActivity {
    int quantity =  1;
    int quantity1 = 1;
    int quantity2 = 1;
    EditText nameField;
    EditText cheesecakeQuantity;
    EditText pumpkinpieQuantity;
    CustomInputFilter check = new CustomInputFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nescafe);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#f9e0bd"));
        //hide soft keyboard on EditText
        nameField = (EditText) findViewById(R.id.name_field);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        displayPricePerCup(70,60,60,10);


        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.drawable.nescafe);
        }
        cheesecakeQuantity = (EditText)findViewById(R.id.cheesecake_quantity);
        check.checkQuantityInput(cheesecakeQuantity);

        pumpkinpieQuantity = (EditText)findViewById(R.id. pumpkinpie_quantity);
        check.checkQuantityInput(pumpkinpieQuantity);
    }

    private void displayPricePerCup(int number, int cheesecake, int pumpkinpie, int toppings) {

        TextView priceTextView = (TextView) findViewById(R.id.nescafe_price);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));

        TextView cheesecakeTextView = (TextView) findViewById(R.id.cheesecake_price);
        cheesecakeTextView.setText(NumberFormat.getCurrencyInstance().format(cheesecake));

        TextView pumpkinpieTextView = (TextView) findViewById(R.id.pumpkinpie_price);
        pumpkinpieTextView.setText(NumberFormat.getCurrencyInstance().format(pumpkinpie));

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

        CheckBox cheesecakeCheckBox = (CheckBox) findViewById(R.id.cheesecake_checkbox);
        boolean hasCheesecake = cheesecakeCheckBox.isChecked();

        CheckBox pumpkinpieCheckBox = (CheckBox) findViewById(R.id.pumpkinpie_checkbox);
        boolean hasPumpkinpie = pumpkinpieCheckBox.isChecked();

        cheesecakeQuantity = (EditText)findViewById(R.id.cheesecake_quantity);

        pumpkinpieQuantity = (EditText)findViewById(R.id.pumpkinpie_quantity);

        if(hasCheesecake){
            if (cheesecakeQuantity.getText().toString().matches("")) {
                Toast.makeText(this, getString(R.string.checkInputCheesecake), Toast.LENGTH_SHORT).show();
                return;
            }
            quantity1 = Integer.parseInt(cheesecakeQuantity.getText().toString());
        }
        if(hasPumpkinpie){
            if (pumpkinpieQuantity.getText().toString().matches("")) {
                Toast.makeText(this, getString(R.string.checkInputPumpkinpie), Toast.LENGTH_SHORT).show();
                return;
            }
            quantity2 = Integer.parseInt(pumpkinpieQuantity.getText().toString());
        }

        // Calculate the price
        int price = calculatePrice(hasWhippedCream,hasChocolate,hasCheesecake,hasPumpkinpie,quantity1,quantity2);
        displayTotal(price);

        // Display the order summary on the screen
        String message = createOrderSummary(name, price, hasWhippedCream, hasChocolate,hasCheesecake,hasPumpkinpie);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        // displayMessage(message);
    }
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate, boolean withCheesecake, boolean withPumpkinpie, int quantityCheesecake, int quantityPumpkinpie) {
        int basePrice = 70;
        if(addWhippedCream){
            basePrice = basePrice + 10;
        }
        if(addChocolate){
            basePrice = basePrice + 10;
        }
        if(withCheesecake){
            if (quantityCheesecake == 0) {
               quantityCheesecake = 0;
            }
        } else {
            quantityCheesecake= 0;
            cheesecakeQuantity.setText(R.string.quantity);
        }

        if(withPumpkinpie){

            if (quantityPumpkinpie == 0) {
                quantityPumpkinpie = 0;
            }
        }else{
            quantityPumpkinpie = 0;
            pumpkinpieQuantity.setText(R.string.quantity);
        }

        int totalPrice = quantity *basePrice + quantityCheesecake*60 + quantityPumpkinpie*60;
        return  totalPrice;
    }


    public void showTotal(View view) {

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();


        CheckBox cheesecakeCheckBox = (CheckBox) findViewById(R.id.cheesecake_checkbox);
        boolean hasCheesecake = cheesecakeCheckBox.isChecked();

        CheckBox pumpkinpieCheckBox = (CheckBox) findViewById(R.id.pumpkinpie_checkbox);
        boolean hasPumpkinpie = pumpkinpieCheckBox.isChecked();

        cheesecakeQuantity = (EditText)findViewById(R.id.cheesecake_quantity);

        pumpkinpieQuantity = (EditText)findViewById(R.id.pumpkinpie_quantity);

        if(hasCheesecake){
            if (cheesecakeQuantity.getText().toString().matches("")) {
                Toast.makeText(this, getString(R.string.checkInputCheesecake), Toast.LENGTH_SHORT).show();
                return;
            }
            quantity1 = Integer.parseInt(cheesecakeQuantity.getText().toString());
        }
        if(hasPumpkinpie){
            if (pumpkinpieQuantity.getText().toString().matches("")) {
                Toast.makeText(this, getString(R.string.checkInputPumpkinpie), Toast.LENGTH_SHORT).show();
                return;
            }
            quantity2 = Integer.parseInt(pumpkinpieQuantity.getText().toString());
        }

        // Calculate the price
        int price = calculatePrice(hasWhippedCream,hasChocolate,hasCheesecake,hasPumpkinpie,quantity1,quantity2);

        // Display the price on the screen
        displayTotal(price);
    }

    /**
     * Create summary of the order.
     *
     * @param price           of the order
     * @param addWhippedCream is whether or not to add whipped cream to the coffee
     * @param addChocolate    is whether or not to add chocolate to the coffee
     * @param withCheesecake
     * @param  withPumpkinpie
     * @return text summary
     */
    @SuppressLint("StringFormatInvalid")
    private String createOrderSummary(String name, int price, boolean addWhippedCream,boolean addChocolate, boolean withCheesecake, boolean withPumpkinpie) {
        String priceMessage = getString(R.string.order_summary_name,name);
        priceMessage +="\n" + getString(R.string.nescafe);
        priceMessage +="\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage +="\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage +="\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage +="\n" + getString(R.string.order_summary_cheesecake, withCheesecake);
        if(withCheesecake){priceMessage +="\n" + getString(R.string.order_summary_quantity_cheesecake, quantity1);}
        priceMessage +="\n" + getString(R.string.order_summary_pumpkinpie, withPumpkinpie);
        if(withPumpkinpie){priceMessage +="\n" + getString(R.string.order_summary_quantity_pumpkinpie, quantity1);}
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

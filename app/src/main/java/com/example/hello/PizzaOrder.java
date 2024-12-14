package com.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class PizzaOrder extends AppCompatActivity {

    private CheckBox checkMushrooms, checkOlives, checkPepperoni;
    private RadioGroup sizeRadioGroup;
    private RatingBar spicinessRatingBar;
    private SeekBar cheeseSeekBar;
    private ArrayList<String> selectedToppings = new ArrayList<>();
    private Button orderButton, Button;
    private TextView cheeseQuantityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_order);

        checkMushrooms = findViewById(R.id.check_mushrooms);
        checkOlives = findViewById(R.id.check_olives);
        checkPepperoni = findViewById(R.id.check_pepperoni);
        sizeRadioGroup = findViewById(R.id.size_radio_group);
        spicinessRatingBar = findViewById(R.id.rating_bar);
        cheeseSeekBar = findViewById(R.id.cheese_seek_bar);
        orderButton = findViewById(R.id.submit_button);
        Button = findViewById(R.id.button1);

        cheeseQuantityText = findViewById(R.id.cheese_quantity);

        cheeseSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cheeseQuantityText.setText("Cheese Quantity: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        orderButton.setOnClickListener(v -> showOrderSummary());

        Button.setOnClickListener(v -> {
            Intent mainIntent = new Intent(PizzaOrder.this, RegexValidation.class);
            startActivity(mainIntent);
        });
    }

    private void showOrderSummary() {
        selectedToppings.clear();

        if (checkMushrooms.isChecked()) selectedToppings.add(checkMushrooms.getText().toString());
        if (checkOlives.isChecked()) selectedToppings.add(checkOlives.getText().toString());
        if (checkPepperoni.isChecked()) selectedToppings.add(checkPepperoni.getText().toString());

        int selectedSizeId = sizeRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedSize = findViewById(selectedSizeId);
        if (selectedSize == null) {
            Toast.makeText(this, "Please select a pizza size!", Toast.LENGTH_SHORT).show();
            return;
        }

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_order_summary, null);

        TextView toppingsText = dialogView.findViewById(R.id.toppings_text);
        TextView sizeText = dialogView.findViewById(R.id.size_text);
        TextView spicinessText = dialogView.findViewById(R.id.spiciness_text);
        TextView cheeseText = dialogView.findViewById(R.id.cheese_text);
        Button okButton = dialogView.findViewById(R.id.ok_button);

        toppingsText.setText("Selected Toppings: " + selectedToppings.toString());
        sizeText.setText("Pizza Size: " + selectedSize.getText().toString());
        spicinessText.setText("Spiciness Level: " + spicinessRatingBar.getRating());
        cheeseText.setText("Cheese Quantity: " + cheeseSeekBar.getProgress());

        AlertDialog orderSummaryDialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        okButton.setOnClickListener(v -> {
            orderSummaryDialog.dismiss();
            resetOrder();
        });

        orderSummaryDialog.show();
    }

    private void resetOrder() {
        checkMushrooms.setChecked(false);
        checkOlives.setChecked(false);
        checkPepperoni.setChecked(false);
        sizeRadioGroup.clearCheck();
        spicinessRatingBar.setRating(0);
        cheeseSeekBar.setProgress(0);
        cheeseQuantityText.setText("Cheese Quantity: 0");
        Toast.makeText(this, "Order has been reset!", Toast.LENGTH_SHORT).show();
    }
}

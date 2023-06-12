package com.example.orderapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderapp.Database.OrderContract;

import java.util.List;

public class DrinkSetupActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    ImageView imageView;
    ImageButton plusquantity, minusquantity;
    TextView quantitynumber, drinnkName, coffeePrice;
    CheckBox addToppings, addExtraCream;
    Button addtoCart;
    int quantity;
    public Uri mCurrentCartUri;
    boolean hasAllRequiredValues = false;

    public DrinkSetupActivity() {
    }

    private List<Model> modelList;
    private int selectedPosition;

    public DrinkSetupActivity(List<Model> modelList, int selectedPosition) {
        this.modelList = modelList;
        this.selectedPosition = selectedPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        imageView = findViewById(R.id.imageViewInfo);
        plusquantity = findViewById(R.id.addquantity);
        minusquantity = findViewById(R.id.subquantity);
        quantitynumber = findViewById(R.id.quantity);
        drinnkName = findViewById(R.id.drinkNameinInfo);
        coffeePrice = findViewById(R.id.coffeePrice);
        addToppings = findViewById(R.id.addToppings);
        addtoCart = findViewById(R.id.addtocart);
        addExtraCream = findViewById(R.id.addCream);


        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("position")) {
            int position = intent.getIntExtra("position", 0);
            if (position != -1) {
                if (position == 0) {
                    drinnkName.setText("Green Tea");
                    imageView.setImageResource(R.drawable.greentea);
                } else if (position == 1) {
                    drinnkName.setText("Latte Tea");
                    imageView.setImageResource(R.drawable.late);
                } else if (position == 2) {
                    drinnkName.setText("Orange Smoothie");
                    imageView.setImageResource(R.drawable.orange);
                } else if (position == 3) {
                    drinnkName.setText("Orange Vanilla");
                    imageView.setImageResource(R.drawable.orangevanilla);
                } else if (position == 4) {
                    drinnkName.setText("Cappuccino");
                    imageView.setImageResource(R.drawable.cappcunio);
                } else if (position == 5) {
                    drinnkName.setText("Thai Tea");
                    imageView.setImageResource(R.drawable.thaitea);
                } else if (position == 6) {
                    drinnkName.setText("Tea");
                    imageView.setImageResource(R.drawable.tea);
                } else if (position == 7) {
                    drinnkName.setText("Bubble Tea");
                    imageView.setImageResource(R.drawable.milk);
                } else if (position == 8) {
                    drinnkName.setText("Matcha");
                    imageView.setImageResource(R.drawable.match);
                }



            }
        }

        addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DrinkSetupActivity.this, SummaryActivity.class);
                startActivity(intent);

                SaveCart();
            }
        });

        plusquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int basePrice = 5;
                quantity++;
                displayQuantity();
                int coffePrice = basePrice * quantity;
                String setnewPrice = String.valueOf(coffePrice);
                coffeePrice.setText(setnewPrice);


                int ifCheckBox = CalculatePrice(addExtraCream, addToppings);
                coffeePrice.setText("$ " + ifCheckBox);

            }
        });

        minusquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int basePrice = 5;

                if (quantity == 0) {
                    Toast.makeText(DrinkSetupActivity.this, "Cant decrease quantity < 0", Toast.LENGTH_SHORT).show();
                } else {
                    quantity--;
                    displayQuantity();
                    int coffePrice = basePrice * quantity;
                    String setnewPrice = String.valueOf(coffePrice);
                    coffeePrice.setText(setnewPrice);


                    int ifCheckBox = CalculatePrice(addExtraCream, addToppings);
                    coffeePrice.setText("$ " + ifCheckBox);
                }
            }


        });


    }

    private boolean SaveCart() {

        // from views
        String name = drinnkName.getText().toString();
        String price = coffeePrice.getText().toString();
        String quantity = quantitynumber.getText().toString();

        ContentValues values = new ContentValues();
        values.put(OrderContract.OrderEntry.COLUMN_NAME, name);
        values.put(OrderContract.OrderEntry.COLUMN_PRICE, price);
        values.put(OrderContract.OrderEntry.COLUMN_QUANTITY, quantity);

        if (addExtraCream.isChecked()) {
            values.put(OrderContract.OrderEntry.COLUMN_CREAM, "Has Cream: YES");
        } else {
            values.put(OrderContract.OrderEntry.COLUMN_CREAM, "Has Cream: NO");

        }

        if (addToppings.isChecked()) {
            values.put(OrderContract.OrderEntry.COLUMN_HASTOPPING, "Has Toppings: YES");
        } else {
            values.put(OrderContract.OrderEntry.COLUMN_HASTOPPING, "Has Toppings: NO");

        }

        if (mCurrentCartUri == null) {
            Uri newUri = getContentResolver().insert(OrderContract.OrderEntry.CONTENT_URI, values);
            if (newUri == null) {
                Toast.makeText(this, "Failed to add to Cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Success  adding to Cart", Toast.LENGTH_SHORT).show();

            }
        }

        hasAllRequiredValues = true;
        return hasAllRequiredValues;

    }

    private int CalculatePrice(CheckBox addExtraCream, CheckBox addToppings) {

        int basePrice = 5;

        if (addExtraCream.isChecked()) {
            basePrice = basePrice + 2;
        }

        if (addToppings.isChecked()) {
            basePrice = basePrice + 3;
        }

        return basePrice * quantity;
    }

    private void displayQuantity() {
        quantitynumber.setText(String.valueOf(quantity));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {OrderContract.OrderEntry._ID,
                OrderContract.OrderEntry.COLUMN_NAME,
                OrderContract.OrderEntry.COLUMN_PRICE,
                OrderContract.OrderEntry.COLUMN_QUANTITY,
                OrderContract.OrderEntry.COLUMN_CREAM,
                OrderContract.OrderEntry.COLUMN_HASTOPPING
        };

        return new CursorLoader(this, mCurrentCartUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {

            int name = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_NAME);
            int price = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_PRICE);
            int quantity = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_QUANTITY);
            int hasCream = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_CREAM);
            int hasTopping = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_HASTOPPING);


            String nameofdrink = cursor.getString(name);
            String priceofdrink = cursor.getString(price);
            String quantityofdrink = cursor.getString(quantity);
            String yeshasCream = cursor.getString(hasCream);
            String yeshastopping = cursor.getString(hasTopping);

            drinnkName.setText(nameofdrink);
            coffeePrice.setText(priceofdrink);
            quantitynumber.setText(quantityofdrink);
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {


        drinnkName.setText("");
        coffeePrice.setText("");
        quantitynumber.setText("");

    }
}
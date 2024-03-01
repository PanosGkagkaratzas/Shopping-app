package com.example.bestbeforeapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Calendar;

public class Update_product extends AppCompatActivity {

    DatabaseHelper basketDB;
    Button update;
    EditText p_id,p_name,quantity;
    TextView date;
    Spinner category;
    DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        basketDB = new DatabaseHelper(this);

        p_id = (EditText)findViewById(R.id.pr_ID);
        p_name = (EditText)findViewById(R.id.pr_name);
        quantity = (EditText)findViewById(R.id.posotita);
        category = (Spinner)findViewById(R.id.katigoria);
        date = (TextView) findViewById(R.id.exp_date);
        update = (Button)findViewById(R.id.enimerwsi);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.custom_spinner, getResources().getStringArray(R.array.items));
        adapter.setDropDownViewResource(R.layout.custom_spinner);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Update_product.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String dat = day + "/" + month + "/" + year;
                Log.d(dat,"MY DATE");
                date.setText(dat);
            }
        };

        category.setAdapter(adapter);
        category.setSelection(0);

        updateData();
    }

    public void updateData()
    {
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String query = "Select ID From SHOPPING_BASKET where ID = '"+p_id.getText().toString()+"'";
                final Cursor data = basketDB.showData(query);

                if (p_id.getText().toString().matches("") || p_name.getText().toString().matches("") || quantity.getText().toString().matches("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(Update_product.this).create();
                    alertDialog.setTitle("Προσοχή!");
                    alertDialog.setMessage("Ανιχνέυθηκαν κενά πεδία.\nΠαρακαλώ συμπληρώστε τα.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else if(data.getCount()==0){
                    AlertDialog alertDialog = new AlertDialog.Builder(Update_product.this).create();
                    alertDialog.setTitle("Προσοχή!");
                    alertDialog.setMessage("Δεν βρέθηκε προϊόν με το συγκεκριμένο ID.\nΠαρακαλώ εισάγετε έγκυρο ID.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Update_product.this, My_shopping.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else {
                    String id = p_id.getText().toString();
                    String name = p_name.getText().toString();
                    Editable quan = quantity.getText();
                    String cat = category.getSelectedItem().toString();
                    String dat = date.getText().toString();

                    boolean updateData = basketDB.updateData(id, name, quan, cat, dat);

                    if (updateData == true) {
                        AlertDialog alertDialog = new AlertDialog.Builder(Update_product.this).create();
                        alertDialog.setTitle("Ενημέρωση επιτυχής!");
                        alertDialog.setMessage("Όνομα προϊόντος: " + name + "\nΤο προϊόν ενημερώθηκε με επιτυχία!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Αρχική",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Update_product.this, My_shopping.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
            }
        });
    }
}

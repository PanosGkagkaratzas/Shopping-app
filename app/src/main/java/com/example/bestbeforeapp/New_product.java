package com.example.bestbeforeapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Calendar;

public class New_product extends AppCompatActivity {

    DatabaseHelper basketDB;
    Button add;
    EditText p_name,quantity;
    Spinner category;
    TextView date;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        Intent intent = getIntent();
        int c = intent.getIntExtra("Category", 0);

        basketDB = new DatabaseHelper(this);

        p_name = (EditText)findViewById(R.id.pr_name);
        quantity = (EditText)findViewById(R.id.posotita);
        category = (Spinner)findViewById(R.id.katigoria);
        date = (TextView) findViewById(R.id.exp_date);
        add = (Button)findViewById(R.id.kataxwrisi);

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
                        New_product.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String dat = day + "/" + month + "/" + year;
                date.setText(dat);
            }
        };

        category.setAdapter(adapter);
        if(c==0)
        {
            category.setSelection(0);
        }
        else if(c==1)
        {
            category.setSelection(1);
        }
        else if(c==2)
        {
            category.setSelection(2);
        }
        else if(c==3)
        {
            category.setSelection(3);
        }
        else if(c==4)
        {
            category.setSelection(4);
        }
        else if(c==5)
        {
            category.setSelection(5);
        }
        else if(c==6)
        {
            category.setSelection(6);
        }
        else if(c==7)
        {
            category.setSelection(7);
        }
        else if(c==8)
        {
            category.setSelection(8);
        }
        addData();
    }

    public void addData()
    {
        Intent intent = getIntent();
        final String barcode = intent.getExtras().getString("barcode");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p_name.getText().toString().matches("") || quantity.getText().toString().matches("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(New_product.this).create();
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
                else {
                    String name = p_name.getText().toString();
                    Editable quan = quantity.getText();
                    String cat = category.getSelectedItem().toString();
                    String dat = date.getText().toString();

                    boolean insertData = basketDB.addData(barcode, name, quan, cat, dat);

                    if (insertData == true) {
                        AlertDialog alertDialog = new AlertDialog.Builder(New_product.this).create();
                        alertDialog.setTitle("Σάρωση επιτυχής!");
                        alertDialog.setMessage("Όνομα προϊόντος: " + name + "\nΤο προϊόν προστέθηκε με επιτυχία στο καλάθι σας!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Αρχική",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(New_product.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(New_product.this).create();
                        alertDialog.setTitle("Προσοχή!");
                        alertDialog.setMessage("Προέκυψε σφάλμα κατά την αποθήκευση του προϊόντος.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Σάρωση ξανά",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(New_product.this, Barcode_Scanner.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
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

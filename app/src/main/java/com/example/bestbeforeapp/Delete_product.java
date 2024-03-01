package com.example.bestbeforeapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Delete_product extends AppCompatActivity {

    DatabaseHelper basketDB;
    Button delete;
    EditText p_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);

        basketDB = new DatabaseHelper(this);

        p_id = (EditText) findViewById(R.id.pr_id);
        delete = (Button) findViewById(R.id.diagrafi);

        deleteData();
    }

    public void deleteData() {

        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String query = "Select ID From SHOPPING_BASKET where ID = '"+p_id.getText().toString()+"'";
                final Cursor data = basketDB.getData(query);

                 if (p_id.getText().toString().matches("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(Delete_product.this).create();
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
                     AlertDialog alertDialog = new AlertDialog.Builder(Delete_product.this).create();
                     alertDialog.setTitle("Προσοχή!");
                     alertDialog.setMessage("Δεν βρέθηκε προϊόν με το συγκεκριμένο ID.\nΠαρακαλώ εισάγετε έγκυρο ID.");
                     alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                             new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int which) {
                                     Intent intent = new Intent(Delete_product.this, My_shopping.class);
                                     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                     startActivity(intent);
                                     dialog.dismiss();
                                 }
                             });
                     alertDialog.show();
                 }
                else{
                    String id = p_id.getText().toString();
                    basketDB.deleteData(id);

                    AlertDialog alertDialog = new AlertDialog.Builder(Delete_product.this).create();
                    alertDialog.setTitle("Διαγραφή επιτυχής!");
                    alertDialog.setMessage("Το προϊόν διαγράφθηκε με επιτυχία!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Αρχική",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Delete_product.this, My_shopping.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });
    }
}
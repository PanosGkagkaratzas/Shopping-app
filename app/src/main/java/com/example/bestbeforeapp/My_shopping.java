package com.example.bestbeforeapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class My_shopping extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);
    private String selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shopping);

        ListView list = (ListView)findViewById(R.id.listView);
        ArrayList<String> my_list = new ArrayList<>();


        String query = "Select ID,NAME,QUANTITY,CATEGORY,EXP_DATE From SHOPPING_BASKET order by NAME";

        Cursor data = db.showData(query);


        if (data.getCount()==0)
        {
            final AlertDialog alertDialog = new AlertDialog.Builder(My_shopping.this).create();
            alertDialog.setTitle("Ειδοποίηση!");
            alertDialog.setMessage("Το καλάθι σας είναι άδειο.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Πίσω",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(My_shopping.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("EXIT", true);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        else{
            if (data != null && data.moveToFirst()){
                do {
            my_list.add("ID προϊόντος: " + data.getString(0) + "\n" + "Όνομα: " + data.getString(1) + "\n" + "Ποσότητα: " +data.getString(2) + "\n" + "Κατηγορία: " + data.getString(3) + "\n" + "Ημ/νία Λήξης: " + data.getString(4));
                } while (data.moveToNext());
            }

            ListAdapter listAdapter = new ArrayAdapter<>(this,R.layout.custom_shopping,my_list);
            list.setAdapter(listAdapter);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_shopping, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update:
                AlertDialog alertDialog = new AlertDialog.Builder(My_shopping.this).create();
                alertDialog.setTitle("Προσοχή!");
                alertDialog.setMessage("Για την ενημέρωση ενος προϊόντος χρειάζεστε το ID του.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Έλεγχος ID",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Συνέχεια",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(My_shopping.this, Update_product.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                break;

            case R.id.delete:
                AlertDialog alertDialog2 = new AlertDialog.Builder(My_shopping.this).create();
                alertDialog2.setTitle("Προσοχή!");
                alertDialog2.setMessage("Για την διαγραφή ενος προϊόντος χρειάζεστε το ID του.");
                alertDialog2.setButton(AlertDialog.BUTTON_NEUTRAL, "Έλεγχος ID",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog2.setButton(AlertDialog.BUTTON_POSITIVE, "Συνέχεια",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(My_shopping.this, Delete_product.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                alertDialog2.show();
                break;

            case R.id.sort:
                final AlertDialog.Builder alertDialog3 = new AlertDialog.Builder(My_shopping.this);
                final String[] sort = getResources().getStringArray(R.array.sort);
                final ListView list = (ListView)findViewById(R.id.listView);
                final ArrayList<String> my_list = new ArrayList<>();

                alertDialog3.setTitle("Ταξινόμηση κατά:");
                alertDialog3.setSingleChoiceItems(R.array.sort, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selection = sort[which];
                    }
                });
                alertDialog3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(selection.equals("Κατηγορία")){
                            String query = "Select ID,NAME,QUANTITY,CATEGORY,EXP_DATE From SHOPPING_BASKET order by CATEGORY";
                            Cursor data = db.showData(query);

                            if (data != null && data.moveToFirst()){
                                do {
                                    my_list.add("ID προϊόντος: " + data.getString(0) + "\n" + "Όνομα: " + data.getString(1) + "\n" + "Ποσότητα: " +data.getString(2) + "\n" + "Κατηγορία: " + data.getString(3) + "\n" + "Ημ/νία Λήξης: " + data.getString(4));
                                } while (data.moveToNext());
                            }

                            ListAdapter listAdapter1 = new ArrayAdapter<>(My_shopping.this,R.layout.custom_shopping,my_list);
                            list.setAdapter(listAdapter1);
                            Toast.makeText(My_shopping.this, "Ταξινόμηση κατα κατηγορία επιτυχής!", Toast.LENGTH_LONG).show();
                        }
                        else if(selection.equals("Όνομα")){
                            String query = "Select ID,NAME,QUANTITY,CATEGORY,EXP_DATE From SHOPPING_BASKET order by NAME";
                            Cursor data = db.showData(query);

                            if (data != null && data.moveToFirst()){
                                do {
                                    my_list.add("ID προϊόντος: " + data.getString(0) + "\n" + "Όνομα: " + data.getString(1) + "\n" + "Ποσότητα: " +data.getString(2) + "\n" + "Κατηγορία: " + data.getString(3) + "\n" + "Ημ/νία Λήξης: " + data.getString(4));
                                } while (data.moveToNext());
                            }

                            ListAdapter listAdapter1 = new ArrayAdapter<>(My_shopping.this,R.layout.custom_shopping,my_list);
                            list.setAdapter(listAdapter1);
                            Toast.makeText(My_shopping.this, "Ταξινόμηση κατα όνομα επιτυχής!", Toast.LENGTH_LONG).show();
                        }
                        else if(selection.equals("Ημ/νία Λήξης")){

                            String query = "Select ID,NAME,QUANTITY,CATEGORY,EXP_DATE From SHOPPING_BASKET order by EXP_DATE";
                            Cursor data = db.showData(query);

                            if (data != null && data.moveToFirst()){
                                do {
                                    my_list.add("ID προϊόντος: " + data.getString(0) + "\n" + "Όνομα: " + data.getString(1) + "\n" + "Ποσότητα: " +data.getString(2) + "\n" + "Κατηγορία: " + data.getString(3) + "\n" + "Ημ/νία Λήξης: " + data.getString(4));
                                } while (data.moveToNext());
                            }

                            ListAdapter listAdapter1 = new ArrayAdapter<>(My_shopping.this,R.layout.custom_shopping,my_list);
                            list.setAdapter(listAdapter1);
                            Toast.makeText(My_shopping.this, "Ταξινόμηση κατα ημ/νία λήξης επιτυχής!", Toast.LENGTH_LONG).show();

                        }
                    }
                });
                alertDialog3.show();
                break;
            case R.id.back:
                Intent i = new Intent(My_shopping.this,MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            default:
                break;
        }
        return true;
    }
}

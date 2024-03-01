package com.example.bestbeforeapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Barcode_Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    DatabaseHelper db = new DatabaseHelper(this);
    ZXingScannerView  ScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);

        Toast.makeText(this, "Σαρώστε το barcode του προϊόντος", Toast.LENGTH_LONG).show();
        setContentView(ScannerView);
    }

    @Override
    public void handleResult(final Result result) {
        Intent intent = getIntent();
        final int c = intent.getIntExtra("Category", 0);

        String query = "Select BARCODE From SHOPPING_BASKET where BARCODE = '"+result+"'";
        Cursor data = db.getData(query);

        if(data.getCount()>0){
            final AlertDialog alertDialog = new AlertDialog.Builder(Barcode_Scanner.this).create();
            alertDialog.setTitle("Ειδοποίηση!");
            alertDialog.setMessage("Το προϊόν υπάρχει ήδη στο καλάθι σας.\nΠροσθήκη προϊόντος στο καλάθι;");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Όχι",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Barcode_Scanner.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("EXIT", true);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ναι",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Barcode_Scanner.this, New_product.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("Category",c);
                            intent.putExtra("barcode",result.getText());
                            startActivity(intent);
                            finish();
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
    }
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(Barcode_Scanner.this).create();
            alertDialog.setTitle("Ειδοποίηση!");
            alertDialog.setMessage("Το προϊόν δεν υπάρχει στο καλάθι σας.\nΘέλετε να προστεθεί;");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Πίσω",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Barcode_Scanner.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("EXIT", true);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ναι",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Barcode_Scanner.this, New_product.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("Category",c);
                            intent.putExtra("barcode",result.getText());
                            startActivity(intent);
                            finish();
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    protected void onPause()
    {
        super.onPause();
        ScannerView.stopCamera();
    }

    protected void onResume(){
        super.onResume();
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
}

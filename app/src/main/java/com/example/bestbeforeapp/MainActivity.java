
package com.example.bestbeforeapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    ImageButton frouta,katepsigmena,galaktokomika,ospria,allantika,anapsuktika,kreatika,zumarrika,alla;
    public int default_category=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "Επιλέξτε μια κατηγορία", Toast.LENGTH_LONG).show();

        frouta =(ImageButton)findViewById(R.id.fruits);
        katepsigmena =(ImageButton)findViewById(R.id.katepsigmena);
        galaktokomika =(ImageButton)findViewById(R.id.galaktokomika);
        ospria =(ImageButton)findViewById(R.id.ospria);
        allantika =(ImageButton)findViewById(R.id.allantika);
        anapsuktika =(ImageButton)findViewById(R.id.anapsiktika);
        kreatika =(ImageButton)findViewById(R.id.kreatika);
        zumarrika =(ImageButton)findViewById(R.id.zumarika);
        alla =(ImageButton)findViewById(R.id.diafora);

        frouta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Barcode_Scanner.class);
                i.putExtra("Category",default_category);
                startActivity(i);
            }
        });

        katepsigmena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Barcode_Scanner.class);
                default_category=1;
                i.putExtra("Category",default_category);
                startActivity(i);
            }
        });

        galaktokomika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Barcode_Scanner.class);
                default_category=2;
                i.putExtra("Category",default_category);
                startActivity(i);
            }
        });

        ospria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Barcode_Scanner.class);
                default_category=3;
                i.putExtra("Category",default_category);
                startActivity(i);
            }
        });

        allantika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Barcode_Scanner.class);
                default_category=4;
                i.putExtra("Category",default_category);
                startActivity(i);
            }
        });

        anapsuktika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Barcode_Scanner.class);
                default_category=5;
                i.putExtra("Category",default_category);
                startActivity(i);
            }
        });

        kreatika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Barcode_Scanner.class);
                default_category=6;
                i.putExtra("Category",default_category);
                startActivity(i);
            }
        });

        zumarrika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Barcode_Scanner.class);
                default_category=7;
                i.putExtra("Category",default_category);
                startActivity(i);
            }
        });

        alla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Barcode_Scanner.class);
                default_category=8;
                i.putExtra("Category",default_category);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                finish();
                System.exit(0);
                break;

            case R.id.bug:
                Intent i = new Intent(MainActivity.this,My_shopping.class);
                startActivity(i);
            default:
                break;
        }
        return true;
    }
}

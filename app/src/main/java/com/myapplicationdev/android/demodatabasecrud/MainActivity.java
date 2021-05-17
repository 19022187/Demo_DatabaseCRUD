package com.myapplicationdev.android.demodatabasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnEdit, btnRetrieve;
    TextView tvDBContent;
    EditText etContent;
    ArrayList<Note> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize the variables with UI here
        btnAdd = (Button) this.findViewById(R.id.btnAdd);
        btnEdit = (Button) this.findViewById(R.id.btnEdit);
        btnRetrieve = (Button) this.findViewById(R.id.btnRetrieve);
        etContent =(EditText) this.findViewById(R.id.etContent);
        tvDBContent = (TextView) this.findViewById(R.id.tvDBContent);

        al = new ArrayList<Note>();
        DBHelper dbh = new DBHelper(MainActivity.this);
        al.addAll(dbh.getAllNotes());


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etContent.getText().toString();
                DBHelper dbh = new DBHelper(MainActivity.this);
                long inserted_id = dbh.insertNote(data);
                dbh.close();

                if (inserted_id != -1) {
                    Toast.makeText(MainActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Note target = al.get(0);

                Intent i = new Intent(MainActivity.this,
                        EditActivity.class);
                i.putExtra("data", target);
                startActivity(i);
            }
        });

        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                al.clear();
                al.addAll(dbh.getAllNotes());
                dbh.close();

                String txt = "";
                for (int i = 0; i < al.size(); i++) {
                    Note tmp = al.get(i);
                    txt += "ID:" + tmp.getId() + ", " +
                            tmp.getNoteContent() + "\n";
                }
                tvDBContent.setText(txt);
            }
        });

    }
}
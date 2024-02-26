package com.bilir.noteroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class UpdateNotes extends AppCompatActivity {

    EditText titleComponent, contentComponent;
    Button cancelButton, saveButton;
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);

        Bar();


        // ActionBar'a geri tuşu ekleme
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ActionBar title
        getSupportActionBar().setTitle("Update Note");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.accent)));


        titleComponent = findViewById(R.id.editTextTitle2);
        contentComponent = findViewById(R.id.editTextContent2);
        cancelButton = findViewById(R.id.buttonCancel2);
        saveButton = findViewById(R.id.buttonSave2);

        getData();


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUpdating();
            }
        });
    }


    // AÇILINCA DEĞERLERİ ALMA (ONCREATEDE ÇAĞIRDIK)
    public void getData() {
        Intent intent = getIntent();
        ID = intent.getIntExtra("id", -1);
        titleComponent.setText(intent.getStringExtra("title"));
        contentComponent.setText(intent.getStringExtra("content"));
    }

    public void startUpdating() {


        String title = titleComponent.getText().toString(),
               content = contentComponent.getText().toString();
        int id = ID;

        if(!content.trim().isEmpty() && id != -1) {
            Intent intent = new Intent();

            intent.putExtra("title", title);
            intent.putExtra("content", content);
            intent.putExtra("id", id);

            setResult(RESULT_OK, intent);
        }

        finish();
    }

    void Bar() {
        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.accent2));
        }
    }
}
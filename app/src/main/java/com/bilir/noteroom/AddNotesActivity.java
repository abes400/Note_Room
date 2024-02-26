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

public class AddNotesActivity extends AppCompatActivity {

    EditText titleComponent, contentComponent;
    Button cancelButton, saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);


        // ActionBar'a geri tuşu ekleme
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ActionBar title
        getSupportActionBar().setTitle("Add New Note");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.accent)));

        Bar();


        titleComponent = findViewById(R.id.editTextTitle);
        contentComponent = findViewById(R.id.editTextContent);
        cancelButton = findViewById(R.id.buttonCancel);
        saveButton = findViewById(R.id.buttonSave);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSaving();
            }
        });
    }



    public void startSaving() {

        String title = titleComponent.getText().toString();
        String content = contentComponent.getText().toString();

        if(!content.trim().isEmpty()) {
            // CALLER ACTIVITY'YE DBYE KAYDETMESİ İÇİN VERİ GÖDERİYORUZ
            // BUNUN İÇİN DEFAULT INTENT AÇIP RESULT VERİCEZ

            Intent intent = new Intent();
            intent.putExtra("title", title);
            intent.putExtra("content", content);

            // RESULT GÖNDERİYOZ
            setResult(RESULT_OK, intent);
            // TABİİ İNTENTİMİZİN ALABİLMESİ İÇİN BU ACT'Yİ startActivityForResult(intent, 1) diye
            // çağırması lazım
        }

        finish(); // BU AKT'Yİ KAPATIP SİL
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
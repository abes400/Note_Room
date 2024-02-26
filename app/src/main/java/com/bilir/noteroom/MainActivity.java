package com.bilir.noteroom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    NoteViewModel noteViewModel; // ViewModel objesi için

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ActionBar başlığı değiştirme
        getSupportActionBar().setTitle("Note Room");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.accent)));

        Bar();



        setContentView(R.layout.activity_main);


        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.myView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Yukardan aşağı liste

        NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        // Veritabanındaki değişiklikleri gözlemlemek için (LiveView)
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            // DB'mizde değişiklik olunca çağrılır.
            @Override
            public void onChanged(List<Note> notes) {
                // Recyclerview burada güncellenir.
                noteAdapter.setNotes(notes);
            }
        });


        // RECYCLERVİEW KAYDIRMA İŞLEMLERİ İÇİN ONCREATE METOTUNDA                               // Sağ sol kaydırma
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override // Drag&Drop
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override // Sağ Sol Kaydırma
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                                                        //Kaydırılan endeks
                Note deleteNote = noteAdapter.getNotes(viewHolder.getAdapterPosition()); // Silinecek notu alıyoruz
                noteViewModel.delete(deleteNote); // Siliyoruz
            }
        }).attachToRecyclerView(recyclerView); // RViewa bağlama


        // ADAPTÖRDE OLUŞTURDUĞUMUZ INTERFACE İÇİN ÖZEL FONKSİYONUMUZU TANIIMLAYIP LISTENERİ ADAPTÖRE BAĞLAMA
        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                // Update aktivitesine veri gönderme
                Intent intent = new Intent(MainActivity.this, UpdateNotes.class);
                intent.putExtra("id", note.getId());
                intent.putExtra("title", note.getTitle());
                intent.putExtra("content", note.getDescription());
                startActivityForResult(intent, 2);
            }
        });
    }

    // Menu layoutu inflate edip ekrana verme
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_note_menu, menu);
        return true;
    }


    // Menu itemlerine fonksiyon atama
    @Override                           // Tıklanana menü itemi
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int ID = item.getItemId();

        if(ID == R.id.add_note_button) {
            Intent intent = new Intent(this, AddNotesActivity.class);

            // AÇILAN AKTİVİTEDEN BURAYA ERİŞEBİLMESİ İÇİN
            startActivityForResult(intent, 1);

            return true;
        } else
            return super.onOptionsItemSelected(item);
    }



    // BURADAN AÇTIĞIMIZ AKTİVİTEDEN VERİ ALMAK İÇİN
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK) { // ADD
            String title = data.getStringExtra("title"),
                    content = data.getStringExtra("content");

            // Veritabanına eklemek için bunu yapman kafi
            noteViewModel.insert(new Note(title, content));
        } else if(requestCode == 2 && resultCode == RESULT_OK) {

            String title = data.getStringExtra("title"),
                    content = data.getStringExtra("content");
            int ID = data.getIntExtra("id", -1);

            Note note = new Note(title, content);

            note.setId(ID);

            // Veritabanına eklemek için bunu yapman kafi
            noteViewModel.update(note);
        }
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
package com.bilir.noteroom;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends androidx.lifecycle.AndroidViewModel{
    private NoteRepository repository;
    private LiveData<List<Note>> notes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        notes = repository.getAllNotes(); // LiveData'mızı notes'a bağladık
    }


    // CRUD metotunu burada da tanımlayak
    public void insert(Note note) {repository.insert(note);}
    public void update(Note note) {repository.update(note);}
    public void delete(Note note) {repository.delete(note);}

    //Tüm notları devşirmeyi burada da tanımlayalım
    public LiveData<List<Note>> getAllNotes() {return notes;}
}

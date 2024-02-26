package com.bilir.noteroom;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private  NoteDAO noteDAO; // Bizim databaseimize erişimimiz için oluşturulan database singletonumuzın noteDao objesi bağlanacak
    private LiveData<List<Note>> notes; // Bu dizimizi LiveData gözlemliyor


    //CONSTRUCTOR
    public NoteRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application); // Üstte bahsedilen singletonımızı buraya bağladık
        noteDAO = noteDatabase.noteDAO(); // bu singleton'ın noteDAOsunu bağladık
        notes = noteDAO.getAllNotes(); // DAO'muz ile notları çeken bir query oluşturduk ve bunu listeye attık.
    }

    // Tüm notları al
    public LiveData<List<Note>> getAllNotes() {
        return notes; // Zaten livedata olduğu için veritabanımızda güncelleme olunca notes değişkenine de bu yansır.
        // Room, livedata için gerekli işlemleri arkaplanda otomatik olarak yapacak.
    }

    // Fakat bu üç metot için kendimiz Bakground thread oluşturmalıyız Çünkü Room, mainthreadde DB işi yapmamıza izn. vermiyor.

    public void insert(Note note) {new InsertNoteAsyncTask(noteDAO).execute(note);}
    public void update(Note note) {new UpdateNoteAsyncTask(noteDAO).execute(note);}
    public void delete(Note note) {new DeleteNoteAsyncTask(noteDAO).execute(note);}


    // Arkaplanda ekleme işelmi yapacak gören objesi
    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDAO noteDAO;

        private InsertNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.insert(notes [0]); // parametre olarak verdiğimiz not entitysini eklettik.
            return null;
        }
    }

    // Arkaplanda update işelmi yapacak gören objesi
    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDAO noteDAO;

        private UpdateNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.update(notes [0]); // parametre olarak verdiğimiz not entitysini eklettik.
            return null;
        }
    }

    // Arkaplanda silme işelmi yapacak görev objesi
    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDAO noteDAO;

        private DeleteNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.delete(notes [0]); // parametre olarak verdiğimiz not entitysini eklettik.
            return null;
        }
    }





}

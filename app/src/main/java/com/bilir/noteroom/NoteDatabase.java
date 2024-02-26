package com.bilir.noteroom;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

// Singleton
@Database(entities = Note.class, version = 1) // Bu anotasypna hangi tabloların ekleneceğini yazdık
abstract public class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;
    public abstract NoteDAO noteDAO(); // Room'un bizim interfaceimize bakarak oluşturacağı DAO buraya bağlanacak

    public static synchronized NoteDatabase getInstance(Context context) { // Singleton
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class, "note_database" )
                    .fallbackToDestructiveMigrationFrom()
                    .addCallback(roomCallback)
                    .build(); // Room'un asıl oluşturduğu database'den bu sınıfın örneği çıkarılır
        }
        return instance; // ve döndürülür
    }


    // DB'miz oluşturulunca bizim tanımlayacağımız işlemler burada olacak.
    // DM'mizi ilk açtıımızda veri eklemek için bu prosedür yapılabilir.
    private  static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //new PopulateDatabaseAsyncTask(instance).execute();
        }
    };


    // Veritabanımıza birkaç satır ekleme prosedürü
    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void>{
        private NoteDAO noteDAO;

        private PopulateDatabaseAsyncTask(NoteDatabase database) {
            noteDAO = database.noteDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.insert(new Note("Title 1", "Note 1"));
            noteDAO.insert(new Note("Title 2", "Note 2"));
            noteDAO.insert(new Note("Title 3", "Note 3"));
            return null;
        }
    }
}

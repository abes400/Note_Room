package com.bilir.noteroom;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {
    @Insert // Ben sadece bunun insert işlevi olduğunu anlatıyom.
    void insert(Note note); // Room bizim yerimize Note objesine göre imp. edecek

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM note_table ORDER BY id ASC") // SQL kodunun genel tipini yazdık
    // Altına sorgunun nasıl çağrılacağını yazıcaz
    LiveData<List<Note>> getAllNotes();
    //LiveData ise bağladığımız komponente realtime güncelleme özelliği verecek.


}

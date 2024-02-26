package com.bilir.noteroom;

// Bu class, ROOM kütüphanesini kullanan bir ENTITY classıdır.
// SQLite Veritabanı için bir entity oluşturacağız

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Biz bu annotationu yazarak ROOM'un arkaplanda ilgili kodları yazmasını sağlıyorum.
// Bu sayede ROOM, bu sql tablolarını otomatik olarak oluşturacak.
@Entity(tableName = "note_table")
public class Note {

    // Otomatik olarak bir PK atama
    @PrimaryKey(autoGenerate = true)

    // Sütun adları tanımlayalım.
    // Notlar DM'mizde bir Primary Key ile saklanacak
    public int id;
    public String title, description;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // Getter'lar
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    // Setter'lar

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

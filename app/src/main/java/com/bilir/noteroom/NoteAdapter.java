package com.bilir.noteroom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter <NoteAdapter.NoteHolder>{

    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;

    // Herbir Holder objesi için layout oluşturup Holder classın objesini bağlıyor.
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteHolder(view);
    }

    // Herbir java nesnesinden gelen veriyi notholdera bağladığımız yer
    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.noteTitle.setText(currentNote.getTitle());
        holder.noteContent.setText(currentNote.getDescription());
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged(); // ListViewimiz için
    }

    // Get note of position.
    public Note getNotes(int position) { return notes.get(position); }


    @Override
    public int getItemCount() {return notes.size();}

    // Özel tasarladığımız layout elemanlarını tanımlarız
    // Layoutumuzun her bir objesini tanımlarız
    class NoteHolder extends RecyclerView.ViewHolder {

        private TextView noteTitle, noteContent;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            noteTitle = itemView.findViewById(R.id.noteTitle);
            noteContent = itemView.findViewById(R.id.noteContent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        // MainActivity'de tanımladığımız fonksiyona notes objesi de veriyoruz.
                        listener.onItemClick(notes.get(position));
                    }
                }
            });
        }
    }


    /*
        Bu interfaceden türetilen bir obje için bir değişken de NoteAdapter'da tanımladık.
     */

    public interface OnItemClickListener {
        public void onItemClick(Note note);
    }


    /*

        Tıklama fonku için bu kadar uğraşmamızın sebebi parent aktiviteyi
        MainActivity yapmak istememiz.

        Biz başka bir sınıfta bu Adapter sınıfın bir objesi için bu metotu
        çağırınca, parametredeki OnItemClickListener objesinin metotunu
        implement etmemiz istenecek. İlgili implementasyonu yapılmış olan
        listeneri de bahsi geçen adapter objesinin değişkenine eşitleyecek.

        MainActivity 73.satıra bak

        Daha sonra biz bu modifiye edilmiş listener objesini cardview'in
        onclick metotunda çağırabiliriz.

        Burada 59. satıra bak

        İşin ilginci intentimiz yine MainActivity ile Update arasında olacak.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

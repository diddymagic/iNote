package com.echo.iNote;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PrivateNotesAdapter extends RecyclerView.Adapter<PrivateNotesAdapter.ViewHolder>{
    Context contex;
    ArrayList<Notes> privateNotes;

    PrivateNotesAdapter(ArrayList<Notes> privateNotes){
        this.privateNotes = privateNotes;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notes_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Notes note = privateNotes.get(viewHolder.getAdapterPosition());
        viewHolder.title.setText(note.getTitle());
        viewHolder.textBody.setText(note.getTextBody());
        viewHolder.date.setText(note.getDate());
        viewHolder.category.setText("");
        if(note.getColors() == viewHolder.one){
            viewHolder.categoriesInitializer(viewHolder.one,contex.getString(R.string.toast_message_uncategorized));
        }else if (note.getColors() == viewHolder.two){
            viewHolder.categoriesInitializer(viewHolder.two,contex.getString(R.string.toast_message_work));
        }else if (note.getColors() == viewHolder.three){
            viewHolder.categoriesInitializer(viewHolder.three,contex.getString(R.string.toast_message_family_affair));
        }else if (note.getColors() == viewHolder.four){
            viewHolder.categoriesInitializer(viewHolder.four,contex.getString(R.string.toast_message_study));
        }else if (note.getColors() == viewHolder.five){
            viewHolder.categoriesInitializer(viewHolder.five,contex.getString(R.string.toast_message_personal));
        }
    }

    @Override
    public int getItemCount() {
        return privateNotes.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView title;
        TextView textBody;
        TextView date;
        TextView category;
        CardView cardView;
        CircleImageView circleImageView;
        FrameLayout frameLayout;
        int one = Color.parseColor("#607d8b");//blue grey uncategorized
        int two = Color.parseColor("#7e57c2");//deep purple work
        int three = Color.parseColor("#ef5350");//red family affair
        int four = Color.parseColor("#42a5f5");//blue study
        int five = Color.parseColor("#66bb6a");//green research
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            //context here could be used in place of the open note here
            contex = itemView.getContext();
            title = itemView.findViewById(R.id.recy_title_text);
            textBody = itemView.findViewById(R.id.recy_note_body);
            date = itemView.findViewById(R.id.recy_date);
            category = itemView.findViewById(R.id.recy_category_text);
            circleImageView = itemView.findViewById(R.id.circleImageView);
            cardView = itemView.findViewById(R.id.recy_cardview);
            frameLayout = itemView.findViewById(R.id.recy_frame_layout);
            if(HomePage.showCategoriesView){
                circleImageView.setVisibility(View.VISIBLE);
            }else{
                circleImageView.setVisibility(View.GONE);
            }
            frameLayout.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos;
                    if(HomePage.isSearchView){
                        pos = HomePage.noteList.indexOf(privateNotes.get(getAdapterPosition()));
                        openNote(itemView.getContext(),pos);
                    }else{
                        openNote(itemView.getContext(),getAdapterPosition());
                    }
                }
            });
        }
        public void categoriesInitializer(int color, String categoryText){
            circleImageView.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_OVER);
            category.setTextColor(color);
            category.setText(categoryText);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            int pos;
            if(HomePage.isSearchView){
                /*this part doesn't work yet since we haven't yet implemented the search part for private notes*/
                pos = HomePage.protectedNotesArray.indexOf(privateNotes.get(getAdapterPosition()));
            }else {
                pos = getAdapterPosition();
            }
            /*context menu params (groupID, itemID, menuPosition/Arrangement, StringResource*/
            menu.setHeaderTitle(contex.getString(R.string.select_an_option));
            menu.add(pos,100,0,v.getContext().getString(R.string.contextDelete));
            menu.add(pos,101,1,v.getContext().getString(R.string.contextEdit));
            menu.add(pos,102,2,v.getContext().getString(R.string.contextShare));

            menu.add(pos, 106, 3, v.getContext().getString(R.string.share_multi_choice));

            menu.add(pos, 103, 4, "Move To Notes");
            menu.add(pos, 105, 5, "Send To Contact");


        }

        public void openNote(Context view,int position){
            Intent intent = new Intent(view,EdithNote.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("notes",HomePage.protectedNotesArray.get(position));
            intent.putExtra("p",position);
            view.getApplicationContext().startActivity(intent);
        }
    }
}

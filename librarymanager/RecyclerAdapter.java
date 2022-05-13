package trying.librarymanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import trying.librarymanager.concretes.Book;
import trying.librarymanager.concretes.Card;
import trying.librarymanager.concretes.Singletun;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

   private List<Card> lcards;
   private List<Book> lbooks;
   private boolean listofBook = false , listofCard = false;
   private int key;
   Context ctx;
   Computer computer;
   // Provide a reference to the views for each data item
   // Complex data items may need more than one view per item, and
   // you provide access to all the views for a data item in a view holder
   public class ViewHolder extends RecyclerView.ViewHolder  {
      // each data item is just a string in this case
      View v;
      TextView mName, mData;
      public ViewHolder(View v) {
         super(v);
         this.v = v;
         v.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
               int position = getAdapterPosition();
         if(listofBook){
          computer.deleteBookfromDB( lbooks.get(position) );
         }else if(listofCard){
           computer.deleteStudentfromDB(lcards.get(position) );
         }
               Toast.makeText( ctx, "Deleted", Toast.LENGTH_SHORT).show();
               return true;
            }
         });
      }

   }

   // Provide a suitable constructor (depends on the kind of dataset)
   public RecyclerAdapter(int key , List list ,Context ctx)  {
      this.key = key;
      this.ctx =  ctx;
      computer = Singletun.getInstance();
      if(key==2) {  // if key ==2 then readers if key is 1 then list of books.
         lcards = list;
         listofCard = true;
      }
      else if(key == 1) {
         lbooks = list;
         listofBook = true;
      }
   }

   // Create new views (invoked by the layout manager)
   @Override
   public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
      // create a new view
      View v = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.recylcer_views, parent, false);

      ViewHolder vh = new ViewHolder(v);
      vh.mName = v.findViewById(R.id.mNametxt);
      vh.mData = v.findViewById(R.id.mNumbertxt);
      return vh;
   }

   @Override
      public void onBindViewHolder(ViewHolder holder, int position) {
      // - get element from your dataset at this position
      // - replace the contents of the view with that element
      if(listofCard) {
         Card card = lcards.get(position);
         holder.mName.setText(card.getName() );
         holder.mData.setText(card.getRollNo() );
      }
      else if(listofBook) {
      Book book = lbooks.get(position);
      holder.mName.setText(book.getName() );
      holder.mData.setText(String.valueOf(book.getISBNnum() ) );
            }

   }
   // Return the size of your dataset (invoked by the layout manager)
   @Override
   public int getItemCount() {
      if(listofCard)
      return lcards.size();
      else if(listofBook) return lbooks.size();
      return 0;
   }
}

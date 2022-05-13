package trying.textovoice.dontforget;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   ListView listview;  public static ArrayList<SingleTask> listOfTask;
   NewTaskAdapter adapter;       int count;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      listOfTask = new ArrayList<>();
      listview = findViewById(R.id.mlistview);
      adapter = new NewTaskAdapter(this, listOfTask);
      listview.setAdapter(adapter);
      listview.setEmptyView(findViewById(R.id.emptyImgView));

      listview.setOnItemLongClickListener(new OnItemLongClickListener() {

         @Override
         public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int i, long l) {
            //final int k = i;
            Builder alertBuilder = new Builder(MainActivity.this);
            alertBuilder.setNegativeButton("Delete", new OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int j) {
                  deleteItem(i);
               }
            });
            alertBuilder.setCancelable(true);
            AlertDialog alertDialog = alertBuilder.create();
            alertDialog.show();

            return true;
         }
      });
   }
    public static void editTask(int index, String newTitle,String newDetail){
      listOfTask.get(index).setMtitle(newTitle);
       listOfTask.get(index).setMdescription(newDetail);
    }
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.add_new_task , menu);
    count =0;
      return super.onCreateOptionsMenu(menu);
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();

      if (id == R.id.mybutton) {
         listOfTask.add(new SingleTask("New"+count,"",0));
         count++;
         adapter.notifyDataSetChanged();
      }
      return super.onOptionsItemSelected(item);
   }
   @Override
   protected void onStop()
   {
      super.onStop();

      // Dont forget to cancel the running timers
      adapter.cancelAllTimers();
   }
   protected void deleteItem(int index){
       listOfTask.remove(index);
       adapter.notifyDataSetChanged();
   }
}


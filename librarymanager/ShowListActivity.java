package trying.librarymanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import trying.librarymanager.concretes.Singletun;

public class ShowListActivity extends AppCompatActivity {
   private RecyclerView mrecyclerV;
   private Computer computer;
   private RecyclerView.Adapter mAdapter;
   private RecyclerView.LayoutManager mLayoutManager;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_scrolling);
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      mrecyclerV = findViewById(R.id.mRecyclerView);

      computer = Singletun.getInstance();

      int key = getIntent().getExtras().getInt("key"); // Caution statement !
      if(key == 2){
      getSupportActionBar().setTitle(R.string.reader);
      mAdapter = new RecyclerAdapter(key,computer.getAllStudentinDB() , this);
      }  else if(key == 1) {
         getSupportActionBar().setTitle(R.string.books);
         mAdapter = new RecyclerAdapter(key,computer.getAllBookinDB() , this);

      }

      // use a linear layout manager
      mLayoutManager = new LinearLayoutManager(this);
      mrecyclerV.setLayoutManager(mLayoutManager);

      // specify an adapter (see also next example)
      mrecyclerV.setAdapter(mAdapter);
   }

   @Override
   public void onBackPressed() {
      finish();
      super.onBackPressed();
   }
}

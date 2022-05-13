package trying.librarymanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
   private Intent mainIntent = null;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
   }
   public void provideBtn(View v){
         forSlideAnimation(1);
   }
   //Btn not working
   public void AddStudentBtn(View v){
      mainIntent = new Intent(MainActivity.this, StudentAdderActivity.class);
      startActivity(mainIntent);
   }
   //btn not working
   public void AddBookBtn(View v){
      mainIntent = new Intent(MainActivity.this, BookAdderActivity.class);
      startActivity(mainIntent);
   }

   public void showAllBooksBtn(View v){
      forSlideAnimation(2);
   }
   public void showAllReadersBtn(View v){
      forSlideAnimation(3);
   }
   public void forSlideAnimation(final int code){
      switch (code) {
         case 1: {  mainIntent = new Intent(MainActivity.this,
                 LibraryActivity.class); break; }
         case 2: {
            mainIntent = new Intent(MainActivity.this, ShowListActivity.class);
            mainIntent.putExtra("key", 1);
            break;
         }
         case 3: {
            mainIntent = new Intent(MainActivity.this, ShowListActivity.class);
            mainIntent.putExtra("key", 2);
            break;
         }
      }
      //SplashScreen.this.startActivity(mainIntent);
      final Intent finalMainIntent = mainIntent;
      new Handler().postDelayed(new Runnable() {
         public void run() {

                 startActivity(finalMainIntent);
                     /* Apply our splash exit (fade out) and main
                        entry (fade in) animation transitions. */
            overridePendingTransition(R.anim.mainfadein,R.anim.mainfadeout);
         }
      }, 600);
   }

   @Override
   public void onBackPressed() {
      Intent intent = new Intent(Intent.ACTION_MAIN);
      intent.addCategory(Intent.CATEGORY_HOME);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
      super.onBackPressed();
   }
}

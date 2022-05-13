package trying.librarymanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import trying.librarymanager.concretes.BookManager;
import trying.librarymanager.concretes.Card;
import trying.librarymanager.concretes.CardChecker;
import trying.librarymanager.concretes.Singletun;

public class StudentAdderActivity extends AppCompatActivity {

   private EditText name , roll , expDate;
   private Computer computer;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.student_adder);

      initiate();
   }
   public void initiate(){
      name = findViewById(R.id.mNametxt);
      roll = findViewById(R.id.mrollTxt);
      expDate = findViewById(R.id.mExpiryDate);
      computer = Singletun.getInstance();
   }
   public void saveBtn(View v){
      String n = name.getText().toString();
      String r = roll.getText().toString();
      //this is the place where I was near to death.
      String strDate = expDate.getText().toString();
      try {
      Date d = BookManager.formatStringToDate(strDate);

      if(!new CardChecker().checkRollNum(r)){ throw new NullPointerException(); }

         computer.addStudentToDB(new Card(n,r,d));
         Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
         finish();
         //startActivity(new Intent(StudentAdderActivity.this , MainActivity.class ));
      }catch(Exception e){
         Toast.makeText(getApplicationContext(), "Incorrect data", Toast.LENGTH_LONG).show();
      }
   }
}

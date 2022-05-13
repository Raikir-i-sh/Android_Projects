package trying.librarymanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import trying.librarymanager.concretes.Book;
import trying.librarymanager.concretes.Singletun;

public class BookAdderActivity extends AppCompatActivity {
   private EditText name , price, isbn_no;
   private Spinner spin;
   private Computer computer;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.book_adder_activity);

      List<String> categoryList = new ArrayList<>();
      categoryList.add("PHY");
      categoryList.add("CHE");
      categoryList.add("MATH");
      categoryList.add("COMP");

      initiate();
      spin.setOnItemSelectedListener(new OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

         }
         @Override
         public void onNothingSelected(AdapterView<?> adapterView) {

         }
      });

      ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, categoryList);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      //Setting the ArrayAdapter data on the Spinner
      spin.setAdapter(adapter);

   }
   public void initiate(){
      name = findViewById(R.id.nameId);
      price =  findViewById(R.id.priceId);
      isbn_no  = findViewById(R.id.isbnId);
      spin = (Spinner) findViewById(R.id.categoryId);
      computer = Singletun.getInstance();
   }
   public void saveBtn(View v){
      try {
         String n = name.getText().toString();
         int p = Integer.parseInt(price.getText().toString());
         int isbn = Integer.parseInt(isbn_no.getText().toString());
         String cat = spin.getSelectedItem().toString();
        // taking data from UI and adding new book to DB.
         computer.addBookToDB(new Book(n,p,null,isbn, cat));
         Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
        finish();
         // startActivity(new Intent(BookAdderActivity.this , MainActivity.class ));
      }
      catch(Exception e) {
         Toast.makeText(getApplicationContext(), "Incomplete data", Toast.LENGTH_LONG).show();
      }


   }

}

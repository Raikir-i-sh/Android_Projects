package trying.librarymanager;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;

public class CustomDialogActivity extends Dialog implements View.OnClickListener {
   private Context c;
   private int id;
   private boolean dataChanged =false;
   private EditText isbnET;
   private ImageButton saveBtn;
   private int isbn;
   public CustomDialogActivity(Context a, int index) {
      super(a);
      this.c = a;
      this.id = index;
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.custom_dilog);

      saveBtn = findViewById(R.id.mSaveBtn);
      saveBtn.setOnClickListener(this);
      isbnET = findViewById(R.id.mIsbnid);

   }

   @Override
   public void onClick(View view) {
   if(view.getId() == R.id.mSaveBtn){
          if(!isbnET.getText().toString().isEmpty())  {
            isbn = Integer.parseInt( isbnET.getText().toString() );
            dataChanged = true;
            LibraryActivity.dialogClick(isbn);
            dismiss();
            }
      }
   }

   public int getISBN(){
      return isbn;
   }

   public boolean dataChanged(){
      if(dataChanged == true){
         return true;
      }
      return false;
   }
}

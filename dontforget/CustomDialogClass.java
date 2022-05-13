package trying.textovoice.dontforget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;


public class CustomDialogClass extends Dialog implements View.OnClickListener {
   public Context c;
   public Dialog d;
   private ImageButton saveBtn;
   private EditText title , description;
   private int id;
   private boolean dataChanged =false;

   public CustomDialogClass(Context a, int index) {
      super(a);
      this.c = a;
      id = index;
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.custom_dialog);

      saveBtn = findViewById(R.id.mImgBtn);
      saveBtn.setOnClickListener(this);
      title = findViewById(R.id.mtitleTView);
      description  =findViewById(R.id.mdescripTView);
   }


   @Override
   public void onClick(View view) {
      // use shared preferences or text file to store title & description
      if(view.getId() == R.id.mImgBtn) {
         //if title is empty then do nothing
         if(title.getText().toString().isEmpty()){ }
         MainActivity.editTask(id,String.valueOf(title.getText()),String.valueOf(description.getText()) );
         dataChanged = true;
         dismiss();
      }
   }

   public void setTitle_Description(String t , String d){
      title.setText(t);
      description.setText(d);
   }
   public boolean dataChanged(){
            if(dataChanged == true){
               return true;
            }
            return false;
   }

}

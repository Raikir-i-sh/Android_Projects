package trying.librarymanager;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import trying.librarymanager.concretes.Book;
import trying.librarymanager.concretes.Card;
import trying.librarymanager.concretes.CardChecker;
import trying.librarymanager.concretes.Checker;
import trying.librarymanager.concretes.Singletun;

public class LibraryActivity extends AppCompatActivity{
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_CODE1 = 2;
    ImageView myImgView ;
    TextView mNametxt , memptyTxt;
    AlertDialog builder;
    private static Computer computer;
    private ListView mlistv;
    private Checker cardChecker;
    private static Card card;
    private QRScanner qrScanner;
    private Button scanBtn ,addbtn , removebtn;
    private static ArrayAdapter<String> adapter;
    private static List<String> booknames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_activity);

       initiate();

    }
    private void initiate(){
       scanBtn = findViewById(R.id.mScanBtn);
       addbtn = findViewById(R.id.mAddBtn);
       removebtn = findViewById(R.id.mRemoveAllId);
       mNametxt = findViewById(R.id.mtxtView);
       myImgView= findViewById(R.id.myImgView);
       mlistv = findViewById(R.id.mListView);
       memptyTxt = findViewById(R.id.emptyImgView);

       cardChecker =  new CardChecker();
       computer = Singletun.getInstance();
       qrScanner = new QRScanner(this);
       showAddRemoveBtns(0);
       mNametxt.setVisibility(View.GONE);
       myImgView.setVisibility(View.GONE);
       if(!hasCamera()) { scanBtn.setEnabled(false);}
    }
    //Check if user has camera
    private boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public void scanClicked(View v){
         builder = new AlertDialog.Builder(this)
                .setTitle(R.string.dialogTitle)
                .setPositiveButton(R.string.camera, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Launch camera to get the image of QR/BAR code.
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //Take a picture and pass results along to onActivityResult
                        startActivityForResult(intent , REQUEST_IMAGE_CAPTURE);
                    }
                })
                .setNegativeButton(R.string.gallery, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , REQUEST_CODE1);//one can be replaced with any action code
                    }
                })
                .setCancelable(true).create();
        builder.show();

    }

    public void addBookBtn(View v){
       CustomDialogActivity cdd =new CustomDialogActivity(this,0);
       cdd.show();
    }
    public void removeAllBtn(View v){  computer.removeAllBookFromCard(card);
    booknames.clear();
    adapter.notifyDataSetChanged();
    toast("Removed", 0);  }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
       booknames = new ArrayList<>(6);
       if(((requestCode == REQUEST_IMAGE_CAPTURE) || requestCode == REQUEST_CODE1) && (resultCode == RESULT_OK)) {
                   //get the photo
                   try {
                        card = null;
                         qrScanner.startScanning(imageReturnedIntent);
                         myImgView.setVisibility(View.VISIBLE);
                      myImgView.setImageBitmap(qrScanner.getPhoto());
                      String tempdata = qrScanner.getData();
                      if(!tempdata.isEmpty()) {
                       // Log.i("tag","what happened = "+tempdata);
                         mNametxt.setVisibility(View.VISIBLE);
                         mNametxt.setText(tempdata);

                         card = computer.searchCard(tempdata);
                         mNametxt.setText(card.getName() + "\n" + card.getRollNo() );
                         cardCheck();

                      }else {
                         //if data is null
                         toast("Scan error", 1);
                         card = null;
                         booknames.clear();
                         showAddRemoveBtns(0);
                      }

                   } catch(Exception e) {
                      toast("Account not found", 1);
                      card = null;
                      booknames.clear();
                      showAddRemoveBtns(0);
                   }
                }
                   try {
                      for (Book b : computer.getBooksOfStudent(card)) {
                         if(b.hasExpired()) booknames.add(b.getName() + b.isExpired());
                         else booknames.add(b.getName());
                      }
                      adapter = new ArrayAdapter<String>(this,
                              android.R.layout.simple_list_item_1, booknames);
                      mlistv.setAdapter(adapter);
                      mlistv.setEmptyView(memptyTxt);

                   mlistv.setOnItemLongClickListener(new OnItemLongClickListener() {
                      @Override
                      public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                         computer.studentReturnsBook(card, i);
                         booknames.remove(i);
                         adapter.notifyDataSetChanged();
                         toast("Deleted", 0);
                         return true;
                      }
                   });
                }catch(Exception e){ Log.i("tag", "libraryAct : book setting error ");
                   }

    }

   private void toast(String msg , int short0Long1){
       if(short0Long1 == 0)
      Toast.makeText(LibraryActivity.this, msg , Toast.LENGTH_SHORT).show();
       else Toast.makeText(LibraryActivity.this, msg , Toast.LENGTH_LONG).show();
   }
   private void showAddRemoveBtns(int i){
      if(i == 0){
         addbtn.setVisibility(View.GONE);
         memptyTxt.setVisibility(View.GONE);
         mlistv.setVisibility(View.GONE);
         removebtn.setVisibility(View.GONE);
      }else if(i == 1){
         addbtn.setVisibility(View.VISIBLE);
         memptyTxt.setVisibility(View.VISIBLE);
         mlistv.setVisibility(View.VISIBLE);
         removebtn.setVisibility(View.VISIBLE);
      }
   }

   public void cardCheck(){
      if(checkIfCardIsValid() && checkIfCardIsFromThisLibrary()){
      //   Log.i("tag","libraryActivity : Card is valid");
        showAddRemoveBtns(1);
      }
      else {
      //   Log.i("tag","libraryActivity: Card is not valid");
      }
   }
    //check is card is valid or not
    public boolean checkIfCardIsValid(){
        return cardChecker.IFvalid(card.getExpiryDate());
    }
    public boolean  checkIfCardIsFromThisLibrary(){
      return cardChecker.checkRollNum(card.getRollNo());
    }
   public static void dialogClick(int isbn){
      try{
         Book b = computer.searchBook(isbn);
        if( computer.studentTakesBook(card , b) )
        { booknames.add(b.getName() );
         adapter.notifyDataSetChanged(); }
      } catch(Exception e){
         Log.i("tag","LibraryAct: book not found"); }
   }

   @Override
   public void onBackPressed() {
      finish();
      super.onBackPressed();
   }
}

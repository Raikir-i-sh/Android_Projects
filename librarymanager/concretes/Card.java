package trying.librarymanager.concretes;

import android.util.Log;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class Card extends SugarRecord {
   private String name ;
   private String rollNo;
   private Date cardExpiryDate;
   private String listIsbnNo;

   // telling SugarRecord to ignore these fields.
   @Ignore private   List<String> tempList;
   @Ignore private  String str_separator = ",";

   public Card(){  }
   public Card(String name, String Roll_no, Date expiryDate) {
      this.name = name;
      this.rollNo = Roll_no;
      this.cardExpiryDate = expiryDate;
      tempList= new ArrayList<String>(6);
   }

   public String getName() {
      return name;
   }

//   public void setName(String name) {
//      this.name = name;
//   }

   public String getRollNo() {
      return rollNo;
   }

   public void setRollNo(String Roll_no) {
      this.rollNo = Roll_no;
   }

   public Date getExpiryDate() {
      return cardExpiryDate;
   }

   public void setExpiryDate(Date expiryDate) {
      this.cardExpiryDate = expiryDate;
   }

   public void addNewBook(String isbn){
     // setIsbnNo(  convertListToString(tempList) );

      try {
         tempList=  getIsbnNo();
         if( tempList.size()<6){
            if(listIsbnNo == null){ listIsbnNo = isbn; }
            else listIsbnNo =listIsbnNo+str_separator+ isbn;
            tempList  = getIsbnNo();
            Log.i("tag", "size after adding = " + tempList.size());
            Log.i("tag","listisbnNo = " + convertListToString(tempList));
         } else {
            Log.e("tag","Maximum book taken");
         }

      } catch(Exception e){   listIsbnNo = isbn; }

   }

   public void removeBook(int b) {
      try {
         tempList = getIsbnNo();
         if(tempList != null) tempList.remove(b);

      } catch(NullPointerException e){   Log.e("tag","Could not remove book");  }
      Log.i("tag", "size after removing = " + tempList.size());
      if(tempList.size() == 0){
      listIsbnNo = null;
      }else {
         listIsbnNo  = convertListToString(tempList);
      }
   }

   public void removeAllBooks(){
      listIsbnNo = null;
   }

   public final List<String> getIsbnNo() {
      return convertStringToArray(listIsbnNo);
   }

   private String convertListToString(List<String> arraylist){
      String str = "";
      for (int count=0 ; count< arraylist.size(); count++) {
         str = str+ arraylist.get(count) ;
         // Do not append comma at the end of last element
         if(count<arraylist.size()-1){
            str = str+ str_separator;
         }
      }
      return str;
   }
   private List<String> convertStringToArray(String str){
      try {
         String[] arr = str.split(str_separator);
         tempList = new ArrayList<>(Arrays.asList(arr) );
         return tempList;
      }catch(Exception e){ }
      return null;
   }
}
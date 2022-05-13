package trying.librarymanager.concretes;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;



public class BookManager {
    private Checker bookChecking;
   // private List<String> lbooks;
    // private List<Card> lcards;
    
    public BookManager() {
        bookChecking = new BookChecker();
    }

    //checks if any of books taken by  has not expired.
    public void checkIfBooksAreValid(List<Book> listbook){
       for(Book book: listbook){
          //If book is not valid then book is expired.
          if( !bookChecking.IFvalid( book.getBookExpiryDate() ) ){
             book.isExpired();
          }
       }
    }

    public List<Book> getListOfBooksTakenByThisStudent(Card c){
       List<Book> lbooks = new ArrayList<>(6);
       Book temp = null;
       try {
          int siz = c.getIsbnNo().size();
          for (int i =0 ; i < siz ; i++ ) {
             try {
                 temp = Book.find(Book.class, "ISBN_NUMBER =?", c.getIsbnNo().get(i) ).get(0);
              //   Log.i("tag", "bookmanager: temp name "+i+"="+temp.getName()+temp.getISBNnum());
             lbooks.add(temp);
             }catch(Exception e){
                /*if a book from DB is removed which was already assigned to some
                student , then isbn number is still stored in his account.So we need to delete that  */
                c.removeBook(i);
                c.update();
                i--;
             }
          }
        //  checkIfBooksAreValid(lbooks);
       } catch(Exception e){
          if(temp == null) {
             Log.i("tag", "book manager : No books taken");
          }
       }
          c = null;
          return lbooks;
          }
    public List<Book> getListOfallBook(){
       return Book.listAll(Book.class);
    }

    public List<Card> getListOfStudents(){
         return Card.listAll(Card.class);
    }
    // the card object obtained from qRscanner is used to query from database and get the data of card.
        // if student profile not found in database then "No match found "
    public Card getCardFromDB(String roll_no){
       return Card.find(Card.class,"ROLL_NO = ?",roll_no).get(0);
    }
    public Book getAbookFromDB(String isbnNum){
      return Book.find(Book.class, "ISBN_NUMBER =?", isbnNum).get(0);
    }
    public void setNewExpiryDateForBook(Book b){
        Date expiry = getNewExpDate();
     //   Log.i("tag","new date = "+ formatDateToString(expiry ) );
        //b.setBookExpiryDate(expiry);
    }
    //remove single book
    public void removeBook(Card c ,int index){
        try{
       c.removeBook( index );
       c.save();
       c = null;
       //    Log.i("tag","Book returned");
        }catch (Exception ex) {
           Log.i("tag","Book remove error");
        }
    }
    //remove all book assigned to card
    public void removeBook(Card c){
       c.removeAllBooks();
       c.save();
    }
    public boolean addBooktoCard(Card card,String isbn){
       int count = 0;
       try{
       List<String> templist = card.getIsbnNo();
          //check if book being taken by student is not repeated.
          for(String s : templist){
          if(s.equals(isbn) ){
            count +=1;
             break;
          }
       }
          // while getting isbn numbers from card. Isbn number may be null throwing NullPointerException.
          //if card has taken more than 6 books then dont let him take more book.
          if(count==0 && templist.size() < 6 ){
             card.addNewBook(isbn);
             card.save();
           //  templist = null;
             return true;
          }
         }catch(Exception e){   /*if card has no books then nullException is thrown ,this means we can
          add the book */
          card.addNewBook(isbn);
          card.save();

          return true;  }


       return false;
    }

   public static String formatDateToString(Date date){
      String d = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date);
      return d;
   }
   public static Date formatStringToDate(String s){
      if(s.split("-").length == 3) {

         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
         try {
            Date date = format.parse(s);
            if(date.after(new Date())) {
               return date;
            } else {
            //   Log.e("tag", "old date entered, enter valid date again");
               throw new NullPointerException();
               //return null;
            }
         } catch(ParseException e) {
            Log.e("tag", "could not parse data");
         }

      } else {
         throw new NullPointerException();
      }
      return null;
   }
   public Date getNewExpDate() {
      Date d = new Date();
      String any[] = formatDateToString(d).split("-");
      // 3 months = 90 days.
      int temp = Integer.valueOf(any[2]) + 90;
      any[2] = String.valueOf(temp);
      return formatStringToDate(any[0] + "-" + any[1] + "-" + any[2] );
   }
   public void addNewBookToDB(Book book){
      try {
          getAbookFromDB(String.valueOf(book.getISBNnum()));
         Log.i("tag","book already exists in DB");
      } catch(Exception e){
         book.save();
      }
   }
   public void addNewStudentToDB(Card card){
      try {
         // if card is found, do nothing, if not found then throws exception that will be catched.
         getCardFromDB(card.getRollNo());
         Log.i("tag","card already exists in DB");
      }catch(Exception e) {
         card.save();
         Log.i("tag","card added to DB");
      }
   }
   public void removeBookfromDB(Book book){ book.delete(); }
   public void removeCardfromDB(Card card){ card.delete(); }
}
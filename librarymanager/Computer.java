package trying.librarymanager;

import android.util.Log;

import java.util.Date;
import java.util.List;

import trying.librarymanager.concretes.Book;
import trying.librarymanager.concretes.BookManager;
import trying.librarymanager.concretes.Card;
/**
 * This Class is just for executing methods from BookManager and checking validity of card initially.
 * Hence, this class wont do/execute any operations/code related with database.
 */
public class Computer {
    private final BookManager manager;
    private Card card ,c2 ,c3;
    private Book b, b1, b2;
    Date dummydate;

    public Computer() {
        manager = new BookManager();
    // dummyPreparation();
    }
   public void dummyPreparation(){
      //   Card.deleteAll(Card.class);
      //  Book.deleteAll(Book.class);
      //  dummydate = new Date();
      //   dummyBook();
      //  dummyCards();
   }
   public void addBookToDB(Book b){
      manager.addNewBookToDB(b);
   }
   public void addStudentToDB(Card c){
      manager.addNewStudentToDB(c);
   }
   public void deleteBookfromDB(Book b){ manager.removeBookfromDB(b); }
   public void deleteStudentfromDB(Card c){ manager.removeCardfromDB(c); }
   public void removeAllBookFromCard(Card c){ manager.removeBook(c); }

   public boolean studentReturnsBook(Card card , int index) {
       manager.removeBook(card , index);
       return true;
   }

    public boolean studentTakesBook(Card card , Book b){
         b.setBookExpiryDate(manager.getNewExpDate());
         boolean added = manager.addBooktoCard(card,String.valueOf( b.getISBNnum() ) );
         return added;
   }
    public List<Book> getAllBookinDB(){
        return manager.getListOfallBook();
    }
    public List<Card> getAllStudentinDB(){
       return manager.getListOfStudents();
    }

    public List<Book> getBooksOfStudent(Card c){
        return manager.getListOfBooksTakenByThisStudent(c);
    }
   public Card searchCard(String roll){
      return manager.getCardFromDB(roll);
   }
   public Book searchBook(int isbn){
      return manager.getAbookFromDB(String.valueOf(isbn));
   }

   public boolean checkIfUserIsPresentinLibrary(String roll){
      try {
         card = searchCard(roll);
         Log.i("tag","computer : repeated");
         return true;
      }catch(Exception e){
         Log.i("tag","computer: not present");
         return false;  }
   }
   public boolean checkIfBookIsPresentinLibrary(int isbn){
      try {
         b = searchBook(isbn);
         Log.i("tag","computer : repeated");
         return true;
      }catch(Exception e){
         Log.i("tag","computer: not present");
         return false;  }
   }
   public void dummyCards(){
      card = new Card("Rikrish Shrestha","THA074BEX030",dummydate);
    //  card.addNewBook(tostring(b.getISBNnum()) );
    //  card.addNewBook(tostring(b1.getISBNnum()) );
   //   card.addNewBook(tostring(b2.getISBNnum()) );
      card.save();
    //  Log.e("tag","saved");
      c2 = new Card("Mohan aryal","THA074BEX019",dummydate);
      c2.addNewBook("9800302");
      c2.save();
      c3 = new Card("Bruce lee","THA074BEX100",dummydate);
      c3.save();
   }
   public void dummyBook(){
      b = new Book("engineering maths II",450,dummydate,1,"MAT");
      b.save();
      b1 = new Book("Basic Electronics",150,dummydate,2,"ETX");
      b1.save();
      b2 = new Book("Digital Logic",450,dummydate ,3,"DRW");
      b2.save();
   }

}

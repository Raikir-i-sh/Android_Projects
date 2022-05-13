package trying.librarymanager.concretes;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.Date;

public class Book extends SugarRecord {
    private String name ;
   private int price;
   private int isbnNumber;
   private Date bookExpiryDate;
   private String category;

   @Ignore private boolean hasExpired=false;
   public Book(){  }
   public Book(String n, int p, Date bookExpDate, int isbn, String category) {
      this.name = n;
      this.price = p;
      this.bookExpiryDate = bookExpDate;
      this.isbnNumber =  isbn;
      this.category= category;
   }
   public int getISBNnum() {
      return isbnNumber;
   }

   public void setISBNnum(int ISBNnum) {
      this.isbnNumber = ISBNnum;
   }
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getPrice() {
      return price;
   }

   public void setPrice(int price) {
      this.price = price;
   }

   public Date getBookExpiryDate() {
      return bookExpiryDate;
   }

   public void setBookExpiryDate(Date bookExpiryDate) {
      this.bookExpiryDate = bookExpiryDate;
   }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String isExpired(){
         hasExpired= true;
         return "(Expired)";
    }

    public boolean hasExpired(){
      return hasExpired ;
    }
}


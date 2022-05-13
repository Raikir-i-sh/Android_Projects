package trying.librarymanager.concretes;

import java.util.Date;

;

public abstract class Checker{
    
     public abstract boolean IFlost();

     public boolean IFvalid(Date expiryDate){
          Date currentDate = new Date();
            boolean comparing = currentDate.before(expiryDate);
            // if currentDate is small than expiryDate then comparing = -ve.
            // if current date < expiryDate then card is valid.
            if(comparing) return true;
            else return false;
     }
   public boolean checkRollNum(String roll){
      if(roll.startsWith("THA") ){
         return true;
      }
      else return  false;
   }
}
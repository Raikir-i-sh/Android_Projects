package trying.librarymanager.concretes;

import trying.librarymanager.Computer;
public class Singletun {
   private static Computer computer = null;
  // private static final Singletun ourInstance = new Singletun();

   public static Computer getInstance() {
      if(computer == null){
         computer = new Computer();
         return computer;
      } else {
         return computer;
      }
   }

   private Singletun() {
   }
}

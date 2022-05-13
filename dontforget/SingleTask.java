package trying.textovoice.dontforget;

public class SingleTask {
   private String mtitle , mdescription;
   private long mtimerTime;

   /**
    * Constructor
    * @param title of single task
    * @param description of it.
    */
   public SingleTask(String title, String description,long timerTime){
      this.mtitle = title;
      this.mdescription = description;
      this.mtimerTime = timerTime;
   }
   public void setMtitle(String mtitle) {
      this.mtitle = mtitle;
   }

   public void setMdescription(String mdescription) {
      this.mdescription = mdescription;
   }

   public void setMtimerTime(long mtimerTime) {
      this.mtimerTime = mtimerTime;
   }



   public String getMtitle() {
      return mtitle;
   }

   public String getMdescription() {
      return mdescription;
   }

   public long getMtimerTime() {
      return mtimerTime;
   }
}

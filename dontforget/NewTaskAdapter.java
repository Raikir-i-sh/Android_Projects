package trying.textovoice.dontforget;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class NewTaskAdapter extends BaseAdapter {
   LayoutInflater mInFlator;
   private ArrayList<SingleTask> list = new ArrayList();
   // making list of counters related to text
   private HashMap<EditText,CountDownTimer> counters;
   private SingleTask currentTask;
   Context context;
   TexttoSpeechActivity textToSpeech;

   public NewTaskAdapter(@NonNull Context C, ArrayList<SingleTask> list) {
      this.list = list;
      this.context = C;
      this.counters = new HashMap<EditText, CountDownTimer>();
      textToSpeech = new TexttoSpeechActivity(this.context);
   }

   @Override
   public int getCount() {
      return list.size();
   }

   @Override
   public Object getItem(int i) {
      return list.get(i);
   }

   @Override
   public long getItemId(int i) {
      return i;
   }

   @Override
   public View getView(int i, View convertView, final ViewGroup parent) {
      final int j = i;
      View v = convertView;
      if(v == null) {
         mInFlator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

         v = mInFlator.inflate(R.layout.task_item, null);
      }
      Button mTitle = v.findViewById(R.id.mTextV);
      currentTask = list.get(i);
      mTitle.setText(currentTask.getMtitle());

      mTitle.setOnClickListener(  new OnClickListener() {
         @Override
         public void onClick(View view) {

            //if this line was kept outside of onClick then the list would not work as expected.
            currentTask = list.get(j);

            CustomDialogClass cdd =new CustomDialogClass(context,j);
            cdd.show();
            cdd.setTitle_Description(currentTask.getMtitle(), currentTask.getMdescription());
            if(cdd.dataChanged()){
               notifyDataSetChanged();
            }
         }
      });

      ImageButton playBtn = v.findViewById(R.id.mPlayBtn);
      final EditText timerSet = v.findViewById(R.id.mTimeTView);
      timerSet.setText( String.valueOf(currentTask.getMtimerTime()) );
      playBtn.setOnClickListener(new OnClickListener() {
         @Override
         public void onClick(View view) {
         long timerToSet=0; String t; boolean isStopped = false;
            currentTask = list.get(j);
              t = timerSet.getText().toString();
              if(!t.isEmpty()) {
            CountDownTimer cd = counters.get(timerSet);

          if(checkIfCountdownExist(timerSet,cd)) {
             //if countdown exists these code will run
             timerSet.setEnabled(true);
             timerToSet = currentTask.getMtimerTime();
             timerSet.setText(String.valueOf(timerToSet));
               }
              else {
                   timerToSet = Long.parseLong(t);
                   currentTask.setMtimerTime(timerToSet);
              countdown(timerSet, timerToSet * 1000 ,currentTask.getMdescription());
              }
         }
         }
      });
      return v;
   }
public void countdown(final EditText editTimer, final long timeToSet ,final String msg){
   CountDownTimer cdt = counters.get(editTimer);
   editTimer.setEnabled(false);

   final CountDownTimer finalCdt = cdt;
   cdt = new CountDownTimer(timeToSet, 1000) {
         @Override
         public void onTick(long millisUntilFinished) {
            int days = 0;
            int hours = 0;
            int minutes = 0;
            int seconds = 0;
            String sDate = "";

            //these methods show time in "00:00:00" hour/minutes/seconds format
            if(millisUntilFinished > DateUtils.DAY_IN_MILLIS) {
               days = (int) (millisUntilFinished / DateUtils.DAY_IN_MILLIS);
               sDate += days + "d";
            }

            millisUntilFinished -= (days * DateUtils.DAY_IN_MILLIS);

            if(millisUntilFinished > DateUtils.HOUR_IN_MILLIS) {
               hours = (int) (millisUntilFinished / DateUtils.HOUR_IN_MILLIS);
            }

            millisUntilFinished -= (hours * DateUtils.HOUR_IN_MILLIS);

            if(millisUntilFinished > DateUtils.MINUTE_IN_MILLIS) {
               minutes = (int) (millisUntilFinished / DateUtils.MINUTE_IN_MILLIS);
            }

            millisUntilFinished -= (minutes * DateUtils.MINUTE_IN_MILLIS);

            if(millisUntilFinished > DateUtils.SECOND_IN_MILLIS) {
               seconds = (int) (millisUntilFinished / DateUtils.SECOND_IN_MILLIS);
            }

            sDate += " " + String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
            editTimer.setText(sDate.trim());
         }

         @Override
         public void onFinish() {
            editTimer.setText(String.valueOf(timeToSet / 1000));
            textToSpeech.speakOut(msg);
            checkIfCountdownExist(editTimer, finalCdt);
            editTimer.setEnabled(true);
         }
      };

   counters.put(editTimer, cdt);
   cdt.start();
}
protected boolean checkIfCountdownExist(EditText eT, CountDownTimer cdt){
   if(cdt !=null){
         cdt.cancel();
         cdt = null;
         counters.put(eT , cdt);
         return true;
       }
       return false;
}
   public void cancelAllTimers()
   {
      Set<Entry<EditText, CountDownTimer>> s = counters.entrySet();
      Iterator it = s.iterator();
      while(it.hasNext())
      {
         try
         {
            Map.Entry pairs = (Map.Entry)it.next();
            CountDownTimer cdt = (CountDownTimer)pairs.getValue();

            cdt.cancel();
            cdt = null;
         }
         catch(Exception e){}
      }

      it=null;
      s=null;
      counters.clear();
      textToSpeech.killVoice();
   }
}

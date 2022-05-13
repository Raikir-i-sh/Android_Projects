package trying.textovoice.dontforget;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;
public class TexttoSpeechActivity implements TextToSpeech.OnInitListener {

// Called when the activity is first created.

   private TextToSpeech ttss;
   private Button btnSpeak;
   private EditText txtText;
   private Context c;
   public TexttoSpeechActivity(Context context) {
      c = context;
      ttss = new TextToSpeech(c, this);
   }

   // this method is for checking status.
   //this is a implemented method.
   @Override
   public void onInit(int status) {
      if (status == TextToSpeech.SUCCESS) {

         int result = ttss.setLanguage(Locale.US);

         if (result == TextToSpeech.LANG_MISSING_DATA
                 || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(c,"Language not supported", Toast.LENGTH_LONG).show();
         } else {
           // btnSpeak.setEnabled(true);
            //speakOut();
         }

      } else {
         Log.e("TTS", "Initilization Failed!");
      }
   }
   // android speaks out given text
   public void speakOut(String text) {
      ttss.speak(text, TextToSpeech.QUEUE_FLUSH, null);
   }
   public void killVoice(){
      ttss.stop();
      ttss.shutdown();
   }
}

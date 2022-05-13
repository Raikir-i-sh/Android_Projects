package trying.librarymanager.concretes;;import android.util.Log;
public class BookChecker extends  Checker{

    @Override
    public boolean IFlost() {
        Log.i("tag","Pay full fine of book");
        return true;
    }


}
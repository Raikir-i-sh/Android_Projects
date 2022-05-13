package trying.librarymanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.Frame.Builder;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class QRScanner {
     Context context;
     private Bitmap photo=null;
     private String data="";
     public QRScanner(Context c) {
                context = c;
                setupDetector();
     }
    private BarcodeDetector barcodeDetector;

     private void setupDetector() {
        barcodeDetector = new BarcodeDetector.Builder(context)
                .setBarcodeFormats(Barcode.CODE_39 | Barcode.QR_CODE | Barcode.EAN_13)
                .build();
        if(!barcodeDetector.isOperational()) {
           Toast.makeText(context, "Barcode scanner is not operational", Toast.LENGTH_SHORT).show();
        }
     }

     public void startScanning(Intent imageIntent) throws IOException {
        //get the photo
        try {
        Uri selectedImage = imageIntent.getData();
           photo = Media.getBitmap(context.getContentResolver(), selectedImage);
        } catch(OutOfMemoryError e) {
           // OutOfMemoryError was handled by adding android:largeheap= true in manifest file
           // error if could not get image
        }
        try {
           Frame frame = new Builder().setBitmap(photo).build();
           SparseArray<Barcode> barcodes = barcodeDetector.detect(frame);

            data = barcodes.valueAt(0).displayValue;
           if(data !=null) {
              if(checkIfQrCode(data)) {
                 data = data.split(",")[1];
              }
           }
        } catch(Exception e) {/* could not scan code */
            data = null;
        }
     }
         public boolean checkIfQrCode(String data){
               if(data.contains(",")){
                return true;
               }else return false;
             }

       public String getData(){ return data; }
    public Bitmap getPhoto(){ return photo; }
 }


package github.com.anurag145.helpdelhi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import fr.quentinklein.slt.LocationTracker;
import fr.quentinklein.slt.TrackerSettings;

public class Upload extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
     private Bitmap photo;
    private ProgressDialog Dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
    }
    public void getLocation()
    {
         class GetLocation extends AsyncTask<Void, Void,String>
        {     String s=null;

            @Override
            protected String doInBackground(Void... voids)throws SecurityException {

                try {
                    TrackerSettings settings= new TrackerSettings().setUseGPS(true)
                            .setUseNetwork(true)
                            .setUsePassive(true);
                    LocationTracker tracker = new LocationTracker(getApplicationContext(),settings) {
                        @Override
                        public void onLocationFound(@NonNull Location location) {

                             s=String.valueOf(location.getLatitude()+"\t"+location.getLongitude());

                        }

                        @Override
                        public void onTimeout() {

                        }
                    };
                    tracker.startListening();

            }catch(Exception e)
            {

            }
                return s;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Dialog=ProgressDialog.show(getApplicationContext(),"Fetching your Location....",null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Dialog.dismiss();
            }
        }
    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    //launch camera

    public void launchCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
             photo = (Bitmap) bundle.get("data");
            getLocation();
        }
    }
}
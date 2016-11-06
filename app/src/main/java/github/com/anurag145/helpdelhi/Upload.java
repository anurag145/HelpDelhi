package github.com.anurag145.helpdelhi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Hashtable;
import java.util.Map;

import fr.quentinklein.slt.LocationTracker;
import fr.quentinklein.slt.TrackerSettings;
import github.com.anurag145.helpdelhi.Helper.ImageToString;

public class Upload extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
     private Bitmap photo=null;
    private String s;
    private LocationTracker tracker;
    private ProgressDialog Dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        if(hasCamera()&&photo==null)
        {
            launchCamera();
        }else
        {
            Toast.makeText(getApplicationContext(),"This device does not have a camera",Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void sendData()
    {
         final Context context=getApplicationContext();

              tracker.stopListening();
        class postRequest extends AsyncTask<Void,Void,String>
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://sponsorhub.xyz/api/v1/products",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {

                                Log.d("fuckity fuck ",s);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                            Log.d("fuckity fuck fuck",volleyError.toString());
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        ImageToString ob = new ImageToString();

                        String image = ob.converterImage(photo);
                         Map<String,String> params = new Hashtable<String, String>();
                        params.put("name","Anurag");
                        params.put("description",s);
                        params.put("image", image);



                        return params;
                    }
                };

                //Creating a Request Queue
                RequestQueue requestQueue = Volley.newRequestQueue(context);
              Log.d("Fuck you ","I am here too");
                //Adding request to the queue

                requestQueue.add(stringRequest);
                return null;
            }
        }
        postRequest ob = new postRequest();
        ob.execute();

    }
    public void getLocation()throws SecurityException
    {


                try {
                    Log.d("Fuck it","I am here");

                    TrackerSettings settings= new TrackerSettings().setUseGPS(true)
                            .setUseNetwork(true)
                            .setUsePassive(true);
                      tracker = new LocationTracker(getApplicationContext(),settings) {

                        @Override
                        public void onLocationFound(@NonNull Location location) {

                             s=String.valueOf(location.getLatitude()+"\t"+location.getLongitude());

                            sendData();
                        }

                        @Override
                        public void onTimeout() {

                        }
                    };
                    tracker.startListening();

            }catch(Exception e)
                {
                    Log.d("fuck me",e.toString());
                }

    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    //launch camera

    public void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("Fuck","fuck here");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();

             photo = (Bitmap) bundle.get("data");
            getLocation();
        }
    }
}
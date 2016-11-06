package github.com.anurag145.helpdelhi;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class First_Screen extends AppCompatActivity {
 private RecyclerView Recyclerview;
    private int PERMISSION_CODE_1 = 23;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first__screen);
        if (Build.VERSION.SDK_INT >= 23) {

            if (ActivityCompat.checkSelfPermission(First_Screen.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestpermisions();
            } else {

            }
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://sponsorhub.xyz/api/v1/products",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d("hello",s);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                   Log.d("error : ",volleyError.toString());

                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
                queue.add(stringRequest);

    }
    public void requestpermisions() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE_1);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Intent intent = new Intent(this,Upload.class);
        startActivity(intent);
    }
}

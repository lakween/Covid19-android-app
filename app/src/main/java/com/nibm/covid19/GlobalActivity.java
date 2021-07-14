package com.nibm.covid19;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GlobalActivity extends AppCompatActivity {

    TextView todayDate, txtTot, txtTotNew, txtActiveCases, txtInHospital, txtRecover, txtDead;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);


        todayDate = findViewById(R.id.txtdate);
        txtTot = findViewById(R.id.txtTot);
        txtTotNew = findViewById(R.id.txtTotNew);
        txtActiveCases = findViewById(R.id.txtActive);
        txtInHospital = findViewById(R.id.txtinHos);
        txtRecover = findViewById(R.id.txtRec);
        txtDead = findViewById(R.id.txtDead);

        loadDataFromServer();


    }

    void loadDataFromServer() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://hpb.health.gov.lk/api/get-current-statistical";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject coronaDataObject = new JSONObject(response);
                    todayDate.setText(coronaDataObject.getJSONObject("data").getString("update_date_time"));
                    txtTot.setText(coronaDataObject.getJSONObject("data").getString("global_total_cases"));
                    txtTotNew.setText(coronaDataObject.getJSONObject("data").getString("total_pcr_testing_count"));
                    txtActiveCases.setText(coronaDataObject.getJSONObject("data").getString("global_new_cases"));
                    txtInHospital.setText(coronaDataObject.getJSONObject("data").getString("global_deaths"));
                    txtRecover.setText(coronaDataObject.getJSONObject("data").getString("global_recovered"));
                    txtDead.setText(coronaDataObject.getJSONObject("data").getString("global_new_deaths"));

                }catch (JSONException ex) {

                }


                pDialog.dismissWithAnimation();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismissWithAnimation();
            }
        });
        queue.add(request);
        //Log.e("Methord", "Methord Excuted");

    }
}
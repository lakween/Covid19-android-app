package com.nibm.covid19;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LocalActivity extends AppCompatActivity {


    TextView todayDate, txtTot, txtTotNew, txtActiveCases, txtInHospital, txtRecover, txtDead;
    CardView barChart, pieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);


        todayDate = findViewById(R.id.txtdate);
        txtTot = findViewById(R.id.txtTot);
        txtTotNew = findViewById(R.id.txtTotNew);
        txtActiveCases = findViewById(R.id.txtActive);
        txtInHospital = findViewById(R.id.txtinHos);
        txtRecover = findViewById(R.id.txtRec);
        txtDead = findViewById(R.id.txtDead);
        barChart = findViewById(R.id.localbarChart);
        pieChart = findViewById(R.id.localpiChart);

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
                    txtTot.setText(coronaDataObject.getJSONObject("data").getString("local_total_cases"));
                    txtTotNew.setText(coronaDataObject.getJSONObject("data").getString("local_active_cases"));
                    txtActiveCases.setText(coronaDataObject.getJSONObject("data").getString("local_new_cases"));
                    txtInHospital.setText(coronaDataObject.getJSONObject("data").getString("local_total_number_of_individuals_in_hospitals"));
                    txtRecover.setText(coronaDataObject.getJSONObject("data").getString("local_recovered"));
                    txtDead.setText(coronaDataObject.getJSONObject("data").getString("local_deaths"));

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

        barChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LocalActivity.this,barChart.class));
            }
        });

        pieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LocalActivity.this,pieChart.class));
            }
        });



    }
}

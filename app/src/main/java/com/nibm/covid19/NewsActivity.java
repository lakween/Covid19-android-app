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

import org.json.JSONArray;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class NewsActivity extends AppCompatActivity {
    TextView txtHeader, txtAthor, txtDate, txtBody;
    TextView txtHeader1, txtAthor1, txtDate1, txtBody1;
    TextView txtHeader2, txtAthor2, txtDate2, txtBody2;
    TextView txtHeader3, txtAthor3, txtDate3, txtBody3;
    TextView txtHeader4, txtAthor4, txtDate4, txtBody4;

    TextView txtToday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        txtHeader = findViewById(R.id.txtheader);
        txtAthor = findViewById(R.id.txtathor);
        txtDate = findViewById(R.id.txtpublish);
        txtBody = findViewById(R.id.txtbody);

        txtHeader1 = findViewById(R.id.txtheader1);
        txtAthor1 = findViewById(R.id.txtathor1);
        txtDate1 = findViewById(R.id.txtpublish1);
        txtBody1 = findViewById(R.id.txtbody1);

        txtHeader2 = findViewById(R.id.txtheader2);
        txtAthor2 = findViewById(R.id.txtathor2);
        txtDate2 = findViewById(R.id.txtpublish2);
        txtBody2 = findViewById(R.id.txtbody2);

        txtHeader3 = findViewById(R.id.txtheader3);
        txtAthor3 = findViewById(R.id.txtathor3);
        txtDate3 = findViewById(R.id.txtpublish3);
        txtBody3 = findViewById(R.id.txtbody3);

        txtHeader4 = findViewById(R.id.txtheader4);
        txtAthor4 = findViewById(R.id.txtathor4);
        txtDate4 = findViewById(R.id.txtpublish4);
        txtBody4 = findViewById(R.id.txtbody4);

        txtToday = findViewById(R.id.txtToday);

        loadDataFromServer();

    }



    void loadDataFromServer(){


        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        String url = "http://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=1ff5672f970b490a82113fab64ca60b0";

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject articleObject = new JSONObject(response);

                    JSONArray articleArray = articleObject.getJSONArray("articles");

                    txtHeader.setText(articleArray.getJSONObject(0).getString("title") );
                    txtAthor.setText(articleArray.getJSONObject(0).getString("author") );
                    txtDate.setText(articleArray.getJSONObject(0).getString("publishedAt") );
                    txtBody.setText(articleArray.getJSONObject(0).getString("description") );

                    txtHeader1.setText(articleArray.getJSONObject(1).getString("title") );
                    txtAthor1.setText(articleArray.getJSONObject(1).getString("author") );
                    txtDate1.setText(articleArray.getJSONObject(1).getString("publishedAt") );
                    txtBody1.setText(articleArray.getJSONObject(1).getString("description") );

                    txtHeader2.setText(articleArray.getJSONObject(2).getString("title") );
                    txtAthor2.setText(articleArray.getJSONObject(2).getString("author") );
                    txtDate2.setText(articleArray.getJSONObject(2).getString("publishedAt") );
                    txtBody2.setText(articleArray.getJSONObject(2).getString("description") );

                    txtHeader3.setText(articleArray.getJSONObject(3).getString("title") );
                    txtAthor3.setText(articleArray.getJSONObject(3).getString("author") );
                    txtDate3.setText(articleArray.getJSONObject(3).getString("publishedAt") );
                    txtBody3.setText(articleArray.getJSONObject(3).getString("description") );

                    txtHeader4.setText(articleArray.getJSONObject(8).getString("title") );
                    txtAthor4.setText(articleArray.getJSONObject(8).getString("author") );
                    txtDate4.setText(articleArray.getJSONObject(8).getString("publishedAt") );
                    txtBody4.setText(articleArray.getJSONObject(8).getString("description") );

                    txtToday.setText(articleArray.getJSONObject(0).getString("publishedAt"));



                }catch (Exception e){

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



    }

}
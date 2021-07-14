package com.nibm.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class pieChart extends AppCompatActivity {

    PieChart pieChart;
    ArrayList<PieEntry> pieEntries;
    ArrayList<chartDataAdapter> regionalSalesDataArrayList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        pieChart = findViewById(R.id.pieChart);
        pieEntries = new ArrayList<>();
        fillRegionalSalesArrayList();


        for (int i =0; i < regionalSalesDataArrayList.size();i++){
            String region = regionalSalesDataArrayList.get(i).getDate();
            int sales = regionalSalesDataArrayList.get(i).getCount();
            pieEntries.add(new PieEntry(sales,region));

        }


        PieDataSet pieDataSet = new PieDataSet(pieEntries,"District Distribution (Top 5)");
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setValueTextSize(16);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        Legend legend = pieChart.getLegend();
        legend.setTextSize(13);
        legend.setDrawInside(false);
        legend.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        legend.setWordWrapEnabled(true);

        pieChart.animateXY(2000,2000);
        pieChart.invalidate();
    }


    private void fillRegionalSalesArrayList(){

        regionalSalesDataArrayList.add(new chartDataAdapter("Colombo",150));
        regionalSalesDataArrayList.add(new chartDataAdapter("Gampaha",36));
        regionalSalesDataArrayList.add(new chartDataAdapter("Puttalam",35));
        regionalSalesDataArrayList.add(new chartDataAdapter("Kaluthara",34));
    }
}

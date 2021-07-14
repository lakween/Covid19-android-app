package com.nibm.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class barChart extends AppCompatActivity {

    BarChart barChart;
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String > lableName;

    ArrayList<chartDataAdapter> monthlySalesDataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        barChart = findViewById(R.id.barChart);
        barEntriesArrayList = new ArrayList<>();
        lableName  = new ArrayList<>();
        fillMonthlySalesArrayList();

        for (int i =0; i < monthlySalesDataArrayList.size();i++){
            String month = monthlySalesDataArrayList.get(i).getDate();
            int sales = monthlySalesDataArrayList.get(i).getCount();
            barEntriesArrayList.add(new BarEntry(i,sales));
            lableName.add(month);
        }

        BarDataSet barDataSet = new BarDataSet(barEntriesArrayList," Daily New Cases in Sri Lanka Last 14 Days");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Description description = new Description();
        description.setText("Months");
        barChart.setDescription(description);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(lableName));
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(lableName.size());
        xAxis.setLabelRotationAngle(270);
        barChart.animateY(2000);
        barChart.invalidate();

    }

    private void fillMonthlySalesArrayList(){

        monthlySalesDataArrayList.clear();
        monthlySalesDataArrayList.add(new chartDataAdapter("July 29",0));
        monthlySalesDataArrayList.add(new chartDataAdapter("July 30",4));
        monthlySalesDataArrayList.add(new chartDataAdapter("July 31",1));
        monthlySalesDataArrayList.add(new chartDataAdapter("Aug 1",0));
        monthlySalesDataArrayList.add(new chartDataAdapter("Aug 2",8));
        monthlySalesDataArrayList.add(new chartDataAdapter("Aug 3",5));
        monthlySalesDataArrayList.add(new chartDataAdapter("Aug 4",6));
        monthlySalesDataArrayList.add(new chartDataAdapter("Aug 5",5));
        monthlySalesDataArrayList.add(new chartDataAdapter("Aug 6",0));
        monthlySalesDataArrayList.add(new chartDataAdapter("Aug 7",0));
        monthlySalesDataArrayList.add(new chartDataAdapter("Aug 8",2));
        monthlySalesDataArrayList.add(new chartDataAdapter("Aug 9",3));
    }
}

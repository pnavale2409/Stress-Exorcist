package com.example.fy_project;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class DiaryResultActivity extends AppCompatActivity {

    String result_string ;

    TextView result_box;


    PieChart pieChart;
    PieChart pieChart2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_result);


        pieChart = findViewById(R.id.pie_chart);
        pieChart2 = findViewById(R.id.pie_chart2);
        result_box = findViewById(R.id.result_box);

        result_string = getIntent().getStringExtra("result");
        if (result_string.equals("0")){
            result_box.setText("Your mood look great today. Why stop here have a more better mental health by participating in many other activies.");
        }
        else {
            result_box.setText("Your mood dosen't look good today. Help yourself by participating in mental health improvement activies and have a great mental health.");
        }


        //Pie chart 1
        ArrayList<PieEntry> data2 = new ArrayList<>();
        data2.add(new PieEntry(5,"No Record"));
        data2.add(new PieEntry(12,"Positive"));
        data2.add(new PieEntry(13,"Negative"));
        PieDataSet pieDataSet = new PieDataSet(data2,"");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setText("30 Days Analysis Report");
        pieChart.setHoleRadius(0);
        pieChart.animate();


        //Pie chart 2
        ArrayList<PieEntry> data1 = new ArrayList<>();
        data1.add(new PieEntry(3,"No Record"));
        data1.add(new PieEntry(2,"Positive"));
        data1.add(new PieEntry(2,"Negative"));
        PieDataSet pieDataSet2 = new PieDataSet(data1,"");
        pieDataSet2.setColors(ColorTemplate.LIBERTY_COLORS);
        pieDataSet2.setValueTextColor(Color.WHITE);
        pieDataSet2.setValueTextSize(16f);

        PieData pieData2 = new PieData(pieDataSet2);

        pieChart2.setData(pieData2);
        pieChart2.getDescription().setText("7 Days Analysis Report");
        pieChart2.setHoleRadius(0);
        pieChart2.animate();
        pieChart2.getDescription().setTextColor(Color.WHITE);
        pieChart2.setEntryLabelColor(Color.WHITE);


    }
}
package com.group13.behealthy;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Weight_Progress extends AppCompatActivity {

    private LineGraphSeries<DataPoint> series1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        double x,y;
        x = 0;

        GraphView graph = (GraphView) findViewById(R.id.graph);
        series1 = new LineGraphSeries<>();


        int numDataPoints = 100;
        for(int i = 0; i <numDataPoints; i++) {
            x = x + 0.1;
            y = Math.sin(x);
            series1.appendData(new DataPoint(x,y), true, 100);
        }
        graph.addSeries(series1);

    }
}

package es.jescuderv.unex.facetrackernearbytfg.ui.views;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

import es.jescuderv.unex.facetrackernearbytfg.R;

public class LineChartView {

    private LineChart mChart;
    private Context mContext;

    public LineChartView(LineChart chart, Context context) {
        mChart = chart;
        mContext = context;
    }

    public void buildChart() {
        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);
        mChart.setDragDecelerationFrictionCoef(0.9f);

        mChart.getLegend().setTextColor(Color.WHITE);

        MyMarkerView markerView = new MyMarkerView(mContext, R.layout.custom_marker_view);
        markerView.setChartView(mChart);
        mChart.setMarker(markerView);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setGranularity(1f); // one hour


        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setAxisMaximum(120);
        leftAxis.setAxisMinimum(35);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setTextSize(10f);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    public void setValueFormatter(IAxisValueFormatter formatter){
        mChart.getXAxis().setValueFormatter(formatter);
    }

    public void setDataSet(ArrayList<Entry> entries, String label){
        LineDataSet dataSet = new LineDataSet(entries, label); // add entries to data set
        dataSet.setDrawValues(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setCubicIntensity(0.2f);
        dataSet.setDrawFilled(true);
        dataSet.setDrawCircles(false);
        dataSet.setLineWidth(1.8f);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setCircleRadius(2f);
        dataSet.setCircleColor(Color.WHITE);
        dataSet.setHighLightColor(R.color.colorPrimaryDark);
        dataSet.setColor(Color.WHITE);
        dataSet.setFillColor(Color.WHITE);
        dataSet.setFillAlpha(20);
        LineData lineData = new LineData(dataSet);
        mChart.setData(lineData);
        mChart.animateXY(1000, 1000);
        mChart.invalidate(); // refresh
    }


    private class MyMarkerView extends MarkerView {

        private final TextView tvContent;

        public MyMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);
            tvContent = findViewById(R.id.tvContent);
        }

        // runs every time the MarkerView is redrawn, can be used to update the
        // content (user-interface)
        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            tvContent.setText(String.valueOf((int) e.getY()));
            super.refreshContent(e, highlight);
        }

        @Override
        public MPPointF getOffset() {
            return new MPPointF(-(getWidth() / 2), -getHeight());
        }
    }

}

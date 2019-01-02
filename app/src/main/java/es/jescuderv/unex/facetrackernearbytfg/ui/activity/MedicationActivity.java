package es.jescuderv.unex.facetrackernearbytfg.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.jescuderv.unex.facetrackernearbytfg.R;
import es.jescuderv.unex.facetrackernearbytfg.ui.views.LineChartView;

public class MedicationActivity extends AppCompatActivity {

    @BindView(R.id.medication_diabetes_chart)
    LineChart mDiabetesChart;

    @BindView(R.id.medication_heartbeat_chart)
    LineChart mHeartbeatChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
        ButterKnife.bind(this);

        setUpDiabetesChart();
        setUpHeartbeatChart();

    }


    @OnClick(R.id.medication_close_button)
    public void onCloseClick() {
        super.onBackPressed();
    }


    private void setUpDiabetesChart() {
        LineChartView lineChartView = new LineChartView(mDiabetesChart, this);
        lineChartView.buildChart();
        lineChartView.setValueFormatter(new IAxisValueFormatter() {
            private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.getDefault());

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));
            }
        });

        // now in hours
        long to = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis());
        ArrayList<Entry> entries = new ArrayList<>();

        float from = to - 24;
        // increment by 1 hour
        for (float x = from; x < to; x++) {
            Random r = new Random();
            int low = 70;
            int high = 100;
            int result = r.nextInt(high - low) + low;

            entries.add(new Entry(x, result)); // add one entry per hour
        }
        lineChartView.setDataSet(entries, "Nivel de glucosa (mg/dL)");
    }

    private void setUpHeartbeatChart() {
        LineChartView lineChartView = new LineChartView(mHeartbeatChart, this);
        lineChartView.buildChart();
        lineChartView.setValueFormatter(new IAxisValueFormatter() {
            private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.getDefault());

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));
            }
        });

        // now in hours
        long to = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis());
        ArrayList<Entry> entries = new ArrayList<>();

        float from = to - 24;
        // increment by 1 hour
        for (float x = from; x < to; x++) {
            Random r = new Random();
            int low = 60;
            int high = 100;
            int result = r.nextInt(high - low) + low;

            entries.add(new Entry(x, result)); // add one entry per minute
        }
        lineChartView.setDataSet(entries, "Pulso cardíaco (p/m)");
    }

}

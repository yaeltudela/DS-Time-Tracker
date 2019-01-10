package com.dstimetracker.devsodin.ds_timetracker;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Activity that shows (only layout) how to generate reports for the tree.
 * It doesn't implements really the report generation.
 */
public class ReportGenerator extends AppCompatActivity {

    private EditText fromSelect_etxt, toSelect_etxt;
    private Spinner format_sp;
    private RadioButton shortType_rb, detailType_rb;

    private DateFormat dateFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_layout);
        dateFormat = new SimpleDateFormat("HH:mm dd-MM-yy");
        setViewControl();
        setDefaultValue();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setViewControl() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fromSelect_etxt = findViewById(R.id.fromSelect_etxt);
        toSelect_etxt = findViewById(R.id.toSelect_etxt);
        format_sp = findViewById(R.id.format_sp);
        shortType_rb = findViewById(R.id.shortType_rb);
        detailType_rb = findViewById(R.id.detailType_rb);
    }

    /**
     * method that set up the default text (hints) to the "from" and "to" fields.
     */
    private void setDefaultValue() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, -12);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        fromSelect_etxt.setText(dateFormat.format(calendar.getTime()));
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        toSelect_etxt.setText(dateFormat.format(calendar.getTime()));

        shortType_rb.setChecked(true);

        String[] arraySpinner = new String[]{"HTML", "TXT"};
        format_sp.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arraySpinner));
    }

    public void fromSelect_btn_onClick(View view) throws ParseException {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(dateFormat.parse(fromSelect_etxt.getText().toString()));
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                new TimePickerDialog(ReportGenerator.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, month);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        cal.set(Calendar.HOUR, hourOfDay);
                        cal.set(Calendar.MINUTE, minute);
                        fromSelect_etxt.setText(dateFormat.format(cal.getTime()));
                    }
                }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true).show();
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void toSelect_btn_onClick(View view) throws ParseException {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(dateFormat.parse(toSelect_etxt.getText().toString()));
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                new TimePickerDialog(ReportGenerator.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, month);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        cal.set(Calendar.HOUR, hourOfDay);
                        cal.set(Calendar.MINUTE, minute);
                        toSelect_etxt.setText(dateFormat.format(cal.getTime()));
                    }
                }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true).show();
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void generate_btn_onClick(View view) {
        generateReport();
    }

    /**
     * Method called when click on generateReport.
     * It creates an empty file and creates an intent expecting someone who can handle this kind of file.
     * Not generating the report because on handout says is not needed.
     */
    @SuppressLint("NewApi")
    public void generateReport() {
        try {
            String reportFormat = format_sp.getSelectedItem().toString();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            File file;
            Uri contentUri;
            switch (reportFormat) {
                case "TXT":
                    file = new File(getFilesDir() + "/report/report.txt");
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    file = new File(getFilesDir() + "/report/report.txt");
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    contentUri = FileProvider.getUriForFile(getApplicationContext(),
                            "com.dstimetracker.devsodin.ds_timetracker.fileprovider", file);
                    intent.setDataAndType(contentUri, "text/plain");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);
                    break;
                case "HTML":
                    file = new File(getFilesDir() + "/report/report.html");
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    contentUri = FileProvider.getUriForFile(getApplicationContext(),
                            "com.dstimetracker.devsodin.ds_timetracker.fileprovider", file);
                    intent.setDataAndType(contentUri, "text/html");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);

                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

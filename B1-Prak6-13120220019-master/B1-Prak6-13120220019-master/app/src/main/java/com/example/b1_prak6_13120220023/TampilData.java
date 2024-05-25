package com.example.b1_prak6_13120220023;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class TampilData extends AppCompatActivity {
    private TableLayout tblMhs;
    private TableRow tr;
    private TextView col1, col2, col3;
    private RestHelper restHelper;
    private String stb, nama, angkatan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tampil_data);

        restHelper = new RestHelper(this);
        tblMhs = findViewById(R.id.tbl_mhs);
        tampilData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void tampilTblMhs(ArrayList<Mahasiswa> arraListMhs){
        tblMhs.removeAllViews();

        tr = new TableRow(this);
        col1 = new TextView(this);
        col2 = new TextView(this);
        col3 = new TextView(this);

        col1.setText("Stambuk");
        col2.setText("Nama Mahasiswa");
        col3.setText("Angkatan");

        col1.setWidth(300);
        col2.setWidth(500);
        col3.setWidth(200);

        tr.addView(col1);
        tr.addView(col2);
        tr.addView(col3);

        tblMhs.addView(tr);

        for(final Mahasiswa mhs: arraListMhs){
            tr = new TableRow(this);
            col1 = new TextView(this);
            col2 = new TextView(this);
            col3 = new TextView(this);

            col1.setText(mhs.getStb());
            col2.setText(mhs.getNama());
            col3.setText(String.valueOf(mhs.getAngkatan()));

            col1.setWidth(300);
            col2.setWidth(500);
            col3.setWidth(200);

            tr.addView(col1);
            tr.addView(col2);
            tr.addView(col3);

            tblMhs.addView(tr);

            tr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i<tblMhs.getChildCount(); i++){
                        stb = mhs.getNama();
                        nama = mhs.getNama();
                        angkatan = String.valueOf(mhs.getAngkatan());
                        if (tblMhs.getChildAt(i) == view)
                            tblMhs.getChildAt(i).setBackgroundColor(Color.LTGRAY);
                        else
                            tblMhs.getChildAt(i).setBackgroundColor(Color.WHITE);
                    }
                }
            });
        }
    }

    private void tampilData(){
        restHelper.getDataMhs(new RestCallbackMahasiswa() {
            @Override
            public void requestDataMhsSucces(ArrayList<Mahasiswa> arrayList) {
                tampilTblMhs(arrayList);
            }
        });
    }

    public void btnEditClick(View view){
        Intent intent = new Intent();
        intent.putExtra("stb", stb);
        intent.putExtra("nama", nama);
        intent.putExtra("angkatan", angkatan);
        setResult(1, intent);
        finish();
    }

    public void btnHapusClick(View view){
        if (stb == null) return;
        restHelper.hapusData(stb, new RestCallbackMahasiswa() {
            @Override
            public void requestDataMhsSucces(ArrayList<Mahasiswa> arrayList) {
                tampilTblMhs(arrayList);
            }
        });
    }

}
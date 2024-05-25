package com.example.b1_prak6_13120220023;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private EditText txtStb, txtNama, txtAngkatan;
    private RestHelper restHelper;
    private Mahasiswa mhs;
    private Intent intentEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        restHelper = new RestHelper(this);
        intentEdit = null;

        txtStb = findViewById(R.id.txt_Stambuk);
        txtNama = findViewById(R.id.txt_nama);
        txtAngkatan = findViewById(R.id.txt_angkatan);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void clearData(){
        txtStb.setText("");
        txtNama.setText("");
        txtAngkatan.setText("");
        intentEdit = null;
        txtStb.requestFocus();
    }

    public void btnSimpanClick(View view){
        mhs = new Mahasiswa(
                txtStb.getText().toString(),
                txtNama.getText().toString(),
                Integer.parseInt(txtAngkatan.getText().toString())
        );
        try{
            if (intentEdit == null)
                restHelper.insertData(mhs.toJSON());
            else
                restHelper.editData(intentEdit.getStringExtra("stb"), new Mahasiswa(
                        txtStb.getText().toString(),
                        txtNama.getText().toString(),
                        Integer.parseInt(txtAngkatan.getText().toString())
                ));
        }catch (JSONException e){
            e.printStackTrace();
        }
        clearData();
    }

    public void btnTampilDataClick(View view){
        intentEdit = null;
        Intent intent = new Intent(this,TampilData.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            intentEdit = data;
            txtStb.setText(data.getStringExtra("stb"));
            txtNama.setText(data.getStringExtra("nama"));
            txtAngkatan.setText(data.getStringExtra("angkatan"));
        }
    }
}
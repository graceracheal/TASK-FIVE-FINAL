package com.example.taskfive;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MyBottomSheetFragment.BottomSheetListener {

    private TextView resultTextView;
    private Button openBottomSheetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = findViewById(R.id.inputField);
        openBottomSheetButton = findViewById(R.id.openBottomSheetButton);

        openBottomSheetButton.setOnClickListener(v -> showBottomSheet());
    }

    private void showBottomSheet() {
        MyBottomSheetFragment bottomSheet = new MyBottomSheetFragment();
        bottomSheet.show(getSupportFragmentManager(), "MyBottomSheet");
    }

    @Override
    public void onDataSent(String data) {
        resultTextView.setText("Received: " + data);
        Toast.makeText(this, "Data received: " + data, Toast.LENGTH_SHORT).show();
    }
}

package com.example.taskfive;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class MyBottomSheetFragment extends BottomSheetDialogFragment {

    public interface BottomSheetListener {
        void onDataSent(String data);
    }

    private BottomSheetListener mListener;
    private EditText editText;
    private Button submitButton, cancelButton;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<String> itemList = new ArrayList<>();


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BottomSheetListener) {
            mListener = (BottomSheetListener) context;
        } else {
            throw new RuntimeException(context + " must implement BottomSheetListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);


        editText = view.findViewById(R.id.inputField);
        submitButton = view.findViewById(R.id.submitButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        recyclerView = view.findViewById(R.id.recyclerView);

        setupRecyclerView();
        setupButtons();

        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        itemList.add("Pizza");
        itemList.add("Burgers");
        itemList.add("Sushi");
        itemList.add("Salads");

        itemAdapter = new ItemAdapter(itemList, item ->
                Toast.makeText(getContext(), "Clicked: " + item, Toast.LENGTH_SHORT).show()
        );
        recyclerView.setAdapter(itemAdapter);


        DividerItemDecoration divider = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(divider);
    }


    private void setupButtons() {
        submitButton.setOnClickListener(v -> {
            Log.d("MyBottomSheet", "Submit Button Clicked");
            addNewItem();
        });

        cancelButton.setOnClickListener(v -> {
            Log.d("MyBottomSheet", "Cancel Button Clicked");
            dismiss();
        });
    }

    private void addNewItem() {
        String inputText = editText.getText().toString().trim();

        if (!inputText.isEmpty()) {
            itemList.add(inputText);
            itemAdapter.notifyDataSetChanged();

            if (mListener != null) {
                mListener.onDataSent(inputText);
            }

            Toast.makeText(getContext(), "Added: " + inputText, Toast.LENGTH_SHORT).show();
            editText.setText("");
        } else {
            Toast.makeText(getContext(), "Please enter some text", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}

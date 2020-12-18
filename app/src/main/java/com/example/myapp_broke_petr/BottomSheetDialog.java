package com.example.myapp_broke_petr;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    //private BottomSheetListener listener;

    Button btnConfirm;
    Button btnCancel;
    Button btnClear;


    //inflat the we defined for bottom sheet dialogue
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnConfirm = view.findViewById(R.id.btnConfirm);
        btnClear = view.findViewWithTag(R.id.btnClear);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //listener.onButtonClicked("btnConform");
                dismiss();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //listener.onButtonClicked("btnCancel");
                Toast.makeText(getContext(), "Please fill alll fields", Toast.LENGTH_LONG).show();
                dismiss();

            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //listener.onButtonClicked("btnClear");
                dismiss();

            }
        });


    }
}



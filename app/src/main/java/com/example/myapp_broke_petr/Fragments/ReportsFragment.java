package com.example.myapp_broke_petr.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapp_broke_petr.R;
import com.example.myapp_broke_petr.Transaction;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReportsFragment extends Fragment {


    TextView tvLogged;
    int item_count=0; //total number of logged transactions
    private float[] yData={};
    private String[] xData={"Home and Utilities","Entertainment","Food","Subscriptions","Miscellaneous"};
    BarChart barChart; //bar chart for spending in different categories

    //firebase componenets
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    FirebaseAuth mAuth;
    FirebaseUser curr_user;



    HashMap<String, Float> calculations=new HashMap<String,Float>();
    public ReportsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reports, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvLogged=view.findViewById(R.id.tvLogged);


        barChart= (BarChart) view.findViewById(R.id.pcSpendCat);

        //intialize categories for calculation
        calculations.put("Home and Utilities", (float) 0.0);
        calculations.put("Total", (float) 0.0);
        calculations.put("Entertainment", (float) 0.0);
        calculations.put("Food", (float) 0.0);
        calculations.put("Subscriptions", (float) 0.0);
        calculations.put("Miscellaneous", (float) 0.0);


        //setting up firebase
        mAuth = FirebaseAuth.getInstance();
        curr_user=mAuth.getCurrentUser();
        database= FirebaseDatabase.getInstance();
        myRef=database.getReference(curr_user.getUid()).child("transactions");

        //getting information form firebase for the pie chart
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d: snapshot.getChildren())
                {
                    item_count++;
                    Transaction t= d.getValue(Transaction.class); //make value a transaction object
                    //update total amount spent in category
                    String key=t.getCategory();
                    calculations.put(key,calculations.get(key)+(float)t.getAmount());
                    calculations.put("Total",calculations.get(key)+(float)t.getAmount());


                }
                String count=item_count+ " times.";
                tvLogged.setText(count);
                addDataSet(barChart);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("Calculation", String.valueOf(calculations));






    }

    private void addDataSet(BarChart barChart) {
        ArrayList<PieEntry> yPoints= new ArrayList<>();
        ArrayList<String> xPoints=new ArrayList<>();

        List<BarEntry> entries=new ArrayList<>();


        for(int i=0; i<xData.length;i++)
        {
            //xPoints.add(xData[i]);
            float frac=(float)(calculations.get(xData[i])/calculations.get("Total"));
            Log.d("Fracs", String.valueOf(frac));
            //yPoints.add(new PieEntry(frac,i));
            entries.add(new BarEntry((float)i,frac));

        }

        //create dataset
        BarDataSet barDataSet= new BarDataSet(entries,"BarDataSet");
        //set the data
        BarData data=new BarData(barDataSet);
        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.invalidate();







    }
}
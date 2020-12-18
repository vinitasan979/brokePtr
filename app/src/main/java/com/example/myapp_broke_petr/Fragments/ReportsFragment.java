package com.example.myapp_broke_petr.Fragments;

import android.content.Context;
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
import android.widget.Toast;

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

    Context context;

    TextView tvLogged;
    int item_count=0; //total number of logged transactions
    private String[] xData={"Home and Utilities","Entertainment","Food","Subscriptions","Miscellaneous"};
    //BarChart barChart; //bar chart for spending in different categories

    PieChart pieChart; //pie chart contain spending in each category.


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
        context=getContext();
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


        //intialize categories for calculation
        calculations.put("Home and Utilities", (float) 0.0);
        calculations.put("Total", (float) 0.0);
        calculations.put("Entertainment", (float) 0.0);
        calculations.put("Food", (float) 0.0);
        calculations.put("Subscriptions", (float) 0.0);
        calculations.put("Miscellaneous", (float) 0.0);

        //set up pie chart
        pieChart= (PieChart) view.findViewById(R.id.pcSpendCat);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(35f);
        pieChart.setCenterText("Spending in Each Category");
        pieChart.setCenterTextSize(10);



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

                    //add up amount of each category and also total amount
                    calculations.put(key,calculations.get(key)+(float)t.getAmount());
                    calculations.put("Total",calculations.get(key)+(float)t.getAmount());


                }

                //set text for total logged transactions
                String count=item_count+ " times.";
                tvLogged.setText(count);
                addDataSet(pieChart);

                //create listener so user can get more information when clicked
                pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {
                        //entry contains a string of format Entry x: xvalues ycd: yvalue

                        Log.d("ENtry e",e.toString());
                        int pos=e.toString().indexOf("y: ");
                        //skip y: and get only the value
                        String amt=e.toString().substring((pos+3));
                        //fall back, so no outbounds error
                        pos=0;

                        //look for respective category
                        for (int j=0;j<xData.length;j++){
                            if(calculations.get(xData[j])== Float.parseFloat(amt))
                            {
                                Log.d("Positions","found");
                                pos=j;
                                break;
                            }
                        }
                        Log.d("Positions", String.valueOf(pos));

                        String cat=xData[pos];

                        //Display toast
                        String displayMsg="Category: "+cat+"\n";
                        Toast.makeText(context,displayMsg,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected() {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("Calculation", String.valueOf(calculations));







    }

    private void addDataSet(PieChart pieChart) {
        ArrayList<PieEntry> yPoints= new ArrayList<>();

        //init yPoints with data type Pie entry made from the calculations
        for(int i=0; i<xData.length;i++)
        {

            float frac=(float)(calculations.get(xData[i])/calculations.get("Total"));
            Log.d("Fracs", String.valueOf(frac));
            yPoints.add(new PieEntry(frac,i));

            //update cal
            calculations.put(xData[i],frac);

        }

        //create dataset
        PieDataSet pieDataSet=new PieDataSet(yPoints,"PieDataSet");
        pieDataSet.setSliceSpace(3);
        pieDataSet.setValueTextSize(8);

        //set the colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.MAGENTA);
        colors.add(Color.LTGRAY);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);

        pieDataSet.setColors(colors);

        //create pie object
        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();









    }
}
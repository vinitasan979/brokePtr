package com.example.myapp_broke_petr.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.myapp_broke_petr.R;
import com.example.myapp_broke_petr.Transaction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DataFragment extends Fragment {


    TextView tvHeading;
    RecyclerView rvItems;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    FirebaseAuth mAuth;
    FirebaseUser curr_user;

    private ArrayList<Transaction> allPurchases; //list of all purchases by user
    private RecyclerView.Adapter adapter;

    public DataFragment() {
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
        return inflater.inflate(R.layout.fragment_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeading=view.findViewById(R.id.tvHeading);
        //get arguments passed from TransFragment
        Bundle bundle=this.getArguments();
        String cat=bundle.getString("category");

        tvHeading.setText(cat);

        rvItems=view.findViewById(R.id.rvItems);
        allPurchases= new ArrayList<Transaction>();

        //initialize firebase components
        mAuth = FirebaseAuth.getInstance();
        curr_user=mAuth.getCurrentUser();
        database= FirebaseDatabase.getInstance();
        myRef=database.getReference(curr_user.getUid()).child("transactions");

        //Loop through all transaction
        //get current snapshot to have updated values
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allPurchases.clear(); //restart data to get most recent data
                for (DataSnapshot)
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })





    }
}
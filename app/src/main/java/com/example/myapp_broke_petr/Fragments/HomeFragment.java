package com.example.myapp_broke_petr.Fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapp_broke_petr.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class HomeFragment extends Fragment {

    TextView tvDisplayDate;
    TextView tvDisplayName;
    Button btnGoAdd;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    FirebaseAuth mAuth;
    FirebaseUser curr_user;

    Date today;



    public HomeFragment() {
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
        return inflater.inflate(R.layout.fragment_home2, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Connect varibles to UI components
        tvDisplayDate=view.findViewById(R.id.tvDisplayDate);
        tvDisplayName=view.findViewById(R.id.tvName);
        btnGoAdd=view.findViewById(R.id.btngoAdd);


        //initialize firebase components
        mAuth = FirebaseAuth.getInstance();
        curr_user=mAuth.getCurrentUser();
        database= FirebaseDatabase.getInstance();
        myRef=database.getReference(curr_user.getUid());

        //Get Name from Database and display to the user
        myRef.child("first_name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fullname=(String)snapshot.getValue();
                //bind it to the textview
                tvDisplayName.setText(fullname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        //Display todays date
        SimpleDateFormat formatter= new SimpleDateFormat("dd MMMM yyyy");
        Date now=new Date();
        String strDate=formatter.format(now);
        tvDisplayDate.setText(strDate);


        //when button click, send user to add Data fragment
        btnGoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddItemFragment addItemFragment= new AddItemFragment();
                //switch fragments by replacing the container contents
                FragmentManager manager=getFragmentManager();
                manager.beginTransaction().replace(R.id.flContainer, addItemFragment).commit();

            }
        });

    }
}
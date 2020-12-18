package com.example.myapp_broke_petr.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.myapp_broke_petr.Adapters.TransAdapter;
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


    Context context;
    TextView tvHeading;
    RecyclerView rvItems;
    String selectedCat;
    ImageButton btnBack;

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
        context=getContext();
        return inflater.inflate(R.layout.fragment_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeading=view.findViewById(R.id.tvHeading);
        //get arguments passed from TransFragment
        Bundle bundle=this.getArguments();
        selectedCat=bundle.getString("category");

        tvHeading.setText(selectedCat);

        //Set up back button
        btnBack=view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go back to View Trans Fragment
                //switch fragments
                ViewTransFragment viewTransFragment= new ViewTransFragment();

                FragmentManager manager=getFragmentManager();
                manager.beginTransaction().replace(R.id.flContainer, viewTransFragment).commit();
            }
        });

        //Set up Recycler view components
        rvItems=view.findViewById(R.id.rvItems);
        allPurchases= new ArrayList<Transaction>();
        //Set a linear Layout inside the recycler view
        //Items will appear one after another linearly
        rvItems.setLayoutManager(new LinearLayoutManager(context));
        //add space between each item
        rvItems.addItemDecoration(new DividerItemDecoration(rvItems.getContext(),DividerItemDecoration.VERTICAL));

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
                for (DataSnapshot d: snapshot.getChildren())
                {

                    Transaction t= d.getValue(Transaction.class); //make value a transaction object
                    //Create the list allPurchases based on the selected category
                    if(selectedCat.equals("all") || selectedCat.equals(t.getCategory()))
                    {
                        allPurchases.add(t);
                    }
                }
                //create adapter with data from the firebase date
                adapter= new TransAdapter(context,allPurchases);
                //set adapter to recycler view
                rvItems.setAdapter(adapter);
                //refresh recycler view
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
}
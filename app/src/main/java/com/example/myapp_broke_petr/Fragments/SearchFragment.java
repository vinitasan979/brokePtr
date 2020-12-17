package com.example.myapp_broke_petr.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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


public class SearchFragment extends Fragment {

    //UI components
    Context context;
    EditText etSearchInput;
    ImageButton ibSearch;
    RecyclerView rvSearchItems;


    private FirebaseDatabase database;
    private DatabaseReference myRef;
    FirebaseAuth mAuth;
    FirebaseUser curr_user;

    private ArrayList<Transaction> searchResults; //list of all purchases by user
    private RecyclerView.Adapter adapter;




    public SearchFragment() {
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //connect components to varibles
        etSearchInput=view.findViewById(R.id.etSearchInput);
        ibSearch=view.findViewById(R.id.ibSearch);

        //Set up Recycler view components
        rvSearchItems=view.findViewById(R.id.rvSearch);
        searchResults= new ArrayList<Transaction>();
        rvSearchItems.setLayoutManager(new LinearLayoutManager(context));
        rvSearchItems.addItemDecoration(new DividerItemDecoration(rvSearchItems.getContext(),DividerItemDecoration.VERTICAL));

        //initialize firebase components
        mAuth = FirebaseAuth.getInstance();
        curr_user=mAuth.getCurrentUser();
        database= FirebaseDatabase.getInstance();
        myRef=database.getReference(curr_user.getUid()).child("transactions");

        //when button is clicked, read through transactions and find products that match the result
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get String that needs to be search
                //convert to lowercase so search is not case sensitive
                final String searchString=etSearchInput.getText().toString().toLowerCase();

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        searchResults.clear(); //restart data to get most recent data
                        for (DataSnapshot d: snapshot.getChildren())
                        {

                            Transaction t= d.getValue(Transaction.class); //make value a transaction object
                            //Create the list allPurchases based on the selected category
                            if(searchString.equals(t.getProduct().toLowerCase()))
                            {
                                searchResults.add(t);
                            }
                        }
                        adapter= new TransAdapter(context,searchResults);
                        rvSearchItems.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        if(searchResults.size()==0)
                        {
                            Toast.makeText(context, "No Results Found", Toast.LENGTH_LONG).show();
                            etSearchInput.setText("");

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

        });


    }
});
    }
}
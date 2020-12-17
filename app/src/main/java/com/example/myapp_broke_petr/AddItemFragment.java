package com.example.myapp_broke_petr;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AddItemFragment extends Fragment {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    FirebaseAuth mAuth;
    FirebaseUser curr_user;

    Context context;
    Button btnAdd;
    EditText etProduct;
    EditText etAmount;
    String product;
    Float amount;

    //Transaction Date
    TextView etDate;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    Date date;

    //Spinner components
    Spinner opExpense;
    String category;




    public AddItemFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=container.getContext();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //getting current instance of database
        mAuth = FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();

        btnAdd=view.findViewById(R.id.btnAdd);
        // get product name and amount
        etProduct=view.findViewById(R.id.etProduct);
        etAmount=view.findViewById(R.id.etAmount);



        //get category selected
        opExpense=view.findViewById(R.id.opCat);
        //add options to the spinner
        // options for spinner created in resource
        //connect array to current spiner using an array adapter
        ArrayAdapter<CharSequence> opadapter= ArrayAdapter.createFromResource(context,R.array.expenses,android.R.layout.simple_spinner_item);
        opadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        opExpense.setAdapter(opadapter);
        opExpense.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category= adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //picking the date
        etDate=view.findViewById(R.id.etDate);
        //when date object is selected display the date picker
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog= new DatePickerDialog(context, android.R.style.Theme_Black, onDateSetListener, year,month,day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();


            }
        });

        //get selected date
        onDateSetListener= new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                //months start from 0; Jan=0
                m=m+1;
                String strdate= m+"/"+ d +"/"+ y;
                //display user chosen date
                etDate.setText(strdate);

                //create a date object from the string
                SimpleDateFormat formatter = new SimpleDateFormat("M/dd/yyyy",Locale.ENGLISH);
                try {
                    date=formatter.parse(strdate);
                    Log.d("Date picker",date.toString());

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        };

        //when button is clicked, add current transaction to the database
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get user inputs
                product=etProduct.getText().toString();
                amount=Float.parseFloat(etAmount.getText().toString());

                //add transcation under logged in user
                curr_user=mAuth.getCurrentUser();
                myRef=database.getReference(curr_user.getUid()).child("transactions");
                //create a new key for the transaction
                String keyid=myRef.push().getKey();
                //create new transaction object
                Transaction transaction= new Transaction(date,product,category,amount);
                //set this object as the value of the key created
                myRef.child(keyid).setValue(transaction);


            }
        });

    }
    public void openDialog(){}

}
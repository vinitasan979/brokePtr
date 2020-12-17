package com.example.myapp_broke_petr.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.myapp_broke_petr.Fragments.DataFragment;
import com.example.myapp_broke_petr.R;

public class ViewTransFragment extends Fragment {

    RelativeLayout rlaLL;
    RelativeLayout rlUtil;
    RelativeLayout rlEntertain;
    RelativeLayout rlFood;
    RelativeLayout rlSub;
    RelativeLayout rlMis;




    public ViewTransFragment() {
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
        return inflater.inflate(R.layout.fragment_view_trans, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //connect components
        rlaLL=view.findViewById(R.id.layouthome);
        rlEntertain=view.findViewById(R.id.rlEntertainment);
        rlUtil=view.findViewById(R.id.rlUtils);
        rlFood=view.findViewById(R.id.rlFood);
        rlSub=view.findViewById(R.id.rlSub);
        rlMis=view.findViewById(R.id.rlMis);



        //The user can click on each layout to be taken to its respective fragment
        rlaLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataFragment dataFragment= new DataFragment();

                //pass data on to the frag to tell which category was selected
                Bundle bundle= new Bundle();
                bundle.putString("category","all");
                dataFragment.setArguments(bundle);
                //switch fragments
                FragmentManager manager=getFragmentManager();
                manager.beginTransaction().replace(R.id.flContainer, dataFragment).commit();
            }
        });

        rlUtil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataFragment dataFragment= new DataFragment();

                //pass data on to the frag to tell which category was selected
                Bundle bundle= new Bundle();
                bundle.putString("category","Home and Utilities");
                dataFragment.setArguments(bundle);
                //switch fragments
                FragmentManager manager=getFragmentManager();
                manager.beginTransaction().replace(R.id.flContainer, dataFragment).commit();
            }
        });

        rlEntertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataFragment dataFragment= new DataFragment();

                //pass data on to the frag to tell which category was selected
                Bundle bundle= new Bundle();
                bundle.putString("category","Entertainment");
                dataFragment.setArguments(bundle);
                //switch fragments
                FragmentManager manager=getFragmentManager();
                manager.beginTransaction().replace(R.id.flContainer, dataFragment).commit();
            }
        });

        rlFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataFragment dataFragment= new DataFragment();

                //pass data on to the frag to tell which category was selected
                Bundle bundle= new Bundle();
                bundle.putString("category","Food");
                dataFragment.setArguments(bundle);
                //switch fragments
                FragmentManager manager=getFragmentManager();
                manager.beginTransaction().replace(R.id.flContainer, dataFragment).commit();
            }
        });

        rlSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataFragment dataFragment= new DataFragment();

                //pass data on to the frag to tell which category was selected
                Bundle bundle= new Bundle();
                bundle.putString("category","Subscriptions");
                dataFragment.setArguments(bundle);
                //switch fragments
                FragmentManager manager=getFragmentManager();
                manager.beginTransaction().replace(R.id.flContainer, dataFragment).commit();
            }
        });

        rlMis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataFragment dataFragment= new DataFragment();

                //pass data on to the frag to tell which category was selected
                Bundle bundle= new Bundle();
                bundle.putString("category","Miscellaneous");
                dataFragment.setArguments(bundle);
                //switch fragments
                FragmentManager manager=getFragmentManager();
                manager.beginTransaction().replace(R.id.flContainer, dataFragment).commit();
            }
        });



    }
}
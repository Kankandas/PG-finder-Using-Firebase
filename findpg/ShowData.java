package com.example.kankan.findpg;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowData extends Fragment implements SearchView.OnQueryTextListener {

    private SearchView search;
    private RecyclerView reView;
    private DatabaseReference mDatabase;
    private ArrayList<MyRoom> myRooms;
    private SwipeRefreshLayout swipe;
    private MyAdapter myAdapter;

    public ShowData() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_show_data, container, false);

        search=(SearchView)view.findViewById(R.id.search);
        swipe=(SwipeRefreshLayout)view.findViewById(R.id.swipe);
        reView=(RecyclerView)view.findViewById(R.id.reView);
        myRooms=new ArrayList<>();
        search.setOnQueryTextListener(this);
        reView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //refreshTheList();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                    refreshTheList();
            }
        });
        return view;
    }


    @Override
    public void onResume() {
        refreshTheList();
        super.onResume();
    }

    public void refreshTheList()
    {
        swipe.setRefreshing(true);
        mDatabase= FirebaseDatabase.getInstance().getReferenceFromUrl("https://findpg-6492d.firebaseio.com/");
        myRooms.clear();
        mDatabase.child("Final Rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot kankan:dataSnapshot.getChildren())
                {
                    MyRoom myRoom=kankan.getValue(MyRoom.class);
                    myRooms.add(myRoom);
                }

                myAdapter = new MyAdapter(getActivity(), myRooms);
                reView.setAdapter(myAdapter);
                swipe.setRefreshing(false);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        List<MyRoom> filterList=filter(myRooms,newText);
        myAdapter=new MyAdapter(getActivity(),filterList);
        reView.setAdapter(myAdapter);
        //swipe.setRefreshing(false);

        return true;
    }

    private ArrayList<MyRoom> filter(ArrayList<MyRoom> mainList,String query)
    {
        //query=query.toLowerCase();

        ArrayList<MyRoom> filterModeList=new ArrayList<>();

        for(MyRoom iter:mainList)
        {
            String text=iter.getPicode();
            if(text.startsWith(query))
            {
                filterModeList.add(iter);
            }
        }
        return filterModeList;

    }
}

package com.example.appdistancia.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appdistancia.Model.Distance;
import com.example.appdistancia.Model.Project;
import com.example.appdistancia.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewDistances extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    TableLayout table;
    ListView objectAListView;
    ListView distancesListView;
    ListView objectBListView;

    ArrayAdapter objectAAdapter;
    ArrayAdapter distanceAdapter;
    ArrayAdapter objectBAdapter;

    String projectId;
    private List<String> listObjectA = new ArrayList<String>();
    private List<Double> listDistances = new ArrayList<Double>();
    private List<String> listObjectB = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdistances);

        table = findViewById(R.id.tableLayout1);
        objectAListView = findViewById(R.id.listViewObjectA);
        distancesListView = findViewById(R.id.listViewDistance);
        objectBListView = findViewById(R.id.listViewObjectB);

        initializeFirebase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            projectId = extras.getString("projectId");
        }

        listarDistances();
    }

    public void initializeFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void crearDistancia(View v){
        Intent i = new Intent(ViewDistances.this, AddDistanceActivity.class);
        i.putExtra("projectId",projectId);
        startActivity(i);
    }

    public void listarDistances(){
        databaseReference.child("Distances").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listObjectA.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Distance d = objSnapshot.getValue(Distance.class);
                    //Toast.makeText(ViewDistances.this, "" + d.getIdProject(), Toast.LENGTH_LONG).show();
                    if(projectId.equals(d.getIdProject())) {
                        //Toast.makeText(ViewDistances.this, ""+ d.getIdProject(), Toast.LENGTH_LONG).show();
                        listObjectA.add(d.getObjectA());
                        listDistances.add(d.getDistance());
                        listObjectB.add(d.getObjectB());

                        objectAAdapter = new ArrayAdapter<String>(ViewDistances.this, android.R.layout.simple_list_item_1, listObjectA);
                        objectAListView.setAdapter(objectAAdapter);

                        distanceAdapter = new ArrayAdapter<Double>(ViewDistances.this, android.R.layout.simple_list_item_1, listDistances);
                        distancesListView.setAdapter(distanceAdapter);

                        objectBAdapter = new ArrayAdapter<String>(ViewDistances.this, android.R.layout.simple_list_item_1, listObjectB);
                        objectBListView.setAdapter(objectBAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

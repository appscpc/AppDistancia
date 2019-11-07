package com.example.appdistancia.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

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
import java.util.Collections;
import java.util.List;

import static java.lang.Double.parseDouble;

public class BestRouteActivity extends AppCompatActivity {
    String projectId;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    static int cont;

    ArrayList<Double> list;

    TableLayout table;
    ListView objectAListView;
    ListView distancesListView;
    ListView objectBListView;

    ArrayAdapter objectAAdapter;
    ArrayAdapter distanceAdapter;
    ArrayAdapter objectBAdapter;

    private List<String> listObjectA = new ArrayList<String>();
    private List<Double> listDistances = new ArrayList<Double>();
    private List<String> listObjectB = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bestroute);

        table = findViewById(R.id.tableLayout1);
        objectAListView = findViewById(R.id.listViewObjectA);
        distancesListView = findViewById(R.id.listViewDistance);
        objectBListView = findViewById(R.id.listViewObjectB);

        cont = 0;
        list = new ArrayList();

        initializeFirebase();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            projectId = extras.getString("projectId");
        }
        calculateBest();
        listarDistances();
    }

    private void initializeFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

//Orden AGUA,CEMENTO,ARENA, PIEDRA
    public void calculateBest(){
        databaseReference.child("Distances").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Distance d = objSnapshot.getValue(Distance.class);
                    if(projectId.equals(d.getIdProject())) {
                        Log.e("MyApp","ENTROOO");
                        createBestDistance(d.getIdDistance(),d.getObjectA(),d.getObjectB(),d.getDistance(),d.getIdProject(),d.getRestrictions());
                        list.add(parseDouble(String.valueOf(d.getDistance())));
                    }
                }
                hacer();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void createBestDistance(String idDistance, String objectA, String objectB, double distance, String idProject, String restrictions){
        Distance d = new Distance();
        d.setIdDistance(idDistance);
        d.setObjectA(objectA);
        d.setObjectB(objectB);
        d.setDistance(distance);
        d.setIdProject(idProject);
        d.setRestrictions(restrictions);
        databaseReference.child("BestDistances").child(d.getIdDistance()).setValue(d);
    }

    public void updateDistances(final String idDistance, final double bestDistance) {
        databaseReference.child("BestDistances").child(idDistance).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("distance").setValue(bestDistance);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void hacer(){
        while(!list.isEmpty()){
                completar(minValue());
            list.remove(minIndex());
        }
    }

    public void completar(final double value){
        databaseReference.child("BestDistances").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Distance d = objSnapshot.getValue(Distance.class);
                    if (projectId.equals(d.getIdProject())) {
                        System.out.println(value);
                        if (d.getObjectB().equals("Agua") && cont == 3) {
                            updateDistances(d.getIdDistance(), value);
                            cont++;
                            break;
                        } else if (d.getObjectB().equals("Arena") && cont == 1) {
                            Log.e("MyApp", "ARENAA");
                            updateDistances(d.getIdDistance(), value);
                            cont++;
                            break;
                        } else if (d.getObjectB().equals("Piedra") && cont == 0) {
                            Log.e("MyApp", "PIEDRA");
                            updateDistances(d.getIdDistance(), value);
                            cont++;
                            break;
                        } else if (d.getObjectB().equals("Cemento") && cont == 2) {
                            Log.e("MyApp", "CEMENTOO");
                            updateDistances(d.getIdDistance(), value);
                            cont++;
                            break;
                        }

                        }
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public int minIndex(){
        System.out.println(Collections.min(list));
        int indexOfMinimum = list.indexOf(Collections.min(list));
        return indexOfMinimum;
    }

    public double minValue(){
        System.out.println(Collections.min(list));
        return Collections.min(list);
    }

    public void listarDistances(){
        databaseReference.child("BestDistances").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listObjectA.clear();
                listDistances.clear();
                listObjectB.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Distance d = objSnapshot.getValue(Distance.class);
                    if(projectId.equals(d.getIdProject())) {
                        listObjectA.add(d.getObjectA());
                        listDistances.add(d.getDistance());
                        listObjectB.add(d.getObjectB());

                        objectAAdapter = new ArrayAdapter<String>(BestRouteActivity.this, android.R.layout.simple_list_item_1, listObjectA);
                        objectAListView.setAdapter(objectAAdapter);

                        distanceAdapter = new ArrayAdapter<Double>(BestRouteActivity.this, android.R.layout.simple_list_item_1, listDistances);
                        distancesListView.setAdapter(distanceAdapter);

                        objectBAdapter = new ArrayAdapter<String>(BestRouteActivity.this, android.R.layout.simple_list_item_1, listObjectB);
                        objectBListView.setAdapter(objectBAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void calculateRoute(View v){
        Intent i = new Intent(BestRouteActivity.this,CalculateBestRouteActivity.class);
        i.putExtra("projectId",projectId);
        startActivity(i);
    }

}

package com.example.appdistancia.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appdistancia.Model.Distance;
import com.example.appdistancia.Model.Project;
import com.example.appdistancia.Model.Resistance;
import com.example.appdistancia.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CalculateRouteActivity  extends AppCompatActivity {

    TextView routeTotal;

    String projectId;
    double cubicMeters;
    String resistance;
    static double total;
    double amount;
    double amountR;
    double distance;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculateroute);

        initializeFirebase();

        routeTotal = findViewById(R.id.lbl_routeTotal);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            projectId = extras.getString("projectId");
        }

        total = 0.0;
        amountR = 0.0;
        amount = 0.0;
        getCubicMeters();
        getResistance();
        calculate();

    }

    public void initializeFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void getCubicMeters() {
        databaseReference.child("Project").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Project p = objSnapshot.getValue(Project.class);
                    if (projectId.equals(p.getIdProject())) {
                        cubicMeters = p.getMeters();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    public void getResistance(){
        databaseReference.child("Project").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Project p = objSnapshot.getValue(Project.class);
                    if (projectId.equals(p.getIdProject())) {
                        resistance = p.getResistencia();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getValues(final String objectName, final String type) {
        databaseReference.child("Resistance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Resistance r = objSnapshot.getValue(Resistance.class);
                    if (r.getName().equals(type)) {
                        if (r.getObject().equals(objectName)) {
                            amount = amount + r.getAmount();
                            toast(String.valueOf(amount));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void toast(String n){
        Toast.makeText(this,"" + n, Toast.LENGTH_LONG).show();
    }

    public void calculate(){
        databaseReference.child("Distances").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Distance d = objSnapshot.getValue(Distance.class);
                    if(projectId.equals(d.getIdProject())) {
                        //getValues(d.getObjectB(),resistance);
                        distance = distance + d.getDistance();
                        Log.e("MyApp",d.getObjectB());
                        Log.e("MyApp",resistance);
                        if(resistance.equals("210")){
                            Log.e("MyApp","ENTROOO");
                            if(d.getObjectB().equals("Agua")) {
                                amount = amount + 0.5;
                            } else if(d.getObjectB().equals("Arena")){
                                amount = amount + 2.5;
                            } else if(d.getObjectB().equals("Piedra")){
                                amount = amount + 3.5;
                            } else if(d.getObjectB().equals("Cemento")){
                                amount = amount + 1;
                            }
                        } else if(resistance.equals("180")) {
                            if (d.getObjectB().equals("Agua")) {
                                amount = amount + 0.5;
                            } else if (d.getObjectB().equals("Arena")) {
                                amount = amount + 2.5;
                            } else if (d.getObjectB().equals("Piedra")) {
                                amount = amount + 4;
                            } else if (d.getObjectB().equals("Cemento")) {
                                amount = amount + 1;
                            }
                        }
                    }
                }
                total = total + (amount * cubicMeters) + distance;
                routeTotal.setText(String.valueOf(total));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void calculateBest(View v){
        Intent i = new Intent(CalculateRouteActivity.this, BestRouteActivity.class);
        i.putExtra("projectId",projectId);
        startActivity(i);
    }
}

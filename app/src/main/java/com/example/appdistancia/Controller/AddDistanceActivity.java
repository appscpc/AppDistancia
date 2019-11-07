package com.example.appdistancia.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdistancia.Model.Distance;
import com.example.appdistancia.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddDistanceActivity extends AppCompatActivity {
    EditText txt_distance;
    TextView txt_batidora;
    Spinner spinnerRestrictions;
    Spinner spinnerB;

    String projectId;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddistance);

        txt_batidora = findViewById(R.id.txt_objectABatidora);

        /*spinnerRestrictions = findViewById(R.id.spinner_resistencia);
        ArrayAdapter<CharSequence> adapterResistance = ArrayAdapter.createFromResource(this, R.array.restrictions_array, android.R.layout.simple_spinner_item);
        adapterResistance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRestrictions.setAdapter(adapterResistance); */

        spinnerB = findViewById(R.id.spinner_objectB);
        ArrayAdapter<CharSequence> adapterB = ArrayAdapter.createFromResource(this, R.array.objects_array , android.R.layout.simple_spinner_item);
        adapterB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerB.setAdapter(adapterB);



        initializeFirebase();

        txt_distance = findViewById(R.id.txt_distance);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            projectId = extras.getString("projectId");
        }
    }

    private void initializeFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void addDistance(View v){
        Distance d = new Distance();
        d.setIdDistance(UUID.randomUUID().toString());
        d.setObjectA(txt_batidora.getText().toString());
        d.setObjectB(spinnerB.getSelectedItem().toString());
        d.setDistance(Double.parseDouble(txt_distance.getText().toString()));
        d.setIdProject(projectId);
        //d.setRestrictions(spinnerRestrictions.getSelectedItem().toString());
        if(insertDistance(d)){
            Toast.makeText(this,"Las distancias se han creado.",Toast.LENGTH_LONG).show();
            viewDistances(v);
        } else{
            Toast.makeText(this,"No se pudo crear.",Toast.LENGTH_LONG).show();
        }
    }

    public boolean insertDistance(Distance d ){
        databaseReference.child("Distances").child(d.getIdDistance()).setValue(d);
        return true;
    }

    public void viewDistances(View v){
        Intent i = new Intent(AddDistanceActivity.this, ViewDistances.class);
        i.putExtra("projectId",projectId);
        startActivity(i);
    }

    public void addDistance1(View v){

        startActivity(new Intent(AddDistanceActivity.this, ProjectsActivity.class));
    }
}

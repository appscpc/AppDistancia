package com.example.appdistancia.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdistancia.Model.Project;
import com.example.appdistancia.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class CreateProjectActivity extends AppCompatActivity {
    EditText projectName;
    EditText projectDescription;
    EditText txt_meters;
    Spinner spinner_resistencia;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createproject);

        projectName = findViewById(R.id.txt_name2);
        projectDescription = findViewById(R.id.txt_description2);
        txt_meters =  findViewById(R.id.edit_meters);

        spinner_resistencia = findViewById(R.id.spinner_resistencia);
        ArrayAdapter<CharSequence> adapterResistencia = ArrayAdapter.createFromResource(this, R.array.resistencia_array , android.R.layout.simple_spinner_item);
        adapterResistencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_resistencia.setAdapter(adapterResistencia);


        initializeFirebase();
    }

    private void initializeFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void addProject(View v){
        Project p = new Project();
        p.setIdProject(UUID.randomUUID().toString());
        p.setName(projectName.getText().toString());
        p.setDescription(projectDescription.getText().toString());
        p.setIdUser(firebaseAuth.getCurrentUser().getUid());
        p.setMeters(Double.parseDouble(txt_meters.getText().toString()));
        p.setResistencia(spinner_resistencia.getSelectedItem().toString());
        if(!p.isNull()){
            Toast.makeText(this,"Error hay espacios vacios.",Toast.LENGTH_LONG).show();
        } else if(insertProject(p)){
            Toast.makeText(this,"El proyecto se ha creado.",Toast.LENGTH_LONG).show();
            createProject(v);
        } else{
            Toast.makeText(this,"El nombre del proyecto ya existe.",Toast.LENGTH_LONG).show();
        }
    }

    public boolean insertProject(Project p ){
        databaseReference.child("Project").child(p.getIdProject()).setValue(p);
        return true;
    }

    public void createProject(View v){
        startActivity(new Intent(CreateProjectActivity.this, ProjectsActivity.class));
    }
}

package com.example.appdistancia.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class ProjectsActivity extends AppCompatActivity {
    private List<Project> listProject = new ArrayList<Project>();

    ArrayAdapter projectsAdapter;
    ListView projectListView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        initializeFirebase();
        projectListView = findViewById(R.id.listViewProjects);

        listarProjects();

        projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Project project = (Project) projectListView.getItemAtPosition(position);

                String projectId = project.getIdProject();
                Intent i = new Intent(ProjectsActivity.this, ViewDistances.class);
                i.putExtra("projectId",projectId);
                startActivity(i);
            }
        });



    }

    private void listarProjects(){
        databaseReference.child("Project").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listProject.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Project p = objSnapshot.getValue(Project.class);
                    //Toast.makeText(ProjectsActivity.this, "" + p.getIdUser(), Toast.LENGTH_LONG).show();
                    if(firebaseAuth.getCurrentUser().getUid().equals(p.getIdUser())) {
                        //Toast.makeText(ProjectsActivity.this, ""+p.getIdUser(), Toast.LENGTH_LONG).show();
                        listProject.add(p);

                        projectsAdapter = new ArrayAdapter<Project>(ProjectsActivity.this, android.R.layout.simple_list_item_1, listProject);
                        projectListView.setAdapter(projectsAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initializeFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void createNewProject(View v){
        startActivity(new Intent(ProjectsActivity.this, CreateProjectActivity.class));
    }

    public void ejemplo(View v){
        startActivity(new Intent(ProjectsActivity.this, ViewDistances.class));
    }
}

package com.example.appdistancia.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdistancia.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText emailTxt;
    private EditText passwordTxt;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailTxt = findViewById(R.id.txt_email);
        passwordTxt = findViewById(R.id.txt_password);

        initializeFirebase();
    }

    private void initializeFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void signupUsers(View v){
        startActivity(new Intent(MainActivity.this, SignupActivity.class));
    }

    public void login1(View v){
        startActivity(new Intent(MainActivity.this, ViewDistances.class));
    }

    public void login(View v){
        firebaseAuth.signInWithEmailAndPassword(emailTxt.getText().toString(),passwordTxt.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Toast.makeText(MainActivity.this, "" + firebaseAuth.getCurrentUser().getProviderId() ,Toast.LENGTH_LONG).show();
                            System.out.println("Usuario:" + firebaseAuth.getCurrentUser().getProviderId());
                            startActivity(new Intent(MainActivity.this, ProjectsActivity.class));
                        } else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(MainActivity.this, "El usuario ya existe",Toast.LENGTH_LONG).show();
                            } else{
                                Toast.makeText(MainActivity.this, "No se pudo ingresar.",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}

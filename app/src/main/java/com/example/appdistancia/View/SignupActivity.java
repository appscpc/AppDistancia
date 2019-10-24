package com.example.appdistancia.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdistancia.Model.User;
import com.example.appdistancia.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    EditText txtNombre, txtMail, txtPassword;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        txtNombre = findViewById(R.id.txt_nombre2);
        txtMail = findViewById(R.id.txt_email);
        txtPassword = findViewById(R.id.txt_password);

        initializeFirebase();
    }

    private void initializeFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void signUp(View v){
        User u = new User();
        u.setIdUser(UUID.randomUUID().toString());
        u.setName(txtNombre.getText().toString());
        u.setEmail(txtMail.getText().toString());
        u.setPassword(txtPassword.getText().toString());
        if(!u.isNull()){
            Toast.makeText(this,"Error hay espacios vacios.", Toast.LENGTH_LONG).show();
        } else if(insertUser(u)){
            Toast.makeText(this,"El usuario se ha creado.", Toast.LENGTH_LONG).show();
            callLogin(v);
        } else{
            Toast.makeText(this,"Error el usuario ya existe.", Toast.LENGTH_LONG).show();
        }
    }

    public boolean insertUser(User u){
        //if(buscarUser(u.getEmail()) == 0){
        databaseReference.child("User").child(u.getIdUser()).setValue(u);
        return true;
    }

    public void callLogin(View v){
        startActivity(new Intent(SignupActivity.this, MainActivity.class));
    }


    public boolean validateEmail(String emailStr)
    {
        Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(emailStr);
        if(matcher.find()) {
            return true;
        }
        else {
            return false;
        }
    }

    public void callInicio(View v){
        startActivity(new Intent(SignupActivity.this, MainActivity.class));
    }

    public void registrar(View v){
        String name = txtNombre.getText().toString();
        String mail = txtMail.getText().toString();
        String pasw = txtPassword.getText().toString();
        if(validateEmail(mail) == true) {
            if(pasw.length() >= 6) {
                firebaseAuth.createUserWithEmailAndPassword(mail, pasw)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    DatabaseReference myRef = firebaseDatabase.getReference().child("Users");
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String uid = user.getUid();
                                    myRef.child(uid).setValue(user);

                                    Toast.makeText(SignupActivity.this, "Se ha registrado satisfactoriamente.",
                                            Toast.LENGTH_LONG).show();
                                    callInicio(findViewById(android.R.id.content));
                                } else {
                                    Toast.makeText(SignupActivity.this, "La cuenta ya existe!",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
            else{
                Toast.makeText(SignupActivity.this, "Error: La contraseña debe tener mas de 6 digitos.",
                        Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(SignupActivity.this, "Error: Correo invalido.",
                    Toast.LENGTH_LONG).show();
        }
    }
}

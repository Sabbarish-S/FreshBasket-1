package com.sabbarish.androidjcomp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sabbarish.androidjcomp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText Name, Email, Password;
    Button RegBtn;
    FirebaseAuth mAuth;
    Toolbar mToolBbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Name = findViewById(R.id.reg_name);
        Email = findViewById(R.id.reg_email);
        Password = findViewById(R.id.reg_password);
        RegBtn  = findViewById(R.id.log_btn);
        mAuth = FirebaseAuth.getInstance();
        mToolBbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolBbar);
        setTitle("FreshBasket");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString();
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && password.toString().length()>7){
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegisterActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }else{
                    Toast.makeText(RegisterActivity.this, "Please fill empty field or the length of the password is below 7", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void signIn(View view) {
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
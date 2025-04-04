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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {
    private EditText mName, mAddress, mCity, mCode, mNumber;
    private Button mAddAddress;
    private FirebaseFirestore mStore;
    private FirebaseAuth mAuth;
    Toolbar mToolBbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        mName = findViewById(R.id.ad_name);
        mAddress = findViewById(R.id.ad_address);
        mCity = findViewById(R.id.ad_city);
        mCode = findViewById(R.id.ad_postal_code);
        mNumber = findViewById(R.id.ad_phone_number);
        mAddAddress = findViewById(R.id.ad_add_address);
        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mToolBbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolBbar);
        setTitle("FreshBasket");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString();
                String address = mAddress.getText().toString();
                String city = mCity.getText().toString();
                String code = mCode.getText().toString();
                String number = mNumber.getText().toString();
                String final_address = "";

                if(name.isEmpty()){
                    Toast.makeText(AddAddressActivity.this,"Please enter all the fields", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddAddressActivity.this, AddressActivity.class);
                    startActivity(intent);
                }
                if(!name.isEmpty()){
                    final_address+=name+", ";
                }
                if(!address.isEmpty()){
                    final_address+=address+", ";
                }
                if(!city.isEmpty()){
                    final_address+=city+", ";
                }
                if(!code.isEmpty()){
                    final_address+=code+", ";
                }
                if(!number.isEmpty()){
                    final_address+=number+"";
                }

                Map<String,String> mMap = new HashMap<>();
                mMap.put("address",final_address);

                mStore.collection("User").document(mAuth.getCurrentUser().getUid())
                        .collection("Address").add(mMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(AddAddressActivity.this,"Address Added", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });

            }
        });

    }
}
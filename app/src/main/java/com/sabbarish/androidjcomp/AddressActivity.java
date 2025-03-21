package com.sabbarish.androidjcomp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sabbarish.androidjcomp.R;
import com.sabbarish.androidjcomp.adapter.AddressAdapter;
import com.sabbarish.androidjcomp.domain.Address;
import com.sabbarish.androidjcomp.domain.BestSell;
import com.sabbarish.androidjcomp.domain.Feature;
import com.sabbarish.androidjcomp.domain.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {
    private RecyclerView mAddressRecyclerView;
    private Button paymentBtn;
    private Button mAddAddress;
    private AddressAdapter mAddressAdapter;
    private List<Address> mAddresslist;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    String address = "";
    Toolbar mToolBbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        Object obj = getIntent().getSerializableExtra("item");
        List<Items> itemsList = (ArrayList<Items>) getIntent().getSerializableExtra("itemList");
        mAddressRecyclerView=findViewById(R.id.address_recycler);
        paymentBtn = findViewById(R.id.payment_btn);
        mAddAddress=findViewById(R.id.add_address_btn);
        mAddresslist = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        mAddressAdapter = new AddressAdapter(getApplicationContext(),mAddresslist,this);
        mAddressRecyclerView.setLayoutManager(new LinearLayoutManager((getApplicationContext())));
        mAddressRecyclerView.setAdapter(mAddressAdapter);
        mToolBbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolBbar);
        setTitle("FreshBasket");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mStore.collection("User").document(mAuth.getCurrentUser().getUid())
                        .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot doc:task.getResult()){
                                Address address = doc.toObject(Address.class);
                                mAddresslist.add(address);
                                mAddressAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        mAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                startActivity(intent);
            }
        });

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = 0.0;
                String url = "";
                String name = "";
                if(obj instanceof Feature){
                    Feature f = (Feature)obj;
                    amount=f.getPrice();
                    url = f.getImg_url();
                    name = f.getName();
                }
                if(obj instanceof BestSell){
                    BestSell f = (BestSell) obj;
                    amount=f.getPrice();
                    url = f.getImg_url();
                    name = f.getName();

                }
                if(obj instanceof Items){
                    Items f = (Items) obj;
                    amount=f.getPrice();
                    url = f.getImg_url();
                    name = f.getName();

                }
                if (itemsList != null && itemsList.size()>0){
                    Intent intent = new Intent(AddressActivity.this, PaymentActivity.class);
                    intent.putExtra("itemsList",(Serializable) itemsList);
                    startActivity(intent);

                }else{

                    Intent intent = new Intent(AddressActivity.this, PaymentActivity.class);
                    intent.putExtra("amount",amount);
                    intent.putExtra("img_url",url);
                    intent.putExtra("name",name);
                    intent.putExtra("address",address);
                    startActivity(intent);

                }

            }
        });
    }

    @Override
    public void setAddress(String s) {
        address = s;

    }
}
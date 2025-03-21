package com.sabbarish.androidjcomp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sabbarish.androidjcomp.R;
import com.sabbarish.androidjcomp.adapter.CartAdapter;
import com.sabbarish.androidjcomp.domain.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.ItemRemoved {
    FirebaseFirestore mStore;
    FirebaseAuth mAuth;

    List<Items> itemsList;
    RecyclerView cartRecyclerView;
    CartAdapter cartAdapter;
    Button buyItembtn;
    TextView totalamount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        itemsList = new ArrayList<>();
        buyItembtn = findViewById(R.id.cart_item_buy_now);
        totalamount = findViewById(R.id.total_amount);
        cartRecyclerView = findViewById(R.id.cart_item_container);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setHasFixedSize(true);

        buyItembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, AddressActivity.class);
                intent.putExtra("itemList", (Serializable) itemsList);
                startActivity(intent);
            }
        });
        cartAdapter = new CartAdapter(itemsList, this);
        cartRecyclerView.setAdapter(cartAdapter);
        mStore.collection("Users").document(mAuth.getCurrentUser().getUid())
                .collection("Cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                for (DocumentChange doc : task.getResult().getDocumentChanges()) {
                                    String documentid = doc.getDocument().getId();
                                    Items item = doc.getDocument().toObject(Items.class);
                                    item.setDocId(documentid);
                                    itemsList.add(item);
                                }
                                calculateAmount(itemsList);
                                cartAdapter.notifyDataSetChanged();
//                                cartRecyclerView.setAdapter(cartAdapter);

                            }
                        } else {
                            Toast.makeText(CartActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void calculateAmount(List<Items> itemsList) {
        double totalAmountInDouble = 0.0;
        for(Items item:itemsList){
            totalAmountInDouble+=item.getPrice();
        }
        totalamount.setText("Total Amount : ₹"+totalAmountInDouble);


    }

    @Override
    public void onItemRemoved(List<Items> itemsList) {
        calculateAmount(itemsList);
    }
}

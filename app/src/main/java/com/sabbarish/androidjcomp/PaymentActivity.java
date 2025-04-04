package com.sabbarish.androidjcomp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.sabbarish.androidjcomp.R;
import com.sabbarish.androidjcomp.domain.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity  {
    TextView mTotal;
    TextView totalamt;
    Button payBtn;
    double amount = 0.0;
    String name = "";
    String img_url = "";
    FirebaseFirestore mStore;
    FirebaseAuth mAuth;
    List<Items> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        amount = getIntent().getDoubleExtra("amount",0.0);
        img_url = getIntent().getStringExtra("img_url");
        name = getIntent().getStringExtra("name");
        itemsList = (ArrayList<Items>)getIntent().getSerializableExtra("itemsList");
        mStore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        Checkout.preload(getApplicationContext());
        setContentView(R.layout.activity_payment);
        mTotal=findViewById(R.id.sub_total);
        totalamt=findViewById(R.id.total_amt);
        payBtn=findViewById(R.id.pay_btn);
        if(itemsList !=  null && itemsList.size()>0){
            amount = 0.0;
            for(Items item: itemsList){
                amount += item.getPrice();

            }
            mTotal.setText("₹ "+amount+"");
            totalamt.setText("₹ "+amount+"");
        }else{
            mTotal.setText("₹ "+amount+"");
            totalamt.setText("₹ "+amount+"");
        }

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
    }
    public void startPayment() {

        Checkout checkout = new Checkout();


        final Activity activity = PaymentActivity.this;

        try {
            JSONObject options = new JSONObject();
            //Set Company Name
            options.put("name", "FreshBasket");
            //Ref no
            options.put("description", "Reference No. #123456");
            //Image to be display
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", "order_9A33XWu170gUtm");
            // Currency type
            options.put("currency", "INR");
            //double total = Double.parseDouble(mAmountText.getText().toString());
            //multiply with 100 to get exact amount in rupee
            amount = amount * 100;
            //amount
            options.put("amount", amount);
            JSONObject preFill = new JSONObject();
            //email
            preFill.put("email", "sabbarishsathyanarayanan@gmail.com");
            //contact
            preFill.put("contact", "9345884829");

            options.put("prefill", preFill);

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
        if(itemsList!=null && itemsList.size()>0){
            for(Items items : itemsList ){
                Map<String,Object> mMap = new HashMap<>();
                mMap.put("item_name",items.getName());
                mMap.put("img_url",items.getImg_url());
                mMap.put("payment_id",s);
                mStore.collection("Users").document(mAuth.getCurrentUser().getUid()).
                        collection("Orders").add(mMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
            }
            mStore.collection("Users").document(mAuth.getCurrentUser().getUid())
                    .collection("Cart").document().delete();

        }else{
            Map<String,Object> mMap = new HashMap<>();
            mMap.put("item_name",name);
            mMap.put("img_url",img_url);
            mMap.put("payment_id",s);
            mStore.collection("Users").document(mAuth.getCurrentUser().getUid()).
                    collection("Orders").add(mMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

        }


    }

    public void onPaymentError(int i, String s) {
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }


}







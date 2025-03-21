package com.sabbarish.androidjcomp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sabbarish.androidjcomp.R;
import com.sabbarish.androidjcomp.domain.BestSell;
import com.sabbarish.androidjcomp.domain.Feature;
import com.sabbarish.androidjcomp.domain.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailActivity extends AppCompatActivity {
    private ImageView mImage;
    private TextView mItemName;
    private TextView mPrice;
    private Button mItemRating;
    private TextView mItemRatDesc;
    private TextView mItemDesc;
    private Button mItemBuyNow;
    private Button mItemAddCart;
    Feature feature = null;
    BestSell bestSell = null;
    Toolbar mToolBbar;
    Items items = null;
    FirebaseFirestore mStore;
    FirebaseAuth mAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mImage = findViewById(R.id.item_img);
        mItemName = findViewById(R.id.item_name);
        mPrice = findViewById(R.id.item_price);
        mItemRating = findViewById(R.id.rating);
        mItemRatDesc = findViewById(R.id.item_rat_de);
        mItemDesc = findViewById(R.id.item_des);
        mItemBuyNow = findViewById(R.id.item_buy_now);
        mItemAddCart = findViewById(R.id.item_add_cart);
        mToolBbar = findViewById(R.id.login_toolbar);
        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        setSupportActionBar(mToolBbar);
        setTitle("FreshBasket");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Object obj =getIntent().getSerializableExtra("detail");
        if(obj instanceof Feature){
            feature =(Feature) obj;
        }else if (obj instanceof BestSell ){
             bestSell = (BestSell) obj;
        }
        else if (obj instanceof Items ){
            items = (Items) obj;
        }
        if (feature!=null){
            Glide.with(getApplicationContext()).load(feature.getImg_url()).into(mImage);
            mItemName.setText(feature.getName());
            mPrice.setText(feature.getPrice()+"₹");
            mItemRating.setText(feature.getRating()+"");

            if(feature.getRating()>4.5){
                mItemRatDesc.setText("Very Good");
            }else {
                mItemRatDesc.setText("Good");
            }
            mItemDesc.setText(feature.getDescription());

        }
        if (bestSell!=null){
            Glide.with(getApplicationContext()).load(bestSell.getImg_url()).into(mImage);
            mItemName.setText(bestSell.getName());
            mPrice.setText(bestSell.getPrice()+"₹");
            mItemRating.setText(bestSell.getRating()+"");

            if(bestSell.getRating()>4.5){
                mItemRatDesc.setText("Very Good");
            }else {
                mItemRatDesc.setText("Good");
            }
            mItemDesc.setText(bestSell.getDescription());
            Log.d("Alliswell",bestSell.getDescription());

        }
        if(items!=null){
            Glide.with(getApplicationContext()).load(items.getImg_url()).into(mImage);
            mItemName.setText(items.getName());
            mPrice.setText(items.getPrice()+"₹");
            mItemRating.setText(items.getRating()+"");
            if(items.getRating()>4){
                mItemRatDesc.setText("Very Good");
            }else{
                mItemRatDesc.setText("Good");
            }
            mItemDesc.setText(items.getDescription());
        }
//        Toast.makeText(this,""+feature.getName(),Toast.LENGTH_SHORT).show();


        mItemAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feature!=null){
                    mStore.collection("Users").document(mAuth.getCurrentUser().getUid())
                            .collection("Cart").add(feature).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast.makeText(DetailActivity.this,"Item Added to Cart",Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                if (bestSell!=null){
                    mStore.collection("Users").document(mAuth.getCurrentUser().getUid())
                            .collection("Cart").add(bestSell).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast.makeText(DetailActivity.this,"Item Added to Cart",Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                if(items!=null){
                    mStore.collection("Users").document(mAuth.getCurrentUser().getUid())
                            .collection("Cart").add(items).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast.makeText(DetailActivity.this,"Item Added to Cart",Toast.LENGTH_SHORT).show();
                                }
                            });

                }


            }
        });
        mItemBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, AddressActivity.class);
                if (feature!=null){
                    intent.putExtra("item",feature);
                }
                if (bestSell!=null){
                    intent.putExtra("item",bestSell);
                }
                if(items!=null){
                    intent.putExtra("item", items);
                }
                startActivity(intent);
            }
        });
    }
}
package com.sabbarish.androidjcomp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabbarish.androidjcomp.R;
import com.sabbarish.androidjcomp.domain.Address;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    Context applicationContext;
    List<Address> mAddresslist;

    private RadioButton mSelectedRadioButton;

    SelectedAddress selectedAddress;

    public AddressAdapter(Context applicationContext, List<Address> mAddresslist,SelectedAddress selectedAddress) {
        this.applicationContext = applicationContext;
        this.mAddresslist =mAddresslist;
        this.selectedAddress = selectedAddress;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(applicationContext).inflate(R.layout.single_address_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, final int position) {
        holder.mAddress.setText(mAddresslist.get(position).getAddress());
        holder.mRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Address address:mAddresslist){
                    address.setSelected(false);
                }
                mAddresslist.get(position).setSelected(true);

                if(mSelectedRadioButton!=null){
                    mSelectedRadioButton.setChecked(false);
                }
                mSelectedRadioButton = (RadioButton) v;
                mSelectedRadioButton.setChecked(true);
                selectedAddress.setAddress(mAddresslist.get(position).getAddress());

            }
        });

    }

    @Override
    public int getItemCount() {
        return mAddresslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mAddress;
        private RadioButton mRadio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mAddress = itemView.findViewById(R.id.address_add);
            mRadio = itemView.findViewById(R.id.select_address);
        }
    }
    public interface SelectedAddress{
        public void setAddress(String s);


    }
}

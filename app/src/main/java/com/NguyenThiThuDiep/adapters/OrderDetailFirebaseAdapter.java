package com.NguyenThiThuDiep.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.NguyenThiThuDiep.k234111e_mobile.R;
import com.NguyenThiThuDiep.models.OrderDetailFirebase;

import java.util.Locale;

public class OrderDetailFirebaseAdapter extends ArrayAdapter<OrderDetailFirebase> {
    Activity context;
    int resource;

    public OrderDetailFirebaseAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View custom = inflater.inflate(resource, null);
        
        OrderDetailFirebase detail = getItem(position);
        
        TextView txtProductDetailId = custom.findViewById(R.id.txtProductDetailId);
        TextView txtProductPrice = custom.findViewById(R.id.txtProductPrice);
        TextView txtProductQuantity = custom.findViewById(R.id.txtProductQuantity);
        TextView txtSubTotal = custom.findViewById(R.id.txtSubTotal);

        if (detail != null) {
            txtProductDetailId.setText("Product: " + detail.getProductId());
            txtProductPrice.setText(String.format(Locale.getDefault(), "Price: %,.0f", detail.getUnitPrice()));
            txtProductQuantity.setText("Qty: " + detail.getQuantity());
            
            double subtotal = detail.getUnitPrice() * detail.getQuantity();
            txtSubTotal.setText(String.format(Locale.getDefault(), "%,.0f", subtotal));
        }

        return custom;
    }
}

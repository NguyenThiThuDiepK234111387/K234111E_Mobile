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
import com.NguyenThiThuDiep.models.OrderFirebase;

import java.util.Locale;

public class OrderFirebaseAdapter extends ArrayAdapter<OrderFirebase> {
    Activity context;
    int resource;

    public OrderFirebaseAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View custom = inflater.inflate(resource, null);

        OrderFirebase order = getItem(position);
        TextView txtOrderId = custom.findViewById(R.id.txtOrderId);
        TextView txtOrderDate = custom.findViewById(R.id.txtOrderDate);
        TextView txtStatus = custom.findViewById(R.id.txtStatus);
        TextView txtOrderTotal = custom.findViewById(R.id.txtOrderTotal);

        if (order != null) {
            txtOrderId.setText(order.getOrderId());
            txtOrderDate.setText(order.getFormattedDate());
            txtStatus.setText(order.getStatus());
            txtOrderTotal.setText(String.format(Locale.getDefault(), "%,.0f", order.getTotalAmount()));

            // Set background color based on status
            String status = order.getStatus() != null ? order.getStatus().toLowerCase() : "";
            int colorResId;
            switch (status) {
                case "completed":
                    colorResId = R.color.order_completed;
                    break;
                case "shipping":
                    colorResId = R.color.order_shipping;
                    break;
                case "pending":
                    colorResId = R.color.order_pending;
                    break;
                case "cancelled":
                    colorResId = R.color.order_cancelled;
                    break;
                default:
                    colorResId = R.color.order_processing;
                    break;
            }
            
            // Apply background color to the entire item row
            custom.setBackgroundColor(context.getColor(colorResId));
            
            // Set all text colors to black to ensure they are visible on colored backgrounds
            int blackColor = context.getColor(R.color.black);
            txtOrderId.setTextColor(blackColor);
            txtOrderDate.setTextColor(blackColor);
            txtStatus.setTextColor(blackColor);
            txtOrderTotal.setTextColor(blackColor);
        }

        return custom;
    }
}

package com.NguyenThiThuDiep.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.NguyenThiThuDiep.k234111e_mobile.R;
import com.NguyenThiThuDiep.models.DataWareHouse;
import com.NguyenThiThuDiep.models.Order;

public class OrderAdapter extends ArrayAdapter<Order>
{
    Activity context;
    int resource;
    public OrderAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View custom=inflater.inflate(resource,null);
        Order order=getItem(position);
        TextView txtOrderId=custom.findViewById(R.id.txtOrderId);
        TextView txtOrderDate=custom.findViewById(R.id.txtOrderDate);
        TextView txtStatus=custom.findViewById(R.id.txtStatus);
        TextView txtOrderTotal=custom.findViewById(R.id.txtOrderTotal);
        txtOrderId.setText(order.getOrderId() != null ? order.getOrderId().toString() : "");
        txtOrderDate.setText(order.getOrderDate() != null ? order.getOrderDate().toString() : "");
        if (order.getOrderStatus() != null) {
            txtStatus.setText(order.getOrderStatus().getDescription());
            switch (order.getOrderStatus()) {
                case COMPLETED:
                    txtStatus.setTextColor(context.getColor(R.color.order_completed));
                    break;
                case NOT_YET_PAYMENT:
                    txtStatus.setTextColor(context.getColor(R.color.order_not_yet_payment));
                    break;
                case GOING_LOGISTIC:
                    txtStatus.setTextColor(context.getColor(R.color.order_going_logistic));
                    break;
                case CUSTOMER_COMPLAIN:
                    txtStatus.setTextColor(context.getColor(R.color.order_customer_complain));
                    break;
                default:
                    txtStatus.setTextColor(context.getColor(R.color.black));
                    break;
            }
        }
        txtOrderTotal.setText(String.valueOf(DataWareHouse.sumOfValueOrder(order)));
        return custom;
    }
}

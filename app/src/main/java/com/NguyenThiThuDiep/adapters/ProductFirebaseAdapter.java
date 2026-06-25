package com.NguyenThiThuDiep.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.NguyenThiThuDiep.k234111e_mobile.R;
import com.NguyenThiThuDiep.models.ProductFirebaseItem;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

public class ProductFirebaseAdapter extends ArrayAdapter<ProductFirebaseItem> {
    private final Activity context;
    private final int resource;
    private final List<ProductFirebaseItem> objects;

    public ProductFirebaseAdapter(@NonNull Activity context, int resource, @NonNull List<ProductFirebaseItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    private static class ViewHolder {
        ImageView imgProduct;
        TextView txtProductName;
        TextView txtProductPrice;
        TextView txtProductQuantity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = this.context.getLayoutInflater();
            convertView = inflater.inflate(this.resource, parent, false);
            holder = new ViewHolder();
            holder.imgProduct = convertView.findViewById(R.id.imgProduct);
            holder.txtProductName = convertView.findViewById(R.id.txtProductName);
            holder.txtProductPrice = convertView.findViewById(R.id.txtProductPrice);
            holder.txtProductQuantity = convertView.findViewById(R.id.txtProductQuantity);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProductFirebaseItem product = this.objects.get(position);
        if (product != null) {
            android.util.Log.d("FIREBASE_ADAPTER", "Product: " + product.getProductName() + " - Image URL: " + product.getImage() + " - Quantity: " + product.getQuantity());
            holder.txtProductName.setText(product.getProductName());
            holder.txtProductPrice.setText(String.format(Locale.getDefault(), "Price: %,.0f VND", product.getPrice()));
            holder.txtProductQuantity.setText(String.format(Locale.getDefault(), "Quantity: %d", product.getQuantity()));

            // Load image using Glide
            if (product.getImage() != null && !product.getImage().isEmpty()) {
                Glide.with(context)
                        .load(product.getImage())
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .error(android.R.drawable.ic_menu_report_image)
                        .into(holder.imgProduct);
            } else {
                holder.imgProduct.setImageResource(android.R.drawable.ic_menu_gallery);
            }
        }

        return convertView;
    }
}

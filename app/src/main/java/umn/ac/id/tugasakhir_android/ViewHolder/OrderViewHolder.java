package umn.ac.id.tugasakhir_android.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import umn.ac.id.tugasakhir_android.Interface.ItemClickListener;
import umn.ac.id.tugasakhir_android.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

    public TextView txtOrderList, txtOrderStatus, txtOrderPhone, txtOrderAddress, txtOrderName, txtOrderId;
    private ItemClickListener itemClickListener;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        txtOrderId = (TextView) itemView.findViewById(R.id.orderID);
        txtOrderName = (TextView) itemView.findViewById(R.id.namaPemesan);
        txtOrderList = (TextView) itemView.findViewById(R.id.list_makanan);
        txtOrderStatus = (TextView) itemView.findViewById(R.id.order_status);
        txtOrderPhone = (TextView) itemView.findViewById(R.id.order_phone);
        txtOrderAddress = (TextView) itemView.findViewById(R.id.order_address);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("PILIH AKSI");

        menu.add(0,0, getAdapterPosition(), "Update");
        menu.add(0,1, getAdapterPosition(), "Delete");
    }
}

package umn.ac.id.tugasakhir_android.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import umn.ac.id.tugasakhir_android.Common.Common;
import umn.ac.id.tugasakhir_android.Interface.ItemClickListener;
import umn.ac.id.tugasakhir_android.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView food_name;
    public ImageView food_image, fav_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);


        food_image = itemView.findViewById(R.id.food_image);
        food_name = itemView.findViewById(R.id.food_name);
        //fav_image = itemView.findViewById(R.id.fav);


        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

}

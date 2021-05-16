package umn.ac.id.tugasakhir_android.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.firestore.v1.StructuredQuery;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import umn.ac.id.tugasakhir_android.Interface.ItemClickListener;
import umn.ac.id.tugasakhir_android.R;

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

  public TextView txt_cart_name, txt_price;
  public ImageView img_cart_count;

  private ItemClickListener itemClickListener;

  public void setTxt_cart_name(TextView txt_cart_name){
    this.txt_cart_name = txt_cart_name;
  }

  public CartViewHolder(View itemView){
    super(itemView);
    txt_cart_name = (TextView)itemView.findViewById(R.id.cart_item_name);
    txt_price = (TextView)itemView.findViewById(R.id.cart_item_Price);
    img_cart_count = (ImageView)itemView.findViewById(R.id.cart_item_count);
  }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
  private List<StructuredQuery.Order> listData = new ArrayList<>();
  private Context context;

  public CartAdapter(List<StructuredQuery.Order> listData, Context context){
    this.listData = listData;
    this.context = context;
  }

  @Override
  public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View itemView = inflater.inflate(R.layout.cart_layout,parent,false);
    return new CartViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(CartViewHolder holder, int position) {
    TextDrawable drawable = TextDrawable.builder()
      .buildRound(""+listData.get(position).getQuantity(), Color.RED);
    holder.img_cart_count.setImageDrawable(drawable);

    Locale locale = new Locale("idn","INDONESIA");
    NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
    int price = (Integer.parseInt(listData.get(position).getPrice()))*(Integer.
      parseInt(listData.get(position).getQuantity()));
    holder.txt_price.setText(fmt.format(price));
    holder.txt_cart_name.setText(listData.get(position).getProductName());
  }

  @Override
  public int getItemCount() {
    return listData.size();
  }
}

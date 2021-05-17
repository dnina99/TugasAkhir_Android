package umn.ac.id.tugasakhir_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

import umn.ac.id.tugasakhir_android.Common.Common;
import umn.ac.id.tugasakhir_android.Model.Order;
import umn.ac.id.tugasakhir_android.Model.Request;
import umn.ac.id.tugasakhir_android.ViewHolder.OrderViewHolder;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = (RecyclerView) findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(Common.currentUser.getMobileNumber());
    }

    private void loadOrders(String mobileNumber) {

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Requests");

        FirebaseRecyclerOptions<Request> options =
                new FirebaseRecyclerOptions.Builder<Request>()
                        .setQuery(query, new SnapshotParser<Request>() {
                            @NonNull
                            @Override
                            public Request parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new Request(snapshot.child("mobileNumber").getValue().toString(),
                                        snapshot.child("name").getValue().toString(),
                                        snapshot.child("address").getValue().toString(),
                                        snapshot.child("total").getValue().toString(),
                                        snapshot.child("foods").getValue(Request.class); //untuk ambil data list masih belum ketemu prompt (?) nya :">
                                        );
                            }
                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder viewHolder, int i, @NonNull Request request) {
                viewHolder.txtOrderId.setText(adapter.getRef(i).getKey());
                viewHolder.txtOrderStatus.setText(convertCodeToStatus(request.getStatus()));
                viewHolder.txtOrderAddress.setText(request.getAddress());
                viewHolder.txtOrderPhone.setText(request.getMobileNumber());
            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private String convertCodeToStatus(String status) {
        if(status.equals("0"))
            return "Placed";
        else if(status.equals("1"))
            return "On my way";
        else
            return "Shipped";
    }

}
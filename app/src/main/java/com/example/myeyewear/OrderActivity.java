package com.example.myeyewear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderActivity extends AppCompatActivity {

    ImageButton back;
    private RecyclerView ordersList;
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
//        ordersList.setNestedScrollingEnabled(false);

        ordersRef= FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone());

        ordersList=findViewById(R.id.order_l);
        ordersList.setLayoutManager(new LinearLayoutManager(this));

        back=(ImageButton) findViewById(R.id.btnBackO);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();

        FirebaseRecyclerOptions<Orders> options=
                new FirebaseRecyclerOptions.Builder<Orders>()
                .setQuery(ordersRef, Orders.class)
                .build();

        FirebaseRecyclerAdapter<Orders,OrderViewHolder>adapter=
                new FirebaseRecyclerAdapter<Orders, OrderViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Orders model) {

                        holder.userOid.setText(model.getOid());

                        holder.userDateTime.setText(model.getDate().toString());
                        holder.userTotalPrice.setText("RM "+model.getTotalAmount().toString());
                        holder.userStatus.setText(model.getStatus().toString());
                        holder.userPayment.setText(model.getPayment().toString());
                    }

                    @NonNull
                    @Override
                    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_items_layout,parent,false);
                        return new OrderViewHolder(view);
                    }
                };
        ordersList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class OrderViewHolder extends  RecyclerView.ViewHolder{

        public TextView userOid,userDateTime, userTotalPrice,userStatus,userPayment;
        public Button ShowOrdersBtn;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            userOid=itemView.findViewById(R.id.orderId);
            userDateTime= itemView.findViewById (R.id.orderDate);
            userStatus= itemView.findViewById (R.id.orderStatus);
            userPayment = itemView.findViewById (R.id.orderPayment);
            userTotalPrice = itemView.findViewById (R.id.orderTotal);


        }
    }

}
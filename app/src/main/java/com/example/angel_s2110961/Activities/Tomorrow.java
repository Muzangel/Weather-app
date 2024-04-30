//package com.example.angel_s2110961.Activities; // Corrected typo in package name
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.angel_s2110961.Adapter.TomorrowAdapter;
//import com.example.angel_s2110961.Domains.TomorrowDomain;
//import com.example.angel_s2110961.R;
//
//import java.util.ArrayList;
//
//public class Tomorrow extends AppCompatActivity {
//    private RecyclerView.Adapter adapterTomorrow;
//    private RecyclerView recyclerView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.view_tomorrow);
//
//        initRecyclerView();
//        setVariable();
//    }
//
//    private void setVariable() {
//        ImageView next7dayBtn = findViewById(R.id.back_btn);
//        next7dayBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Tomorrow.this, MainActivity.class)); // Corrected method name
//            }
//        });
//    }
//
//    private void initRecyclerView() { // Corrected method name
//        ArrayList<TomorrowDomain> items = new ArrayList<>();
//
//        items.add(new TomorrowDomain("Fri", "Storm", "Storm", 24, 20));
//        items.add(new TomorrowDomain("Sat", "cloudy", "Cloudy", 26, 22));
//        items.add(new TomorrowDomain("Sun", "Sun", "Rainy_Sunny", 28, 26));
//        items.add(new TomorrowDomain("Mon", "wind", "Windy", 27, 22));
//        items.add(new TomorrowDomain("Tue", "cloudy_3", "Rainy", 26, 20));
//        items.add(new TomorrowDomain("Wed", "sun", "Sunny", 28, 27));
//
//        recyclerView = findViewById(R.id.view2);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//
//        adapterTomorrow = new TomorrowAdapter(items);
//        recyclerView.setAdapter(adapterTomorrow);
//    }
//}

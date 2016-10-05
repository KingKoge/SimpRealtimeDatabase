package com.ssru.mrsmile.simprealtimedatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ValueEventListener {
    private Button btnSunny;
    private Button btnFoggy;

    private TextView tvDisplay;

    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mConditionRef = mRootRef.child("condition");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindView();
    }

    private void bindView() {
        btnFoggy = (Button) findViewById(R.id.btn_foggy);
        btnSunny = (Button) findViewById(R.id.btn_sunny);

        btnFoggy.setOnClickListener(this);
        btnSunny.setOnClickListener(this);

        tvDisplay = (TextView) findViewById(R.id.tv_display);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mConditionRef.addValueEventListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mConditionRef.removeEventListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnFoggy) {
            mConditionRef.setValue("Foggy");
        } else if (v == btnSunny) {
            mConditionRef.setValue("Sunny");
        }
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        String text = dataSnapshot.getValue(String.class);
        tvDisplay.setText(text);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}

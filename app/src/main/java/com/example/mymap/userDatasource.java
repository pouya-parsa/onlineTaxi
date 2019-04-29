package com.example.mymap;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class userDatasource extends Datasource {
    private DatabaseReference ref;
    private Long userBalance;
    private String userFullname;

    private TextView balance_txt;
    private TextView fullname_txt;

    public userDatasource(String username, TextView balance_txt, TextView fullname_txt) {
        this.balance_txt = balance_txt;
        this.fullname_txt = fullname_txt;

        this.getUserBalanceHandler(username);
        this.getUserFullnameHandler(username);
    }

    public void updateUserBalance() {
        this.balance_txt.setText(String.valueOf(this.userBalance));
    }

    public void updateUserFullname () {
        this.fullname_txt.setText(this.userFullname);
    }

    public void getUserBalanceHandler(String username) {
        ref = database.getReference("passengers/" + username + "/balance");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long balance = dataSnapshot.getValue(Long.class);
                Log.d("database", String.valueOf(balance));
                userBalance = balance;
                updateUserBalance();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getUserFullnameHandler(String username) {
        ref = database.getReference("passengers/" + username + "/name");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userfullname = dataSnapshot.getValue(String.class);
                Log.d("database", userfullname);
                userFullname = userfullname;
                updateUserFullname();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateBalance(String username, double value) {
        ref = database.getReference("passengers/" + username + "/balance");
        ref.setValue(this.userBalance - value);
    }
}

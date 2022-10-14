package com.example.login_module_ssg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button SignOUT_Bttn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth Auth = FirebaseAuth.getInstance() ;
        FirebaseUser Current_User = Auth.getCurrentUser();


        if(Current_User== null)
        {
            Intent intent = new Intent(this, Login_Activity.class);
            startActivity(intent);
            finish();
            return;
        }

        SignOUT_Bttn = findViewById(R.id.SigningOUT_Button);

        SignOUT_Bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignOUT_User_Function();
            }
        });



        FirebaseDatabase FB_DB = FirebaseDatabase.getInstance();
        DatabaseReference FB_DB_Ref = FB_DB.getReference("users").child(Current_User.getUid());

        FB_DB_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user_Instance = dataSnapshot.getValue(User.class);
                if(user_Instance != null){}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void SignOUT_User_Function() {
FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, Login_Activity.class);
        startActivity(intent);
        finish();

    }
}
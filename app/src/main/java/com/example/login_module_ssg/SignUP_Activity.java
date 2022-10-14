package com.example.login_module_ssg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.login_module_ssg.User;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SignUP_Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    User User_OBJ;
    EditText et_U_Name, et_U_Email, et_U_Password;
    TextView Activate_login_Activity;
    Button SigningUP_Bttn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        SigningUP_Bttn = findViewById(R.id.SigningUP_Button);
        et_U_Name = findViewById(R.id.U_Name_SignUp_Act);
        et_U_Email = findViewById(R.id.Email_SignUp_Act);
        et_U_Password = findViewById(R.id.Passcode_SignUp_Act);
        Activate_login_Activity = findViewById(R.id.Account_SignUP_Question);

        User_OBJ = new User();
        mAuth = FirebaseAuth.getInstance();


        if(mAuth.getCurrentUser() != null)
        {
            finish();
            return;
        }

        SigningUP_Bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUP_User_Function();
            }
        });

        Activate_login_Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch_ToLogIN_Function();
            }
        });

        /*  Activate_login_Activity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Sign_Up_Activity.this, Sign_In_Activity.class);
                    startActivity(intent);
                }
*/
    }

    private void Switch_ToLogIN_Function() {
        Intent intent = new Intent(this, Login_Activity.class);
        startActivity(intent);
        finish();

    }

    private void SignUP_User_Function()
    {
        TextView Activate_login_Activity;
        Button SigningUP_Bttn;

        SigningUP_Bttn = findViewById(R.id.SigningUP_Button);
        et_U_Name = findViewById(R.id.U_Name_SignUp_Act);
        et_U_Email = findViewById(R.id.Email_SignUp_Act);
        et_U_Password = findViewById(R.id.Passcode_SignUp_Act);
        Activate_login_Activity = findViewById(R.id.Account_SignUP_Question);

        String U_Name = et_U_Name.getText().toString();
        String  U_Email = et_U_Email.getText().toString();
        String  U_Password = et_U_Password.getText().toString();

        if(U_Name.isEmpty() ||U_Email.isEmpty()|| U_Password.isEmpty() )
        {
            Toast.makeText(SignUP_Activity.this,"Please fill required fields",Toast.LENGTH_LONG).show();
            return;
        }


        mAuth.createUserWithEmailAndPassword(U_Email, U_Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User User_OBJ = new User(U_Name,U_Email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(User_OBJ).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Convey_MainActivity_Function();
                                        }
                                    });


                        } else {
                            Toast.makeText(SignUP_Activity.this,"Authentication Failed",Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }


    private void Convey_MainActivity_Function() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }




}
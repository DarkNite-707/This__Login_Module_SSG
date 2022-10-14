package com.example.login_module_ssg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login_Activity extends AppCompatActivity {


    private TextView textView;
    private GoogleSignInClient client;
    private Button BTTN;



    EditText et_U_Name, et_U_Email, et_U_Password;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView = findViewById(R.id.GooGle_Account_Link);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this, options);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = client.getSignInIntent();
                startActivityForResult(i, 1234);


            }

        });

        mAuth= FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null)
        {
            finish();
            return;
        }
        Button SignIN_Bttn = findViewById(R.id.SigningIN_Button);

        SignIN_Bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIN_User_Function();
            }
        });

        TextView Activate_Register_Activity = findViewById(R.id.Account_SignIN_Question);
        Activate_Register_Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch_ToSignUP_Function();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
//                                    finish();

                                } else {
                                    Toast.makeText(Login_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            } catch (ApiException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(Login_Activity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }


    }


    private void SignIN_User_Function() {


        et_U_Email = findViewById(R.id.Email_SignIN_Act);
        et_U_Password = findViewById(R.id.Passcode_SignIN_Act);

        String  U_Email = et_U_Email.getText().toString();
        String  U_Password = et_U_Password.getText().toString();

        if(U_Email.isEmpty()|| U_Password.isEmpty() )
        {
            Toast.makeText(Login_Activity.this,"Please fill required fields",Toast.LENGTH_LONG).show();
            return;
        }


        mAuth.signInWithEmailAndPassword(U_Email, U_Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser userFirebase = mAuth.getCurrentUser();
                    Toast.makeText(Login_Activity.this,"Login successful",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login_Activity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(Login_Activity.this,"Check your email or password",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void Switch_ToSignUP_Function() {
        Intent intent = new Intent(this, SignUP_Activity.class);
        startActivity(intent);
        finish();


    }

}
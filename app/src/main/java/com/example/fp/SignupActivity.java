package com.example.fp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    Button NextStepButton;
    EditText RegisterEmail, RegisterPassword,RegisterConfPassword;
    TextView AlreadyHaveAccount;
    FirebaseAuth mAuth;
    ProgressDialog loadingBar;
    DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();

        InitializeFields();
        AlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToLoginActivity();
            }
        });

        NextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });

    }

    private void CreateNewAccount() {

        String email = RegisterEmail.getText().toString();
        String password = RegisterPassword.getText().toString();
        String confPassword = RegisterConfPassword.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(SignupActivity.this, "Enter Email...",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(SignupActivity.this, "Enter Password...",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(confPassword))
        {
            Toast.makeText(SignupActivity.this, "Re Enter Password...",Toast.LENGTH_LONG).show();
        }
        else if(!TextUtils.equals(password,confPassword))
        {
            Toast.makeText(SignupActivity.this, "Password Don't Match..",Toast.LENGTH_LONG).show();
        }
        else
        {
            loadingBar.setTitle("Creating New Account...");
            loadingBar.setMessage("Please wait, while we are validating email");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {


                        String currentUserId = mAuth.getCurrentUser().getUid();

                        Toast.makeText(SignupActivity.this, "Email Validation successful...",Toast.LENGTH_LONG).show();
                        SendUserToPhoneActivity();
                        loadingBar.dismiss();
                    }
                    else
                    {
                        String error = task.getException().getMessage();
                        Toast.makeText(SignupActivity.this, "Error: "+error,Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }
    }

    private void SendUserToLoginActivity()
    {
        Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    private void SendUserToPhoneActivity()
    {
        Intent PhoneIntent = new Intent(SignupActivity.this, PhoneActivity.class);

        PhoneIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(PhoneIntent);
        finish();
    }
    private void InitializeFields() {
        NextStepButton = (Button)findViewById(R.id.next_step_button);
        RegisterEmail = (EditText)findViewById(R.id.register_email);
        RegisterPassword = (EditText)findViewById(R.id.register_password);
        RegisterConfPassword = (EditText)findViewById(R.id.register_confirm_password);
        AlreadyHaveAccount = (TextView)findViewById(R.id.already_have_account);
        loadingBar = new ProgressDialog(this);
    }
}

package com.example.fp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {

    CountryCodePicker ccp;
    EditText phoneText,codeText;
    Button continueAndNextButton;
    String checker="",phoneNumber="";
    RelativeLayout relativeLayout;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private ProgressDialog loadingbar;
    private DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        phoneText = (EditText) findViewById(R.id.phoneText);
        codeText = (EditText) findViewById(R.id.codeText);
        loadingbar = new ProgressDialog(this);
        continueAndNextButton = (Button)findViewById(R.id.continueNextButton);
        relativeLayout = (RelativeLayout)findViewById(R.id.phoneAuth);
        mAuth = FirebaseAuth.getInstance();
        ccp = (CountryCodePicker)findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phoneText);
        RootRef = FirebaseDatabase.getInstance().getReference();


        continueAndNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(continueAndNextButton.getText().equals("Submit")|| checker.equals("Code Sent"))
                {
                    String verificationCode = codeText.getText().toString();
                    if(verificationCode.equals(""))
                    {
                        Toast.makeText(PhoneActivity.this, "Please Write Verification Code",Toast.LENGTH_LONG);
                    }
                    else
                    {
                        loadingbar.setTitle("Code Verification");
                        loadingbar.setMessage("Please Wait, while we are verifying your Code");
                        loadingbar.setCanceledOnTouchOutside(false);
                        loadingbar.show();
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,verificationCode);
                        signInWithPhoneAuthCredentials(credential);
                    }

                }else
                {
                    phoneNumber = ccp.getFullNumberWithPlus();
                    if(!phoneNumber.equals(""))
                    {
                        loadingbar.setTitle("Phone Number Verification");
                        loadingbar.setMessage("Please Wait, while we are verifying your Phone Number");
                        loadingbar.setCanceledOnTouchOutside(false);
                        loadingbar.show();

                        PhoneAuthOptions options =
                                PhoneAuthOptions.newBuilder(mAuth).setPhoneNumber(phoneNumber)
                                        .setTimeout(60L, TimeUnit.SECONDS).setActivity(PhoneActivity.this)
                                        .setCallbacks(mcallbacks).build();

                        PhoneAuthProvider.verifyPhoneNumber(options);

                    }
                    else
                    {
                        Toast.makeText(PhoneActivity.this, "Please Write Valid Phone Number",Toast.LENGTH_LONG);

                    }
                }
            }

        });

        mcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredentials(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(PhoneActivity.this, "Invalid Phone Number!!", Toast.LENGTH_LONG);
                relativeLayout.setVisibility(View.VISIBLE);
                continueAndNextButton.setText("Continue");
                codeText.setVisibility(View.GONE);

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                mVerificationId = s;
                mResendToken = forceResendingToken;

                relativeLayout.setVisibility(View.GONE);
                checker = "Code Sent";
                continueAndNextButton.setText("Submit");
                codeText.setVisibility(View.VISIBLE);
                loadingbar.dismiss();
                Toast.makeText(PhoneActivity.this, "Code has been sent, Please Check!!",Toast.LENGTH_LONG);
            }
        };
    }

    private void signInWithPhoneAuthCredentials(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            String CurrentUserId = mAuth.getCurrentUser().getUid();
                            RootRef.child("Users").child(CurrentUserId).setValue("");
                            loadingbar.dismiss();
                            Toast.makeText(PhoneActivity.this, "Congratulations, Account Created Successfully!!", Toast.LENGTH_LONG);
                            SendUserToMainActivity();

                        }
                        else
                        {
                            loadingbar.dismiss();
                            String e = task.getException().toString();
                            Toast.makeText(PhoneActivity.this, "ERROR:"+e, Toast.LENGTH_LONG);

                        }
                    }
                });
    }
    private void SendUserToMainActivity()
    {
        Intent intent = new Intent(PhoneActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}

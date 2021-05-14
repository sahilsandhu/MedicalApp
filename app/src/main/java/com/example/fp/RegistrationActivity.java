package com.example.fp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    CircleImageView profileImage;
    TextView RegisteringAs;
    Switch aSwitch;
    EditText UserName;
    EditText Age;
    Spinner spin;
    EditText YearsOfExp;
    MaterialCheckBox checkBox;
    Button UpdatingAccnt;
    LinearLayout DoctorLayout;
    String CurrentUserId;
    FirebaseAuth mAuth;
    DatabaseReference RootRef;
    String[] Options = {"Brain", "EYE","None"};
    String specialization;
    String email="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        CurrentUserId = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();

        profileImage = (CircleImageView) findViewById(R.id.profile_image);
        //RegisteringAs = (TextView) findViewById(R.id.registering_as);
        UserName = (EditText) findViewById(R.id.set_user_name);
        Age = (EditText) findViewById(R.id.set_user_age);
        aSwitch = (Switch) findViewById(R.id.switch1);

        YearsOfExp = (EditText) findViewById(R.id.yearsOfExperience);
        checkBox = (MaterialCheckBox) findViewById(R.id.checkbox1);
        UpdatingAccnt = (Button) findViewById(R.id.updatingAccount);
        DoctorLayout = (LinearLayout) findViewById(R.id.doctor_layout);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    aSwitch.setText("Registering as DOCTOR");
                    DoctorLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    aSwitch.setText("Registering as PATIENT");
                    DoctorLayout.setVisibility(View.INVISIBLE);
                }
            }
        });



        spin = (Spinner) findViewById(R.id.spinner1);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Options);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);


        UpdatingAccnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = getIntent().getStringExtra("email");

                Boolean SwitchState = aSwitch.isChecked();
                String username = UserName.getText().toString();
                String age = Age.getText().toString();
                String imageURL = "default";
                String bio ="";
                String yearsOfExp = "";
                if (SwitchState) {
                    yearsOfExp = YearsOfExp.getText().toString();
                }

                if (username.equals("")) {
                    Toast.makeText(RegistrationActivity.this, "UserName is Empty!!", Toast.LENGTH_LONG).show();
                } else if (age.equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Age is Empty!!", Toast.LENGTH_LONG).show();

                } else if (yearsOfExp.equals("") && SwitchState) {
                    Toast.makeText(RegistrationActivity.this, "Years of Experience is Empty!!", Toast.LENGTH_LONG).show();

                } else if (!checkBox.isChecked()) {
                    Toast.makeText(RegistrationActivity.this, "Agree to the conditions", Toast.LENGTH_LONG).show();

                } else {
                    HashMap<String, String> profMap = new HashMap<>();
                    profMap.put("uid", CurrentUserId);
                    profMap.put("name", username);
                    profMap.put("age", age);

                    profMap.put("yearsofexp", yearsOfExp);
                    profMap.put("specialization",specialization);
                    profMap.put("imageurl",imageURL);
                    profMap.put("bio",bio);
                    RootRef.child("Users").child(CurrentUserId).setValue(profMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                        SendUserToMainActivity();
                                    } else {
                                        String error = task.getException().toString();
                                        Toast.makeText(RegistrationActivity.this, "error: " + error, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        specialization = Options[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        specialization = "None";
    }

    private void SendUserToMainActivity()
    {
        Intent intent = new Intent(RegistrationActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }



}

package com.nibm.covid19;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nibm.covid19.DatabaseHelper;
import com.nibm.covid19.R;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText firstname, password, email, lastname, address;
    Button register;
    DatabaseHelper databaseHelper;
    TextView regback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstname = findViewById(R.id.xusername);
        password = findViewById(R.id.xpassword);
        email = findViewById(R.id.email);
        lastname = findViewById(R.id.lastn);
        address=findViewById(R.id.address);
        register = findViewById(R.id.register);
        regback = findViewById(R.id.backlogin);


        databaseHelper = new DatabaseHelper(this);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameValue = firstname.getText().toString();
                String passwordValue = password.getText().toString();
                String emailValue = email.getText().toString();
                String lastnameValue = lastname.getText().toString();
                String addressValue = address.getText().toString();

                checkDataEntered();

                if (isEmpty(firstname)==false && password.length()>=8 && isEmail(email)==true && (isEmpty(lastname)==false && isEmpty(address)==false)) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("firstname", usernameValue);
                    contentValues.put("Lastname", lastnameValue);
                    contentValues.put("password", passwordValue);
                    contentValues.put("email", emailValue);
                    contentValues.put("address", addressValue);
                    databaseHelper.insertUser(contentValues);

                    Toast.makeText(RegisterActivity.this, "User registered!", Toast.LENGTH_SHORT).show();


                } else {

                    Toast.makeText(RegisterActivity.this, "all field need", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }


    void checkDataEntered() {
        if (isEmpty(firstname)) {
            firstname.setError("Enter the First Name");
        }

        if (isEmpty(lastname)) {
            lastname.setError("Last Name is required!");
        }

        if (isEmail(email) == false) {
            email.setError("Enter valid email!");
        }

        if(password.length()<8){
            password.setError("8 characters with at least 1 Upper Case, 1 lower case");

        }

        if (isEmpty(address)){
            address.setError("Enter the address");
        }

        regback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });


    }
}

package umn.ac.id.tugasakhir_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import umn.ac.id.tugasakhir_android.Model.User;

public class RegisterActivity extends AppCompatActivity {

    String mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button cancel = findViewById(R.id.btnCancelRegister);
        Button register = findViewById(R.id.btnRegisterRegister);

        EditText etFullName = findViewById(R.id.etNameRegister);
        EditText etEmail = findViewById(R.id.etEmailRegister);
        EditText etMobileNumber = findViewById(R.id.etMobileNumberRegister);
        EditText etUsername = findViewById(R.id.etUsernameRegister);
        EditText etPassword = findViewById(R.id.etPasswordRegister);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(cancel);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String primaryKey = etUsername.getText().toString();
                String name = etFullName.getText().toString();
                String email = etEmail.getText().toString();
                String number = etMobileNumber.getText().toString();
                String password = etPassword.getText().toString();

                if (name.isEmpty()) {
                    etFullName.setError("Name is Required");
                    etFullName.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    etEmail.setError("Email is Required");
                    etEmail.requestFocus();
                    return;
                }
                if (number.isEmpty()) {
                    etMobileNumber.setError("Mobile Number is Required");
                    etMobileNumber.requestFocus();
                    return;
                }
                if (primaryKey.isEmpty()) {
                    etUsername.setError("Username is Required");
                    etUsername.requestFocus();
                    return;
                }
                if (password.isEmpty() || password.length() <= 6) {
                    etPassword.setError("Password is Required and need more than 6 letters");
                    etPassword.requestFocus();
                    return;
                }


                final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
                mDialog.show();

                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // check if user already exits  through phone number
                        if (dataSnapshot.child(etUsername.getText().toString()).exists()) {
                            mDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Username Taken", Toast.LENGTH_SHORT).show();
                        } else {
                            mDialog.dismiss();
                            User user = new User(
                                    etUsername.getText().toString(),
                                    etPassword.getText().toString(),
                                    etFullName.getText().toString(),
                                    etEmail.getText().toString(),
                                    etMobileNumber.getText().toString(),
                                    "default.jpg"

                            );
                            table_user.child(etUsername.getText().toString()).setValue(user);
                            Toast.makeText(RegisterActivity.this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
package umn.ac.id.tugasakhir_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import umn.ac.id.tugasakhir_android.Common.Common;
import umn.ac.id.tugasakhir_android.Model.User;

public class ForgetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Button cancel = findViewById(R.id.btnCancelForgetPass);
        Button forgetPass = findViewById(R.id.btnForgetPasswordForgetPass);
        EditText etUsername = findViewById(R.id.etUsernameForgetPass);
        EditText etEmail = findViewById(R.id.etEmailForgetPass);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(cancel);
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(ForgetPasswordActivity.this);
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(etUsername.getText().toString()).exists()) {
                            mDialog.dismiss();
                            User user = dataSnapshot.child(etUsername.getText().toString()).getValue(User.class);
                            if (user.getEmail().equals(etEmail.getText().toString()) && user.getUsername().equals(etUsername.getText().toString())) {
                                Toast.makeText(ForgetPasswordActivity.this, "Your new Password : resetpassword", Toast.LENGTH_SHORT).show();
                                table_user.child(etUsername.getText().toString()).child("password").setValue("resetpassword");
                                Intent main = new Intent(getApplicationContext(), MainActivity.class); //sementara resto home rusak.
                                Common.currentUser = user;
                                startActivity(main);
                                finish();
                            } else {
                                Toast.makeText(ForgetPasswordActivity.this, "Username or Email not match", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            mDialog.dismiss();
                            Toast.makeText(ForgetPasswordActivity.this, "Have you register?", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
            }
        });

    }
}
package umn.ac.id.tugasakhir_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import java.security.SecureRandom;

import umn.ac.id.tugasakhir_android.Common.Common;
import umn.ac.id.tugasakhir_android.Model.User;

public class ForgetPasswordActivity extends AppCompatActivity {

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    String randomString(int len){
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
    String newPass;
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

                if (etUsername.getText().toString().isEmpty()) {
                    etUsername.setError("Username is Required");
                    etUsername.requestFocus();
                    return;
                }
                if (etEmail.getText().toString().isEmpty()) {
                    etEmail.setError("Email is Required");
                    etEmail.requestFocus();
                    return;
                }

                final ProgressDialog mDialog = new ProgressDialog(ForgetPasswordActivity.this);
                mDialog.show();

                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(etUsername.getText().toString()).exists()) {
                            mDialog.dismiss();
                            User user = dataSnapshot.child(etUsername.getText().toString()).getValue(User.class);
                            if (user.getEmail().equals(etEmail.getText().toString()) && user.getUsername().equals(etUsername.getText().toString())) {
                                newPass = randomString(8);
                                //Toast.makeText(ForgetPasswordActivity.this, "Your new Password : " + newPass, Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPasswordActivity.this);
                                builder.setCancelable(true);
                                builder.setTitle("Your new Password, please save only show once");
                                builder.setMessage(newPass);
                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        Intent main = new Intent(getApplicationContext(), MainActivity.class); //sementara resto home rusak.
                                        startActivity(main);
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                                table_user.child(etUsername.getText().toString()).child("password").setValue(newPass);
                                Common.currentUser = user;

                                //finish();
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
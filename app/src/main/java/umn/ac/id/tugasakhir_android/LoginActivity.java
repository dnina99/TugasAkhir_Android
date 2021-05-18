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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import umn.ac.id.tugasakhir_android.Common.Common;
import umn.ac.id.tugasakhir_android.Model.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button forgetPass = findViewById(R.id.btnForgetPasswordLogin);
        Button login = findViewById(R.id.btnLoginLogin);
        EditText etUsername = findViewById(R.id.etUsernameLogin);
        EditText etPassword = findViewById(R.id.etPasswordLogin);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.show();

                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // check if user already exits  through phone number
                        if (dataSnapshot.child(etUsername.getText().toString()).exists()) {
                            mDialog.dismiss();
                            User user = dataSnapshot.child(etUsername.getText().toString()).getValue(User.class);
                            if(user.getPassword().equals(etPassword.getText().toString())){
                                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                if(user.getUsername().equals("admin".toString())){
                                    Intent resto = new Intent(getApplicationContext(), RestoHomeActivity.class); //sementara resto home rusak.
                                    Common.currentUser = user;
                                    startActivity(resto);
                                }else{
                                    Intent home = new Intent(getApplicationContext(), Home.class);
                                    Common.currentUser = user;
                                    startActivity(home);
                                }
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, "Password Incorect", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            mDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Have you register?", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgetPass = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
                startActivity(forgetPass);

            }
        });
    }
}
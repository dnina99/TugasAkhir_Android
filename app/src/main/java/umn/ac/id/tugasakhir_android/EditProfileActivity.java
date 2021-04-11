package umn.ac.id.tugasakhir_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import umn.ac.id.tugasakhir_android.Common.Common;
import umn.ac.id.tugasakhir_android.Model.User;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        EditText etName = findViewById(R.id.etNameEditProfile);
        EditText etPasswrod = findViewById(R.id.etPasswordEditProfile);
        EditText etRetype = findViewById(R.id.etRetypeEditProfile);
        Button btnSave = findViewById(R.id.btnSaveEditProfile);
        Button btnCancel = findViewById(R.id.btnCancelEditProfile);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        etName.setHint(Common.currentUser.getName());
        //etPasswrod.setHint(Common.currentUser.getMobileNumber());
        //etRetype.setHint(Common.currentUser.getEmail());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(EditProfileActivity.this);
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.child(Common.currentUser.getUsername()).getValue(User.class);
                        if (etPasswrod.getText().toString().equals(etRetype.getText().toString())) {
                            Toast.makeText(EditProfileActivity.this, "Your Password has been Changed", Toast.LENGTH_SHORT).show();
                            table_user.child(Common.currentUser.getUsername()).child("password").setValue(etPasswrod.getText().toString());
                            Intent home = new Intent(getApplicationContext(), Home.class); //sementara resto home rusak.
                            Common.currentUser = user;
                            startActivity(home);
                            finish();
                        } else {
                            Toast.makeText(EditProfileActivity.this, "Password not Match", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(getApplicationContext(), ViewProfileActivity.class);
                startActivity(cancel);
            }
        });

    }
}
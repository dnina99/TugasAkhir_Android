package umn.ac.id.tugasakhir_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import umn.ac.id.tugasakhir_android.Common.Common;
import umn.ac.id.tugasakhir_android.Model.User;

public class EditProfileActivity extends AppCompatActivity {

    ImageView ivPicture;
    public Uri imageUri;
    StorageReference folder;
    DatabaseReference table_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        EditText etName = findViewById(R.id.etNameEditProfile);
        EditText etPasswrod = findViewById(R.id.etPasswordEditProfile);
        EditText etRetype = findViewById(R.id.etRetypeEditProfile);
        ivPicture = findViewById(R.id.ivPictureEditProfile);
        Button btnSave = findViewById(R.id.btnSaveEditProfile);
        Button btnCancel = findViewById(R.id.btnCancelEditProfile);
        ImageButton btnCamera = findViewById(R.id.btnPictureEditProfile);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");
        StorageReference storage = FirebaseStorage.getInstance().getReference().child("User/"+Common.currentUser.getPicture());
        folder = FirebaseStorage.getInstance().getReference();

        etName.setText(Common.currentUser.getName());

        try {
            File local = File.createTempFile(Common.currentUser.getPicture(),"jpg");
            storage.getFile(local).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(local.getAbsolutePath());
                    ivPicture.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditProfileActivity.this, "Failed load photo", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                            table_user.child(Common.currentUser.getUsername()).child("name").setValue(etName.getText().toString());
                            Intent home = new Intent(getApplicationContext(), Home.class);
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

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //intent.setType("image/*");
                //intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            imageUri = data.getData();
            //Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.my_image);
            ivPicture.setImageURI(imageUri);


            //ivPicture.setImageURI(imageUri);
///////////////////
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Uploading Image...");
            pd.show();

            final String randomKey = UUID.randomUUID().toString();
            StorageReference upload = folder.child("User/" + randomKey + ".jpg");
            //StorageReference temp = FirebaseStorage.getInstance().getReference().child("User/" + randomKey + ".jpg");

            upload.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Toast.makeText(EditProfileActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfileActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            pd.setMessage("Progress : " + (int) progress + "%");
                        }
            });
////////////////


        }
    }


}
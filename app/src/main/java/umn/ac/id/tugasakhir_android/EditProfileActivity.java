package umn.ac.id.tugasakhir_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
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

import java.io.ByteArrayOutputStream;
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
    FirebaseDatabase database;
    String randomKey;

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

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle("Edit Profile");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");
        StorageReference storage = FirebaseStorage.getInstance().getReference().child("User/"+Common.currentUser.getPicture());
        folder = FirebaseStorage.getInstance().getReference();
        randomKey = Common.currentUser.getPicture();

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
                //table_user = database.getReference("User");
                ProgressDialog mDialog = new ProgressDialog(EditProfileActivity.this);
                mDialog.show();
                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.child(Common.currentUser.getUsername()).getValue(User.class);
                        if (etPasswrod.getText().toString().equals(etRetype.getText().toString())) {

                            table_user.child(Common.currentUser.getUsername()).child("password").setValue(etPasswrod.getText().toString());
                            table_user.child(Common.currentUser.getUsername()).child("name").setValue(etName.getText().toString());

                            if(randomKey == Common.currentUser.getPicture()){
                                //randomKey = Common.currentUser.getPicture();
                                table_user.child(Common.currentUser.getUsername()).child("picture").setValue(randomKey.toString());
                            }else{
                                table_user.child(Common.currentUser.getUsername()).child("picture").setValue(randomKey.toString()+".jpg");

                            }
                            Toast.makeText(EditProfileActivity.this, "Your Password has been Changed", Toast.LENGTH_SHORT).show();
                            Common.currentUser = user;
                            Intent home = new Intent(getApplicationContext(), Home.class);
                            startActivity(home);
                            finish();
                        } else {
                            Toast.makeText(EditProfileActivity.this, "Password not Match", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
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
                final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                builder.setTitle("Add Photo!");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo"))
                        {
                            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePicture, 0);
                        }
                        else if (options[item].equals("Choose from Gallery"))
                        {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 1);
                        }
                        else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0 :
                if(resultCode == Activity.RESULT_OK){
                    
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    ivPicture.setImageBitmap(imageBitmap);

                    final ProgressDialog pd = new ProgressDialog(this);
                    pd.setTitle("Uploading Image...");
                    pd.show();
                    randomKey = UUID.randomUUID().toString();

                    ivPicture.setDrawingCacheEnabled(true);
                    ivPicture.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) ivPicture.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] foto = baos.toByteArray();

                    StorageReference upload = folder.child("User/" + randomKey + ".jpg");
                    UploadTask uploadTask = upload.putBytes(foto);
                    uploadTask
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
                                    pd.dismiss();
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

                }
                break;

            case 1 :
                if(resultCode == Activity.RESULT_OK){
                    imageUri = data.getData();
                    ivPicture.setImageURI(imageUri);

                    final ProgressDialog pd = new ProgressDialog(this);
                    pd.setTitle("Uploading Image...");
                    pd.show();

                    randomKey = UUID.randomUUID().toString();
                    StorageReference upload = folder.child("User/" + randomKey + ".jpg");

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
                                    pd.dismiss();
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

                }
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
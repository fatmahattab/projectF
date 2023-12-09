package com.example.orderfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class addfood extends AppCompatActivity {
    ImageView uploadImage;
    Button saveButton;
    EditText foodname, fooddescrp,prix;
    String imageURL;
    Uri uri;
    Integer i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfood);
        uploadImage = findViewById(R.id.uploadImage);
        fooddescrp = findViewById(R.id.fooddescrp);
        foodname = findViewById(R.id.foodname);
        prix=findViewById(R.id.prix);
        i=0;
        saveButton = findViewById(R.id.saveButton);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImage.setImageURI(uri);
                        } else {
                            Toast.makeText(addfood.this, "No image selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }
        public void saveData(){

            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                    .child(uri.getLastPathSegment());
            AlertDialog.Builder builder = new AlertDialog.Builder(addfood.this);
            builder.setCancelable(false);
            builder.setView(R.layout.progress_layout);
            AlertDialog dialog = builder.create();
            dialog.show();
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask. TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete());
                    Uri urlImage = uriTask.getResult();
                    imageURL = urlImage.toString();

                    uploadFood();
                    dialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure (@NonNull Exception e) {
                    dialog.dismiss();
                }
            });
        }
public void uploadFood(){
        String nom=foodname.getText().toString();
    String description=fooddescrp.getText().toString();
    String prixx=prix.getText().toString();
    dataFood food=new dataFood(nom,description,imageURL, prixx);
    //String currentDate="";
 //  currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
 //  Toast.makeText(addfood.this,"hi:"+currentDate,Toast.LENGTH_SHORT).show();

    //Toast.makeText(addfood.this,"hi:"+i,Toast.LENGTH_SHORT).show();
    FirebaseDatabase.getInstance().getReference("categories").child(nom)
            .setValue(food).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText( addfood.this,  "Saved", Toast.LENGTH_SHORT).show();
                     //   i++;
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure (@NonNull Exception e) {
                    Toast.makeText( addfood.this, e.getMessage().toString() ,Toast.LENGTH_SHORT).show();
                }
            });

}

}

package alextexamplecom.salsa_company;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.builders.Actions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Tanzkursplan extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String filename = "mountains.jpg";
        FirebaseStorage mStorage = FirebaseStorage.getInstance();
        setContentView(R.layout.v_tanzkursplan);

        final ImageView image = (ImageView) findViewById(R.id.c_tanzkursplan_view);

        // question.imgQuestion  = "qimgs/-KiTpzP5t-xJOO5nSK0A/1493896460324-ch1pg2.jpg"
        final StorageReference ref = mStorage.getReference().child(filename);
        ref.getDownloadUrl().addOnSuccessListener(this, new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(Tanzkursplan.this)
                        .load(uri)
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .into(image);
            }
        });


        //TODO: Make filename dependant on user (tanzschule)
/*
        StorageReference storageReference =
                FirebaseStorage
                .getInstance()
                .getReference()
                .child(filename);



        // Load the image using Glide
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(storageReference.getDownloadUrl())
                .into(image);
    }*/
    }
}

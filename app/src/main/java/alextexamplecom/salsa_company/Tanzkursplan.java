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

public class Tanzkursplan {

    public void SetImage(final NavigationActivity nav, final ImageView image, String filename) {
        FirebaseStorage mStorage = FirebaseStorage.getInstance();

        // question.imgQuestion  = "qimgs/-KiTpzP5t-xJOO5nSK0A/1493896460324-ch1pg2.jpg"
        final StorageReference ref = mStorage.getReference().child(filename);
        ref.getDownloadUrl().addOnSuccessListener(nav, new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(nav)
                        .load(uri)
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .into(image);
            }
        });
    }
}

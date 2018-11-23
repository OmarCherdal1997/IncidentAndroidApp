package com.example.omar.project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SelectPhotoDialog extends DialogFragment {
    private static final String TAG ="SelectPhotoDialog" ;
    private static final int PICKFILE_REQUEST_CODE= 1259;
    private static final int CAMERA_REQUEST_CODE= 1299;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_selectphoto,container,false);
        TextView selectPhoto=(TextView)view.findViewById(R.id.dialogChoosePhoto);
        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Accessing phones memory");
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,PICKFILE_REQUEST_CODE);
            }
        });
        TextView takePhoto=(TextView)view.findViewById(R.id.dialogOpenCamera);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Starting Camera");
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent,CAMERA_REQUEST_CODE);
            }
        });

        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICKFILE_REQUEST_CODE && resultCode==Activity.RESULT_OK){
            Uri selectedImageUri=data.getData();
            Log.d(TAG,"onActivityResult: image uri "+selectedImageUri);
        }
        else if(requestCode==CAMERA_REQUEST_CODE && resultCode==Activity.RESULT_OK){
            Log.d(TAG,"onActivityResult: done taking new photo ");
            Bitmap bitmap;
            bitmap=(Bitmap) data.getExtras().get("DATA");
            DeclareActivity.camera_btn.setImageBitmap(bitmap);
        }
    }
}

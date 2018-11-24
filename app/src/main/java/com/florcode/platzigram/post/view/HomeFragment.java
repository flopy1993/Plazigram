package com.florcode.platzigram.post.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.florcode.platzigram.R;
import com.florcode.platzigram.adapter.PictureAdapterRecyclerView;
import com.florcode.platzigram.model.Picture;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.support.v4.content.FileProvider.getUriForFile;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final int REQUEST_CAMARA = 1;
    private FloatingActionButton fabCamera;
    private String photoPathTemp = "";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        showToolbar(getResources().getString(R.string.tab_home), false, view);
        RecyclerView picturesRecycler = view.findViewById(R.id.pictureRecycler);

        fabCamera = (FloatingActionButton) view.findViewById(R.id.fabCamera);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        picturesRecycler.setLayoutManager(linearLayoutManager);

        PictureAdapterRecyclerView pictureAdapterRecyclerView = new PictureAdapterRecyclerView(buidPictures(), R.layout.cardview_picture, getActivity());
        picturesRecycler.setAdapter(pictureAdapterRecyclerView);

        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        return view;
    }

    private void takePicture(){
        Intent intentTakePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentTakePicture, REQUEST_CAMARA);

        if(intentTakePicture.resolveActivity(getActivity().getPackageManager()) != null){
            File photoFile = null;
            try{
                photoFile = createImageFile();

            }catch(Exception e){
                e.printStackTrace();
            }

            if(photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(getActivity(), "com.florcode.platzigram", photoFile);//contenedor para el archivo de imagen
                //Uri photoUri = Uri.fromFile(photoFile);
                intentTakePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); //para que funcione hay que hacer xml

                startActivityForResult(intentTakePicture, REQUEST_CAMARA);
            }

        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HH-mm-ss")
                .format(new Date()); //formato de fecha
        String imageFileName = "JPEG" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES); //captura el directorio

        //File photo = new File(storageDir, imageFileName + ".jpg");

        File photo = File.createTempFile(imageFileName, ".jpg", storageDir); //nombre, extension, directorio

        photoPathTemp = "file:" + photo.getAbsolutePath(); //acceso a la imagen

        return photo;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(requestCode == REQUEST_CAMARA && resultCode == getActivity().RESULT_OK){
           Log.d("HomeFragment", "CAMERA OK!!");
           Intent i = new Intent(getActivity(), NewPostActivity.class);
           i.putExtra("PHOTO_PATH_TEMP", photoPathTemp);
           startActivity(i);

       }
    }

    public ArrayList<Picture> buidPictures(){
        ArrayList<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture("https://www.chiquipedia.com/imagenes/imagenes-amor08.jpg", "Uriel Ramirez", "6 días", "5 Me Gusta"));
        pictures.add(new Picture("https://www.chiquipedia.com/imagenes/imagenes-amor20.jpg", "Uriel Ramirez", "6 días", "5 Me Gusta"));
        pictures.add(new Picture("https://www.chiquipedia.com/imagenes/imagenes-amor18.jpg", "Uriel Ramirez", "6 días", "5 Me Gusta"));
        return pictures;
    }


    public void showToolbar(String tittle, boolean upButton, View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
}

package com.florcode.platzigram.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.florcode.platzigram.R;
import com.florcode.platzigram.adapter.PictureAdapterRecyclerView;
import com.florcode.platzigram.model.Picture;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView picturesRecyclerSearch = view.findViewById(R.id.pictureRecyclerSearch);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        picturesRecyclerSearch.setLayoutManager(gridLayoutManager);

        PictureAdapterRecyclerView pictureAdapterRecyclerView = new PictureAdapterRecyclerView(buidPicturesSearch(), R.layout.cardview_picture, getActivity());
        picturesRecyclerSearch.setAdapter(pictureAdapterRecyclerView);

        return view;
    }

    public ArrayList<Picture> buidPicturesSearch(){
        ArrayList<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture("https://www.chiquipedia.com/imagenes/imagenes-amor01.jpg", "Uriel Ramirez", "6 días", "5 Me Gusta"));
        pictures.add(new Picture("https://www.chiquipedia.com/imagenes/imagenes-amor02.jpg", "Uriel Ramirez", "6 días", "5 Me Gusta"));
        pictures.add(new Picture("https://www.chiquipedia.com/imagenes/imagenes-amor03.jpg", "Uriel Ramirez", "6 días", "5 Me Gusta"));
        pictures.add(new Picture("https://www.chiquipedia.com/imagenes/imagenes-amor01.jpg", "Uriel Ramirez", "6 días", "5 Me Gusta"));
        pictures.add(new Picture("https://www.chiquipedia.com/imagenes/imagenes-amor02.jpg", "Uriel Ramirez", "6 días", "5 Me Gusta"));
        pictures.add(new Picture("https://www.chiquipedia.com/imagenes/imagenes-amor03.jpg", "Uriel Ramirez", "6 días", "5 Me Gusta"));
        return pictures;
    }




}

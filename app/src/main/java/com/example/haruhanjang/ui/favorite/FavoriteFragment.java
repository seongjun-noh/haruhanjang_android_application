package com.example.haruhanjang.ui.favorite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haruhanjang.DiaryData;
import com.example.haruhanjang.MyDBHelper;
import com.example.haruhanjang.R;
import com.example.haruhanjang.RecyclerViewAdapter;
import com.example.haruhanjang.databinding.FragmentFavoriteBinding;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdaptor;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<DiaryData> myDataset;

    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoriteViewModel favoriteViewModel =
                new ViewModelProvider(this).get(FavoriteViewModel.class);

        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        myDataset = new ArrayList();

        myDataset = new ArrayList();
        myDBHelper = new MyDBHelper(getActivity().getBaseContext());
        sqlDB = myDBHelper.getReadableDatabase();

        Cursor cursor = sqlDB.rawQuery("SELECT * FROM diary WHERE isFavorite = 1;", null);

        while (cursor.moveToNext()) {
            myDataset.add(new DiaryData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5)));
        }

        sqlDB.close();

        recyclerView = (RecyclerView) root.findViewById(R.id.grid_recyclerView);
        recyclerViewAdaptor = new RecyclerViewAdapter(getActivity(), myDataset);
        layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdaptor);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.vindys.musicorganiser.main;

import android.os.Bundle;
import android.view.View;

import com.vindys.musicorganiser.R;
import com.vindys.musicorganiser.data.model.SortedSongs;
import com.vindys.musicorganiser.databinding.ActivityMainBinding;
import com.vindys.musicorganiser.databinding.CustomToolbarMainBinding;
import com.vindys.musicorganiser.main.adapter.VerticalRecyclerAdapter;
import com.vindys.musicorganiser.main.viewmodel.SongsViewModel;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity{
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_ASK_LOCATION_PERMISSION = 100;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    ActivityMainBinding activityMainBinding;
    private SongsViewModel viewModel;
    private VerticalRecyclerAdapter verticalRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        /*AppCompatSpinner spinner = view.findViewById(R.id.filter_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                Toast.makeText(MainActivity.this,
                        "OnItemSelectedListener : " + adapterView.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
        initDataBinding();
        setToolbar();
        observeViewModel();
        //viewModel.getData();
    }

    private void setToolbar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar_main);
        View view =getSupportActionBar().getCustomView();

        CustomToolbarMainBinding customToolbarMainBinding = CustomToolbarMainBinding.bind(view);
        customToolbarMainBinding.setSongsViewModel(viewModel);
    }

    private void initDataBinding() {
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this,
                viewModelFactory).get(SongsViewModel.class);

        observeViewModel();
        initializeRecyclerView(activityMainBinding);
        activityMainBinding.setSongsViewModel(viewModel);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    private void observeViewModel(){

        viewModel.getMusicListMediatorLiveData().observe(this, new Observer<List<SortedSongs>>() {
            @Override
            public void onChanged(List<SortedSongs> sortedSongs) {
                if(sortedSongs!=null)
                    verticalRecyclerAdapter.setSongs(sortedSongs);
            }
        });

        viewModel.getSpanCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer > 1)
                    verticalRecyclerAdapter.setSpanCount(integer);
            }
        });

    }


    private void initializeRecyclerView(ActivityMainBinding sampleActivityBinding){
        verticalRecyclerAdapter = new VerticalRecyclerAdapter();
        sampleActivityBinding.weatherList.setLayoutManager(new LinearLayoutManager(this));
        sampleActivityBinding.weatherList.setHasFixedSize(false);
        sampleActivityBinding.weatherList.setAdapter(verticalRecyclerAdapter);

    }
}

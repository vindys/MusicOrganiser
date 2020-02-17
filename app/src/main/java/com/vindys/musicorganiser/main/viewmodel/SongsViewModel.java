package com.vindys.musicorganiser.main.viewmodel;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.vindys.musicorganiser.data.Resource;
import com.vindys.musicorganiser.data.local.entity.MusicList;
import com.vindys.musicorganiser.data.local.entity.Song;
import com.vindys.musicorganiser.data.model.SortedSongs;
import com.vindys.musicorganiser.data.repository.SongsRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class SongsViewModel extends ViewModel {
    private static final String TAG = "SongsViewModel";

    @Inject
    SongsRepository songsRepository;
    private MutableLiveData<Resource<MusicList>> musicListLiveData;
    private MediatorLiveData<List<SortedSongs>> musicListMediatorLiveData;
    private MutableLiveData<String> selectedType;

    public MutableLiveData<Integer> getSpanCount() {
        return spanCount;
    }

    public void setSpanCount(Integer spanCount) {
        this.spanCount.setValue(spanCount);
    }

    private MutableLiveData<Integer> spanCount;

    @Inject
    SongsViewModel(SongsRepository songsRepository) {
        super();
        musicListMediatorLiveData = new MediatorLiveData<>();
        this.songsRepository = songsRepository;
        selectedType = new MutableLiveData<>();
        spanCount = new MutableLiveData<>();

        musicListLiveData = new MutableLiveData<>();

        musicListMediatorLiveData.addSource(selectedType, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(musicListLiveData.getValue()!= null &&musicListLiveData.getValue().data!=null)
                    musicListMediatorLiveData.setValue(getSongs(musicListLiveData.getValue().data,s));
            }
        });


        musicListMediatorLiveData.addSource(songsRepository.getSongs(), new Observer<Resource<MusicList>>() {
            @Override
            public void onChanged(Resource<MusicList> musicListResource) {
                if(musicListResource!=null && musicListResource.data!=null) {
                    musicListLiveData.setValue(musicListResource);
                    musicListMediatorLiveData.setValue(getSongs(musicListResource.data, selectedType.getValue()));
                }
            }
        });
    }

    //Handle Spinner's change events
    public void onTypeSelected(AdapterView<?> parent, View view, int pos, long id){
        if((TextView)view!=null) {
            selectedType.setValue( ((TextView) view).getText().toString());

        }
    }

    public void onSpanSelected(AdapterView<?> parent, View view, int pos, long id){
        if((TextView)view!=null) {
            setSpanCount( (Integer.valueOf(((TextView) view).getText().toString())));
        }
    }

    public MediatorLiveData<List<SortedSongs>> getMusicListMediatorLiveData() {
        return musicListMediatorLiveData;
    }

    private List<SortedSongs> getSongs(MusicList musicList, String sortKey){
        Map<String, List<Song>> result ;
        if(sortKey.equals("Album"))
            result = musicList.getSongs().stream()
                .collect(Collectors.groupingBy(Song::getAlbum));
        else
            result = musicList.getSongs().stream()
                    .collect(Collectors.groupingBy(Song::getArtist));

        return result.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey()))
                .map(e -> new SortedSongs(e.getKey(), e.getValue())).collect(Collectors.toList());
    }

}

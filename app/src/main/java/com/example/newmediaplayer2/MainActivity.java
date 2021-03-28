package com.example.newmediaplayer2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity<OnRequestPermissionsResult> extends AppCompatActivity {
    ListView recyclerView;
    SearchView searchView;
    ArrayList<song> songs;
    ArrayList<String> s;
    song_adaptor song_adaptor;
    static int req = 1;

    void calling() {
        recyclerView = findViewById(R.id.itmes);
        searchView = findViewById(R.id.search_view);

    }

    public static ArrayList<String> play_song2(Context context) {
        ArrayList<String> tempaudiolist = new ArrayList<String>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {

                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST,
        };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String album = cursor.getString(0);
                String title = cursor.getString(1);
                String duration = cursor.getString(2);
                String data = cursor.getString(3);
                String Artist = cursor.getString(4);
                tempaudiolist.add(title);

            }
            cursor.close();

        }
        return tempaudiolist;
    }

    public static ArrayList<song> play_song(Context context) {
        ArrayList<song> tempaudiolist = new ArrayList<song>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {

                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST,
        };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String album = cursor.getString(0);
                String title = cursor.getString(1);
                String duration = cursor.getString(2);
                String data = cursor.getString(3);
                String Artist = cursor.getString(4);

                tempaudiolist.add(new song(title, Artist, data, album));
            }
            cursor.close();

        }
        return tempaudiolist;
    }

    void permission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, req);
        } else {
            songs = play_song(this);
            s = play_song2(this);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calling();
        permission();
    //    song_adaptor = new song_adaptor(this, songs);
        ArrayAdapter a = new ArrayAdapter(this, R.layout.song, R.id.Song, s);
        recyclerView.setAdapter(a);
        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                song song = songs.get(position);
                startActivity(new Intent(MainActivity.this, MainActivity2.class)
                        .putExtra("song", song)
                        .putExtra("all", songs)
                        .putExtra("position", position)
                );
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                a.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                a.getFilter().filter(newText);
                return false;
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == req) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, req);

            } else {
                songs = play_song(this);
                s = play_song2(this);

            }


        }
    }


}
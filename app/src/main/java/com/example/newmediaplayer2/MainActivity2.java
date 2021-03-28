package com.example.newmediaplayer2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity2<OnOptionItem> extends AppCompatActivity implements View.OnClickListener {
    ImageButton play, next, previous;
    SeekBar seekBar;
    TextView songname, songartist, starttime, endtime;
    MediaPlayer mediaPlayer;
    ArrayList<song> songs;
    static int position;
    song song;

    void calling() {
        play = findViewById(R.id.play_pause);
        //  song_pic = findViewById(R.id.imageView);
        seekBar = findViewById(R.id.seekBar);
        songname = findViewById(R.id.Song_Name);
        songartist = findViewById(R.id.Artist_Name);
        starttime = findViewById(R.id.start_time);
        endtime = findViewById(R.id.end_time);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);

    }

    void hand() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        songs = (ArrayList) b.getParcelableArrayList("all");
        position = b.getInt("position");
        song = songs.get(position);
        mediaPlayer = new MediaPlayer();
        songname.setText(song.getTitle());
        songartist.setText(song.getArtist());
        try {
            mediaPlayer.setDataSource(song.getData());
            mediaPlayer.prepare();

        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        mediaPlayer.seekTo(0);
        mediaPlayer.setLooping(true);
        String time = convert_meills(mediaPlayer.getDuration());
        endtime.setText(time);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {

                        try {

                            String time_curr = convert_meills(mediaPlayer.getCurrentPosition());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    starttime.setText(time_curr);
                                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                                }
                            });

                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        try {
                            final double current = mediaPlayer.getCurrentPosition();
                            final String elapsedTime = convert_meills((int) current);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    starttime.setText(elapsedTime);
                                    seekBar.setProgress((int) current);
                                }
                            });

                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
        }).start();

        play.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);


    }

    String convert_meills(int milles) {
        String time = "";
        int min = (milles / 1000 / 60);
        int seconds = (milles / 1000 % 60);
        time += min + ":";
        if (seconds < 10) {
            time += "0" + seconds;
        } else {
            time += seconds;
        }
        return time;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        calling();
        hand();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.play_pause) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                play.setImageResource(R.drawable.ic_play);
            } else {
                mediaPlayer.start();
                play.setImageResource(R.drawable.ic_pause);


            }
        }
        else if (v.getId() == R.id.next && position < songs.size()) {

            mediaPlayer.stop();
            seekBar.setProgress(0);
            starttime.setText("0:00");
            play.setImageResource(R.drawable.ic_pause);
            song = songs.get(position++);
            song = songs.get(position);
            mediaPlayer = new MediaPlayer();
            songname.setText(song.getAlbums());
            songartist.setText(song.getArtist());
            try {
                mediaPlayer.setDataSource(song.getData());
                mediaPlayer.prepare();

            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            mediaPlayer.seekTo(0);
            mediaPlayer.setLooping(true);
            String time = convert_meills(mediaPlayer.getDuration());
            endtime.setText(time);
            seekBar.setMax(mediaPlayer.getDuration());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress);
                        seekBar.setProgress(progress);
                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {

                            try {

                                String time_curr = convert_meills(mediaPlayer.getCurrentPosition());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        starttime.setText(time_curr);
                                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                                    }
                                });

                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            try {
                                final double current = mediaPlayer.getCurrentPosition();
                                final String elapsedTime = convert_meills((int) current);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        starttime.setText(elapsedTime);
                                        seekBar.setProgress((int) current);
                                    }
                                });

                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                            }
                        }
                    }
                }
            }).start();
            mediaPlayer.start();


        }
        else if (v.getId() == R.id.previous && position > 0) {
            mediaPlayer.stop();
            seekBar.setProgress(0);
            starttime.setText("0:00");
            play.setImageResource(R.drawable.ic_pause);
            song = songs.get(position--);
            song = songs.get(position);
            mediaPlayer = new MediaPlayer();
            songname.setText(song.getTitle());
            songartist.setText(song.getArtist());
            try {
                mediaPlayer.setDataSource(song.getData());
                mediaPlayer.prepare();

            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            mediaPlayer.seekTo(0);
            mediaPlayer.setLooping(true);
            String time = convert_meills(mediaPlayer.getDuration());
            endtime.setText(time);
            seekBar.setMax(mediaPlayer.getDuration());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress);
                        seekBar.setProgress(progress);
                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {

                            try {

                                String time_curr = convert_meills(mediaPlayer.getCurrentPosition());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        starttime.setText(time_curr);
                                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                                    }
                                });

                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            try {
                                final double current = mediaPlayer.getCurrentPosition();
                                final String elapsedTime = convert_meills((int) current);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        starttime.setText(elapsedTime);
                                        seekBar.setProgress((int) current);
                                    }
                                });

                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                            }
                        }
                    }
                }
            }).start();
            mediaPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }
        return super.onOptionsItemSelected(item);
    }


}
package org.esiea.dagniau.crespin.musicseverywhere;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin Crespin et Marie Dagniau on 08/12/2014.
 */
public class MainActivity extends Activity{
    MediaPlayer mediaPlayer;
    String urlMusic = "/storage/extSdCard/Music/Workout Motivation Music.mp3";
    //TextView titleSong = (TextView) findViewById(R.id.titleSong);
    List<String> favoriteMusics = new ArrayList<String>();
    List<String> deleteMusics = new ArrayList<String>();

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = this;
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(urlMusic);
            mediaPlayer.prepare();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }


    }

    public void playMusic(View v){
      mediaPlayer.start();
    }

    public void pauseMusic(View v){
        mediaPlayer.pause();
    }

    /*public void likeMusic(View v){
        favoriteMusics.add(titleSong.getText().toString());
    }


    public void dislikeMusic(View v){
        favoriteMusics.add(titleSong.getText().toString());
    }*/

}

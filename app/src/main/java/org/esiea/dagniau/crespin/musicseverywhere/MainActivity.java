package org.esiea.dagniau.crespin.musicseverywhere;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * Created by Benjamin Crespin et Marie Dagniau on 08/12/2014.
 */
public class MainActivity extends Activity{
    MediaPlayer mediaPlayer;
    String urlMusic = null;
    String singerstring = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onClickTest(View v){
        EditText singer = (EditText) findViewById(R.id.Research);

        singerstring = singer.getText().toString();
        new DLTask().execute("http://api.deezer.com/search?q=" + singerstring);

    }

    private class DLTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground (String... urls){
            try {
                return downloadUrl(urls[0]);
            }catch(IOException e){
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        @Override
        protected void onPostExecute(String result){
            try {
                JSONObject js = new JSONObject(result);
                urlMusic = js.getJSONArray("data").getJSONObject(0).getString("preview");
                String titrechanson = js.getJSONArray("data").getJSONObject(0).getString("title");
                String titrealbum = js.getJSONArray("data").getJSONObject(0).getJSONObject("album").getString("title");
                String jstexttotal = "Titre d'une chanson - " + titrechanson +"\nTitre d'un album - "+ titrealbum ;
                ((TextView) findViewById(R.id.DLTaskText)).setText(jstexttotal);
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
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String downloadUrl(String myurl) throws IOException{
        InputStream is = null;

        try{
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            is = conn.getInputStream();

            return readIt(is, 80000);
        } finally {
            if (is != null){
                is.close();
            }
        }
    }

    private String readIt(InputStream is, int i)throws IOException{
        Reader reader;
        reader = new InputStreamReader(is, "UTF-8");
        char[] buffer = new char[i];
        reader.read(buffer);
        return new String(buffer);
    }

    public void playMusic(View v){
      mediaPlayer.start();
    }

    public void pauseMusic(View v){
        mediaPlayer.pause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Les Settings ne sont pas encore disponibles car en cours de développement.", Toast.LENGTH_LONG).show();

            return true;
        }
        if (id == R.id.action_test1) {

            MyDialog mydialog = new MyDialog();
            mydialog.show(getFragmentManager(), "Benjamin CRESPIN Marie DAGNIAU");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class MyDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("© 2015 - Benjamin CRESPIN" +" - "+
                    " Marie DAGNIAU");
            return builder.create();
        }

    }

}

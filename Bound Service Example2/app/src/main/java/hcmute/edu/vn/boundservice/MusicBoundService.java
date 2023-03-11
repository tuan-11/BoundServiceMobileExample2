package hcmute.edu.vn.boundservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import androidx.annotation.NonNull;


public class MusicBoundService extends Service {

    public static final int MSG_PLAY_MUSIC = 1;
    private MediaPlayer mMediaPlayer;
    private Messenger mMessenger;

    public class MyHandler extends Handler {
        private Context applicationContext;
        public MyHandler(Context context){
            this.applicationContext = context.getApplicationContext();
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MSG_PLAY_MUSIC:
                    startMusic();
                    break;

                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.e("MusicBoundService","onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("MusicBoundService","onBind");
        mMessenger = new Messenger(new MyHandler(this));
        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent){
        Log.e("MusicBoundService","onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e("MusicBoundService","onDestroy");
        super.onDestroy();
        if(mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void startMusic(){
        if(mMediaPlayer == null){
            mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.file_music);
        }
        mMediaPlayer.start();
    }
}

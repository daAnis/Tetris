package ru.ssau.fiit.tetris;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler {

	private MediaPlayer mediaPlayer;

	protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			finish();
		}
	};

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		Bundle arguments = getIntent().getExtras();
		if (arguments == null) return;
		//получаем выбранный стакан
		Glass glass = (Glass) arguments.getSerializable(Glass.class.getSimpleName());
		//получаем выбранный аудио фрагмент
		Audio audio = (Audio) arguments.getSerializable(Audio.class.getSimpleName());
		//запускаем его на воспроизведение
		if ((audio.getUriSerializable() != null)&&(SettingsActivity.musicOnOff_show)) {
			mediaPlayer = MediaPlayer.create(this, Uri.parse(audio.getUriSerializable()));
			mediaPlayer.start();
			mediaPlayer.setLooping(true);
		}
		//устанавливаем значения в соответствии с настройками
		byte id;
		if (SettingsActivity.results_show == R.id.rb_points) id = 0;
		else if (SettingsActivity.results_show == R.id.rb_time) id = 1;
		else id = 2;
		initialize(new Tetris(this,
				glass.getWidth(), glass.getHeight(), glass.getPoints_k(), glass.getSpeed_k(),
				SettingsActivity.nextF_show, id), config);
	}

	@Override
	public void onGameClosed(int score, long time) {
		if (mediaPlayer != null)
			mediaPlayer.stop();
		PlayerActivity.setScore(score, time);
		handler.sendEmptyMessage(score);
	}

	@Override
	public void onGamePaused() {
		if (mediaPlayer != null)
			mediaPlayer.pause();
	}
}

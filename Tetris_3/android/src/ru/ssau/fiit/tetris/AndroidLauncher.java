package ru.ssau.fiit.tetris;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler {

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
		initialize(new Tetris(this), config);
	}

	@Override
	public void closeGame(int score, long time) {
		PlayerActivity.setScore(score, time);
		handler.sendEmptyMessage(score);
	}
}

package ru.ssau.fiit.tetris;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

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
		Bundle arguments = getIntent().getExtras();
		if (arguments == null) return;
		Glass glass = (Glass) arguments.getSerializable(Glass.class.getSimpleName());
		initialize(new Tetris(this, glass.getWidth(), glass.getHeight()), config);
	}

	@Override
	public void closeGame(int score, long time) {
		PlayerActivity.setScore(score, time);
		handler.sendEmptyMessage(score);
	}
}

package com.me.myopgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.me.myopgame.MyOpGame;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = MyOpGame.MY_TITLE + " v" + MyOpGame.MY_VERSION;
		cfg.vSyncEnabled = true;
		cfg.useGL20 = true;
		cfg.width = MyOpGame.MY_APP_WINDOW_WIDTH_IN_PIXELS;
		cfg.height = MyOpGame.MY_APP_WINDOW_HEIGHT_IN_PIXELS;
		
		new LwjglApplication(new MyOpGame(), cfg);
	}
}

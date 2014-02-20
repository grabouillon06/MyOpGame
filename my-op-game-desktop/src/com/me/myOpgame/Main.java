package com.me.myopgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "my-op-game";
		cfg.useGL20 = false;
		cfg.width = MyOpGame.MY_APP_WINDOW_WIDTH;
		cfg.height = MyOpGame.MY_APP_WINDOW_HEIGHT;
		
		new LwjglApplication(new MyOpGame(), cfg);
	}
}

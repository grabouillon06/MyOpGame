package com.me.myopgame;

import com.badlogic.gdx.Game;

public class MyOpGame extends Game {

	public static final String MY_TITLE = "my-op1-game";
	public static final String MY_VERSION = "0.0.0.0";
	
	public static int MY_WORLD_WIDTH = 8192;
	public static int MY_WORLD_HEIGHT = 4096;
	public static int MY_MINIMAP_WIDTH = 256;
	public static int MY_MINIMAP_HEIGHT = 128;
	public static int MY_APP_WINDOW_WIDTH = 1024;
	public static int MY_APP_WINDOW_HEIGHT = 512 + MY_MINIMAP_HEIGHT;
	public static int MY_MINIMAP_SCALE_FACTOR = MY_WORLD_WIDTH / MY_MINIMAP_WIDTH;
	public static int MY_ORIGINAL_CAMERA_POSITION_X = MY_APP_WINDOW_WIDTH/2;
	public static int MY_ORIGINAL_CAMERA_POSITION_Y = MY_APP_WINDOW_HEIGHT/2;
	public static final String LOG = "MyOp0Game";
	
	MyScreen0 my_screen_0;
	
	@Override
	public void create() {
		
		// allocate screen
		my_screen_0 = new MyScreen0(this);
		
		// set current screen
		setScreen(my_screen_0);
	}

	@Override
    public void render() {
        super.render();
    }	
}

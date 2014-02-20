package com.me.myopgame;

import com.badlogic.gdx.Game;

public class MyOpGame extends Game {

	public static int MY_APP_WINDOW_WIDTH = 512;
	public static int MY_APP_WINDOW_HEIGHT = 512;
	public static int MY_WORLD_WIDTH = 2048;
	public static int MY_WORLD_HEIGHT = 2048;
	public static int MY_MINIMAP_WIDTH = 256;
	public static int MY_MINIMAP_HEIGHT = 256;
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

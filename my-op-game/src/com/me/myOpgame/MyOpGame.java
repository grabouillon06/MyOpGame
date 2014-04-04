package com.me.myopgame;

import com.badlogic.gdx.Game;

public class MyOpGame extends Game {

	public static final String MY_TITLE = "my-op-game";
	public static final String MY_VERSION = "0.6.0.0";
	public static final String LOG = "MyOpGameLog";
	
	public static int MY_TILE_SIZE_IN_PIXELS = 32;
	
	public static int MY_WORLD_WIDTH_IN_PIXELS = 8192;
	public static int MY_WORLD_HEIGHT_IN_PIXELS = 4096;
	public static int MY_MINIMAP_WIDTH_IN_PIXELS = 256;
	public static int MY_MINIMAP_HEIGHT_IN_PIXELS = 128;
	public static int MY_APP_WINDOW_WIDTH_IN_PIXELS = 1024;
	public static int MY_APP_WINDOW_HEIGHT_IN_PIXELS = 512 + MY_MINIMAP_HEIGHT_IN_PIXELS;
	public static int MY_ORIGINAL_CAMERA_POSITION_X_IN_PIXELS = MY_APP_WINDOW_WIDTH_IN_PIXELS/2;
	public static int MY_ORIGINAL_CAMERA_POSITION_Y_IN_PIXELS = MY_APP_WINDOW_HEIGHT_IN_PIXELS/2;	

	public static int MY_WORLD_WIDTH_IN_TILES = MY_WORLD_WIDTH_IN_PIXELS/MY_TILE_SIZE_IN_PIXELS;
	public static int MY_WORLD_HEIGHT_IN_TILES = MY_WORLD_HEIGHT_IN_PIXELS/MY_TILE_SIZE_IN_PIXELS;
	public static int MY_MINIMAP_WIDTH_IN_TILES = MY_MINIMAP_WIDTH_IN_PIXELS/MY_TILE_SIZE_IN_PIXELS;
	public static int MY_MINIMAP_HEIGHT_IN_TILES = MY_MINIMAP_HEIGHT_IN_PIXELS/MY_TILE_SIZE_IN_PIXELS;
	public static int MY_APP_WINDOW_WIDTH_IN_TILES = MY_APP_WINDOW_WIDTH_IN_PIXELS/MY_TILE_SIZE_IN_PIXELS;
	public static int MY_APP_WINDOW_HEIGHT_IN_TILES = MY_APP_WINDOW_HEIGHT_IN_PIXELS/MY_TILE_SIZE_IN_PIXELS;
	public static int MY_ORIGINAL_CAMERA_POSITION_X_IN_TILES = MY_ORIGINAL_CAMERA_POSITION_X_IN_PIXELS/MY_TILE_SIZE_IN_PIXELS;
	public static int MY_ORIGINAL_CAMERA_POSITION_Y_IN_TILES = MY_ORIGINAL_CAMERA_POSITION_Y_IN_PIXELS/MY_TILE_SIZE_IN_PIXELS;
	
	public static int MY_MINIMAP_SCALE_FACTOR = MY_WORLD_WIDTH_IN_PIXELS / MY_MINIMAP_WIDTH_IN_PIXELS;
	public static int MY_ACTOR_SPEED_FACTOR = 8;

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

package com.me.myopgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MyScreen0 implements Screen{
	
	protected MyOpGame game;
	protected Label fpsLabel;
	protected Label GLabel;
	protected Label HLabel;
	protected Label FLabel;
	protected Label ILabel;
    protected Skin skin;
    protected TiledMap map;
    protected OrthogonalTiledMapRenderer renderer;
    protected Stage stage0;
    protected MyButton0 button0_0;
    protected MyButton0 button0_1;
    protected MyButton0 button0_2;
    protected MyActor0 actor0_0;
    protected MyActor0 actor0_1;
    protected MyActor0 actor0_2;
    protected MyActor1 actor1_0;
    protected MyActor2 actor2_0;
    protected boolean gogogo;
    
    public MyScreen0(MyOpGame game) {
    	
        // link screen to game
    	this.game = game;
    	
    	// initialize gogogo button to false, nothing will move at creation
    	this.gogogo = false;
    	
    	// allocate skin
    	skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    	
        // allocate stage; viewport size maps game window size
        this.stage0 = new Stage( Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), true );
        
        // allocate actor0_0 and add to stage
        this.actor0_0 = new MyActor0(this, 0*MyOpGame.MY_TILE_SIZE_IN_PIXELS, 0*MyOpGame.MY_TILE_SIZE_IN_PIXELS + MyOpGame.MY_MINIMAP_HEIGHT_IN_PIXELS);
        this.stage0.addActor(this.actor0_0);

        // allocate actor0_1 and add to stage, actor1 is placed next to actor0
        this.actor0_1 = new MyActor0(this, 255*MyOpGame.MY_TILE_SIZE_IN_PIXELS, 127*MyOpGame.MY_TILE_SIZE_IN_PIXELS + MyOpGame.MY_MINIMAP_HEIGHT_IN_PIXELS);
        this.stage0.addActor(this.actor0_1);
                
        // allocate actor0_2 and add to stage, actor1 is placed next to actor0 -> this is the target for now
        this.actor0_2 = new MyActor0(this, 55*MyOpGame.MY_TILE_SIZE_IN_PIXELS, 7*MyOpGame.MY_TILE_SIZE_IN_PIXELS + MyOpGame.MY_MINIMAP_HEIGHT_IN_PIXELS);
        this.stage0.addActor(this.actor0_2);
        
        // set target for actor0_0
        this.actor0_0.aPathSetTarget((int)(this.actor0_2.getX()),(int)(this.actor0_2.getY()));
                
        // allocate actor2_0 and add to stage
        this.actor2_0 = new MyActor2();
        this.actor2_0.setPosition(0, 0);
        this.stage0.addActor(this.actor2_0);

        // allocate actor1_0 and add to stage
        this.actor1_0 = new MyActor1();
        this.actor1_0.setPosition(0, 0);
        this.stage0.addActor(this.actor1_0);
        
        // allocate button0_0 and add to stage
    	button0_0 = new MyButton0(this,"map0", skin, "default",new Vector2(128 + 128,98));
        stage0.addActor(button0_0);        

        // allocate button0_1 and add to stage
    	button0_1 = new MyButton0(this,"map1", skin, "default",new Vector2(128 + 128,68));
        stage0.addActor(button0_1);        

        // allocate button0_2 and add to stage
    	button0_2 = new MyButton0(this,"go go go !!!", skin, "default",new Vector2(128 + 128,38));
        stage0.addActor(button0_2);        

        // allocate fps label
        fpsLabel = new Label( "FPS: ", skin);
        fpsLabel.setPosition(128 + 128, 0 );
        fpsLabel.setColor(0, 0, 0, 1);
        stage0.addActor( fpsLabel );    	
        
        // print G, H, F, I positions in square
        GLabel = new Label( "G", skin);
        GLabel.setPosition(128 + 128 + 64, 0 );
        GLabel.setFontScale((float) 0.75);
        GLabel.setColor(0, 0, 0, 1);
        stage0.addActor( GLabel );    	
        HLabel = new Label( "H", skin);
        HLabel.setPosition(128 + 128 + 64 + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16, 0 );
        HLabel.setFontScale((float) 0.75);
        HLabel.setColor(0, 0, 0, 1);
        stage0.addActor( HLabel );    	
        FLabel = new Label( "F", skin);
        FLabel.setPosition(128 + 128 + 64, 0 + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16 );
        FLabel.setFontScale((float) 0.75);
        FLabel.setColor(0, 0, 0, 1);
        stage0.addActor( FLabel );    	
        ILabel = new Label( "I", skin);
        ILabel.setPosition(128 + 128 + 64 + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16, 0 + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16 );
        ILabel.setFontScale((float) 0.75);
        ILabel.setColor(0, 0, 0, 1);
        stage0.addActor( ILabel );    	

        
        Gdx.input.setInputProcessor(stage0);
    }
    
    @Override
	public void render(float delta) {
    	
    	int lvTranslateX = 0;
    	int lvTranslateY = 0;
    	
		// the following code clears the screen with the given RGB color (green)
        Gdx.gl.glClearColor( 0f, 0f, 1f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );	
        
        // render orthogonal map once a map button has been pressed only
        if (renderer != null)
        {
        	renderer.setView((OrthographicCamera) this.stage0.getCamera());
        	renderer.render();
        }
        
        // check if mouse pressed
        if (Gdx.input.isTouched())
        {
        	// check click is inside the map, then check the direction to move screen
        	if (MyOpGame.MY_APP_WINDOW_HEIGHT_IN_PIXELS - Gdx.input.getY() >= MyOpGame.MY_MINIMAP_HEIGHT_IN_PIXELS)
        	{
        		if (Gdx.input.getX() >= (MyOpGame.MY_ORIGINAL_CAMERA_POSITION_X_IN_PIXELS * 1.15))
        		{
        			if (this.stage0.getCamera().position.x - MyOpGame.MY_ORIGINAL_CAMERA_POSITION_X_IN_PIXELS < MyOpGame.MY_WORLD_WIDTH_IN_PIXELS - MyOpGame.MY_APP_WINDOW_WIDTH_IN_PIXELS)
        				lvTranslateX = 32;
        		}
        		else if (Gdx.input.getX() < (MyOpGame.MY_ORIGINAL_CAMERA_POSITION_X_IN_PIXELS * 0.85))
        		{
        			if (this.stage0.getCamera().position.x - MyOpGame.MY_ORIGINAL_CAMERA_POSITION_X_IN_PIXELS > 0)
        				lvTranslateX = -32;
        		}

        		if (MyOpGame.MY_APP_WINDOW_HEIGHT_IN_PIXELS - Gdx.input.getY() >= MyOpGame.MY_MINIMAP_HEIGHT_IN_PIXELS + (MyOpGame.MY_ORIGINAL_CAMERA_POSITION_Y_IN_PIXELS * 1.05))
        		{
        			if (this.stage0.getCamera().position.y - MyOpGame.MY_ORIGINAL_CAMERA_POSITION_Y_IN_PIXELS < MyOpGame.MY_WORLD_HEIGHT_IN_PIXELS - MyOpGame.MY_APP_WINDOW_HEIGHT_IN_PIXELS + MyOpGame.MY_MINIMAP_HEIGHT_IN_PIXELS )
        				lvTranslateY = 32;
        		}
        		else if ((MyOpGame.MY_APP_WINDOW_HEIGHT_IN_PIXELS - Gdx.input.getY() < (MyOpGame.MY_ORIGINAL_CAMERA_POSITION_Y_IN_PIXELS * 0.95)) &&(MyOpGame.MY_APP_WINDOW_HEIGHT_IN_PIXELS - Gdx.input.getY() > MyOpGame.MY_MINIMAP_HEIGHT_IN_PIXELS))
        		{
        			if (this.stage0.getCamera().position.y - MyOpGame.MY_ORIGINAL_CAMERA_POSITION_Y_IN_PIXELS > 0)
        				lvTranslateY = -32;
        		}
        	}
            
        	if (lvTranslateX != 0 || lvTranslateY != 0)
        	{
	        	// translate stage camera to go from actor0 to actor1
		        this.stage0.getCamera().translate(lvTranslateX, lvTranslateY, 0);
	          
		        // update actor2_0 (ctrl panel) location to move with camera 
		        this.actor2_0.translate(lvTranslateX,lvTranslateY);

		        // update actor1_0 (minimap) location to move with camera 
		        this.actor1_0.translate(lvTranslateX,lvTranslateY);
		        
		        // update button0_0 (button) location to move with camera 
		        this.button0_0.translate(lvTranslateX,lvTranslateY);
		        
		        // update button0_1 (button) location to move with camera 
		        this.button0_1.translate(lvTranslateX,lvTranslateY);

		        // update button0_1 (button) location to move with camera 
		        this.button0_2.translate(lvTranslateX,lvTranslateY);

		        // update fps label location to move with camera
		        this.fpsLabel.translate(lvTranslateX,lvTranslateY);

		        // update fps label location to move with camera
		        this.GLabel.translate(lvTranslateX,lvTranslateY);
		        // update fps label location to move with camera
		        this.HLabel.translate(lvTranslateX,lvTranslateY);
		        // update fps label location to move with camera
		        this.FLabel.translate(lvTranslateX,lvTranslateY);
		        // update fps label location to move with camera
		        this.ILabel.translate(lvTranslateX,lvTranslateY);
        	}
        };
        
        // update fps label
        this.fpsLabel.setText("fps: " + Integer.toString(Gdx.graphics.getFramesPerSecond()));
        
        // draw stage -> draw actors
        this.stage0.draw();        
    }

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		this.stage0.dispose();
		this.map.dispose();
		this.renderer.dispose();
	}

}

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
    protected Skin skin;
    protected TiledMap map;
    protected OrthogonalTiledMapRenderer renderer;
    protected Stage stage0;
    protected MyButton0 button0_0;
    protected MyButton0 button0_1;
    protected MyActor0 actor0_0;
    protected MyActor0 actor0_1;
    protected MyActor0 actor0_2;
    protected MyActor1 actor1_0;
    protected MyActor2 actor2_0;
    
    public MyScreen0(MyOpGame game) {
    	
        // link screen to game
    	this.game = game;
    	
    	// allocate skin
    	skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    	
        // allocate stage; viewport size maps game window size
        this.stage0 = new Stage( Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), true );
        
        // allocate actor0_0 and add to stage
        this.actor0_0 = new MyActor0();
        this.actor0_0.setPosition(255*32, 127*32 + MyOpGame.MY_MINIMAP_HEIGHT);
        this.stage0.addActor(this.actor0_0);

        // allocate actor0_1 and add to stage, actor1 is placed next to actor0
        this.actor0_1 = new MyActor0();
        this.actor0_1.setPosition(10*32, 15*32 + MyOpGame.MY_MINIMAP_HEIGHT);
        this.stage0.addActor(this.actor0_1);
                
        // allocate actor0_2 and add to stage, actor1 is placed next to actor0
        this.actor0_2 = new MyActor0();
        this.actor0_2.setPosition(54*32, 76*32 + MyOpGame.MY_MINIMAP_HEIGHT);
        this.stage0.addActor(this.actor0_2);
                
        // allocate actor2_0 and add to stage
        this.actor2_0 = new MyActor2();
        this.actor2_0.setPosition(0, 0);
        this.stage0.addActor(this.actor2_0);

        // allocate actor1_0 and add to stage
        this.actor1_0 = new MyActor1();
        this.actor1_0.setPosition(0, 0);
        this.stage0.addActor(this.actor1_0);
        
        // allocate button0_0 and add to stage
    	button0_0 = new MyButton0(this,"map0", skin, "default",new Vector2(128 + 128,108));
        stage0.addActor(button0_0);        

        // allocate button0_1 and add to stage
    	button0_1 = new MyButton0(this,"map1", skin, "default",new Vector2(128 + 128,88));
        stage0.addActor(button0_1);        

    	// allocate fps label
        fpsLabel = new Label( "FPS: ", skin);
        fpsLabel.setPosition(128 + 128, 0 );
        stage0.addActor( fpsLabel );    	
        
        Gdx.input.setInputProcessor(stage0);
    }
    
    @Override
	public void render(float delta) {
    	
    	int lvTranslateX = 0;
    	int lvTranslateY = 0;
    	
		// the following code clears the screen with the given RGB color (green)
        Gdx.gl.glClearColor( 0f, 1f, 0f, 1f );
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
        	if (MyOpGame.MY_APP_WINDOW_HEIGHT - Gdx.input.getY() >= MyOpGame.MY_MINIMAP_HEIGHT)
        	{
        		if (Gdx.input.getX() >= (MyOpGame.MY_ORIGINAL_CAMERA_POSITION_X * 1.15))
        		{
        			if (this.stage0.getCamera().position.x - MyOpGame.MY_ORIGINAL_CAMERA_POSITION_X < MyOpGame.MY_WORLD_WIDTH - MyOpGame.MY_APP_WINDOW_WIDTH)
        				lvTranslateX = 32;
        		}
        		else if (Gdx.input.getX() < (MyOpGame.MY_ORIGINAL_CAMERA_POSITION_X * 0.85))
        		{
        			if (this.stage0.getCamera().position.x - MyOpGame.MY_ORIGINAL_CAMERA_POSITION_X > 0)
        				lvTranslateX = -32;
        		}

        		if (MyOpGame.MY_APP_WINDOW_HEIGHT - Gdx.input.getY() >= MyOpGame.MY_MINIMAP_HEIGHT + (MyOpGame.MY_ORIGINAL_CAMERA_POSITION_Y * 1.05))
        		{
        			if (this.stage0.getCamera().position.y - MyOpGame.MY_ORIGINAL_CAMERA_POSITION_Y < MyOpGame.MY_WORLD_HEIGHT - MyOpGame.MY_APP_WINDOW_HEIGHT + MyOpGame.MY_MINIMAP_HEIGHT )
        				lvTranslateY = 32;
        		}
        		else if ((MyOpGame.MY_APP_WINDOW_HEIGHT - Gdx.input.getY() < (MyOpGame.MY_ORIGINAL_CAMERA_POSITION_Y * 0.95)) &&(MyOpGame.MY_APP_WINDOW_HEIGHT - Gdx.input.getY() > MyOpGame.MY_MINIMAP_HEIGHT))
        		{
        			if (this.stage0.getCamera().position.y - MyOpGame.MY_ORIGINAL_CAMERA_POSITION_Y > 0)
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

		        // update fps label location to move with camera
		        this.fpsLabel.translate(lvTranslateX,lvTranslateY);
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

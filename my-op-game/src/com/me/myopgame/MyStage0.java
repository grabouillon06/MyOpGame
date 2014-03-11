package com.me.myopgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class MyStage0 {
	
	protected Stage stage0;
	protected MyScreen0 screen0;
    protected MyActor0 actor0_0;
    protected MyActor0 actor0_1;
    protected MyActor0 actor0_2;
    protected MyActor1 actor1_0;
    protected MyActor2 actor2_0;
    protected MyButton0 button0_map0;
    protected MyButton0 button0_map1;
    protected MyButton0 button0_gogogo;
    protected MyButton0 button0_reset;
	protected Label fpsLabel;
	protected Label GLabel;
	protected Label HLabel;
	protected Label FLabel;
	protected Label ILabel;

	protected boolean gogogo;
    protected boolean mapDefined;

    public MyStage0(MyScreen0 screen) {
    	
    	this.screen0 = screen;
    	
    	// initialize gogogo button to false, nothing will move at creation
    	this.gogogo = false;
    	
    	// initialize mapDefined flag to false as now map defined yet
    	this.mapDefined = false;
    	
    	// allocate stage; viewport size maps game window size
        this.stage0 = new Stage( Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), true );
        
        // allocate actor0_0 and add to stage
        this.actor0_0 = new MyActor0(this, 0*MyOpGame.MY_TILE_SIZE_IN_PIXELS, 0*MyOpGame.MY_TILE_SIZE_IN_PIXELS + MyOpGame.MY_MINIMAP_HEIGHT_IN_PIXELS);
        this.stage0.addActor(this.actor0_0);
        
        // allocate actor0_1 and add to stage, actor1 is placed next to actor0
        this.actor0_1 = new MyActor0(this, 255*MyOpGame.MY_TILE_SIZE_IN_PIXELS, 127*MyOpGame.MY_TILE_SIZE_IN_PIXELS + MyOpGame.MY_MINIMAP_HEIGHT_IN_PIXELS);
        this.stage0.addActor(this.actor0_1);
                
        // allocate actor0_2 and add to stage, actor1 is placed next to actor0 -> this is the target for now
        this.actor0_2 = new MyActor0(this, 27*MyOpGame.MY_TILE_SIZE_IN_PIXELS, 5*MyOpGame.MY_TILE_SIZE_IN_PIXELS + MyOpGame.MY_MINIMAP_HEIGHT_IN_PIXELS);
        this.stage0.addActor(this.actor0_2);
        
        // set target for actor0_0
        this.actor0_0.setTarget((int)(this.actor0_2.getX()),(int)(this.actor0_2.getY()));
                
        // allocate actor2_0 and add to stage
        this.actor2_0 = new MyActor2();
        this.actor2_0.setPosition(0, 0);
        this.stage0.addActor(this.actor2_0);

        // allocate actor1_0 and add to stage
        this.actor1_0 = new MyActor1();
        this.actor1_0.setPosition(0, 0);
        this.stage0.addActor(this.actor1_0);
        
        // allocate button0_map0 and add to stage
    	button0_map0 = new MyButton0(this,"map0", this.screen0.skin, "default",new Vector2(128 + 128 + 256,98));
        stage0.addActor(button0_map0);       
        
        // allocate button0_map1 and add to stage
    	button0_map1 = new MyButton0(this,"map1", this.screen0.skin, "default",new Vector2(128 + 128 + 256,68));
        stage0.addActor(button0_map1);        

        // allocate button0_gogogo and add to stage
    	button0_gogogo = new MyButton0(this,"go go go !!!", this.screen0.skin, "default",new Vector2(128 + 128,68));
        stage0.addActor(button0_gogogo);    
        
        // allocate button0_reset and add to stage
    	button0_reset = new MyButton0(this,"reset", this.screen0.skin, "default",new Vector2(128 + 128,98));
        stage0.addActor(button0_reset);    

        // allocate fps label
        fpsLabel = new Label( "FPS: ", this.screen0.skin);
        fpsLabel.setPosition(128 + 128, 0 );
        fpsLabel.setColor(0, 0, 0, 1);
        stage0.addActor( fpsLabel );    	
        
        // print G, H, F, I positions in square
        GLabel = new Label( "G", this.screen0.skin);
        GLabel.setPosition(128 + 128 + 64, 0 );
        GLabel.setFontScale((float) 0.75);
        GLabel.setColor(0, 0, 0, 1);
        stage0.addActor( GLabel );    	
        HLabel = new Label( "H", this.screen0.skin);
        HLabel.setPosition(128 + 128 + 64 + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16, 0 );
        HLabel.setFontScale((float) 0.75);
        HLabel.setColor(0, 0, 0, 1);
        stage0.addActor( HLabel );    	
        FLabel = new Label( "F", this.screen0.skin);
        FLabel.setPosition(128 + 128 + 64, 0 + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16 );
        FLabel.setFontScale((float) 0.75);
        FLabel.setColor(0, 0, 0, 1);
        stage0.addActor( FLabel );    	
        ILabel = new Label( "I", this.screen0.skin);
        ILabel.setPosition(128 + 128 + 64 + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16, 0 + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16 );
        ILabel.setFontScale((float) 0.75);
        ILabel.setColor(0, 0, 0, 1);
        stage0.addActor( ILabel );    
        
    	Gdx.input.setInputProcessor(this.stage0);
    }
    
    public void reset() {
    	this.gogogo = false;
    	this.mapDefined = false;
    	this.actor0_0.reset();
    	return;
    }
      
    public void act(float delta) {
    	this.stage0.act();  	
    }
    
    public void render(float delta) {
    	
    	int lvTranslateX = 0;
    	int lvTranslateY = 0;
    	
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
		        
		        // update button0_map0 (button) location to move with camera 
		        this.button0_map0.translate(lvTranslateX,lvTranslateY);
		        
		        // update button0_map1 (button) location to move with camera 
		        this.button0_map1.translate(lvTranslateX,lvTranslateY);

		        // update button0_gogogo (button) location to move with camera 
		        this.button0_gogogo.translate(lvTranslateX,lvTranslateY);

		        // update button0_reset (button) location to move with camera 
		        this.button0_reset.translate(lvTranslateX,lvTranslateY);

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
    
	public void dispose() {
		this.stage0.dispose();
	}
}

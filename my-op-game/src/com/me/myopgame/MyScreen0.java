package com.me.myopgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MyScreen0 implements Screen{
	
    protected final Stage stage0;
	protected final MyOpGame game;
    protected final MyActor0 actor0_0;
    protected final MyActor0 actor0_1;
    protected final MyActor1 actor1_0;

    
    public MyScreen0(MyOpGame game) {
    	
        // link screen to game
    	this.game = game;
    	
        // allocate stage; viewport size maps game window size
        this.stage0 = new Stage( Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), true );

        // allocate actor0_0 and add to stage
        this.actor0_0 = new MyActor0();
        this.actor0_0.setPosition(0, 0);
        this.stage0.addActor(this.actor0_0);

        // allocate actor0_1 and add to stage, actor1 is placed next to actor0
        this.actor0_1 = new MyActor0();
        this.actor0_1.setPosition(512, 256);
        this.stage0.addActor(this.actor0_1);
                
        // allocate actor1_0 and add to stage
        this.actor1_0 = new MyActor1();
        this.actor1_0.setPosition(0, MyOpGame.MY_APP_WINDOW_HEIGHT - MyOpGame.MY_MINIMAP_HEIGHT);
        this.stage0.addActor(this.actor1_0);
    }
    
    @Override
	public void render(float delta) {
    	
    	int lvTranslateX = 0;
    	int lvTranslateY = 0;
    	
		// the following code clears the screen with the given RGB color (green)
        Gdx.gl.glClearColor( 0f, 1f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );	
        
        /* check if mouse pressed */
        if (Gdx.input.isTouched())
        {
        	//Gdx.app.log( MyOp0Game.LOG, " mouse X = "+ Gdx.input.getX() +
        	//							" Camera.x = " + this.stage0.getCamera().position.x +
        	//							" mouse Y = "+ (MyOp0Game.MY_APP_WINDOW_HEIGHT - Gdx.input.getY()) +
        	//							" Camera.y = " + this.stage0.getCamera().position.y);
        	
            if (Gdx.input.getX() >= (MyOpGame.MY_ORIGINAL_CAMERA_POSITION_X * 1.5))
            {
                if (this.stage0.getCamera().position.x - MyOpGame.MY_ORIGINAL_CAMERA_POSITION_X < MyOpGame.MY_WORLD_WIDTH - MyOpGame.MY_APP_WINDOW_WIDTH)
                	lvTranslateX = 3;
            }
            else if (Gdx.input.getX() < (MyOpGame.MY_ORIGINAL_CAMERA_POSITION_X * 0.5))
            {
                if (this.stage0.getCamera().position.x - MyOpGame.MY_ORIGINAL_CAMERA_POSITION_X > 0)
                	lvTranslateX = -3;
            }

            if (MyOpGame.MY_APP_WINDOW_HEIGHT - Gdx.input.getY() >= (MyOpGame.MY_ORIGINAL_CAMERA_POSITION_Y * 1.5))
            {
                if (this.stage0.getCamera().position.y - MyOpGame.MY_ORIGINAL_CAMERA_POSITION_Y < MyOpGame.MY_WORLD_HEIGHT - MyOpGame.MY_APP_WINDOW_HEIGHT)
                	lvTranslateY = 3;
            }
            else if (MyOpGame.MY_APP_WINDOW_HEIGHT - Gdx.input.getY() < (MyOpGame.MY_ORIGINAL_CAMERA_POSITION_Y * 0.5))
            {
                if (this.stage0.getCamera().position.y - MyOpGame.MY_ORIGINAL_CAMERA_POSITION_Y > 0)
                	lvTranslateY = -3;
            }
            
        	if (lvTranslateX != 0 || lvTranslateY != 0)
        	{
	        	// translate stage camera to go from actor0 to actor1
		        this.stage0.getCamera().translate(lvTranslateX, lvTranslateY, 0);
	          
		        // update actor1_0 (minimap) location to move with camera 
		        this.actor1_0.translate(lvTranslateX,lvTranslateY);
        	}
        };
        
        // draw stage -> draw actors
        this.stage0.draw();
        
    }

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// not required as no abstract screen for now
		//super.show();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
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
	}

}

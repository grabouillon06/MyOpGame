package com.me.myopgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class MyActor1 extends Actor {

	Texture texture;
	Texture blackMarkerTexture;
	Texture yelloCameraMinimapTexture;
	
	public MyActor1() {
        this.texture = new Texture(Gdx.files.internal("data/pinkBg_256_128.png"));
        this.blackMarkerTexture = new Texture(Gdx.files.internal("data/blackActor0_2_2.png"));
        this.yelloCameraMinimapTexture = new Texture(Gdx.files.internal("data/yelloCameraMinimap_32_16.png"));
        this.setName("minimap");
        
	}
	
    public void draw(SpriteBatch batch, float alpha){
    	
    	// draw actor
        batch.draw(texture,this.getX(),this.getY());

        // draw application window in minimap
		batch.draw(	this.yelloCameraMinimapTexture, 
					this.getX() + (this.getStage().getCamera().position.x - MyOpGame.MY_ORIGINAL_CAMERA_POSITION_X_IN_PIXELS)/MyOpGame.MY_MINIMAP_SCALE_FACTOR, 
					this.getY() + (this.getStage().getCamera().position.y - MyOpGame.MY_ORIGINAL_CAMERA_POSITION_Y_IN_PIXELS)/MyOpGame.MY_MINIMAP_SCALE_FACTOR);
        
        // retrieve and draw actors of type "actor0" from stage
    	Array<Actor> lvActorArray = this.getStage().getActors();
    	for ( int lvIdx = 0; lvIdx < lvActorArray.size; lvIdx++ )
    	{
    		Actor lvActor = lvActorArray.get(lvIdx);
    		if (lvActor.getName() == "actor0")
    		{
    			batch.draw(	this.blackMarkerTexture, 
    						this.getX() + (lvActor.getX()/MyOpGame.MY_MINIMAP_SCALE_FACTOR),
    						this.getY() + ((lvActor.getY() - MyOpGame.MY_MINIMAP_HEIGHT_IN_PIXELS)/MyOpGame.MY_MINIMAP_SCALE_FACTOR));
    		}
    	}
    }

}

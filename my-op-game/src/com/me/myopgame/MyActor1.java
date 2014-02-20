package com.me.myopgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class MyActor1 extends Actor {

	SpriteBatch batch;
	Texture texture;
	Texture blackMarkerTexture;
	Texture yelloCameraMinimapTexture;
	
	public MyActor1() {
        this.batch = new SpriteBatch();
        this.texture = new Texture(Gdx.files.internal("data/blueBg_256_256.png"));
        this.blackMarkerTexture = new Texture(Gdx.files.internal("data/blackMarker_32_32.png"));
        this.yelloCameraMinimapTexture = new Texture(Gdx.files.internal("data/yelloCameraMinimap_64_64.png"));
        this.setName("minimap");
        
	}
	
    public void draw(SpriteBatch batch, float alpha){
    	
    	// draw actor
        batch.draw(texture,this.getX(),this.getY());

        // draw game window in minimap
		batch.draw(	this.yelloCameraMinimapTexture, 
					this.getX() + (this.getStage().getCamera().position.x - MyOpGame.MY_ORIGINAL_CAMERA_POSITION_X)/MyOpGame.MY_MINIMAP_SCALE_FACTOR, 
					this.getY() + (this.getStage().getCamera().position.y - MyOpGame.MY_ORIGINAL_CAMERA_POSITION_Y)/MyOpGame.MY_MINIMAP_SCALE_FACTOR);
        
        // retrieve actors from stage
    	Array<Actor> lvActorArray = this.getStage().getActors();
    	for ( int lvIdx = 0; lvIdx < lvActorArray.size; lvIdx++ )
    	{
    		Actor lvActor = lvActorArray.get(lvIdx);
    		if (lvActor.getName() != "minimap")
    		{
    			batch.draw(	this.blackMarkerTexture, 
    						this.getX() + (lvActor.getX()/MyOpGame.MY_MINIMAP_SCALE_FACTOR),
    						this.getY() + (lvActor.getY()/MyOpGame.MY_MINIMAP_SCALE_FACTOR));
    		}
    	}
    }

}

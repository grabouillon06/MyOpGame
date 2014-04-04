package com.me.myopgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.me.DStarLite.*;

public class MyActor0 extends Actor {
	
	public static int ACTOR0_A_STAR_PATH_SEARCH = 0;
	public static int ACTOR0_D_STAR_LITE_PATH_SEARCH = 1;

	
	protected final MyStage0 myStage0;
	
	// sprite section
	public Texture textureActor0;
	
	public int originX;
	public int originY;
	
	public int targetX;
	public int targetY;

	public boolean targetDefined;
	public boolean targetLocked;
	
	public int pathSearchType;
	
	public MyAStar aStar;
	public MyDStarLite dStarLite;
	
	public MyActor0(MyStage0 stage, int x, int y) {	
		
		this.myStage0 = stage;

		this.textureActor0 = new Texture(Gdx.files.internal("data/unit_green_32.png"));

        this.setStage(this.myStage0.stage0);
        this.setName("actor0");
        this.setPosition(x, y);       
        
        this.originX = x;
        this.originY = y;
        
        this.targetX = -1;
        this.targetY = -1;
        
        this.targetDefined = false;
        this.targetLocked = false;
        
        //pathSearchType = ACTOR0_A_STAR_PATH_SEARCH;
        pathSearchType = ACTOR0_D_STAR_LITE_PATH_SEARCH;
        
        if (pathSearchType == ACTOR0_A_STAR_PATH_SEARCH)
        	this.aStar = new MyAStar(this);
        
        if (pathSearchType == ACTOR0_D_STAR_LITE_PATH_SEARCH)
        	this.dStarLite = new MyDStarLite(this);    
    }
		
	public void reset() {
        this.setPosition(this.originX, this.originY);       		
        this.targetDefined = true;
        this.targetLocked = false;

        if (pathSearchType == ACTOR0_A_STAR_PATH_SEARCH)
        	this.aStar.reset();

        if (pathSearchType == ACTOR0_D_STAR_LITE_PATH_SEARCH)
        	this.dStarLite.reset();
	}
	
	@Override
	public void act (float delta) {
		super.act(delta);
		
    	if (this.myStage0.mapDefined == true)
    	{	
    		if (this.targetDefined == true)
    		{
    			if (this.targetLocked == false)
    			{
	        		if (pathSearchType == ACTOR0_A_STAR_PATH_SEARCH)
		            {
			        	this.aStar.reset();
			        	this.aStar.registerBlockedTiles();
			        	this.aStar.computePath();
		            }
	        		else if (pathSearchType == ACTOR0_D_STAR_LITE_PATH_SEARCH)
		            {
			        	this.dStarLite.init(this.convertInTiles(originX),this.convertInTiles(originY),this.convertInTiles(targetX),this.convertInTiles(targetY));
			        	this.dStarLite.registerBlockedTiles();
			        	this.dStarLite.replan();
		            }
    			}
    			else if (this.myStage0.gogogo == true)
    			{
    				if (pathSearchType == ACTOR0_A_STAR_PATH_SEARCH)
    				{
		            	this.aStar.discoverEnv();
		            	this.aStar.moveActor();
		            }
	
		            if (pathSearchType == ACTOR0_D_STAR_LITE_PATH_SEARCH)
		            {
		            	this.dStarLite.moveActor();
		            }
    			}
	    	}    	
    	}
	}
	
    public void draw(SpriteBatch batch, float alpha) {
    	
        // draw path information
        if (pathSearchType == ACTOR0_A_STAR_PATH_SEARCH)
        	this.aStar.drawInfo(batch, alpha);
    	
    	// draw D* lite complete path
        if (pathSearchType == ACTOR0_D_STAR_LITE_PATH_SEARCH)
        	this.dStarLite.drawInfo(batch, alpha);

    	// draw actor
        batch.draw(textureActor0,this.getX(),this.getY());
    }
    
    public void setTarget(int x, int y) {
    	this.targetX = x;
    	this.targetY = y;
        this.targetDefined = true;
    }
    
    public int convertInTiles(int x) {
    	return (int)(x/MyOpGame.MY_TILE_SIZE_IN_PIXELS);
    }
    
    public int convertInPixels(int x) {
    	return (int)(x*MyOpGame.MY_TILE_SIZE_IN_PIXELS);
    }
    
}
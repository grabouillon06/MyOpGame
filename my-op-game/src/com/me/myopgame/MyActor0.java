package com.me.myopgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MyActor0 extends Actor {
	
	protected final MyStage0 myStage0;
	
	// sprite section
	public Texture textureActor0;
	
	public int originX;
	public int originY;
	
	public int targetX;
	public int targetY;

	public boolean targetDefined;
	public boolean targetLocked;
	
	public MyAStar aStar;

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
        
        aStar = new MyAStar(this);
    }
	
	public void reset() {
        this.setPosition(this.originX, this.originY);       		
        this.targetDefined = true;
        this.targetLocked = false;
	}
	
    public void draw(SpriteBatch batch, float alpha) {
    	
    	if (this.myStage0.mapDefined == true && this.targetDefined == true && this.targetLocked == false)
    	{
        	this.aStar.reset();
        	this.aStar.computePath();
    	}
    	
    	// draw path information
    	this.aStar.drawInfo(batch, alpha);
    	
    	// move actor
    	if (this.targetLocked == true && this.myStage0.gogogo == true)
    	{
    		if (this.aStar.aPathCurrent.next != null)
    			if (((int) this.getX() == this.convertInPixels((int) this.aStar.aPathCurrent.next.posInTiles.x)) && ((int) this.getY() == this.convertInPixels((int) this.aStar.aPathCurrent.next.posInTiles.y)))
    			{
    				this.aStar.aPathCurrent = this.aStar.aPathCurrent.next;			
    				//Gdx.app.log( MyOpGame.LOG,"position : x = " + this.aStar.aPathCurrent.posInTiles.x + " ("+(int) this.getX()+"), y = " + this.aStar.aPathCurrent.posInTiles.y + " ("+(int) this.getY()+"), G = " + this.aStar.aPathCurrent.G + ", H = " + this.aStar.aPathCurrent.H + ", F = " + this.aStar.aPathCurrent.F);
    			}
			
        	if (this.aStar.aPathCurrent.next != null)
			{
				int x = this.convertInPixels((int) this.aStar.aPathCurrent.next.posInTiles.x);
				int y = this.convertInPixels((int) this.aStar.aPathCurrent.next.posInTiles.y);
				this.translate((float)(x - this.convertInPixels((int) this.aStar.aPathCurrent.posInTiles.x))/(MyOpGame.MY_TILE_SIZE_IN_PIXELS/MyOpGame.MY_ACTOR_SPEED_FACTOR), (float)(y - this.convertInPixels((int) this.aStar.aPathCurrent.posInTiles.y))/(MyOpGame.MY_TILE_SIZE_IN_PIXELS/MyOpGame.MY_ACTOR_SPEED_FACTOR));
			}
    	}
    	
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
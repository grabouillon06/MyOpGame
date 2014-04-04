package com.me.myopgame;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.me.DStarLite.DStarLite;
import com.me.DStarLite.State;

public class MyDStarLite {
	
	public MyActor0 myActor0;
	public DStarLite pf;
	public int pfStateCurrentIndex;
	public Texture pfTexturePath;
	
	public MyDStarLite(MyActor0 actor0) {
	
		this.myActor0 = actor0;

        this.pf = new DStarLite();
        this.pfStateCurrentIndex = 0;
		this.pfTexturePath = new Texture(Gdx.files.internal("data/redMarker_32_32.png"));
	}
	public void reset() {
        this.pfStateCurrentIndex = 0;
	}
	public void moveActor()
	{
		State lvLastState = this.pf.getPath().get(this.pf.getPath().size() - 1);
		State lvCurrentState = this.pf.getPath().get(this.pfStateCurrentIndex);
		
		if (lvCurrentState.neq(lvLastState)) {
    		State lvNextState = this.pf.getPath().get(this.pfStateCurrentIndex + 1);
			if (((int) this.myActor0.getX() == this.myActor0.convertInPixels((int)(lvNextState.x))) && ((int) this.myActor0.getY() == this.myActor0.convertInPixels((int)(lvNextState.y))))
			{
				this.pfStateCurrentIndex ++;	
				lvCurrentState = this.pf.getPath().get(this.pfStateCurrentIndex);
				//Gdx.app.log( MyOpGame.LOG,"position : x = " + this.aStar.aPathCurrent.posInTiles.x + " ("+(int) this.getX()+"), y = " + this.aStar.aPathCurrent.posInTiles.y + " ("+(int) this.getY()+"), G = " + this.aStar.aPathCurrent.G + ", H = " + this.aStar.aPathCurrent.H + ", F = " + this.aStar.aPathCurrent.F);
			}
		}
		
    	if (lvCurrentState.neq(lvLastState))
		{
    		State lvNextState = this.pf.getPath().get(this.pfStateCurrentIndex + 1);
			int x = this.myActor0.convertInPixels((int) lvNextState.x);
			int y = this.myActor0.convertInPixels((int) lvNextState.y);
			this.myActor0.translate((float)(x - this.myActor0.convertInPixels((int) lvCurrentState.x))/(MyOpGame.MY_TILE_SIZE_IN_PIXELS/MyOpGame.MY_ACTOR_SPEED_FACTOR), (float)(y - this.myActor0.convertInPixels((int) lvCurrentState.y))/(MyOpGame.MY_TILE_SIZE_IN_PIXELS/MyOpGame.MY_ACTOR_SPEED_FACTOR));
		}        	
		
	}
    public void drawInfo(SpriteBatch batch, float alpha) {
    	if (this.myActor0.targetLocked == true) {
	    	// draw complete D* lite path
    		boolean lvValid = true;
	    	if (lvValid == true) {
	    		for (State i : pf.getPath())
	    		{
	    			//System.out.println("x: " + i.x + " y: " + i.y);
    			    batch.draw(this.pfTexturePath,(float)(this.myActor0.convertInPixels((int)(i.x))),(float)(this.myActor0.convertInPixels((int)(i.y))));
	    		}
	    	}
    		
    	}
    }
    public void registerBlockedTiles()
    {
    	// tag blocked map objects as A_PATH_ELEMENT_CLOSED
    	{
	    	MapObjects objects = this.myActor0.myStage0.screen0.map.getLayers().get("Object Layer 1").getObjects();
	    	for (int k = 0; k < objects.getCount(); k++ )
	    	{
	    		RectangleMapObject object = (RectangleMapObject) objects.get(k);
	    		for (int 	i = this.myActor0.convertInTiles((int) object.getRectangle().x); 
	    					i < this.myActor0.convertInTiles((int) object.getRectangle().x) + this.myActor0.convertInTiles((int) object.getRectangle().width); 
	    					i++ )
	    		{
	        		for (int 	j = this.myActor0.convertInTiles((int) object.getRectangle().y); 
	    						j < this.myActor0.convertInTiles((int) object.getRectangle().y) + this.myActor0.convertInTiles((int) object.getRectangle().height); 
	    						j++ )
	        		{
	        			this.updateCell(i, j, -1);
		    			//System.out.println("x: " + i + " y: " + j);
	        		}			
	    		}
	    		
	    		for (int i=0; i<MyOpGame.MY_WORLD_WIDTH_IN_TILES; i++)
	    		{
	    			this.updateCell(i, 0, -1);
	    			this.updateCell(i, 1, -1);
	    			this.updateCell(i, 2, -1);
	    			this.updateCell(i, 3, -1);
	    		}
	    	}
    	}    	
    }
	public void init(int sX, int sY, int gX, int gY)
	{
		pf.init(sX, sY, gX, gY);
	}
	public void updateCell(int x, int y, double val)
	{
		pf.updateCell(x, y, val);
	}
	public boolean replan()
	{
		boolean ret;
		ret = pf.replan();
    	this.myActor0.targetLocked = true;
    	return ret;
	}	
	public List<State> getPath()
	{
		return pf.getPath();
	}
}

package com.me.myopgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.me.DStarLite.*;

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
	
	public DStarLite pf;
	public int pfStateCurrentIndex;
	public Texture pfTexturePath;

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
        
        pf = new DStarLite();
        pfStateCurrentIndex = 0;
		this.pfTexturePath = new Texture(Gdx.files.internal("data/redMarker_32_32.png"));
    }
	
    public void drawPfInfo(SpriteBatch batch, float alpha) {
    	if (this.targetLocked == true) {
	    	// draw complete D* lite path
    		boolean lvValid = true;
	    	if (lvValid == true) {
	    		for (State i : pf.getPath())
	    		{
	    			//System.out.println("x: " + i.x + " y: " + i.y);
    			    batch.draw(this.pfTexturePath,(float)(this.convertInPixels((int)(i.x))),(float)(this.convertInPixels((int)(i.y))));
	    		}
	    	}
    		
    	}
    }
	
	public void reset() {
        this.setPosition(this.originX, this.originY);       		
        this.targetDefined = true;
        this.targetLocked = false;
        pfStateCurrentIndex = 0;
	}
	
	@Override
	public void act (float delta) {
		super.act(delta);
		
    	if (this.myStage0.mapDefined == true && this.targetDefined == true && this.targetLocked == false)
    	{
        	this.aStar.reset();
        	this.aStar.computePath();
        	this.pf.init(this.convertInTiles(originX),this.convertInTiles(originY),this.convertInTiles(targetX),this.convertInTiles(targetY));
        	// tag blocked map objects as A_PATH_ELEMENT_CLOSED
        	{
    	    	MapObjects objects = this.myStage0.screen0.map.getLayers().get("Object Layer 1").getObjects();
    	    	for (int k = 0; k < objects.getCount(); k++ )
    	    	{
    	    		RectangleMapObject object = (RectangleMapObject) objects.get(k);
    	    		for (int 	i = this.convertInTiles((int) object.getRectangle().x); 
    	    					i < this.convertInTiles((int) object.getRectangle().x) + this.convertInTiles((int) object.getRectangle().width); 
    	    					i++ )
    	    		{
    	        		for (int 	j = this.convertInTiles((int) object.getRectangle().y); 
    	    						j < this.convertInTiles((int) object.getRectangle().y) + this.convertInTiles((int) object.getRectangle().height); 
    	    						j++ )
    	        		{
    	        			pf.updateCell(i, j, -1);
    		    			//System.out.println("x: " + i + " y: " + j);
    	        		}			
    	    		}
    	    		
    	    		for (int i=0; i<MyOpGame.MY_WORLD_WIDTH_IN_TILES; i++)
    	    		{
    	    			pf.updateCell(i, 0, -1);
    	    			pf.updateCell(i, 1, -1);
    	    			pf.updateCell(i, 2, -1);
    	    			pf.updateCell(i, 3, -1);
    	    		}
    	    	}
        	}

        	pf.replan();
    	}
    	
    	// discover actor environment
    	if (this.targetLocked == true && this.myStage0.gogogo == true)
    	{
			for(int i=-2; i<3; i++ ) 
			{
				int relativeI = (int) (i+this.aStar.current.posInTiles.x);
				for(int j=-2; j<3; j++) 
				{
					int relativeJ = (int) (j+this.aStar.current.posInTiles.y);
	
	            	if (	(relativeI >= 0) && (relativeJ >= 4) && 
	            			(relativeI <= MyOpGame.MY_WORLD_WIDTH_IN_TILES) && (relativeJ <= (MyOpGame.MY_WORLD_HEIGHT_IN_TILES + 4))) 
	            	{
	            		if (this.aStar.map[relativeI][relativeJ].F != -1)
	            		{
			            	if (this.aStar.map[relativeI][relativeJ].discoveredFlag == false) 
			            	{
			            		this.aStar.map[relativeI][relativeJ].discoveredFlag = true;
			            		this.aStar.discoveredElementsList.add(this.aStar.map[relativeI][relativeJ]);
					        }
	            		}
	            	}
				}
			}
		}

    	// move actor following A* lite
    	//if (this.targetLocked == true && this.myStage0.gogogo == true)
    	//{
    	//	if (this.aStar.current.next != null)
    	//		if (((int) this.getX() == this.convertInPixels((int) this.aStar.current.next.posInTiles.x)) && ((int) this.getY() == this.convertInPixels((int) this.aStar.current.next.posInTiles.y)))
    	//		{
    	//			this.aStar.current = this.aStar.current.next;			
    	//			//Gdx.app.log( MyOpGame.LOG,"position : x = " + this.aStar.aPathCurrent.posInTiles.x + " ("+(int) this.getX()+"), y = " + this.aStar.aPathCurrent.posInTiles.y + " ("+(int) this.getY()+"), G = " + this.aStar.aPathCurrent.G + ", H = " + this.aStar.aPathCurrent.H + ", F = " + this.aStar.aPathCurrent.F);
    	//		}
		//	
        //	if (this.aStar.current.next != null)
		//	{
		//		int x = this.convertInPixels((int) this.aStar.current.next.posInTiles.x);
		//		int y = this.convertInPixels((int) this.aStar.current.next.posInTiles.y);
		//		this.translate((float)(x - this.convertInPixels((int) this.aStar.current.posInTiles.x))/(MyOpGame.MY_TILE_SIZE_IN_PIXELS/MyOpGame.MY_ACTOR_SPEED_FACTOR), (float)(y - this.convertInPixels((int) this.aStar.current.posInTiles.y))/(MyOpGame.MY_TILE_SIZE_IN_PIXELS/MyOpGame.MY_ACTOR_SPEED_FACTOR));
		//	}        	
    	//} 
    	
    	// move actor following D* lite path
    	if (this.targetLocked == true && this.myStage0.gogogo == true)
    	{
    		State lvLastState = this.pf.getPath().get(this.pf.getPath().size() - 1);
    		State lvCurrentState = this.pf.getPath().get(pfStateCurrentIndex);
    		
    		if (lvCurrentState.neq(lvLastState)) {
        		State lvNextState = this.pf.getPath().get(pfStateCurrentIndex + 1);
    			if (((int) this.getX() == this.convertInPixels((int)(lvNextState.x))) && ((int) this.getY() == this.convertInPixels((int)(lvNextState.y))))
    			{
    				pfStateCurrentIndex ++;	
    				lvCurrentState = this.pf.getPath().get(pfStateCurrentIndex);
    				//Gdx.app.log( MyOpGame.LOG,"position : x = " + this.aStar.aPathCurrent.posInTiles.x + " ("+(int) this.getX()+"), y = " + this.aStar.aPathCurrent.posInTiles.y + " ("+(int) this.getY()+"), G = " + this.aStar.aPathCurrent.G + ", H = " + this.aStar.aPathCurrent.H + ", F = " + this.aStar.aPathCurrent.F);
    			}
    		}
    		
        	if (lvCurrentState.neq(lvLastState))
			{
        		State lvNextState = this.pf.getPath().get(pfStateCurrentIndex + 1);
				int x = this.convertInPixels((int) lvNextState.x);
				int y = this.convertInPixels((int) lvNextState.y);
				this.translate((float)(x - this.convertInPixels((int) lvCurrentState.x))/(MyOpGame.MY_TILE_SIZE_IN_PIXELS/MyOpGame.MY_ACTOR_SPEED_FACTOR), (float)(y - this.convertInPixels((int) lvCurrentState.y))/(MyOpGame.MY_TILE_SIZE_IN_PIXELS/MyOpGame.MY_ACTOR_SPEED_FACTOR));
			}        	
    	}    	
	}
	
    public void draw(SpriteBatch batch, float alpha) {
    	
        // draw path information
    	this.aStar.drawInfo(batch, alpha);
    	
    	// draw D* lite complete path
    	this.drawPfInfo(batch, alpha);

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
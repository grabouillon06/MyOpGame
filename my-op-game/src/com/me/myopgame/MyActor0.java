package com.me.myopgame;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MyActor0 extends Actor {
	
	protected final MyScreen0 myScreen0;
	
	// sprite section
	public Texture textureActor0;
	public Texture texturePath;
	public BitmapFont yourBitmapFontName;
	
	public int originX;
	public int originY;
	
	public boolean targetDefined;
	public boolean targetDone;

	// path search section
	public MyAPathElement0 aPathTarget;
	public MyAPathElement0 aPathStart;
	public MyAPathElement0 aPathCurrent;
	
	public MyAPathElement0[][] aPathMap;
	
	public List<List<MyAPathElement0>> aPathOpenElementsList = new ArrayList<List<MyAPathElement0>>();
	int openListcount;
	public List<List<MyAPathElement0>> aPathClosedElementsList = new ArrayList<List<MyAPathElement0>>();
	int closedListCount;
	
	public MyActor0(MyScreen0 screen, int x, int y) {	
		
		this.myScreen0 = screen;

		this.textureActor0 = new Texture(Gdx.files.internal("data/unit_green_32.png"));
		this.texturePath = new Texture(Gdx.files.internal("data/blackMarker_32_32.png"));
		this.yourBitmapFontName = new BitmapFont();
		this.yourBitmapFontName.setScale((float) 0.5);

        this.setStage(this.myScreen0.stage0);
        this.setName("actor0");
        this.setPosition(x, y);       
        
        this.originX = x;
        this.originY = y;
        
        this.targetDefined = false;
        this.targetDone = false;
        
        this.aPathMemoryAllocate();
        this.aPathConfigurationReset();
    }
	
    public void draw(SpriteBatch batch, float alpha) {
    	
    	// gogogo button false, no path valid
    	if ((this.myScreen0.gogogo == false) && (this.targetDefined == true))
    	{
    		this.aPathConfigurationReset();
    		this.targetDone = false;
    	}
    	
    	// compute aPath if required
    	if ((this.myScreen0.gogogo == true) && (this.targetDefined == true) && (this.targetDone == false))
    	{
        	this.aPathComputePath();
    	}
    	
    	if (this.targetDone == true)
    	{
	    	// draw element state
	    	{
	    		MyAPathElement0 currentElement = aPathStart;
        		while(currentElement.next != null)
        		{
        			batch.draw(texturePath,(float)(this.convertInPixels((int) currentElement.posInTiles.x)),(float)(this.convertInPixels((int) currentElement.posInTiles.y)));
        			currentElement = currentElement.next;
        		}	    		
        		// draw target
    			batch.draw(texturePath,(float)(this.convertInPixels((int) currentElement.posInTiles.x)),(float)(this.convertInPixels((int) currentElement.posInTiles.y)));
	    	}

	    	// draw path metrics
	    	for(int i=0; i<(aPathMap.length); i++ )
	        {
	        	int x = 3 + i*MyOpGame.MY_TILE_SIZE_IN_PIXELS;
	            for(int j=0; j<aPathMap[i].length; j++)
	            {
	            	int y = 12 + j*MyOpGame.MY_TILE_SIZE_IN_PIXELS;
	            	
	            	if (aPathMap[i][j].H != 0)
			        {
	            		yourBitmapFontName.draw(batch, Integer.toString((int) aPathMap[i][j].G), x, y);
			            yourBitmapFontName.draw(batch, Integer.toString((int) aPathMap[i][j].H), x + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16, y);
			            yourBitmapFontName.draw(batch, Integer.toString((int) aPathMap[i][j].F), x, y + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16);
			            yourBitmapFontName.draw(batch, Integer.toString(aPathMap[i][j].I), x + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16, y + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16);
			        }
	            }
	        }
    	}

    	// draw actor
    	if (this.targetDone == true)
    	{
    		if (this.aPathCurrent.next != null)
    			if (((int) this.getX() == this.convertInPixels((int) this.aPathCurrent.next.posInTiles.x)) && ((int) this.getY() == this.convertInPixels((int) this.aPathCurrent.next.posInTiles.y)))
    			{
    				this.aPathCurrent = this.aPathCurrent.next;			
    				Gdx.app.log( MyOpGame.LOG,"position : x = " + this.aPathCurrent.posInTiles.x + " ("+(int) this.getX()+"), y = " + this.aPathCurrent.posInTiles.y + " ("+(int) this.getY()+"), G = " + this.aPathCurrent.G + ", H = " + this.aPathCurrent.H + ", F = " + this.aPathCurrent.F);
    			}
			
        	if (this.aPathCurrent.next != null)
			{
				int x = this.convertInPixels((int) this.aPathCurrent.next.posInTiles.x);
				int y = this.convertInPixels((int) this.aPathCurrent.next.posInTiles.y);
				this.translate((float)(x - this.convertInPixels((int) this.aPathCurrent.posInTiles.x))/(MyOpGame.MY_TILE_SIZE_IN_PIXELS/MyOpGame.MY_ACTOR_SPEED_FACTOR), (float)(y - this.convertInPixels((int) this.aPathCurrent.posInTiles.y))/(MyOpGame.MY_TILE_SIZE_IN_PIXELS/MyOpGame.MY_ACTOR_SPEED_FACTOR));
			}
    	}
    	
        batch.draw(textureActor0,this.getX(),this.getY());
    }
    
    public void aPathSetTarget(int x, int y) {
    	this.aPathTarget = aPathMap[this.convertInTiles((int) x)][this.convertInTiles((int) y)];
        targetDefined = true;
    }
    
    public int convertInTiles(int x) {
    	return (int)(x/MyOpGame.MY_TILE_SIZE_IN_PIXELS);
    }
    
    public int convertInPixels(int x) {
    	return (int)(x*MyOpGame.MY_TILE_SIZE_IN_PIXELS);
    }
    
    public void aPathMemoryAllocate()
    {
        // allocate and initialize a* path map array
        aPathMap = new MyAPathElement0[MyOpGame.MY_WORLD_WIDTH_IN_TILES][MyOpGame.MY_WORLD_HEIGHT_IN_TILES + MyOpGame.MY_MINIMAP_WIDTH_IN_TILES];
        for(int i=0; i<(aPathMap.length); i++ )
             for(int j=0;j<aPathMap[i].length;j++)
             {
            	 aPathMap[i][j] = new MyAPathElement0();
            	 aPathMap[i][j].posInTiles.set(i,j);
             }
    	
        // allocate start and target
        aPathCurrent = aPathMap[this.convertInTiles((int) this.getX())][this.convertInTiles((int) this.getY())];
        aPathStart = aPathMap[this.convertInTiles((int) this.getX())][this.convertInTiles((int) this.getY())];
        aPathTarget = aPathStart;

        // allocate list of lists for OPEN and CLOSED elements
        int openSize = 100 + (int) Math.sqrt((MyOpGame.MY_WORLD_WIDTH_IN_TILES*MyOpGame.MY_WORLD_WIDTH_IN_TILES) + (MyOpGame.MY_WORLD_HEIGHT_IN_TILES*MyOpGame.MY_WORLD_HEIGHT_IN_TILES));
        for(int i=0; i<openSize; i++ )
        {
        	aPathOpenElementsList.add(new ArrayList<MyAPathElement0>());
        	aPathClosedElementsList.add(new ArrayList<MyAPathElement0>());
        }
    }

    public void aPathConfigurationReset()
    {
        // reset aPathMap
        for(int i=0; i<(aPathMap.length); i++ )
        {
            for(int j=0;j<aPathMap[i].length;j++)
            {
           	    aPathMap[i][j].F = 0;
           	    aPathMap[i][j].G = 0;
           		aPathMap[i][j].H = 0;
           		aPathMap[i][j].I = 0;
           		aPathMap[i][j].next = null;
           		aPathMap[i][j].previous = null;
           		aPathMap[i][j].state = MyAPathElement0.A_PATH_ELEMENT_NEW;
            }
        }

        // reset list of lists for OPEN and CLOSED elements
        openListcount = 0;
        closedListCount = 0;
        int openSize = 100 + (int) Math.sqrt((MyOpGame.MY_WORLD_WIDTH_IN_TILES*MyOpGame.MY_WORLD_WIDTH_IN_TILES) + (MyOpGame.MY_WORLD_HEIGHT_IN_TILES*MyOpGame.MY_WORLD_HEIGHT_IN_TILES));
        for(int i=0; i<openSize; i++ )
        {
        	aPathOpenElementsList.get(i).clear();
        	aPathClosedElementsList.get(i).clear();
        }
    }

    public void aPathComputePath() {
    	
    	MyAPathElement0 startElement;
    	MyAPathElement0 currentElement = aPathMap[0][0];
    	int index =0;
    	
    	// tag map object locations as A_PATH_ELEMENT_CLOSED
    	MapObjects objects = this.myScreen0.map.getLayers().get("Object Layer 1").getObjects();
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
        			aPathMap[i][j].state = MyAPathElement0.A_PATH_ELEMENT_CLOSED;
        		}
    			
    		}
    	}
    	

        // Summary of the A* Method:
        {
        	//- Add the starting square (or node) to the open list.
        	startElement = aPathStart;
        	startElement.G = 0;
        	startElement.H = (float) startElement.computeDistanceTo(this.aPathTarget);
        	startElement.F = startElement.G + startElement.H;  
        	startElement.I = index;
        	startElement.state = MyAPathElement0.A_PATH_ELEMENT_OPEN;
    		aPathOpenElementsList.get((int) startElement.F).add(startElement);
    		this.openListcount++;
    		index++;

    		// search path
        	while ( (this.openListcount != 0) && (targetDone != true))
        	{	
        		// look for lowest F
                int openSize = 100 + (int) Math.sqrt((MyOpGame.MY_WORLD_WIDTH_IN_TILES*MyOpGame.MY_WORLD_WIDTH_IN_TILES) + (MyOpGame.MY_WORLD_HEIGHT_IN_TILES*MyOpGame.MY_WORLD_HEIGHT_IN_TILES));
        		for(int i=0; i<openSize; i++ )
        		{
        			if (aPathOpenElementsList.get(i).isEmpty() == false)
        			{
        				// get lowest F
        				currentElement = aPathOpenElementsList.get(i).get(0);
        				// assign I
        				currentElement.I = index;
        				// increment I
        				index++;
        				// move to CLOSED state
                		currentElement.state = MyAPathElement0.A_PATH_ELEMENT_CLOSED;
        				// remove lowest F from open list
        				aPathOpenElementsList.get(i).remove(0);
        				// decrement open list count
        				this.openListcount--;
                		// add lowest F to closed list
        				aPathClosedElementsList.get(i).add(currentElement);
                		// increment closed list count
        	        	this.closedListCount++;
        	        	
        	        	//Gdx.app.log( MyOpGame.LOG,"position : x = " + currentElement.posInTiles.x + ", y = " + currentElement.posInTiles.y + ", G = " + currentElement.G + ", H = " + currentElement.H + ", F = " + currentElement.F);
        	        	
        	        	// case when the target is reached
        	        	if (currentElement.posInTiles.x == this.aPathTarget.posInTiles.x)
            	        	if (currentElement.posInTiles.y == this.aPathTarget.posInTiles.y)
            	        		targetDone = true;
        	        	
        	        	// break the loop when new lowest F found
           				break;
        			}
        		}
	        	
        		// compute environment of lowest F.
        		MyAPathElement0 nextElement;
        		for(int i=-1; i<2; i++ ) 
        		{
            		for(int j=-1; j<2; j++ )
            		{
            			// remove case for current tile
            			if (i != 0 || j != 0)
            			{
            				// compute next x and y
	            			int lvX = (int)(currentElement.posInTiles.x) + i;
	            			int lvY = (int)(currentElement.posInTiles.y) + j;
            			
            				// check if next x and y are within the world boundaries
	            			if ( (lvX >= 0) && (lvX < MyOpGame.MY_WORLD_WIDTH_IN_TILES) )
	            			{
	                			if ( (lvY >= 0 + MyOpGame.MY_MINIMAP_HEIGHT_IN_TILES) && (lvY < MyOpGame.MY_WORLD_HEIGHT_IN_TILES + MyOpGame.MY_MINIMAP_HEIGHT_IN_TILES) )
	                			{
	                	        	nextElement = aPathMap[lvX][lvY];
	                	        	// check tile has not been opened yet
	                	        	if (nextElement.state == MyAPathElement0.A_PATH_ELEMENT_NEW)
	                	        	{
	                	        	    // compute G, H and F
	                	        		nextElement.G = (float) (currentElement.G + nextElement.computeDistanceTo(currentElement));
	                	        		nextElement.H = (float) nextElement.computeDistanceTo(this.aPathTarget);
	                	        		nextElement.F = nextElement.G + nextElement.H;  
	                	        		//nextToOpen.I = index;
	                	        		// increment index
	                	        		//index++;
	                	        	    // update state
	                	        		nextElement.state = MyAPathElement0.A_PATH_ELEMENT_OPEN;
	                	        	    // update previous
	                	        		nextElement.previous = currentElement;
	                	        	    // add to open list
	                	        		aPathOpenElementsList.get((int) nextElement.F).add(nextElement);
	                	        		this.openListcount++;
	                	        	}
	                	        	else if (nextElement.state == MyAPathElement0.A_PATH_ELEMENT_OPEN)
	                	        	{
	                	        		// compute new G
	                	        		float newG = (float) (currentElement.G + nextElement.computeDistanceTo(currentElement));
	                	        		// check if with new G < old G
	                	        		if (newG < nextElement.G)
	                	        		{
	                	        			// remove nextToOpen from OPEN list at old F value
	                	        			aPathOpenElementsList.get((int) nextElement.F).remove(nextElement);
	                	        			// update G
	                	        			nextElement.G = newG;
	                	        			// update F
	                	        			nextElement.F = nextElement.G + nextElement.H;
	                	        			// add nextToOpen to oPEN list a new F value
	                	        			aPathOpenElementsList.get((int) nextElement.F).add(nextElement);
	                	        		}
	                	        	}
	                			}
	            			}
            			}
            		}
        		}
        	}
        	
        	// back track path from target to start
        	if (this.targetDone == true)
        	{
        		currentElement = aPathMap[(int) this.aPathTarget.posInTiles.x][(int) this.aPathTarget.posInTiles.y];
        		while(currentElement.previous != null)
        		{
        			currentElement.previous.next = currentElement;
        			currentElement = currentElement.previous;
        		}
        	}
        }
    }
}
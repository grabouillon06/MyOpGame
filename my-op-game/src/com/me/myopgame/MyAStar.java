package com.me.myopgame;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;

public class MyAStar {
	
	MyActor0 myActor0;
	
	public Texture texturePath;
	public BitmapFont yourBitmapFontName;
	
	// path search section
	public MyAStarElement aPathTarget;
	public MyAStarElement aPathStart;
	public MyAStarElement aPathCurrent;
	
	public MyAStarElement[][] aPathMap;
	
	public List<List<MyAStarElement>> aPathOpenElementsList = new ArrayList<List<MyAStarElement>>();
	int aPathOpenListcount;
	public List<List<MyAStarElement>> aPathClosedElementsList = new ArrayList<List<MyAStarElement>>();
	int aPathClosedListCount;
	
	public MyAStar(MyActor0 actor0) {
		
		myActor0 = actor0;
		
		this.texturePath = new Texture(Gdx.files.internal("data/blackMarker_32_32.png"));
		this.yourBitmapFontName = new BitmapFont();
		this.yourBitmapFontName.setScale((float) 0.5);
		
        // allocate and initialize a* path map array
        aPathMap = new MyAStarElement[MyOpGame.MY_WORLD_WIDTH_IN_TILES][MyOpGame.MY_WORLD_HEIGHT_IN_TILES + MyOpGame.MY_MINIMAP_WIDTH_IN_TILES];
        for(int i=0; i<(aPathMap.length); i++ )
             for(int j=0;j<aPathMap[i].length;j++)
            	 aPathMap[i][j] = new MyAStarElement();
    	
        // allocate list of lists for OPEN and CLOSED elements
        int openSize = 100 + (int) Math.sqrt((MyOpGame.MY_WORLD_WIDTH_IN_TILES*MyOpGame.MY_WORLD_WIDTH_IN_TILES) + (MyOpGame.MY_WORLD_HEIGHT_IN_TILES*MyOpGame.MY_WORLD_HEIGHT_IN_TILES));
        for(int i=0; i<openSize; i++ )
        {
        	aPathOpenElementsList.add(new ArrayList<MyAStarElement>());
        	aPathClosedElementsList.add(new ArrayList<MyAStarElement>());
        }
    }

    public void reset()
    {
        // reset aPathMap
        for(int i=0; i<(aPathMap.length); i++ )
            for(int j=0;j<aPathMap[i].length;j++)
            {
           	    aPathMap[i][j].reset();
           	    aPathMap[i][j].posInTiles.x = i;
           	    aPathMap[i][j].posInTiles.y = j;
            }

        // reset aPathStart
    	this.aPathStart = aPathMap[this.myActor0.convertInTiles((int) myActor0.originX)][this.myActor0.convertInTiles((int) myActor0.originY)];
                
        // reset aPathTarget
    	this.aPathTarget = aPathMap[this.myActor0.convertInTiles((int) myActor0.targetX)][this.myActor0.convertInTiles((int) myActor0.targetY)];

        // reset aPathCurrent to aPathStart
    	this.aPathCurrent = this.aPathStart;

        // reset list of lists for OPEN and CLOSED elements
        aPathOpenListcount = 0;
        aPathClosedListCount = 0;
        int openSize = 100 + (int) Math.sqrt((MyOpGame.MY_WORLD_WIDTH_IN_TILES*MyOpGame.MY_WORLD_WIDTH_IN_TILES) + (MyOpGame.MY_WORLD_HEIGHT_IN_TILES*MyOpGame.MY_WORLD_HEIGHT_IN_TILES));
        for(int i=0; i<openSize; i++ )
        {
        	aPathOpenElementsList.get(i).clear();
        	aPathClosedElementsList.get(i).clear();
        }
    }

    public void computePath() {
    	
    	MyAStarElement startElement;
    	MyAStarElement currentElement = aPathMap[0][0];
    	int index =0;
    	
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
	        			aPathMap[i][j].state = MyAStarElement.A_PATH_ELEMENT_CLOSED;
	        		}
	    			
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
        	startElement.state = MyAStarElement.A_PATH_ELEMENT_OPEN;
    		aPathOpenElementsList.get((int) startElement.F).add(startElement);
    		this.aPathOpenListcount++;
    		index++;

    		// search path
        	while ( (this.aPathOpenListcount != 0) && (myActor0.targetLocked != true))
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
                		currentElement.state = MyAStarElement.A_PATH_ELEMENT_CLOSED;
        				// remove lowest F from open list
        				aPathOpenElementsList.get(i).remove(0);
        				// decrement open list count
        				this.aPathOpenListcount--;
                		// add lowest F to closed list
        				aPathClosedElementsList.get(i).add(currentElement);
                		// increment closed list count
        	        	this.aPathClosedListCount++;
        	        	
        	        	//Gdx.app.log( MyOpGame.LOG,"position : x = " + currentElement.posInTiles.x + ", y = " + currentElement.posInTiles.y + ", G = " + currentElement.G + ", H = " + currentElement.H + ", F = " + currentElement.F);
        	        	
        	        	// case when the target is reached
        	        	if (currentElement.posInTiles.x == this.aPathTarget.posInTiles.x)
            	        	if (currentElement.posInTiles.y == this.aPathTarget.posInTiles.y)
            	        		myActor0.targetLocked = true;
        	        	
        	        	// break the loop when new lowest F found
           				break;
        			}
        		}
	        	
        		// compute environment of lowest F.
        		MyAStarElement nextElement;
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
	                	        	if (nextElement.state == MyAStarElement.A_PATH_ELEMENT_NEW)
	                	        	{
	                	        	    // compute G, H and F
	                	        		nextElement.G = (float) (currentElement.G + currentElement.computeDistanceTo(nextElement));
	                	        		nextElement.H = (float) nextElement.computeDistanceTo(this.aPathTarget);
	                	        		nextElement.F = nextElement.G + nextElement.H;  
	                	        		//nextToOpen.I = index;
	                	        		// increment index
	                	        		//index++;
	                	        	    // update state
	                	        		nextElement.state = MyAStarElement.A_PATH_ELEMENT_OPEN;
	                	        	    // update previous
	                	        		nextElement.previous = currentElement;
	                	        	    // add to open list
	                	        		//aPathOpenElementsList.get((int) nextElement.F).add(nextElement);
	                	        		this.sortAndInsertToOpenlist(nextElement);
	                	        		this.aPathOpenListcount++;
	                	        	}
	                	        	else if (nextElement.state == MyAStarElement.A_PATH_ELEMENT_OPEN)
	                	        	{
	                	        		// compute new G
	                	        		float newG = (float) (currentElement.G + currentElement.computeDistanceTo(nextElement));
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
	                	        			sortAndInsertToOpenlist(nextElement);
	                	        		}
	                	        	}
	                			}
	            			}
            			}
            		}
        		}
        	}
        	
        	// back track path from target to start
        	if (myActor0.targetLocked == true)
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
    
    public void sortAndInsertToOpenlist(MyAStarElement element) {
    	if (aPathOpenElementsList.get((int) element.F).isEmpty())
    	   aPathOpenElementsList.get((int) element.F).add(element);
    	else
    	{
        	int index = 0;
        	int count = aPathOpenElementsList.get((int) element.F).size();
    		while (index < count)
    		{
        		MyAStarElement lvElement = aPathOpenElementsList.get((int) element.F).get(index);
        		if (element.F < lvElement.F )
        			break;
    			index ++;
    		}
    		aPathOpenElementsList.get((int) element.F).add(index, element);
    	}
    }

    public void drawInfo(SpriteBatch batch, float alpha) {
    	if (myActor0.targetLocked == true)
    	{
	    	// draw element state
	    	boolean lvValid = true;
	    	if (lvValid == true) {
	    		MyAStarElement currentElement = this.aPathStart;
        		while(currentElement.next != null)
        		{
        			batch.draw(texturePath,(float)(myActor0.convertInPixels((int) currentElement.posInTiles.x)),(float)(myActor0.convertInPixels((int) currentElement.posInTiles.y)));
        			currentElement = currentElement.next;
        		}	    		
        		// draw target
    			batch.draw(texturePath,(float)(myActor0.convertInPixels((int) currentElement.posInTiles.x)),(float)(myActor0.convertInPixels((int) currentElement.posInTiles.y)));
	    	}

	    	// draw path
	    	lvValid = true;
	    	if (lvValid == true) {
		    	for(int i=0; i<(this.aPathMap.length); i++ ) {
		        	int x = 3 + i*MyOpGame.MY_TILE_SIZE_IN_PIXELS;
		            for(int j=0; j<this.aPathMap[i].length; j++)
		            {
		            	int y = 12 + j*MyOpGame.MY_TILE_SIZE_IN_PIXELS;
		            	
		            	if (this.aPathMap[i][j].H != -1)
				        {
		            		yourBitmapFontName.draw(batch, Integer.toString((int) this.aPathMap[i][j].G), x, y);
				            yourBitmapFontName.draw(batch, Integer.toString((int) this.aPathMap[i][j].H), x + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16, y);
				            yourBitmapFontName.draw(batch, Integer.toString((int) this.aPathMap[i][j].F), x, y + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16);
				            yourBitmapFontName.draw(batch, Integer.toString(this.aPathMap[i][j].I), x + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16, y + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16);
				        }
		            }
		        }
	    	}
    	}
        
    }
}

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
	public BitmapFont bitmapFont;
	
	// path search section
	public MyAStarElement target;
	public MyAStarElement start;
	public MyAStarElement current;
	
	public MyAStarElement[][] map;
	
	public List<List<MyAStarElement>> openElementsList = new ArrayList<List<MyAStarElement>>();
	int openListcount;
	public List<List<MyAStarElement>> closedElementsList = new ArrayList<List<MyAStarElement>>();
	int closedListCount;
	
	public ArrayList<MyAStarElement> discoveredElementsList;
	
	public MyAStar(MyActor0 actor0) {
		
		myActor0 = actor0;
		
		this.texturePath = new Texture(Gdx.files.internal("data/blackMarker_32_32.png"));
		this.bitmapFont = new BitmapFont();
		this.bitmapFont.setScale((float) 0.5);
		
        // allocate and initialize a* path map array
        map = new MyAStarElement[MyOpGame.MY_WORLD_WIDTH_IN_TILES][MyOpGame.MY_WORLD_HEIGHT_IN_TILES + MyOpGame.MY_MINIMAP_WIDTH_IN_TILES];
        for(int i=0; i<(map.length); i++ )
             for(int j=0;j<map[i].length;j++)
            	 map[i][j] = new MyAStarElement();
    	
        // allocate list of lists for OPEN and CLOSED elements
        int openSize = 100 + (int) Math.sqrt((MyOpGame.MY_WORLD_WIDTH_IN_TILES*MyOpGame.MY_WORLD_WIDTH_IN_TILES) + (MyOpGame.MY_WORLD_HEIGHT_IN_TILES*MyOpGame.MY_WORLD_HEIGHT_IN_TILES));
        for(int i=0; i<openSize; i++ )
        {
        	openElementsList.add(new ArrayList<MyAStarElement>());
        	closedElementsList.add(new ArrayList<MyAStarElement>());
        }
        
        discoveredElementsList = new ArrayList<MyAStarElement>();
    }

    public void reset()
    {
        // reset aPathMap
        for(int i=0; i<(map.length); i++ )
        {
            for(int j=0;j<map[i].length;j++)
            {
           	    map[i][j].reset();
           	    map[i][j].posInTiles.x = i;
           	    map[i][j].posInTiles.y = j;
            }
        }

        // reset aPathStart
    	this.start = map[this.myActor0.convertInTiles((int) myActor0.originX)][this.myActor0.convertInTiles((int) myActor0.originY)];
                
        // reset aPathTarget
    	this.target = map[this.myActor0.convertInTiles((int) myActor0.targetX)][this.myActor0.convertInTiles((int) myActor0.targetY)];

        // reset aPathCurrent to aPathStart
    	this.current = this.start;

        // reset list of lists for OPEN and CLOSED elements
        openListcount = 0;
        closedListCount = 0;
        int openSize = 100 + (int) Math.sqrt((MyOpGame.MY_WORLD_WIDTH_IN_TILES*MyOpGame.MY_WORLD_WIDTH_IN_TILES) + (MyOpGame.MY_WORLD_HEIGHT_IN_TILES*MyOpGame.MY_WORLD_HEIGHT_IN_TILES));
        for(int i=0; i<openSize; i++ )
        {
        	openElementsList.get(i).clear();
        	closedElementsList.get(i).clear();
        }
        
        //reset discovered elements list
        discoveredElementsList.clear();
    }

    public void registerBlockedTiles ()
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
	        			map[i][j].state = MyAStarElement.A_PATH_ELEMENT_CLOSED;
	        		}
	    			
	    		}
	    	}
    	}

    }
    
    public void computePath() {
    	
    	MyAStarElement startElement;
    	MyAStarElement currentElement = map[0][0];
    	int index = 0;    	

        // Summary of the A* Method:
        {
        	//- Add the starting square (or node) to the open list.
        	startElement = start;
        	startElement.G = 0;
        	startElement.H = (float) startElement.computeDistanceTo(this.target);
        	startElement.F = startElement.G + startElement.H;  
        	startElement.I = index;
        	startElement.state = MyAStarElement.A_PATH_ELEMENT_OPEN;
    		openElementsList.get((int) startElement.F).add(startElement);
    		this.openListcount++;
    		index++;

    		// search path
        	while ( (this.openListcount != 0) && (myActor0.targetLocked != true))
        	{	
        		// look for lowest F
                int openSize = 100 + (int) Math.sqrt((MyOpGame.MY_WORLD_WIDTH_IN_TILES*MyOpGame.MY_WORLD_WIDTH_IN_TILES) + (MyOpGame.MY_WORLD_HEIGHT_IN_TILES*MyOpGame.MY_WORLD_HEIGHT_IN_TILES));
        		for(int i=0; i<openSize; i++ )
        		{
        			if (openElementsList.get(i).isEmpty() == false)
        			{
        				// get lowest F
        				currentElement = openElementsList.get(i).get(0);
        				// assign I
        				currentElement.I = index;
        				// increment I
        				index++;
        				// move to CLOSED state
                		currentElement.state = MyAStarElement.A_PATH_ELEMENT_CLOSED;
        				// remove lowest F from open list
        				openElementsList.get(i).remove(0);
        				// decrement open list count
        				this.openListcount--;
                		// add lowest F to closed list
        				closedElementsList.get(i).add(currentElement);
                		// increment closed list count
        	        	this.closedListCount++;
        	        	
        	        	//Gdx.app.log( MyOpGame.LOG,"position : x = " + currentElement.posInTiles.x + ", y = " + currentElement.posInTiles.y + ", G = " + currentElement.G + ", H = " + currentElement.H + ", F = " + currentElement.F);
        	        	
        	        	// case when the target is reached
        	        	if (currentElement.posInTiles.x == this.target.posInTiles.x)
            	        	if (currentElement.posInTiles.y == this.target.posInTiles.y)
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
	                	        	nextElement = map[lvX][lvY];
	                	        	// check tile has not been opened yet
	                	        	if (nextElement.state == MyAStarElement.A_PATH_ELEMENT_NEW)
	                	        	{
	                	        	    // compute G, H and F
	                	        		nextElement.G = (float) (currentElement.G + currentElement.computeDistanceTo(nextElement));
	                	        		nextElement.H = (float) nextElement.computeDistanceTo(this.target);
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
	                	        		this.openListcount++;
	                	        	}
	                	        	else if (nextElement.state == MyAStarElement.A_PATH_ELEMENT_OPEN)
	                	        	{
	                	        		// compute new G
	                	        		float newG = (float) (currentElement.G + currentElement.computeDistanceTo(nextElement));
	                	        		// check if with new G < old G
	                	        		if (newG < nextElement.G)
	                	        		{
	                	        			// remove nextToOpen from OPEN list at old F value
	                	        			openElementsList.get((int) nextElement.F).remove(nextElement);
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
        		currentElement = map[(int) this.target.posInTiles.x][(int) this.target.posInTiles.y];
        		while(currentElement.previous != null)
        		{
        			currentElement.previous.next = currentElement;
        			currentElement = currentElement.previous;
        		}
        	}
        }
    }
    
    public void sortAndInsertToOpenlist(MyAStarElement element) {
    	if (openElementsList.get((int) element.F).isEmpty())
    	   openElementsList.get((int) element.F).add(element);
    	else
    	{
        	int index = 0;
        	int count = openElementsList.get((int) element.F).size();
    		while (index < count)
    		{
        		MyAStarElement lvElement = openElementsList.get((int) element.F).get(index);
        		if (element.F < lvElement.F )
        			break;
    			index ++;
    		}
    		openElementsList.get((int) element.F).add(index, element);
    	}
    }

    public void discoverEnv()
    {
		for(int i=-2; i<3; i++ ) 
		{
			int relativeI = (int) (i+this.current.posInTiles.x);
			for(int j=-2; j<3; j++) 
			{
				int relativeJ = (int) (j+this.current.posInTiles.y);

            	if (	(relativeI >= 0) && (relativeJ >= 4) && 
            			(relativeI <= MyOpGame.MY_WORLD_WIDTH_IN_TILES) && (relativeJ <= (MyOpGame.MY_WORLD_HEIGHT_IN_TILES + 4))) 
            	{
            		if (this.map[relativeI][relativeJ].F != -1)
            		{
		            	if (this.map[relativeI][relativeJ].discoveredFlag == false) 
		            	{
		            		this.map[relativeI][relativeJ].discoveredFlag = true;
		            		this.discoveredElementsList.add(this.map[relativeI][relativeJ]);
				        }
            		}
            	}
			}
		}

    }

    public void moveActor()
    {
    	if (this.current.next != null)
    		if (((int) this.myActor0.getX() == this.myActor0.convertInPixels((int) this.current.next.posInTiles.x)) && ((int) this.myActor0.getY() == this.myActor0.convertInPixels((int) this.current.next.posInTiles.y)))
    		{
    			this.current = this.current.next;			
    			//Gdx.app.log( MyOpGame.LOG,"position : x = " + this.aStar.aPathCurrent.posInTiles.x + " ("+(int) this.getX()+"), y = " + this.aStar.aPathCurrent.posInTiles.y + " ("+(int) this.getY()+"), G = " + this.aStar.aPathCurrent.G + ", H = " + this.aStar.aPathCurrent.H + ", F = " + this.aStar.aPathCurrent.F);
    		}
		
        if (this.current.next != null)
		{
			int x = this.myActor0.convertInPixels((int) this.current.next.posInTiles.x);
			int y = this.myActor0.convertInPixels((int) this.current.next.posInTiles.y);
			this.myActor0.translate((float)(x - this.myActor0.convertInPixels((int) this.current.posInTiles.x))/(MyOpGame.MY_TILE_SIZE_IN_PIXELS/MyOpGame.MY_ACTOR_SPEED_FACTOR), (float)(y - this.myActor0.convertInPixels((int) this.current.posInTiles.y))/(MyOpGame.MY_TILE_SIZE_IN_PIXELS/MyOpGame.MY_ACTOR_SPEED_FACTOR));
		}        	
    }
    
    public void drawInfo(SpriteBatch batch, float alpha) {
    	if (myActor0.targetLocked == true)
    	{
    		// draw complete selected A* path
	    	boolean lvValid = true;
	    	if (lvValid == true) {
	    		MyAStarElement currentElement = this.start;
        		while(currentElement.next != null)
        		{
        			batch.draw(texturePath,(float)(myActor0.convertInPixels((int) currentElement.posInTiles.x)),(float)(myActor0.convertInPixels((int) currentElement.posInTiles.y)));
        			currentElement = currentElement.next;
        		}	    		
        		// draw target
    			batch.draw(texturePath,(float)(myActor0.convertInPixels((int) currentElement.posInTiles.x)),(float)(myActor0.convertInPixels((int) currentElement.posInTiles.y)));
	    	}

	    	// draw all path metrics detected during the search
	    	lvValid = false;
	    	if (lvValid == true) {
		    	for(int i=0; i<(this.map.length); i++ ) {
		        	int x = 3 + i*MyOpGame.MY_TILE_SIZE_IN_PIXELS;
		            for(int j=0; j<this.map[i].length; j++)
		            {
		            	int y = 12 + j*MyOpGame.MY_TILE_SIZE_IN_PIXELS;
		            	
		            	if (this.map[i][j].H != -1)
				        {
		            		discoveredElementsList.add(this.map[i][j]);
		            		bitmapFont.draw(batch, Integer.toString((int) this.map[i][j].G), x, y);
				            bitmapFont.draw(batch, Integer.toString((int) this.map[i][j].H), x + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16, y);
				            bitmapFont.draw(batch, Integer.toString((int) this.map[i][j].F), x, y + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16);
				            bitmapFont.draw(batch, Integer.toString(this.map[i][j].I), x + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16, y + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16);
				        }
		            }
		        }
	    	}
	    	
	    	// draw current visible path metrics
	    	lvValid = true;
	    	if (lvValid == true) 
	    	{
	    		for(int i=0; i<this.discoveredElementsList.size(); i++ ) 
	    		{
	    			MyAStarElement lvElement = this.discoveredElementsList.get(i);
		        	int x = (int) (3 + (lvElement.posInTiles.x)*MyOpGame.MY_TILE_SIZE_IN_PIXELS);
	            	int y = (int) (12 + (lvElement.posInTiles.y)*MyOpGame.MY_TILE_SIZE_IN_PIXELS);		            	
            		bitmapFont.draw(batch, Integer.toString((int) lvElement.G), x, y);
		            bitmapFont.draw(batch, Integer.toString((int) lvElement.H), x + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16, y);
		            bitmapFont.draw(batch, Integer.toString((int) lvElement.F), x, y + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16);
		            bitmapFont.draw(batch, Integer.toString(lvElement.I), x + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16, y + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16);
	    		}
	    	}	    	
    	}
    }
}

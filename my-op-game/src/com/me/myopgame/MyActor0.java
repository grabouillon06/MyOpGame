package com.me.myopgame;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MyActor0 extends Actor {
	
	// sprite section
	public Texture textureActor0;
	public Texture texturePath;
	public BitmapFont yourBitmapFontName;
	
	// path search section
	public MyAPathElement0 aPathTarget;
	public boolean targetDone;
	public MyAPathElement0 aPathStart;
	
	public MyAPathElement0[][] aPathMap;
	
	public List<List<MyAPathElement0>> aPathOpenElementsList = new ArrayList<List<MyAPathElement0>>();
	int openListcount;
	public List<List<MyAPathElement0>> aPathClosedElementsList = new ArrayList<List<MyAPathElement0>>();
	int closedListCount;
	
	public MyActor0(Stage stage, int x, int y) {	
		
        textureActor0 = new Texture(Gdx.files.internal("data/unit_green_32.png"));
        texturePath = new Texture(Gdx.files.internal("data/blackMarker_32_32.png"));
        yourBitmapFontName = new BitmapFont();
        yourBitmapFontName.setScale((float) 0.5);

        this.setStage(stage);
        this.setName("actor0");
        this.setPosition(x, y);        
        
        // allocate and initialize a* path map array
        aPathMap = new MyAPathElement0[MyOpGame.MY_WORLD_WIDTH_IN_TILES][MyOpGame.MY_WORLD_HEIGHT_IN_TILES + MyOpGame.MY_MINIMAP_WIDTH_IN_TILES];
        for(int i=0; i<(aPathMap.length); i++ )
             for(int j=0;j<aPathMap[i].length;j++)
             {
            	 aPathMap[i][j] = new MyAPathElement0();
            	 aPathMap[i][j].posInTiles.set(i,j);
             }
        
        // allocate start and target
        aPathStart = new MyAPathElement0();
        aPathTarget = new MyAPathElement0();
        targetDone = false;
        
        this.aPathStart.posInTiles.set(this.convertInTiles(x),this.convertInTiles(y));

        // allocate list of lists for OPEN and CLOSED elements
        for(int i=0; i<(MyOpGame.MY_WORLD_WIDTH_IN_TILES); i++ )
        {
        	aPathOpenElementsList.add(new ArrayList<MyAPathElement0>());
        	aPathClosedElementsList.add(new ArrayList<MyAPathElement0>());
        }
             
        // reset list of lists for OPEN and CLOSED elements
        openListcount = 0;
        closedListCount = 0;
        for(int i=0; i<(MyOpGame.MY_WORLD_WIDTH_IN_TILES); i++ )
        {
        	aPathOpenElementsList.get(i).clear();
        	aPathClosedElementsList.get(i).clear();
        }
    }
	
    public void draw(SpriteBatch batch, float alpha) {
    	
    	if (this.targetDone == true)
    	{
	    	// draw element state
	    	{
	    		MyAPathElement0 currentElement = aPathMap[(int)(aPathStart.posInTiles.x)][(int)(aPathStart.posInTiles.y)];
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
	            	
			        yourBitmapFontName.draw(batch, Integer.toString(aPathMap[i][j].G), x, y);
			        yourBitmapFontName.draw(batch, Integer.toString(aPathMap[i][j].H), x + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16, y);
			        yourBitmapFontName.draw(batch, Integer.toString(aPathMap[i][j].F), x, y + MyOpGame.MY_TILE_SIZE_IN_PIXELS - 16);
	            }
	        }
    	}

    	// draw actor
		MyAPathElement0 currentElement = aPathMap[this.convertInTiles((int) this.getX())][this.convertInTiles((int) this.getY())];
		if (currentElement.next != null)
		{
			int x = this.convertInPixels((int) currentElement.next.posInTiles.x);
			int y = this.convertInPixels((int) currentElement.next.posInTiles.y);
			this.translate((float)(x - this.convertInPixels((int) currentElement.posInTiles.x))/16, (float)(y - this.convertInPixels((int) currentElement.posInTiles.y))/16);
		}

        batch.draw(textureActor0,this.getX(),this.getY());
    }
    
    public void setTarget(int x, int y) {
        this.aPathTarget.posInTiles.set(this.convertInTiles(x),this.convertInTiles(y));
    	
    	// compute aPath
    	this.aPathcompute();
    }
    
    public int convertInTiles(int x) {
    	return (int)(x/MyOpGame.MY_TILE_SIZE_IN_PIXELS);
    }
    
    public int convertInPixels(int x) {
    	return (int)(x*MyOpGame.MY_TILE_SIZE_IN_PIXELS);
    }
    
    public void aPathcompute() {
    	
    	MyAPathElement0 startElement;
    	MyAPathElement0 currentElement = aPathMap[0][0];

        // Summary of the A* Method:
        {
        	//- Add the starting square (or node) to the open list.
        	startElement = aPathMap[(int)(aPathStart.posInTiles.x)][(int)(aPathStart.posInTiles.y)];
        	startElement.G = (int) startElement.computeDistance(this.aPathStart.posInTiles);
        	startElement.H = (int) startElement.computeDistance(this.aPathTarget.posInTiles);
        	startElement.F = startElement.G + startElement.H;  
        	startElement.state = MyAPathElement0.A_PATH_ELEMENT_OPEN;
    		aPathOpenElementsList.get(startElement.F).add(startElement);
    		this.openListcount++;

    		// search path
        	while ( (this.openListcount != 0) && (targetDone != true))
        	{	
        		// look for lowest F
        		for(int i=0; i<(MyOpGame.MY_WORLD_WIDTH_IN_TILES); i++ )
        		{
        			if (aPathOpenElementsList.get(i).isEmpty() == false)
        			{
        				// get lowest F
        				currentElement = aPathOpenElementsList.get(i).get(0);
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
        	        	
        	        	Gdx.app.log( MyOpGame.LOG,"position : x = " + currentElement.posInTiles.x + ", y = " + currentElement.posInTiles.y + ", G = " + currentElement.G + ", H = " + currentElement.H + ", F = " + currentElement.F);
        	        	
        	        	// case when the target is reached
        	        	if (currentElement.posInTiles.x == this.aPathTarget.posInTiles.x)
            	        	if (currentElement.posInTiles.y == this.aPathTarget.posInTiles.y)
            	        		targetDone = true;
        	        	
        	        	// break the loop when new lowest F found
           				break;
        			}
        		}
	        	
        		// compute environment of lowest F.
        		MyAPathElement0 nextToOpen;
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
	                	        	nextToOpen = aPathMap[lvX][lvY];
	                	        	// check tile has not been opened yet
	                	        	if (nextToOpen.state == MyAPathElement0.A_PATH_ELEMENT_NEW)
	                	        	{
	                	        	    // compute G, H and F
	                	        		nextToOpen.G = (int) nextToOpen.computeDistance(this.aPathStart.posInTiles);
	                	        		nextToOpen.H = (int) nextToOpen.computeDistance(this.aPathTarget.posInTiles);
	                	        		nextToOpen.F = nextToOpen.G + nextToOpen.H;  
	                	        	    // update state
	                	        		nextToOpen.state = MyAPathElement0.A_PATH_ELEMENT_OPEN;
	                	        	    // update previous
	                	        		nextToOpen.previous = currentElement;
	                	        	    // add to open list
	                	        		aPathOpenElementsList.get(nextToOpen.F).add(nextToOpen);
	                	        		this.openListcount++;
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
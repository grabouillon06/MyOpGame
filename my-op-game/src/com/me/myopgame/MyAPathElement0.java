package com.me.myopgame;

import com.badlogic.gdx.math.Vector2;

public class MyAPathElement0 {

	public static int A_PATH_ELEMENT_NEW = 0;
	public static int A_PATH_ELEMENT_OPEN = 1;
	public static int A_PATH_ELEMENT_CLOSED = 2;
	
	public int state;
	public Vector2 posInTiles;
	public int G;
	public int H;
	public int F;
	public MyAPathElement0 previous;
	public MyAPathElement0 next;
	
	MyAPathElement0() {
		this.posInTiles = new Vector2(-1,-1);
		this.F = -1;
		this.G = -1;
		this.H = -1;
		this.next = null;
		this.previous = null;
		this.state = MyAPathElement0.A_PATH_ELEMENT_NEW;
	}
	
    public int computeDistance(Vector2 v) {
    	int x = (int) Math.sqrt((this.posInTiles.x - v.x)*(this.posInTiles.x - v.x));
    	int y = (int) Math.sqrt((this.posInTiles.y - v.y)*(this.posInTiles.y - v.y));    	
    	return (x + y);
    }
    

}

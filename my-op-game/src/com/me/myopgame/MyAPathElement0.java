package com.me.myopgame;

import com.badlogic.gdx.math.Vector2;

public class MyAPathElement0 {

	public static int A_PATH_ELEMENT_NEW = 0;
	public static int A_PATH_ELEMENT_OPEN = 1;
	public static int A_PATH_ELEMENT_CLOSED = 2;
	
	public int state;
	public Vector2 posInTiles;
	public float G;
	public float H;
	public float F;
	public int I;
	public MyAPathElement0 previous;
	public MyAPathElement0 next;
	public int weight;
	
	MyAPathElement0() {
		this.posInTiles = new Vector2(-1,-1);
		this.F = -1;
		this.G = -1;
		this.H = -1;
		this.I = -1;
		this.weight = 10;
		this.next = null;
		this.previous = null;
		this.state = MyAPathElement0.A_PATH_ELEMENT_NEW;
	}
	
    public float computeDistanceTo(MyAPathElement0 elem) {
    	float x2 = (float) ((this.posInTiles.x - elem.posInTiles.x)*(this.posInTiles.x - elem.posInTiles.x));
    	float y2 = (float) ((this.posInTiles.y - elem.posInTiles.y)*(this.posInTiles.y - elem.posInTiles.y));    	
    	return ((float)(Math.sqrt(x2 + y2)));
    }

}

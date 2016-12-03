package com.tito.cyber.lompatpanda;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CGRect;

import android.graphics.Bitmap;

public class BoardLine extends CCSprite {
	
	public int touchedCount;

	public BoardLine(CCTexture2D texture) {
		super(texture);
		// TODO Auto-generated constructor stub
	}

	public BoardLine(CCTexture2D texture, CGRect rect) {
		super(texture, rect);
		// TODO Auto-generated constructor stub
	}

	public BoardLine(CCSpriteFrame spriteFrame) {
		super(spriteFrame);
		// TODO Auto-generated constructor stub
	}

	public BoardLine(String spriteFrameName, boolean isFrame) {
		super(spriteFrameName, isFrame);
		// TODO Auto-generated constructor stub
	}

	public BoardLine(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
	}

	public BoardLine() {
		// TODO Auto-generated constructor stub
	}

	public BoardLine(String filepath, CGRect rect) {
		super(filepath, rect);
		// TODO Auto-generated constructor stub
	}

	public BoardLine(Bitmap image, String key) {
		super(image, key);
		// TODO Auto-generated constructor stub
	}

	public BoardLine(CCSpriteSheet spritesheet, CGRect rect) {
		super(spritesheet, rect);
		// TODO Auto-generated constructor stub
	}

}

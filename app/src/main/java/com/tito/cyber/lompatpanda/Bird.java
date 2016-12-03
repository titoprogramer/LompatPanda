package com.tito.cyber.lompatpanda;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCIntervalAction;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.graphics.Bitmap;

import com.tito.cyber.lompatpanda.Common.FoodType;

public class Bird extends CCSprite {

	public CCSprite foodSprite;

	public Bird(CCTexture2D texture) {
		super(texture);
		// TODO Auto-generated constructor stub
	}

	public Bird(CCTexture2D texture, CGRect rect) {
		super(texture, rect);
		// TODO Auto-generated constructor stub
	}

	public Bird(CCSpriteFrame spriteFrame) {
		super(spriteFrame);
		// TODO Auto-generated constructor stub
	}

	public Bird(String spriteFrameName, boolean isFrame) {
		super(spriteFrameName, isFrame);
		// TODO Auto-generated constructor stub
	}

	public Bird(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
	}

	private static Bird _bird = null;
	public static Bird init(int birdType, FoodType setFoodType) {
		// TODO Auto-generated constructor stub
	    switch (birdType) {
        case Common.Bird_Type_bird1:
        	_bird = new Bird("bird1.png", CGRect.make(0, 0, 42, 34));
            width = 42;
            height = 34;
            _bird.runAction(CCRepeatForever.action((CCIntervalAction) Common.ani_bird1.copy()));
            break;
        case Common.Bird_Type_bird2:
        	_bird = new Bird("bird2.png", CGRect.make(0, 0, 42, 34));
            width = 42;
            height = 34;
            _bird.runAction(CCRepeatForever.action((CCIntervalAction) Common.ani_bird2.copy()));
            break;
        default:
            break;
	    }
	    
	    float w = 60;
	    float h = 60;
	
	    switch (setFoodType) {
	        case FoodType_protect:
	        	_bird.foodSprite = CCSprite.sprite("item.png", CGRect.make(420, 0, w, h));
	            break;
	        case FoodType_fast:
	        	_bird.foodSprite = CCSprite.sprite("item.png", CGRect.make(420, 0, w, h));
	            break;
	            
	        default:
	            break;
	    }
	    
	    if (birdType == Common.Bird_Type_bird1) {
	    	_bird.foodSprite.setPosition(width / 2, 7);
	    }
	    else {
	    	_bird.foodSprite.setPosition(width / 2, 12);
	    }
	    _bird.foodSprite.setPosition(width / 2, 0);
	    _bird.foodSprite.setScale(0.3f);
	    _bird.addChild(_bird.foodSprite, 0);
	        
	    _bird.type = birdType;
	    _bird.foodType = setFoodType;
	    _bird.setScale(Common.kXForIPhone);
	    _bird.velocity = CGPoint.ccp(300 * Common.kXForIPhone, 0);
	    
	    return _bird;
	}

	private Bird(String filepath, CGRect rect) {
		super(filepath, rect);
		// TODO Auto-generated constructor stub
	}

	public Bird(Bitmap image, String key) {
		super(image, key);
		// TODO Auto-generated constructor stub
	}

	public Bird(CCSpriteSheet spritesheet, CGRect rect) {
		super(spritesheet, rect);
		// TODO Auto-generated constructor stub
	}

	public CGRect rect()
	{
	    //	CGSize s = [[self texture] contentSize];
	    MoveLayer movelayer = (MoveLayer)this.parent_;
	    float deltaHeight = movelayer.originHeight - movelayer.getPosition().y;
	    float realPosX = (float) (Common.SCREEN_WIDTH / 2 + (getPosition().x - Common.SCREEN_WIDTH / 2) * ( 1 / ( 1 + deltaHeight * 1.5 / Common.SCREEN_HEIGHT) )); 
	    float realPosY = (float) (Common.SCREEN_HEIGHT / 2 + (getPosition().y - Common.SCREEN_HEIGHT / 2) * ( 1 / ( 1 + deltaHeight * 1.5 / Common.SCREEN_HEIGHT) ) - deltaHeight);
	    
		CGRect rt = CGRect.make(-width * getScaleX() / 2 * movelayer.getScaleX() + realPosX,
							   -height * getScaleY() / 2 * movelayer.getScaleY() + realPosY, 
							   width * getScaleX() * movelayer.getScaleX(), 
							   height * getScaleY() * movelayer.getScaleY());
		return rt;
	}

	private static int width;
	private static int height;
	public int type;
	public FoodType foodType;
	public CGPoint velocity;

	private void removeSprite(CCSprite sp)
	{
	    CCTexture2D tex = sp.getTexture();
	    CCTextureCache.sharedTextureCache().removeTexture(tex);
	    sp.removeFromParentAndCleanup(true);
	}

	private void removeCache()
	{
	    removeSprite(foodSprite);
	    
	    if (type == Common.Bird_Type_bird1) {
	    	CCTextureCache.sharedTextureCache().removeTexture("bird1.png");

	    }
	    else if (type == Common.Bird_Type_bird2)
	    {
	    	CCTextureCache.sharedTextureCache().removeTexture("bird2.png");

	    }
	}
	
	@Override
	public void onExit() {
		removeCache();
		super.onExit();
	}
}

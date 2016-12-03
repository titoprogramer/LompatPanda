package com.tito.cyber.lompatpanda;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CGRect;

import android.graphics.Bitmap;

import com.tito.cyber.lompatpanda.Common.FoodType;

public class Food extends CCSprite {

	private static int height;
	private static float width;
	public FoodType type;
	public boolean isEnable;

	public Food(CCTexture2D texture) {
		super(texture);
		// TODO Auto-generated constructor stub
	}

	public Food(CCTexture2D texture, CGRect rect) {
		super(texture, rect);
		// TODO Auto-generated constructor stub
	}

	public Food(CCSpriteFrame spriteFrame) {
		super(spriteFrame);
		// TODO Auto-generated constructor stub
	}

	public Food(String spriteFrameName, boolean isFrame) {
		super(spriteFrameName, isFrame);
		// TODO Auto-generated constructor stub
	}

	public Food(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
	}

	private static Food _food = null;
	public static Food init( FoodType setType ) {
		// TODO Auto-generated constructor stub
	    switch (setType) {
        case FoodType_bomb:
        {
            width = 60;
            height = 60;
            
            _food = new Food("item.png", CGRect.make(0, 0, width, height));

            break;
        }   
        case FoodType_protect:
        {
            
            break;
        }   
        case FoodType_rainbow:
        {
            width = 60;
            height = 60;
            
            _food = new Food("item.png", CGRect.make(420, 0, width, height));

            break;
        }   
        case FoodType_fast:
        {
            
            break;
        }   
        case FoodType_sun:
        {
            width = 60;
            height = 60;

            _food = new Food("item.png", CGRect.make((int)((1 + Math.random() * 6)) * 60, 0, width, height));

            break;
        }   

        default:
            break;
	    }
    
	    _food.setScaleX(Common.kXForIPhone * 0.5f);
	    _food.setScaleY(Common.kXForIPhone * 0.5f);
	    _food.type = setType;
	    _food.isEnable = true;
	    
	    return _food;

	}

	private Food(String filepath, CGRect rect) {
		super(filepath, rect);
		// TODO Auto-generated constructor stub
	}

	public Food(Bitmap image, String key) {
		super(image, key);
		// TODO Auto-generated constructor stub
	}

	public Food(CCSpriteSheet spritesheet, CGRect rect) {
		super(spritesheet, rect);
		// TODO Auto-generated constructor stub
	}

	public CGRect rect()
	{
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

}

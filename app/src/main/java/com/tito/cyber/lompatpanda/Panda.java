package com.tito.cyber.lompatpanda;

import java.util.Date;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCIntervalAction;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import android.graphics.Bitmap;

import com.tito.cyber.lompatpanda.Common.PlayerInfo;

public class Panda extends CCSprite {

	enum DirectState {
	    DirectState_up,
	    DirectState_down
	};

	public final long EFFECT_TIME = 1000;
	public final float FAST_VEL  =  (50 * Common.kXForIPhone);

	public static final int PandaState_start = -1;
	public static final int PandaState_none = 0;
	public static final int PandaState_rainbow = 1;
	public static final int PandaState_fast = 2;
	public static final int PandaState_fail = 3;
	public static final int PandaState_protect = 4;
	
	public static final float GRAVITY = (-400 * Common.kYForIPhone);
	 
	public CCSprite tail;
	public CGPoint velocity;
	public String playerName;
	public float totalDistance;
	public DirectState stateDirect;
	public int state;
	public long startTime_fast = 0;
	public int flayLength;
	public long startTime_rainbow = 0;
	public long startTime_protect = 0;
	public float realHeight;
	public int rank;
	public int birdCount;
	public int sunCount;
	public float endTime = 0.0f;
	public float startTime = 0.0f;

	public Panda(CCTexture2D texture) {
		super(texture);
		// TODO Auto-generated constructor stub
	}

	public Panda(CCTexture2D texture, CGRect rect) {
		super(texture, rect);
		// TODO Auto-generated constructor stub
	}

	public Panda(CCSpriteFrame spriteFrame) {
		super(spriteFrame);
		// TODO Auto-generated constructor stub
	}

	public Panda(String spriteFrameName, boolean isFrame) {
		super(spriteFrameName, isFrame);
		// TODO Auto-generated constructor stub
	}

	public Panda(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
	}

	private static Panda _panda = null;
	public static Panda initPanda() {
		// TODO Auto-generated constructor stub
		_panda = new Panda("0100001.png");
        
		_panda.setScaleX(Common.kXForIPhone);
		_panda.setScaleY(Common.kXForIPhone);

        _panda.tail = CCSprite.sprite("tear1.png");
        _panda.tail.setAnchorPoint(1f, 0.5f);
        _panda.tail.setPosition(_panda.getContentSize().width / 2, _panda.getContentSize().height / 2);
        _panda.addChild(_panda.tail, -1);

        CCAnimation ani = CCAnimation.animation("tear", 0.1f);
        ani.addFrame("tear.png");
        ani.addFrame("tear1.png");
        CCAnimate act = CCAnimate.action(ani);
        _panda.tail.runAction(CCRepeatForever.action(act));

        _panda.velocity = CGPoint.ccp(0, 0);
        _panda.playerName = Common.g_gamePlayerInfo.name;
        _panda.totalDistance = Common.g_gamePlayerInfo.totalDistance;
        _panda.stateDirect = DirectState.DirectState_down;
        _panda.state = PandaState_none;
        
        return _panda;
	}

	public Panda(String filepath, CGRect rect) {
		super(filepath, rect);
		// TODO Auto-generated constructor stub
	}

	public Panda(Bitmap image, String key) {
		super(image, key);
		// TODO Auto-generated constructor stub
	}

	public Panda(CCSpriteSheet spritesheet, CGRect rect) {
		super(spritesheet, rect);
		// TODO Auto-generated constructor stub
	}

	public CGRect rect()
	{
		CGSize s = this.getTexture().getContentSize();
		CGRect rt = CGRect.make(-s.width * getScaleX() / 2 + getPosition().x,
							   -s.height * getScaleY() / 2 + getPosition().y, 
							   s.width * getScaleX(), 
							   s.height * getScaleY());
		return rt;
	}
	
	public void move(float delta)
	{
	    GameLayer gameLayer = (GameLayer)this.parent_;

	    long curTime = new Date().getTime();
	    
	    switch (state) {
	        case PandaState_fast:
	        {
	            if (curTime - startTime_fast > EFFECT_TIME) {
	                state = PandaState_none;
	            }
	            else
	            {
	                setPosition(Common.SCREEN_WIDTH / 3, Common.SCREEN_HEIGHT * 2 / 3);
	                gameLayer.moveLayer.bodysMove(-FAST_VEL);
	                gameLayer.moveLayer.foodsMove(-FAST_VEL);
	                gameLayer.moveLayer.birdsMove(-FAST_VEL);
	                gameLayer.dotMove(-FAST_VEL);
	                
	                tail.setRotation(0);
	                tail.setScale(0.4f);
	                
	                flayLength += (int)FAST_VEL;
	                return;

	            }
	            break;
	        }
	        case PandaState_rainbow:
	            if (curTime - startTime_rainbow > EFFECT_TIME) {
	                state = PandaState_none;
	            }

	            break;
	        case PandaState_protect:
	            if (curTime - startTime_protect > EFFECT_TIME) {
	                state = PandaState_none;
	            }

	            break;
	  
	        default:
	            break;
	    }
	    

	    velocity.y = velocity.y + GRAVITY * delta;
	    
	    float alpha = (float) Math.atan2(velocity.y, velocity.x );
	    alpha = (float) (alpha * 180 / Math.PI);

	    
	    tail.setRotation(-alpha);
	    tail.setScale(0.4f);
	    
	    int n = (int) (Math.random() * 4);
	    if (velocity.y > 0 ) {
	        if (stateDirect == DirectState.DirectState_down) {
	            stateDirect = DirectState.DirectState_up;
	            this.stopAllActions();
	            this.runAction(Common.ani_panda_leaf_up[n].copy());
	        }
	    }
	    else
	    {
	        if (stateDirect == DirectState.DirectState_up) {
	            stateDirect = DirectState.DirectState_down;
	            this.stopAllActions();
	            this.runAction(Common.ani_panda_leaf_down[n].copy());
	        }
	    }
	    
	    if (gameLayer.tipNum < 0) {
	        realHeight = realHeight + velocity.y * delta * 2;
	    }
	    else
	    {
	        realHeight = realHeight + velocity.y * delta;

	    }
	    
	    if (realHeight > Common.SCREEN_HEIGHT / 3 * 2) {
	        
	        gameLayer.cameraMove();
	        
	    }
	    else if (realHeight <= -20)
	    {
	        gameLayer.gameOver();
	    }
	    else
	    {
	        if (gameLayer.tipNum < 0) {
	            setPosition(CGPoint.ccpAdd(getPosition(), CGPoint.ccp(0, velocity.y * delta * 2)));
	        }
	        else
	        {
	            setPosition(CGPoint.ccpAdd(getPosition(), CGPoint.ccp(0, velocity.y * delta)));
	        }
	        
	        realHeight = getPosition().y;
	    }
	    
	    float moveDelta_X = velocity.x * delta;
	    
	    if (moveDelta_X > 0) {
	        if (getPosition().x > Common.SCREEN_WIDTH / 3) {
	            gameLayer.moveLayer.bodysMove(-moveDelta_X);
	            gameLayer.moveLayer.foodsMove(-moveDelta_X);
	            gameLayer.moveLayer.birdsMove(-moveDelta_X);
	            gameLayer.dotMove(-moveDelta_X);
	            gameLayer.tutorialMove(-moveDelta_X);
	            
	            flayLength += (int)moveDelta_X;

	            alpha = (float) (alpha * 180 / Math.PI);
	        }
	        else
	        {
	            setPosition(CGPoint.ccpAdd(getPosition(), CGPoint.ccp(moveDelta_X, 0)));
	            flayLength += (int)moveDelta_X;
	        }
	    }
	    else
	    {
	        setPosition(CGPoint.ccpAdd(getPosition(), CGPoint.ccp(moveDelta_X, 0)));
	        
	        flayLength += (int)moveDelta_X;
	        
	        if (getPosition().x < 0) {
	            velocity = CGPoint.ccp( -velocity.x, velocity.y);
	        }
	    }
	    
	}

	public void calcRank()
	{
	    for (int i = 0; i < 20; i ++) {
	        PlayerInfo playerInfo = Common.gameInfo.get(i);
	        if (flayLength / 3 > playerInfo.score) {
	            rank = i;
	            return;
	        }
	    }
	    rank = 20;
	}

	public void saveUserInfo()
	{
	    for (int i = 0; i < 20; i ++) {
	        if (rank <= i) {
	            Common.gameInfo.remove(Common.gameInfo.size()-1);

	            PlayerInfo newPlayer = new PlayerInfo();
	            newPlayer.rankNum = rank;
	            newPlayer.name = playerName;
	            newPlayer.score = flayLength / 3;
	            
	            Common.gameInfo.add(i, newPlayer);
	            break;
	        }
	    }
	    
	    Common.g_gamePlayerInfo.name = playerName;
	    Common.g_gamePlayerInfo.totalDistance = totalDistance;
	    
	    Common.saveGameInfo();
	}

	public void collisionWithBomb()
	{
	    if (state == PandaState_protect || state == PandaState_fast) {
	        return;
	    }
//	    bomb.play(); sound
	    Common.effectPlay(R.raw.bomb);
	    tail.setVisible(false);
	    
	    state = PandaState_fail;
	    this.stopAllActions();
	    this.runAction(Common.ani_panda_face_fail.copy());
	    velocity = CGPoint.ccp(0, 0);
	};

	public void collisionWithProtect()
	{
	    birdCount ++;
	    Common.effectPlay(R.raw.bounce);
	    tail.setVisible(false);
	    
	    if (state == PandaState_fast) {
	        return;
	    }
	   
	    state = PandaState_protect;
	    startTime_protect = new Date().getTime();
	};

	public void collisionWithRainbow()
	{
	    tail.setVisible(true);
	    
	    if (state == PandaState_fast) {
	        return;
	    }
	    
	    state = PandaState_rainbow;
	    startTime_rainbow = new Date().getTime();
	    this.stopAllActions();
	    this.runAction(CCRepeatForever.action((CCIntervalAction) Common.ani_panda_face_rainbow.copy()));
	};

	public void collisionWithFast()
	{
	    birdCount ++;
	    
	    GameLayer gameLayer = (GameLayer )this.parent_;
	    Common.effectPlay(R.raw.fly);
	    tail.setVisible(true);
	    
	    gameLayer.streak.setVisible(true);
	    gameLayer.streak.setRotation(0);
	    
	    state = PandaState_fast;
	    startTime_fast = new Date().getTime();
	    this.stopAllActions();
	    this.runAction(CCRepeatForever.action((CCIntervalAction) Common.ani_panda_face_fast.copy()));
	};

	private void removeCache()
	{
	    this.stopAllActions();
	}
	
	@Override
	public void onExit() {
		removeCache();
		super.onExit();
	}
}

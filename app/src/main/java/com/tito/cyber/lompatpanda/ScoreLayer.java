package com.tito.cyber.lompatpanda;

import java.util.ArrayList;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCLabelAtlas;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

import android.view.MotionEvent;

import com.tito.cyber.lompatpanda.Common.PlayerInfo;

public class ScoreLayer extends CCLayer {

	private final float BACK_POS_X     = (36 * Common.kXForIPhone);
	private final float BACK_POS_Y    =  (282 * Common.kYForIPhone);

	private final float RANK_NUM_X         = (30 * Common.kXForIPhone);
	private final float RANK_NAME_X        = (68 * Common.kXForIPhone);
	private final float RANK_SCORE_X       = (373 * Common.kXForIPhone);

	private CCSprite topscores;
	private CCMenuItemImage returnButton;
	private ArrayList<CCSprite> backSprites;
	private ArrayList<CCLabelAtlas> rankNums;
	private ArrayList<CCLabel> rankNames;
	private ArrayList<CCLabelAtlas> rankScores;
	private float topThresold;
	private float botThresold;
	private float prevTouchY;

	public ScoreLayer() {
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init(){
        this.setIsTouchEnabled(true);
        
        topscores = CCSprite.sprite("topscores.png");
        topscores.setScaleX(Common.kXForIPhone);
        topscores.setScaleY(Common.kYForIPhone);
        topscores.setPosition(Common.SCREEN_WIDTH / 2, Common.SCREEN_HEIGHT - topscores.getContentSize().height / 2 * topscores.getScaleY());
        addChild(topscores, 1);
        
        returnButton = CCMenuItemImage.item("back.png", "back.png", this, "onBack");
        returnButton.setScale(Common.kXForIPhone);
        returnButton.setPosition(BACK_POS_X, BACK_POS_Y);
        
        CCNode selectedItem = returnButton.getSelectedImage();
        selectedItem.setScale(1.1f);
        
        CCMenu menu = CCMenu.menu(returnButton);
        menu.setPosition(CGPoint.zero());
        addChild(menu, 1);

        float itemPosY = Common.SCREEN_HEIGHT - topscores.getContentSize().height * topscores.getScaleY();
        
        CCSprite itemBack;
        CCLabelAtlas itemNum;
        CCLabel itemName;
        CCLabelAtlas itemScore;
        
        backSprites = new ArrayList<CCSprite>();
        rankNums = new ArrayList<CCLabelAtlas>();
        rankNames = new ArrayList<CCLabel>();
        rankScores = new ArrayList<CCLabelAtlas>();
        
        for (int i = 0; i < 20; i ++) {
            PlayerInfo playerInfo = Common.gameInfo.get(i);
            
            if (i % 2 == 0) {
                itemBack = CCSprite.sprite("topback1.png");
            }
            else
            {
                itemBack = CCSprite.sprite("topback2.png");
            }
            float lineWidth = itemBack.getContentSize().height * Common.kYForIPhone;
            
            if (i == 0) {
                topThresold = itemPosY - lineWidth / 2;
                botThresold = lineWidth / 2;
            }
            
            itemBack.setScaleX(Common.kXForIPhone);
            itemBack.setScaleY(Common.kYForIPhone);
            itemBack.setPosition(Common.SCREEN_WIDTH / 2, itemPosY - lineWidth / 2);
            addChild(itemBack);
            backSprites.add(itemBack);            
            
            String string = String.format("%d", i + 1);
            itemNum = CCLabelAtlas.label("0123456789", "num4.png", 18, 18, '0');
            itemNum.setScale(Common.kXForIPhone);
            itemNum.setPosition(RANK_NUM_X, itemBack.getPosition().y);
            itemNum.setAnchorPoint(0f, 0.5f);
            itemNum.setString(string);
            addChild(itemNum);
            rankNums.add(itemNum);
            
            playerInfo.name = "Player";
            string = String.format("%s", playerInfo.name);
            itemName = CCLabel.makeLabel(" ", "Arial", 20);
            itemName.setScale(Common.kXForIPhone);
            itemName.setPosition(RANK_NAME_X, itemBack.getPosition().y);
            itemName.setAnchorPoint(0f, 0.5f);
            itemName.setString(string);
            addChild(itemName);
            rankNames.add(itemName);

            string = String.format("%d", playerInfo.score);
            itemScore = CCLabelAtlas.label("0123456789", "num4.png", 18, 18, '0');
            itemScore.setScale(Common.kXForIPhone);
            itemScore.setPosition(RANK_SCORE_X, itemBack.getPosition().y);
            itemScore.setAnchorPoint(0f, 0.5f);
            itemScore.setString(string);
            addChild(itemScore);
            rankScores.add(itemScore);
 
            itemPosY -= lineWidth;
        }
	}

	public void onBack( Object sender )
	{
		CCScene scene = CCScene.node();
	 	scene.addChild(new MenuLayer(), 1);
	 	CCDirector.sharedDirector().replaceScene(new CCFadeTransition(1.0f, scene));
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event)
	{
		CGPoint touchPoint = CGPoint.make(event.getX(), event.getY());
		touchPoint = CCDirector.sharedDirector().convertToGL(touchPoint);
	    prevTouchY = touchPoint.y;

	    return CCTouchDispatcher.kEventHandled;	    
	}
	
	@Override
	public boolean ccTouchesMoved(MotionEvent event)
	{
		CGPoint touchPoint = CGPoint.make(event.getX(), event.getY());
		touchPoint = CCDirector.sharedDirector().convertToGL(touchPoint);

	    float deltaY = touchPoint.y - prevTouchY;
	    
	    CCSprite firstItem = backSprites.get(0);
	    if (deltaY < 0 && firstItem.getPosition().y <= topThresold) {
	        return CCTouchDispatcher.kEventIgnored;
	    }
	    
	    CCSprite lastItem = backSprites.get(backSprites.size()-1);
	    if (deltaY > 0 && lastItem.getPosition().y >= botThresold) {
	        return CCTouchDispatcher.kEventIgnored;
	    }
	    
	    for (int i = 0; i < 20; i ++) {
	        CCSprite itemBack = backSprites.get(i);
	        itemBack.setPosition(CGPoint.ccpAdd(itemBack.getPosition(), CGPoint.ccp(0, deltaY)));
	    }
	    
	    CCLabelAtlas itemNum = null;
	    for( int i=0; i<rankNums.size(); i++ ) {
	    	itemNum = rankNums.get(i);
	        itemNum.setPosition(CGPoint.ccpAdd(itemNum.getPosition(), CGPoint.ccp(0, deltaY)));
	    }
	    CCLabel itemName;
	    for( int i=0; i<rankNames.size(); i++ ) {
	    	itemName = rankNames.get(i);
	    	itemName.setPosition(CGPoint.ccpAdd(itemName.getPosition(), CGPoint.ccp(0, deltaY)));
	    }
	    
	    CCLabelAtlas itemScore = null;
	    for( int i=0; i<rankScores.size(); i++ ) {
	    	itemScore = rankScores.get(i);
	    	itemScore.setPosition(CGPoint.ccpAdd(itemScore.getPosition(), CGPoint.ccp(0, deltaY)));
	    }
	    
	    prevTouchY = touchPoint.y;

	    return CCTouchDispatcher.kEventIgnored;
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event)
	{
		CGPoint touchPoint = CGPoint.make(event.getX(), event.getY());
		touchPoint = CCDirector.sharedDirector().convertToGL(touchPoint);

		return CCTouchDispatcher.kEventIgnored;
	}
}

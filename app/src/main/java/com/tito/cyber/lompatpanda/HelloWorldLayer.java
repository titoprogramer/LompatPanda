package com.tito.cyber.lompatpanda;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;


public class HelloWorldLayer extends CCLayer {

	private CCSprite back;

	public HelloWorldLayer() {
		init();
	}
	
	@Override
	public void onEnter() {
		super.onEnter();
		CCScene scene = CCScene.node();
	 	scene.addChild(new MenuLayer(), 1);
	 	CCDirector.sharedDirector().replaceScene(scene);
	}
	
	@Override
	public void onExit() {
		removeCache();
		removeAllChildren(true);
		unscheduleAllSelectors();
		super.onExit();
	}
    	
	private void init() {
        back = CCSprite.sprite("loading.png");
        back.setScaleX(Common.kXForIPhone);
        back.setScaleY(Common.kYForIPhone);
        back.setPosition(Common.SCREEN_WIDTH / 2, Common.SCREEN_HEIGHT / 2);
        addChild(back, 1);
        MediaGlobal._shared().playMusic(R.raw.gamebg, true);
    }

	private void removeSprite( CCSprite sp )
	{
	    CCTexture2D tex = sp.getTexture();
	    CCTextureCache.sharedTextureCache().removeTexture(tex);
	    sp.removeFromParentAndCleanup(true);
	}

	private void removeCache()
	{
	    removeSprite(back);
	}
}

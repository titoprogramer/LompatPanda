package com.tito.cyber.lompatpanda;

import java.util.ArrayList;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;

public class BackLayer extends CCLayer {

	private ArrayList<CCSprite> backs;

	public BackLayer() {
		// TODO Auto-generated constructor stub
        initBacks();
	}

	private void initBacks()
	{
	    backs = new ArrayList<CCSprite>((int) Common.STAGE_COUNT);
	    
	    for (int i = 0; i < (int) Common.STAGE_COUNT; i ++) {
	        CCSprite back = CCSprite.sprite(Common.IMG_BACK[i]);
	        back.setScaleX(Common.kXForIPhone);
	        back.setScaleY(Common.kYForIPhone);
	        back.setPosition(Common.SCREEN_WIDTH / 2, Common.SCREEN_HEIGHT / 2);
	        addChild(back, Common.ztag_In_camera.ztag_min);
	        
	        backs.add(back);
	    }
	}

	public void updateBackground()
	{
	    for (int i = 0; i < (int) Common.STAGE_COUNT; i ++) {
	        CCSprite back = backs.get(i);
	        back.setVisible(false);
	    }
	    CCSprite cur = backs.get(Common.g_stageNum);
	    cur.setVisible(true);
	}

	private void removeSprite(CCSprite sp)
	{
	    CCTexture2D tex = sp.getTexture();
	    CCTextureCache.sharedTextureCache().removeTexture(tex);
	    sp.removeFromParentAndCleanup(true);
	}

	private void removeCache()
	{
	    if (backs.size() > 0 && backs != null) {
	        
	        for (int i = 0; i < (int) Common.STAGE_COUNT; i ++) {
	            CCSprite back = backs.get(i);
	            removeSprite(back);
	        }    
	        backs.removeAll(backs);

	    }
	}
	
	@Override
	public void onExit() {
		removeCache();
		super.onExit();
	}
}

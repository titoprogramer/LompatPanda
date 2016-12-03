package com.tito.cyber.lompatpanda;

import java.util.ArrayList;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Common
{
	public static SoundEngine sound_engine;
	public static GameLayer game_layer = null;

	public static float kXForIPhone = 1;
	public static float kYForIPhone = 1;
	
	public static float SCREEN_WIDTH = 480;
	public static float SCREEN_HEIGHT = 320;
	public static final float START_ACTIVITY_TIME = 2.0f;
	
	public static final float STAGE_COUNT  =  4.0f;
	
	public static float[][] cloudPos = {
		{0, 50},
	    {300, 200},
	    {500, 70},
	    {750, 140},
	    {850, 80}
	};

	public enum GameState {
	    GameState_start,
	    GameState_gaming,
	    GameState_gameover
	    };
	 
	    public static final int Bird_Type_bird1 = 0;
	    public static final int Bird_Type_bird2 = 1;

		public enum FoodType {
		    FoodType_bomb,
		    FoodType_protect,
		    FoodType_rainbow,
		    FoodType_fast,
		    FoodType_sun
		    };
	static float gameTime = 0;
	static int genBirdCount = 0;
////////////////////////////////////////////////////
	public static class ztag_In_camera {
		public static final int ztag_min = -1;
		public static final int ztag_land = 0;
		public static final int ztag_body1 = 1;
		public static final int ztag_body2 = 2;
		public static final int ztag_food = 3;
		public static final int ztag_player = 4;
		public static final int ztag_cloud = 5;
		public static final int ztag_river = 6; 
		public static final int ztag_gameover = 7;
		public static final int ztag_menu = 8;
	};
	
	public static class PlayerInfo
	{
	    public int rankNum;
	    public String name = new String();
	    public int score;	
	};
	
	public static class sPlayerInfo {
		public int rankNum;
		public String name = new String();
		public int score;
	}
	
	public static class GameInfo{
	    public float totalDistance;
	    public String name=new String();
	}
	
	// GAME INFO
	
	public static GameInfo g_gamePlayerInfo = null;
	public static ArrayList<PlayerInfo> gameInfo = null;
	public static int g_stageNum;

	// sound
	
	public static boolean g_soundMute = false;

	// ANIMATIONS
	
	public static CCAction ani_line = null;
	
	public static CCAction[] ani_panda_leaf_up = new CCAction[4];
	public static CCAction[] ani_panda_leaf_down = new CCAction[4];
	
	public static CCAction ani_panda_face_fast = null;
	public static CCAction ani_panda_face_none = null;
	public static CCAction ani_panda_face_rainbow = null;
	public static CCAction ani_panda_face_fail = null;
	
	public static CCAction ani_bird1 = null;
	public static CCAction ani_bird2 = null;
	
	   
	public static String IMG_LINE[] = {
	    "line.png",
	    "line1.png",
	    "line3.png",
	    "line2.png",
	};
	
	public static String IMG_BG1_BACK1[] = {
	    "bga1_1.png",
	    "bga1_1.png",
	    "bga1_1.png",
	};
	public static String IMG_BG1_BACK2[] = {
	    "bga2_1.png",
	    "bga2_1.png",
	    "bga2_1.png",
	};
	public static String IMG_BG2_BACK1[] = {
	    "bgb1_1.png",
	    "bgb1_1.png",
	    "bgb1_1.png",
	};
	public static String IMG_BG3_BACK1[] = {
	    "bgc1_1.png",
	    "bgc1_1.png",
	    "bgc1_1.png",
	};
	public static String IMG_BG4_BACK1[] = {
	    "bgd1_1.png",
	    "bgd1_1.png",
	    "bgd1_1.png",
	};
	
	public static String IMG_BG1_CLOUD[] = {
	    "bga_cloud_1.png",
	    "bga_cloud_2.png",
	};
	public static String IMG_BG2_CLOUD[] = {
	    "bga_cloud_1.png",
	    "bga_cloud_2.png",
	};
	public static String IMG_BG3_CLOUD[] = {
	    "bgc_cloud_1.png",
	    "bgc_cloud_2.png",
	};
	public static String IMG_BG4_CLOUD[] = {
	    "bgd_cloud_1.png",
	    "bgd_cloud_2.png",
	};
	
	public static String IMG_BACK[] = {
	    "bga0.png",
	    "bgb0.png",
	    "bgc0.png",
	    "bgd0.png",
	  
	};
	
	public static String IMG_LIVER[] = {
	    "bg_river1.png",
	    "bg_river2.png",
	};
	
	public static void effectPlay( int resId ) {
		if( !g_soundMute )
			sound_engine.playEffect(CCDirector.sharedDirector().getActivity(), resId);
	}
	
	public static Boolean loadActions()
	{
	    String fileName;
	    ArrayList<CCSpriteFrame> expArray0 = new ArrayList<CCSpriteFrame>();
	    CCAnimation animation;
	    
	    for (int i = 0; i<4; i ++) {
	        fileName = IMG_LINE[i];
	        CCTexture2D texture = CCTextureCache.sharedTextureCache().addImage(fileName);
	    	
	    	CCSpriteFrame frame0 = CCSpriteFrame.frame(texture,
	    			CGRect.make(0, 0, texture.getContentSize().width, texture.getContentSize().height), CGPoint.zero());
	    	expArray0.add(frame0);

	    }
	    animation = CCAnimation.animation("", 0.05f, expArray0);
	    ani_line = CCAnimate.action(animation, true);
	    expArray0.removeAll(expArray0);

	    float width = 50;
	    float height = 36;
	    float startX = 150;
	    float startY = 0;
	    fileName = "bird1.png";
	    
	    for (int num = 0; num < 4; num ++) {
	        CCTexture2D texture = CCTextureCache.sharedTextureCache().addImage(fileName);
	    	
	    	CCSpriteFrame frame0 = CCSpriteFrame.frame(texture,
	    			CGRect.make(startX, startY, width, height), CGPoint.zero());
	    	expArray0.add(frame0);
	    	
	        startX -= width;
	        
	    }
	    animation = CCAnimation.animation("", 0.05f, expArray0);
	    ani_bird1 = CCAnimate.action(animation, false);
	    expArray0.removeAll(expArray0);

	    width = 50;
	    height = 57;
	    startX = 150;
	    startY = 0;
	    fileName = "bird2.png";
	    
	    for (int num = 0; num < 4; num ++) {
	        CCTexture2D texture = CCTextureCache.sharedTextureCache().addImage(fileName);
	    	
	    	CCSpriteFrame frame0 = CCSpriteFrame.frame(texture,
	    			CGRect.make(startX, startY, width, height), CGPoint.zero());
	    	expArray0.add(frame0);
	    	
	        startX -= width;
	        
	    }
	    animation = CCAnimation.animation("", 0.05f, expArray0);
	    ani_bird2 = CCAnimate.action(animation, false);
	    expArray0.removeAll(expArray0);

	    
	    float delta = 0.1f;
	    for (int n = 0; n < 4; n++) {
	        animation = CCAnimation.animation(String.format("leaf_up[%d]", n), delta);
	        for (int i = 0; i < 13; i++) {
	        	animation.addFrame(String.format("%02d000%02d.png", n+1, i+1));
	        }
	        ani_panda_leaf_up[n] = CCAnimate.action(animation);
	        
	        animation = CCAnimation.animation(String.format("leaf_down[%d]", n), delta);
	        for (int i = 13; i < 25; i++) {
	            animation.addFrame(String.format("%02d000%02d.png", n+1, i+1));
	        }
	        ani_panda_leaf_down[n] = CCAnimate.action(animation);
	    }
	    
	    animation = CCAnimation.animation("face_rainbow", delta);
	    for (int i = 0; i < 25; i++) {
	    	animation.addFrame(String.format("01000%02d.png", i+1));
	    }
	    ani_panda_face_rainbow = CCAnimate.action(animation);
	    
	    animation = CCAnimation.animation("face_none", delta);
	    for (int i = 0; i < 25; i++) {
	    	animation.addFrame(String.format("02000%02d.png", i+1));
	    }
	    ani_panda_face_none = CCAnimate.action(animation);
	    
	    animation = CCAnimation.animation("face_fast", delta);
	    for (int i = 0; i < 25; i++) {
	    	animation.addFrame(String.format("01000%02d.png", i+1));
	    }
	    ani_panda_face_fast = CCAnimate.action(animation);
	    
	    animation = CCAnimation.animation("face_fail", delta);
	    for (int i = 0; i < 25; i++) {
	    	animation.addFrame(String.format("03000%02d.png", i+1));
	    }
	    ani_panda_face_fail = CCAnimate.action(animation);
	    
	    return true;
	}
	
	public static boolean loadGameInfo()
	{
		
	    gameInfo = new ArrayList<PlayerInfo>();
	    g_gamePlayerInfo = new GameInfo();
	    
	    SharedPreferences p = CCDirector.sharedDirector().getActivity().getApplicationContext().getSharedPreferences("score", 0);
		
		int nFirst = 0;
		nFirst = p.getInt("First", 0);
		PlayerInfo playerInfo;
		if (nFirst == 0)
		{
	        g_gamePlayerInfo.totalDistance = 0;
	      
	        for (int i = 0; i < 20; i ++) {
	            playerInfo = new PlayerInfo();
	            playerInfo.rankNum = i;
	            gameInfo.add(playerInfo);
	        }
		}
		else 
		{
			g_gamePlayerInfo.totalDistance = p.getFloat("totalDistance", 0);
		    g_gamePlayerInfo.name = p.getString("name", "0");
			    
			sPlayerInfo tempSPlayer = new sPlayerInfo();
			String str;
	        for (int i = 0; i < 20; i ++) {
	            playerInfo = new PlayerInfo();
	        	str=String.format("rankName%d", i);
	    		tempSPlayer.rankNum = p.getInt(str, 0);
	    		
	    		str=String.format("name%d", i);
	    		tempSPlayer.name = p.getString(str, "0");
	    		
	    		str=String.format("score%d", i);
	    		tempSPlayer.score =p.getInt(str, 0);
	    		
	            playerInfo.rankNum = tempSPlayer.rankNum;
	            playerInfo.name = tempSPlayer.name;
	            playerInfo.score = tempSPlayer.score;
	
	            gameInfo.add(playerInfo);
	        }
		}
		
		return true;
	}
	
	public static Boolean saveGameInfo()
	{
	    SharedPreferences p = CCDirector.sharedDirector().getActivity().getApplicationContext().getSharedPreferences("score", 0);
		
		int nFirst = 1;
		Editor ed = p.edit();
		ed.putInt("First", nFirst);
		
	    ed.putFloat("totalDistance", g_gamePlayerInfo.totalDistance);
	    ed.putString("name", g_gamePlayerInfo.name);
	    
	    
	    
	    sPlayerInfo tempSPlayer =new sPlayerInfo();
	    String str;
	    for (int i = 0; i < 20; i ++) {
	        PlayerInfo playerInfo = gameInfo.get(i);
	        tempSPlayer.rankNum = playerInfo.rankNum;
	        tempSPlayer.name = playerInfo.name;
	        tempSPlayer.score = playerInfo.score;
	        
	        str=String.format("rankName%d", i);
	        ed.putInt(str, tempSPlayer.rankNum);
    		
    		str=String.format("name%d", i);
    		ed.putString(str, tempSPlayer.name);
    		
    		str=String.format("score%d", i);
    		ed.putInt(str, tempSPlayer.score);
    		
           
	    }
	    ed.commit();
	    
	    return true;
	}
	
	public static boolean game_initialize()
	{
		CGSize winSize = CCDirector.sharedDirector().winSize();
		SCREEN_WIDTH = winSize.width;
		SCREEN_HEIGHT = winSize.height;
		
		if ( SCREEN_WIDTH == 1024 && SCREEN_HEIGHT == 768)
		{
	        kXForIPhone = 1024.0f / 480.0f;
			kYForIPhone = 768.0f / 320.0f;
	        
		}
		else {
	        kYForIPhone = 1.0f;
			kXForIPhone = 1.0f;
	        
	        if (winSize.width == 568) {
	            kXForIPhone = 568.0f / 480.0f;
	        }
		}
	    
	    if ( loadActions() == false)
	    {
	        return false;
	    }
		
	    if (loadGameInfo() == false) {
	        return false;
	    }

		return true;
	}

	static CGPoint FoodPosInfo_1[] = {
		CGPoint.ccp(240, 74), CGPoint.ccp(240, 107), CGPoint.ccp(243, 140), CGPoint.ccp(247, 173), CGPoint.ccp(255, 203), CGPoint.ccp(264, 236),
		CGPoint.ccp(290,125), CGPoint.ccp(318, 154), CGPoint.ccp(348, 188), CGPoint.ccp(385, 223),
		CGPoint.ccp(279, 86), CGPoint.ccp(318, 98),  CGPoint.ccp(360, 112), CGPoint.ccp(406, 122), CGPoint.ccp(457, 130), 
		CGPoint.ccp(0, 0)
	};

	static CGPoint FoodPosInfo_2[] = {
	    CGPoint.ccp(240, 138), CGPoint.ccp(275, 138), CGPoint.ccp(310, 138), CGPoint.ccp(345, 138), CGPoint.ccp(380, 138), 
	    CGPoint.ccp(240, 176), CGPoint.ccp(275, 176), CGPoint.ccp(310, 176), CGPoint.ccp(345, 176), CGPoint.ccp(380, 176), 
	    CGPoint.ccp(228, 289), CGPoint.ccp(265, 289), CGPoint.ccp(291, 247), CGPoint.ccp(342, 247), CGPoint.ccp(371, 289), 
	    CGPoint.ccp(409, 289), CGPoint.ccp(0, 0)
	};

	static CGPoint FoodPosInfo_3[] = {
	    CGPoint.ccp(255, 187), CGPoint.ccp(279, 210), CGPoint.ccp(304, 231), 
	    CGPoint.ccp(338, 252), CGPoint.ccp(376, 272), CGPoint.ccp(417, 283),
	    CGPoint.ccp(461, 281), CGPoint.ccp(499, 279),CGPoint.ccp(537, 263), 
	    CGPoint.ccp(570, 238), CGPoint.ccp(501, 214), CGPoint.ccp(530, 187),
	    CGPoint.ccp(0, 0)
	};

	static CGPoint FoodPosInfo_4[] = {
	    CGPoint.ccp(267, 97), CGPoint.ccp(304, 108), CGPoint.ccp(335, 128), 
	    CGPoint.ccp(358, 154), CGPoint.ccp(378, 187), CGPoint.ccp(391, 218),
	    CGPoint.ccp(404, 251), CGPoint.ccp(414, 287), CGPoint.ccp(451, 287), 
	    CGPoint.ccp(486, 287), CGPoint.ccp(519, 287), CGPoint.ccp(533, 250), 
	    CGPoint.ccp(545, 218), CGPoint.ccp(559, 187), CGPoint.ccp(576, 153), 
	    CGPoint.ccp(500, 128), CGPoint.ccp(528, 107), CGPoint.ccp(563, 94), 
	    CGPoint.ccp(441, 250), CGPoint.ccp(494, 250), CGPoint.ccp(0, 0)
	};

	static CGPoint FoodPosInfo_5[] = {
	    CGPoint.ccp(292, 250), CGPoint.ccp(330, 250), CGPoint.ccp(365, 250), CGPoint.ccp(400, 250), CGPoint.ccp(435, 250), 
	    CGPoint.ccp(435, 217), CGPoint.ccp(435, 186), CGPoint.ccp(435, 154), CGPoint.ccp(435, 120), CGPoint.ccp(435, 86), 
	    CGPoint.ccp(395, 102), CGPoint.ccp(360, 124), CGPoint.ccp(335, 152), CGPoint.ccp(316, 184), CGPoint.ccp(302, 217), 
	    CGPoint.ccp(0, 0)
	}; 
}
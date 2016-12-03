package com.tito.cyber.lompatpanda;

import java.util.ArrayList;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CGPoint;

public class MoveLayer extends CCLayer {

	public final float FIRST_BODY_Y =   (54 * Common.kYForIPhone);
	public final float SECOND_BODY_Y =   (5 * Common.kYForIPhone);

	private ArrayList<CCSprite> firstBodys1;
	private ArrayList<CCSprite> firstBodys2;
	private ArrayList<CCSprite> firstBodys3;
	private ArrayList<CCSprite> firstBodys4;
	private ArrayList<CCSprite> secondBodys1;
	private ArrayList<CCSprite> secondBodys2;
	private ArrayList<CCSprite> secondBodys3;
	private ArrayList<CCSprite> secondBodys4;
	private ArrayList<CCSprite> clouds1;
	private ArrayList<CCSprite> clouds2;
	private ArrayList<CCSprite> clouds3;
	private ArrayList<CCSprite> clouds4;
	private ArrayList<CCSprite> rivers;
	private ArrayList<CCSprite> curFirstBodys;
	private ArrayList<CCSprite> curSecondBodys;
	private ArrayList<CCSprite> curClouds;
	public ArrayList<Food> foods;
	public ArrayList<Bird> birds;
	private float foodArrayLength;
	
	public float originHeight;

	public MoveLayer() {
		// TODO Auto-generated constructor stub
		init();
	}

	private void initbodys()
	{
	    firstBodys1 = new ArrayList<CCSprite>();
	    firstBodys2 = new ArrayList<CCSprite>();
	    firstBodys3 = new ArrayList<CCSprite>();
	    firstBodys4 = new ArrayList<CCSprite>();
	    
	    secondBodys1 = new ArrayList<CCSprite>();
	    secondBodys2 = new ArrayList<CCSprite>();
	    secondBodys3 = new ArrayList<CCSprite>();
	    secondBodys4 = new ArrayList<CCSprite>();
	    
	    clouds1 = new ArrayList<CCSprite>(10);
	    clouds2 = new ArrayList<CCSprite>(10);
	    clouds3 = new ArrayList<CCSprite>(10);
	    clouds4 = new ArrayList<CCSprite>(10);
	    
	    rivers = new ArrayList<CCSprite>(2);
	    
	    for (int i = 0; i < Common.STAGE_COUNT; i ++) {
	        switch (i) {
	            case 0:
	            {
	                float startPosXFir = -Common.SCREEN_WIDTH / 2;
	                for (int index = 0; index<Common.IMG_BG1_BACK1.length; index ++) {
	                    CCSprite body = CCSprite.sprite(Common.IMG_BG1_BACK1[index]);
	                    body.setScaleX(Common.kXForIPhone);
	                    body.setScaleY(Common.kYForIPhone);
	                    body.setAnchorPoint(0, 0);
	                    body.setPosition(startPosXFir, 0);
	                    addChild(body, Common.ztag_In_camera.ztag_body1);
	                    startPosXFir += body.getContentSize().width * Common.kXForIPhone;
	                    
	                    firstBodys1.add(body);
	                }
	                
	                float startPosXSec = -Common.SCREEN_WIDTH / 2;
	                for (int index = 0; index<Common.IMG_BG1_BACK2.length; index ++) {
	                    CCSprite bodySec = CCSprite.sprite(Common.IMG_BG1_BACK2[index]);
	                    bodySec.setScaleX(Common.kXForIPhone);
	                    bodySec.setScaleY(Common.kYForIPhone);
	                    bodySec.setAnchorPoint(0, 0);
	                    bodySec.setPosition(startPosXSec, 35*Common.kYForIPhone);
	                    addChild(bodySec, Common.ztag_In_camera.ztag_body2);
	                    startPosXSec += bodySec.getContentSize().width * Common.kXForIPhone;
	                    
	                    secondBodys1.add(bodySec);
	                }
	                
	                for (int i1 = 0; i1 < 5; i1 ++) {
	                    CCSprite cloud1 = CCSprite.sprite(Common.IMG_BG1_CLOUD[0]);
	                    CCSprite cloud2 = CCSprite.sprite(Common.IMG_BG1_CLOUD[1]);
	                    cloud1.setScaleX(Common.kXForIPhone);
	                    cloud1.setScaleY(Common.kYForIPhone);
	                    cloud1.setPosition(Common.cloudPos[i1][0] * Common.kXForIPhone, Common.cloudPos[i1][1] * Common.kYForIPhone + Common.SCREEN_HEIGHT);
	                    addChild(cloud1, Common.ztag_In_camera.ztag_cloud);
	                    
	                    cloud2.setScaleX(Common.kXForIPhone);
	                    cloud2.setScaleY(Common.kYForIPhone);
	                    cloud2.setPosition(Common.cloudPos[i1][0] * Common.kXForIPhone, Common.cloudPos[i1][1] * Common.kYForIPhone + Common.SCREEN_HEIGHT);
	                    addChild(cloud2, Common.ztag_In_camera.ztag_cloud);
	                    
	                    clouds1.add(cloud1);
	                    clouds1.add(cloud2);
	                }

	                break;
	            }   
	            case 1:
	            {
	                float startPosXFir = -Common.SCREEN_WIDTH / 2;
	                for (int index = 0; index<Common.IMG_BG2_BACK1.length; index ++) {
	                    CCSprite body = CCSprite.sprite(Common.IMG_BG2_BACK1[index]);
	                    body.setScaleX(Common.kXForIPhone);
	                    body.setScaleY(Common.kYForIPhone);
	                    body.setAnchorPoint(0, 0);
	                    body.setPosition(startPosXFir, FIRST_BODY_Y);
	                    addChild(body, Common.ztag_In_camera.ztag_body1);
	                    startPosXFir += body.getContentSize().width * Common.kXForIPhone;
	                    
	                    firstBodys2.add(body);
	                }
	                
	                for (int i1 = 0; i1 < 5; i1 ++) {
	                    CCSprite cloud1 = CCSprite.sprite(Common.IMG_BG2_CLOUD[0]);
	                    CCSprite cloud2 = CCSprite.sprite(Common.IMG_BG2_CLOUD[1]);
	                    cloud1.setScaleX(Common.kXForIPhone);
	                    cloud1.setScaleY(Common.kYForIPhone);
	                    cloud1.setPosition(Common.cloudPos[i1][0] * Common.kXForIPhone, Common.cloudPos[i1][1] * Common.kYForIPhone + Common.SCREEN_HEIGHT);
	                    addChild(cloud1, Common.ztag_In_camera.ztag_cloud);
	                    
	                    cloud2.setScaleX(Common.kXForIPhone);
	                    cloud2.setScaleY(Common.kYForIPhone);
	                    cloud2.setPosition(Common.cloudPos[i1][0] * Common.kXForIPhone, Common.cloudPos[i1][1] * Common.kYForIPhone + Common.SCREEN_HEIGHT);
	                    addChild(cloud2, Common.ztag_In_camera.ztag_cloud);
	                    
	                    clouds2.add(cloud1);
	                    clouds2.add(cloud2);
	                }

	                break;
	            }   
	            case 2:
	            {
	                float startPosXFir = -Common.SCREEN_WIDTH / 2;
	                for (int index = 0; index<Common.IMG_BG3_BACK1.length; index ++) {
	                    CCSprite body = CCSprite.sprite(Common.IMG_BG3_BACK1[index]);
	                    body.setScaleX(Common.kXForIPhone);
	                    body.setScaleY(Common.kYForIPhone);
	                    body.setAnchorPoint(0, 0);
	                    body.setPosition(startPosXFir, FIRST_BODY_Y);
	                    addChild(body, Common.ztag_In_camera.ztag_body1);
	                    startPosXFir += body.getContentSize().width * Common.kXForIPhone;
	                    
	                    firstBodys3.add(body);
	                }
	                
	                for (int i1 = 0; i1 < 5; i1 ++) {
	                    CCSprite cloud1 = CCSprite.sprite(Common.IMG_BG3_CLOUD[0]);
	                    CCSprite cloud2 = CCSprite.sprite(Common.IMG_BG3_CLOUD[1]);
	                    cloud1.setScaleX(Common.kXForIPhone);
	                    cloud1.setScaleY(Common.kYForIPhone);
	                    cloud1.setPosition(Common.cloudPos[i1][0] * Common.kXForIPhone, Common.cloudPos[i1][1] * Common.kYForIPhone + Common.SCREEN_HEIGHT);
	                    addChild(cloud1, Common.ztag_In_camera.ztag_cloud);
	                    
	                    cloud2.setScaleX(Common.kXForIPhone);
	                    cloud2.setScaleY(Common.kYForIPhone);
	                    cloud2.setPosition(Common.cloudPos[i1][0] * Common.kXForIPhone, Common.cloudPos[i1][1] * Common.kYForIPhone + Common.SCREEN_HEIGHT);
	                    addChild(cloud2, Common.ztag_In_camera.ztag_cloud);
	                    
	                    clouds3.add(cloud1);
	                    clouds3.add(cloud2);
	                }

	                break;
	            }   
	            case 3:
	            {
	                float startPosXFir = -Common.SCREEN_WIDTH / 2;
	                for (int index = 0; index<Common.IMG_BG4_BACK1.length; index ++) {
	                    CCSprite body = CCSprite.sprite(Common.IMG_BG4_BACK1[index]);
	                    body.setScaleX(Common.kXForIPhone);
	                    body.setScaleY(Common.kYForIPhone);
	                    body.setAnchorPoint(0, 0);
	                    body.setPosition(startPosXFir, FIRST_BODY_Y);
	                    addChild(body, Common.ztag_In_camera.ztag_body1);
	                    startPosXFir += body.getContentSize().width * Common.kXForIPhone;
	                    
	                    firstBodys4.add(body);
	                }

	                for (int i1 = 0; i1 < 5; i1 ++) {
	                    CCSprite cloud1 = CCSprite.sprite(Common.IMG_BG4_CLOUD[0]);
	                    CCSprite cloud2 = CCSprite.sprite(Common.IMG_BG4_CLOUD[1]);
	                    cloud1.setScaleX(Common.kXForIPhone);
	                    cloud1.setScaleY(Common.kYForIPhone);
	                    cloud1.setPosition(Common.cloudPos[i1][0] * Common.kXForIPhone, Common.cloudPos[i1][1] * Common.kYForIPhone + Common.SCREEN_HEIGHT);
	                    addChild(cloud1, Common.ztag_In_camera.ztag_cloud);
	                    
	                    cloud2.setScaleX(Common.kXForIPhone);
	                    cloud2.setScaleY(Common.kYForIPhone);
	                    cloud2.setPosition(Common.cloudPos[i1][0] * Common.kXForIPhone, Common.cloudPos[i1][1] * Common.kYForIPhone + Common.SCREEN_HEIGHT);
	                    addChild(cloud2, Common.ztag_In_camera.ztag_cloud);
	                    
	                    clouds4.add(cloud1);
	                    clouds4.add(cloud2);
	                }

	                break;
	            }   
	                
	            default:
	                break;
	        }
	    }
	        
        
        CCSprite river = CCSprite.sprite(Common.IMG_LIVER[0]);
        river.setScaleX(Common.kXForIPhone);
        river.setScaleY(Common.kYForIPhone);
        river.setAnchorPoint(0.5f, 0);
        river.setPosition(Common.SCREEN_WIDTH / 2, 0);
        addChild(river, Common.ztag_In_camera.ztag_river);
        rivers.add(river);
	}

	public void updateStageImages()
	{
	    int arrayCount = firstBodys1.size();
	    for (int num = 0; num < arrayCount; num ++) {
	        CCSprite body = firstBodys1.get(num);
	        body.setVisible(false);
	    }

	    arrayCount = firstBodys2.size();
	    for (int num = 0; num < arrayCount; num ++) {
	        CCSprite body = firstBodys2.get(num);
	        body.setVisible(false);
	    }
	    
	    arrayCount = firstBodys3.size();
	    for (int num = 0; num < arrayCount; num ++) {
	        CCSprite body = firstBodys3.get(num);
	        body.setVisible(false);
	    }
	    
	    arrayCount = firstBodys4.size();
	    for (int num = 0; num < arrayCount; num ++) {
	        CCSprite body = firstBodys4.get(num);
	        body.setVisible(false);
	    }
	    
	    arrayCount = secondBodys1.size();
	    for (int num = 0; num < arrayCount; num ++) {
	        CCSprite body = secondBodys1.get(num);
	        body.setVisible(false);
	    }
	    
	    arrayCount = secondBodys2.size();
	    for (int num = 0; num < arrayCount; num ++) {
	        CCSprite body = secondBodys2.get(num);
	        body.setVisible(false);
	    }
	    
	    arrayCount = secondBodys3.size();
	    for (int num = 0; num < arrayCount; num ++) {
	        CCSprite body = secondBodys3.get(num);
	        body.setVisible(false);
	    }
	    
	    arrayCount = secondBodys4.size();
	    for (int num = 0; num < arrayCount; num ++) {
	        CCSprite body = secondBodys4.get(num);
	        body.setVisible(false);
	    }

	    arrayCount = rivers.size();
	    for (int num = 0; num < arrayCount; num ++) {
	        CCSprite body = rivers.get(num);
	        body.setVisible(true);
	    }
	    arrayCount = clouds1.size();
	    for (int num = 0; num < arrayCount; num ++) {
	        CCSprite body = clouds1.get(num);
	        body.setVisible(false);
	    }
	    
	    arrayCount = clouds2.size();
	    for (int num = 0; num < arrayCount; num ++) {
	        CCSprite body = clouds2.get(num);
	        body.setVisible(false);
	    }
	    
	    arrayCount = clouds3.size();
	    for (int num = 0; num < arrayCount; num ++) {
	        CCSprite body = clouds3.get(num);
	        body.setVisible(false);
	    }
	    
	    arrayCount = clouds4.size();
	    for (int num = 0; num < arrayCount; num ++) {
	        CCSprite body = clouds4.get(num);
	        body.setVisible(false);
	    }


	    switch (Common.g_stageNum) {
	        case 0:
	        {
	            int arrayCount1 = firstBodys1.size();
	            for (int num = 0; num < arrayCount1; num ++) {
	                CCSprite body = firstBodys1.get(num);
	                body.setVisible(true);
	            }

	            arrayCount1 = secondBodys1.size();
	            for (int num = 0; num < arrayCount1; num ++) {
	                CCSprite body = secondBodys1.get(num);
	                body.setVisible(true);
	            }

	            arrayCount1 = clouds1.size();
	            for (int num = 0; num < arrayCount1; num ++) {
	                CCSprite body = clouds1.get(num);
	                body.setVisible(true);
	            }

	            curFirstBodys = firstBodys1;
	            curSecondBodys = secondBodys1;
	            curClouds = clouds1;
	            break;
	        }
	        case 1:
	        {
	            int arrayCount1 = firstBodys2.size();
	            for (int num = 0; num < arrayCount1; num ++) {
	                CCSprite body = firstBodys2.get(num);
	                body.setVisible(true);
	            }
	            
	            arrayCount1 = secondBodys2.size();
	            for (int num = 0; num < arrayCount1; num ++) {
	                CCSprite body = secondBodys2.get(num);
	                body.setVisible(true);
	            }

	            arrayCount1 = clouds2.size();
	            for (int num = 0; num < arrayCount1; num ++) {
	                CCSprite body = clouds2.get(num);
	                body.setVisible(true);
	            }
	            
	            curFirstBodys = firstBodys2;
	            curSecondBodys = secondBodys2;
	            curClouds = clouds2;

	            break;
	        }
	        case 2:
	        {
	            int arrayCount1 = firstBodys3.size();
	            for (int num = 0; num < arrayCount1; num ++) {
	                CCSprite body = firstBodys3.get(num);
	                body.setVisible(true);
	            }
	            
	            arrayCount1 = secondBodys3.size();
	            for (int num = 0; num < arrayCount1; num ++) {
	                CCSprite body = secondBodys3.get(num);
	                body.setVisible(true);
	            }

	            arrayCount1 = clouds3.size();
	            for (int num = 0; num < arrayCount1; num ++) {
	                CCSprite body = clouds3.get(num);
	                body.setVisible(true);
	            }
	            
	            curFirstBodys = firstBodys3;
	            curSecondBodys = secondBodys3;
	            curClouds = clouds3;

	            break;
	        }
	        case 3:
	        {
	            int arrayCount1 = firstBodys4.size();
	            for (int num = 0; num < arrayCount1; num ++) {
	                CCSprite body = firstBodys4.get(num);
	                body.setVisible(true);
	            }
	            
	            arrayCount1 = secondBodys4.size();
	            for (int num = 0; num < arrayCount1; num ++) {
	                CCSprite body = secondBodys4.get(num);
	                body.setVisible(true);
	            }

	            arrayCount1 = clouds4.size();
	            for (int num = 0; num < arrayCount1; num ++) {
	                CCSprite body = clouds4.get(num);
	                body.setVisible(true);
	            }
	            
	            curFirstBodys = firstBodys4;
	            curSecondBodys = secondBodys4;
	            curClouds = clouds4;

	            break;
	        }  
	            
	        default:
	            break;
	    }
	}

	public void generateBird()
	{
	    int randBird = (int) (Math.random() * 100 % 2);
	    int randFoodType = (int) (Math.random() * 100 % 2);
	    
	    Bird bird;
	    
	    if (randFoodType == 0) {
	        bird = Bird.init(randBird, Common.FoodType.FoodType_fast);
	    }
	    else
	    {
	        bird = Bird.init(randBird, Common.FoodType.FoodType_fast);
	    }
	    
	    bird.setPosition(0, Common.SCREEN_HEIGHT * 3 / 4);
	    addChild(bird, Common.ztag_In_camera.ztag_food);
	    
	    birds.add(bird);
	}

	private void initFoods()
	{
	    foods = new ArrayList<Food>();
	    float startPos = 0;
	    
	    int randRainbow = (int) (Math.random() * 100 % 10); // ???
	    randRainbow += 10;
	    int randBomb = (int) (Math.random() * 100 % 10); // ???
	    randBomb += 10;
	    
	    Food food;
	    for (int i = 0; i<15; i ++) {
	        if (foods.size()% randRainbow == 0) { // ???
	            food = Food.init(Common.FoodType.FoodType_rainbow);
	        }
	        else if (foods.size() % randBomb == 0) // ???
	        {
	            food = Food.init(Common.FoodType.FoodType_bomb);
	        }
	        else 
	        {
	            food = Food.init(Common.FoodType.FoodType_sun);
	        }
	        
	        food.setPosition(Common.FoodPosInfo_1[i].x * Common.kXForIPhone + startPos, Common.FoodPosInfo_1[i].y * Common.kYForIPhone);
	        foodArrayLength = food.getPosition().x;
	        
	        addChild(food, Common.ztag_In_camera.ztag_food);
	        foods.add(food);
	    }
	    startPos = foodArrayLength + Common.SCREEN_WIDTH / 2;
	    
	    for (int i = 0; i<16; i ++) {
	        if (foods.size() % randRainbow == 0) {
	            food = Food.init(Common.FoodType.FoodType_rainbow);
	        }
	        else if (foods.size() % randBomb == 0)
	        {
	            food = Food.init(Common.FoodType.FoodType_bomb);
	        }
	        else
	        {
	            food = Food.init(Common.FoodType.FoodType_sun);
	        }

	        food.setPosition(Common.FoodPosInfo_2[i].x * Common.kXForIPhone + startPos, Common.FoodPosInfo_2[i].y * Common.kYForIPhone);
	        foodArrayLength = food.getPosition().x;
	        
	        addChild(food, Common.ztag_In_camera.ztag_food);
	        foods.add(food);
	    }
	    startPos = foodArrayLength + Common.SCREEN_WIDTH / 2;

	    for (int i = 0; i<12; i ++) {
	        if (foods.size() % randRainbow == 0) {
	            food = Food.init(Common.FoodType.FoodType_rainbow);
	        }
	        else if (foods.size() % randBomb == 0)
	        {
	            food = Food.init(Common.FoodType.FoodType_bomb);
	        }
	        else
	        {
	            food = Food.init(Common.FoodType.FoodType_sun);
	        }

	        food.setPosition(Common.FoodPosInfo_3[i].x * Common.kXForIPhone + startPos, Common.FoodPosInfo_3[i].y * Common.kYForIPhone);
	        foodArrayLength = food.getPosition().x;
	        
	        addChild(food, Common.ztag_In_camera.ztag_food);
	        foods.add(food);
	    }
	    startPos = foodArrayLength + Common.SCREEN_WIDTH / 2;

	    for (int i = 0; i<20; i ++) {
	        if (foods.size() % randRainbow == 0) {
	            food = Food.init(Common.FoodType.FoodType_rainbow);
	        }
	        else if (foods.size() % randBomb == 0)
	        {
	            food = Food.init(Common.FoodType.FoodType_bomb);
	        }
	        else
	        {
	            food = Food.init(Common.FoodType.FoodType_sun);
	        }
	            
	        food.setPosition(Common.FoodPosInfo_4[i].x * Common.kXForIPhone + startPos, Common.FoodPosInfo_4[i].y * Common.kYForIPhone);
	        foodArrayLength = food.getPosition().x;
	        
	        addChild(food, Common.ztag_In_camera.ztag_food);
	        foods.add(food);
	    }
	    startPos = foodArrayLength + Common.SCREEN_WIDTH / 2;

	    for (int i = 0; i<15; i ++) {
	        if (foods.size() % randRainbow == 0) {
	            food = Food.init(Common.FoodType.FoodType_rainbow);
	        }
	        else if (foods.size() % randBomb == 0)
	        {
	            food = Food.init(Common.FoodType.FoodType_bomb);
	        }
	        else
	        {
	            food = Food.init(Common.FoodType.FoodType_sun);
	        }

	        food.setPosition(Common.FoodPosInfo_5[i].x * Common.kXForIPhone + startPos, Common.FoodPosInfo_5[i].y * Common.kYForIPhone);
	        foodArrayLength = food.getPosition().x;
	        
	        addChild(food, Common.ztag_In_camera.ztag_food);
	        foods.add(food);
	    }
	    
	    foodArrayLength += Common.SCREEN_WIDTH / 2;

	}
	
	private void init() {
        initbodys();
        updateStageImages();
        riverMove();
        initFoods();
        
        birds = new ArrayList<Bird>();
	}

	private void riverMove()
	{
	    CCAnimation ani = CCAnimation.animation("river", 0.5f);
	    for (int i = 0; i < 2; i++) {
	        ani.addFrame(Common.IMG_LIVER[i]);
	    }
	    CCAnimate act = CCAnimate.action(ani);
	    
	    int arrayCount = rivers.size();
	    for (int i = 0; i < arrayCount; i ++) {
	        CCSprite river = rivers.get(i);
	        river.runAction(CCRepeatForever.action(act));
	    }
	}

	public void foodsMove(float moveDelta)
	{
	    int arrayCount = foods.size();
	    for (int i = 0; i < arrayCount; i ++) {
	        Food food = foods.get(i);
	        food.setPosition(CGPoint.ccpAdd(food.getPosition(), CGPoint.ccp(moveDelta, 0)));
	        
	        if (food.getPosition().x + food.getContentSize().width/2 < 0) {	
	            food.setPosition(CGPoint.ccpAdd(food.getPosition(), CGPoint.ccp(foodArrayLength, 0)));
	            food.setVisible(true);
	            food.isEnable = true;
	        }
	    }
	}

	public void birdsMove(float moveDelta)
	{
	    int birdsCount = birds.size();
	    
	    for (int i = birdsCount - 1; i >= 0; i --) {
	        Bird bird = birds.get(i);
	        bird.setPosition(CGPoint.ccpAdd(bird.getPosition(), CGPoint.ccp(moveDelta, 0)));
	        
	        if (bird.getPosition().x > Common.SCREEN_WIDTH * 1.5f) {
	            birds.remove(bird);
	        }
	    }

	}

	public void birdsMoveOnTime( float delta )
	{
	    int birdsCount = birds.size();
	    
	    for (int i = birdsCount - 1; i >= 0; i --) {
	        Bird bird = birds.get(i);
	        bird.setPosition(CGPoint.ccpAdd(bird.getPosition(), CGPoint.ccp(bird.velocity.x * delta, 0)));
	        
	        if (bird.getPosition().x > Common.SCREEN_WIDTH * 1.5f) {
	            birds.remove(bird);
	        }
	    }
	    
	    
	}

	public void bodysMove(float moveDelta)
	{    
	    int arrayCount = curFirstBodys.size();
	    for (int i = 0; i < arrayCount; i ++) {
	        CCSprite body = curFirstBodys.get(i);
	        body.setPosition(CGPoint.ccpAdd(body.getPosition(), CGPoint.ccp(moveDelta, 0)));
	        
	        if ( (body.getPosition().x + body.getContentSize().width * Common.kXForIPhone) < -Common.SCREEN_WIDTH/2) {
	            CCSprite lastBody = curFirstBodys.get(curFirstBodys.size()-1);
	            float arrayEnd = lastBody.getPosition().x + lastBody.getContentSize().width * Common.kXForIPhone;
	            body.setPosition(CGPoint.ccp(arrayEnd, body.getPosition().y));
	            
	            curFirstBodys.remove(body);
	            curFirstBodys.add(body);
	        }
	    }
	    
	    arrayCount = curSecondBodys.size();
	    for (int i = 0; i < arrayCount; i ++) {
	        CCSprite body = curSecondBodys.get(i);
	        body.setPosition(CGPoint.ccpAdd(body.getPosition(), CGPoint.ccp(moveDelta, 0)));
	        
	        if ( (body.getPosition().x + body.getContentSize().width * Common.kXForIPhone) < -Common.SCREEN_WIDTH/2) {
	            CCSprite lastBody = curFirstBodys.get(curSecondBodys.size()-1);
	            float arrayEnd = lastBody.getPosition().x + lastBody.getContentSize().width * Common.kXForIPhone;
	            body.setPosition(CGPoint.ccp(arrayEnd, body.getPosition().y));
	            
	            curSecondBodys.remove(body);
	            curSecondBodys.add(body);
	        }
	        
	    }
	    
	    arrayCount = curClouds.size();
	    for (int i = 0; i < arrayCount; i ++) {
	        CCSprite body = curClouds.get(i);
	        body.setPosition(CGPoint.ccpAdd(body.getPosition(), CGPoint.ccp(moveDelta, 0)));
	        
	        if ( (body.getPosition().x + body.getContentSize().width / 2 * Common.kXForIPhone) < 0) {
	            body.setPosition(body.getPosition().x + 1100 * Common.kXForIPhone, body.getPosition().y);
	            
	            curClouds.remove(body);
	            curClouds.add(body);
	        }
	        
	    }
	}

	private void removeSprite( CCSprite sp )
	{
	    CCTexture2D tex = sp.getTexture();
	    CCTextureCache.sharedTextureCache().removeTexture(tex);
	    sp.removeFromParentAndCleanup(true);
	}

	public void removeCache()
	{
	    int arrayCount = firstBodys1.size();
	    if (arrayCount > 0 && firstBodys1 != null) {
	        for (int i = 0 ; i < arrayCount; i ++) {
	            CCSprite sprite = firstBodys1.get(i);
	            removeSprite(sprite);
	            sprite.removeFromParentAndCleanup(true);
	        }
	        firstBodys1.removeAll(firstBodys1);
	    }
	    
	    arrayCount = firstBodys2.size();
	    if (arrayCount > 0 && firstBodys2 != null) {
	        for (int i = 0 ; i < arrayCount; i ++) {
	            CCSprite sprite = firstBodys2.get(i);
	            removeSprite(sprite);
	            sprite.removeFromParentAndCleanup(true);
	        }
	        firstBodys2.removeAll(firstBodys2);
	    }

	    arrayCount = firstBodys3.size();
	    if (arrayCount > 0 && firstBodys3 != null) {
	        for (int i = 0 ; i < arrayCount; i ++) {
	            CCSprite sprite = firstBodys3.get(i);
	            removeSprite(sprite);
	            sprite.removeFromParentAndCleanup(true);
	        }
	        firstBodys3.removeAll(firstBodys3);
	    }

	    arrayCount = firstBodys4.size();
	    if (arrayCount > 0 && firstBodys4 != null) {
	        for (int i = 0 ; i < arrayCount; i ++) {
	            CCSprite sprite = firstBodys4.get(i);
	            removeSprite(sprite);
	            sprite.removeFromParentAndCleanup(true);
	        }
	        firstBodys4.removeAll(firstBodys4);
	    }
	    
	    arrayCount = secondBodys1.size();
	    if (arrayCount > 0 && secondBodys1 != null) {
	        for (int i = 0 ; i < arrayCount; i ++) {
	            CCSprite sprite = secondBodys1.get(i);
	            removeSprite(sprite);
	            sprite.removeFromParentAndCleanup(true);
	        }
	        secondBodys1.removeAll(secondBodys1);
	    }
	    arrayCount = secondBodys2.size();
	    if (arrayCount > 0 && secondBodys2 != null) {
	        for (int i = 0 ; i < arrayCount; i ++) {
	            CCSprite sprite = secondBodys2.get(i);
	            removeSprite(sprite);
	            sprite.removeFromParentAndCleanup(true);
	        }
	        secondBodys2.removeAll(secondBodys2);
	    }
	    arrayCount = secondBodys3.size();
	    if (arrayCount > 0 && secondBodys3 != null) {
	        for (int i = 0 ; i < arrayCount; i ++) {
	            CCSprite sprite = secondBodys3.get(i);
	            removeSprite(sprite);
	            sprite.removeFromParentAndCleanup(true);
	        }
	        secondBodys3.removeAll(secondBodys3);
	    }
	    arrayCount = secondBodys4.size();
	    if (arrayCount > 0 && secondBodys4 != null) {
	        for (int i = 0 ; i < arrayCount; i ++) {
	            CCSprite sprite = secondBodys4.get(i);
	            removeSprite(sprite);
	            sprite.removeFromParentAndCleanup(true);
	        }
	        secondBodys4.removeAll(secondBodys4);
	    }

	    arrayCount = clouds1.size();
	    if (arrayCount > 0 && clouds1 != null) {
	        for (int i = 0 ; i < arrayCount; i ++) {
	            CCSprite sprite = clouds1.get(i);
	            removeSprite(sprite);
	            sprite.removeFromParentAndCleanup(true);
	        }
	        clouds1.removeAll(clouds1);
	    }
	    arrayCount = clouds2.size();
	    if (arrayCount > 0 && clouds2 != null) {
	        for (int i = 0 ; i < arrayCount; i ++) {
	            CCSprite sprite = clouds2.get(i);
	            removeSprite(sprite);
	            sprite.removeFromParentAndCleanup(true);
	        }
	        clouds2.removeAll(clouds1);
	    }
	    arrayCount = clouds3.size();
	    if (arrayCount > 0 && clouds3 != null) {
	        for (int i = 0 ; i < arrayCount; i ++) {
	            CCSprite sprite = clouds3.get(i);
	            removeSprite(sprite);
	            sprite.removeFromParentAndCleanup(true);
	        }
	        clouds3.removeAll(clouds3);
	    }
	    arrayCount = clouds4.size();
	    if (arrayCount > 0 && clouds4 != null) {
	        for (int i = 0 ; i < arrayCount; i ++) {
	            CCSprite sprite = clouds4.get(i);
	            removeSprite(sprite);
	            sprite.removeFromParentAndCleanup(true);
	        }
	        clouds4.removeAll(clouds4);
	    }

	    arrayCount = rivers.size();
	    if (arrayCount > 0 && rivers != null) {
	        for (int i = 0 ; i < arrayCount; i ++) {
	            CCSprite sprite = rivers.get(i);
	            removeSprite(sprite);
	            sprite.removeFromParentAndCleanup(true);
	        }
	        rivers.removeAll(rivers);
	    }

	    arrayCount = foods.size();
	    if (arrayCount > 0 && foods != null) {
	        for (int i = 0 ; i < arrayCount; i ++) {
	            Food food = foods.get(i);
	            food.removeFromParentAndCleanup(true);
	        }
	        foods.removeAll(foods);
	    }

	    arrayCount = birds.size();
	    if (arrayCount > 0 && birds != null) {
	        for (int i = 0 ; i < arrayCount; i ++) {
	            Bird bird = birds.get(i);
	            bird.stopAllActions();
	            bird.removeFromParentAndCleanup(true);
	        }
	        birds.removeAll(birds);
	    }

	}
	
	@Override
	public void onExit() {
		removeCache();
		super.onExit();
	}
}

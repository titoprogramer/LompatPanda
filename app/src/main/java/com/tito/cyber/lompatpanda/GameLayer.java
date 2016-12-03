package com.tito.cyber.lompatpanda;

import java.util.ArrayList;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabelAtlas;
import org.cocos2d.nodes.CCMotionStreak;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor4B;

import android.view.MotionEvent;

import com.tito.cyber.lompatpanda.Common.GameState;

public class GameLayer extends CCLayer {

	private static GameLayer _sharedGameLayer = null;
	public static GameLayer sharedGameLayer(){
		return _sharedGameLayer;
	}
	
	public final float STATE_COMPLETE_LENGTH =  35000.0f;
	public final float START_POS_PLAYER_Y = (80 * Common.kYForIPhone);

	public final float SCORE_LENGTH_X = (15 * Common.kXForIPhone);
	public final float SCORE_LENGTH_Y = (283 * Common.kYForIPhone);

	public final float PAUSE_X    =     (455 * Common.kXForIPhone);
	public final float PAUSE_Y    =     (300 * Common.kYForIPhone);

	public final float RESUME_X    =    (240 * Common.kXForIPhone);
	public final float RESUME_Y     =   (222 * Common.kYForIPhone);

	public final float RETRY_X       =  (165 * Common.kXForIPhone);
	public final float RETRY_Y        = (100 * Common.kYForIPhone);

	public final float MENU_X         = (325 * Common.kXForIPhone);
	public final float MENU_Y         = (100 * Common.kYForIPhone);

	public static float TIME_BIERD_GEN       =   10;
	private final float DEFAULT_MOVE_VEL_X = (130 * Common.kXForIPhone);
	
	private int m_length;
	private CCLabelAtlas lblScoreLength;
	private CCMenuItemImage pauseMenuItem;
	private CCMenuItemImage resumeMenuItem;
	private CCMenuItemImage retryMenuItem;
	private CCMenuItemImage menuMenuItem;
	private int dotArrayCount;
	private ArrayList<CCSprite> board_dots_starts;
	private ArrayList<CCSprite> board_dots_ends;
	private ArrayList<BoardLine> board_lines;
	public Panda player;
	public int tipNum;
	private int genBirdCount;
	private float gameTime;
	private GameState gameState;
	private boolean isStartNeed;
	public MoveLayer moveLayer;
	private BackLayer backLayer;
	private CCSprite drawHere;
	private CCSprite tryAgain;
	private CCSprite keepOn;
	private CCSprite goolLuck;
	private CCSprite hand;
	private GameOverLayer gameoverLayer;
	public CCMotionStreak streak;

	private boolean isTouched = false;

	private BoardLine line;

	private CCSprite startDot;

	private CCSprite endDot;
	private CGPoint touchStartPoint;

    public GameLayer()
	{
    	_sharedGameLayer = this;
    	init();
	}
	
    private void initButtons()
    {
        lblScoreLength = CCLabelAtlas.label("0123456789", "num4.png", 18, 18, '0');
        String string = String.format("%d", m_length);
        lblScoreLength.setScaleX(Common.kXForIPhone);
        lblScoreLength.setScaleY(Common.kYForIPhone);
        lblScoreLength.setString(string);
        lblScoreLength.setPosition(SCORE_LENGTH_X, SCORE_LENGTH_Y);
        addChild(lblScoreLength, Common.ztag_In_camera.ztag_menu);

        pauseMenuItem = CCMenuItemImage.item("pause.png", "pause.png", this, "onPause");
        pauseMenuItem.setScaleX(Common.kXForIPhone);
        pauseMenuItem.setScaleY(Common.kYForIPhone);
        pauseMenuItem.setPosition(PAUSE_X, PAUSE_Y);
        
        resumeMenuItem = CCMenuItemImage.item("resume.png", "resume1.png", this, "onResume");
        resumeMenuItem.setScaleX(Common.kXForIPhone);
        resumeMenuItem.setScaleY(Common.kYForIPhone);
        resumeMenuItem.setPosition(RESUME_X, RESUME_Y);
        resumeMenuItem.setVisible(false);

        retryMenuItem = CCMenuItemImage.item("retry.png", "retry1.png", this, "onRetry");
        retryMenuItem.setScaleX(Common.kXForIPhone);
        retryMenuItem.setScaleY(Common.kYForIPhone);
        retryMenuItem.setPosition(RETRY_X, RETRY_Y);
        retryMenuItem.setVisible(false);

        menuMenuItem = CCMenuItemImage.item("menu.png", "menu1.png", this, "onMenu");
        menuMenuItem.setScaleX(Common.kXForIPhone);
        menuMenuItem.setScaleY(Common.kYForIPhone);
        menuMenuItem.setPosition(MENU_X, MENU_Y);
        menuMenuItem.setVisible(false);
        
        CCMenu menu = CCMenu.menu(pauseMenuItem, resumeMenuItem, retryMenuItem, menuMenuItem);
        menu.setPosition(CGPoint.zero());
        addChild(menu, Common.ztag_In_camera.ztag_menu);

    }

    private void initDot_Lines()
    {
        dotArrayCount = 5;
        board_dots_starts = new ArrayList<CCSprite>(dotArrayCount);
        board_dots_ends = new ArrayList<CCSprite>(dotArrayCount);
        board_lines = new ArrayList<BoardLine>(dotArrayCount);
        
        for (int i = 0; i < dotArrayCount; i ++) {
            BoardLine dot_line = new BoardLine("line.png");
            dot_line.touchedCount = 0;
            dot_line.setScaleX(Common.kXForIPhone);
            dot_line.setScaleY(Common.kYForIPhone);
            dot_line.setVisible(false);
            dot_line.setAnchorPoint(0f, 0.5f);
            addChild(dot_line, Common.ztag_In_camera.ztag_player);
            board_lines.add(dot_line);
            
            CCSprite dot_start = CCSprite.sprite("board_dot.png");
            dot_start.setScaleX(Common.kXForIPhone);
            dot_start.setScaleY(Common.kYForIPhone);
            dot_start.setVisible(false);
            addChild(dot_start, Common.ztag_In_camera.ztag_player);
            board_dots_starts.add(dot_start);
            
            CCSprite dot_end = CCSprite.sprite("board_dot.png");
            dot_end.setScaleX(Common.kXForIPhone);
            dot_end.setScaleY(Common.kYForIPhone);
            dot_end.setVisible(false);
            addChild(dot_end, Common.ztag_In_camera.ztag_player);
            board_dots_ends.add(dot_end);
        }

    }

    private void initPlayer()
    {
        player = Panda.initPanda();
        player.setPosition(10, START_POS_PLAYER_Y);
        player.realHeight = player.getPosition().y;
        addChild(player, Common.ztag_In_camera.ztag_player);
    }

    private void initGame()
    {
        tipNum = 0;
        drawHere.setVisible(true);
        hand.setVisible(true);
        CCMoveBy moveHand = CCMoveBy.action(1.0f, CGPoint.ccp(Common.SCREEN_WIDTH / 5, 0));
        CCSequence seq = CCSequence.actions(moveHand,
                  CCCallFunc.action(this, "handResetPos"));
        hand.runAction(CCRepeatForever.action(seq));

        tryAgain.setVisible(false);
        keepOn.setVisible(false);
        goolLuck.setVisible(false);
        hand.setVisible(true);
        
        gameTime = 0.0f;
        genBirdCount = 1;
        
        player.setPosition(10, START_POS_PLAYER_Y);
        player.realHeight = player.getPosition().y;
        player.tail.setVisible(false);
        player.state = Panda.PandaState_none;
        
        player.velocity = CGPoint.ccp(0, 0);
        player.state = Panda.PandaState_start;
        player.stateDirect = Panda.DirectState.DirectState_down;
        
        player.sunCount = 0;
        player.flayLength = 0;
        player.birdCount = 0;
        
        player.setVisible(true);


        gameState = Common.GameState.GameState_start;
        isStartNeed = false;
        
        Common.g_stageNum = 0;
        moveLayer.setPosition(0, 0);
        moveLayer.updateStageImages();
        backLayer.setPosition(0, 0);
        backLayer.updateBackground();
        
        updateFlayLengthLabel();
    }

    public void handResetPos()
    {
        hand.setPosition(Common.SCREEN_WIDTH * 2 / 5, 20 * Common.kYForIPhone);
    }
    
    public void initImages()
    {
        drawHere = CCSprite.sprite("drawhere.png");
        drawHere.setScale(Common.kXForIPhone);
        drawHere.setPosition(Common.SCREEN_WIDTH * 3 / 5, Common.SCREEN_HEIGHT / 3);
        addChild(drawHere);
        drawHere.setVisible(false);
        
        tryAgain = CCSprite.sprite("drawhere1.png");
        tryAgain.setScale(Common.kXForIPhone);
        tryAgain.setPosition(Common.SCREEN_WIDTH * 3 / 5, Common.SCREEN_HEIGHT / 3);
        addChild(tryAgain);
        tryAgain.setVisible(false);
        
        keepOn = CCSprite.sprite("drawhere2.png");
        keepOn.setScale(Common.kXForIPhone);
        keepOn.setPosition(Common.SCREEN_WIDTH * 3 / 5, Common.SCREEN_HEIGHT / 3);
        addChild(keepOn);
        keepOn.setVisible(false);
        
        goolLuck = CCSprite.sprite("drawhere3.png");
        goolLuck.setScale(Common.kXForIPhone);
        goolLuck.setPosition(Common.SCREEN_WIDTH * 3 / 5, Common.SCREEN_HEIGHT / 3);
        addChild(goolLuck);
        goolLuck.setVisible(false);
        
        hand = CCSprite.sprite( "hand.png");
        hand.setScale(Common.kXForIPhone);
        hand.setPosition(Common.SCREEN_WIDTH * 2 / 5, 20 * Common.kYForIPhone);
        addChild(hand);
        
    }
    
    private void init() {
    	this.setIsTouchEnabled(true);
        
        backLayer = new BackLayer();
        backLayer.setPosition(0, 0);
        addChild(backLayer);
        
        moveLayer = new MoveLayer();
        moveLayer.setPosition(0, 0);
        moveLayer.originHeight = moveLayer.getPosition().y;
        addChild(moveLayer);
        
        gameoverLayer = new GameOverLayer();
        gameoverLayer.setPosition(0, 0);
        addChild(gameoverLayer);
        gameoverLayer.setVisible(false);
        
        m_length = 0;
        gameState = Common.GameState.GameState_start;
        
        streak = new CCMotionStreak(1.0f, 0.1f, "tear.png", 16f, 0.01f, new ccColor4B(255, 255, 255, 255));
        streak.setScaleX(Common.kXForIPhone);
        streak.setScaleY(Common.kYForIPhone);
        addChild(streak, Common.ztag_In_camera.ztag_player);
        
        initButtons();
        initDot_Lines();
        initPlayer();
        initImages();
        initGame();
    }

	@Override
	public void onEnter() {
		super.onEnter();

        CCDirector.sharedDirector().pause();
        unschedule("onTimePlayerMove");
        
        CCDirector.sharedDirector().resume();
        initGame();
	}

	public boolean dotIntersectWithLine(Panda target, CGPoint line_start, CGPoint line_end)
    {
        CGPoint dotPoint = target.getPosition();

        CGSize s = target.getContentSize();
    	CGRect targetRect = CGRect.make(-s.width * target.getScaleX() / 2 + dotPoint.x,
    						   -s.height * target.getScaleY() / 2 + dotPoint.y, 
    						   s.width * target.getScaleX(), 
    						   s.height * target.getScaleY());

        
        if ( (dotPoint.x > line_start.x && dotPoint.x < line_end.x) ||
             (dotPoint.x > line_end.x && dotPoint.x < line_start.x)) {
            
            float value_K = (line_end.y - line_start.y) / (line_end.x - line_start.x);
            
            float value_Y_inLine = value_K * (dotPoint.x - line_start.x) + line_start.y;
            
            if (CGRect.containsPoint(targetRect, CGPoint.ccp(dotPoint.x, value_Y_inLine))) {
                return true;
            }

        }
        
        return false;
    }

    public void playerBounsed(CGPoint line_start, CGPoint line_end)
    {
    	Common.effectPlay(R.raw.jumppad);
        
        float alpha_line_in_radian = (float) Math.atan2( (line_end.y - line_start.y), (line_end.x - line_start.x) );
        float alpha_vel_with_line = CGPoint.ccpAngleSigned(player.velocity, CGPoint.ccpSub(line_end, line_start));
        
        float realAlpha = alpha_vel_with_line + alpha_line_in_radian;
        
        float lineLenght = CGPoint.ccpDistance(line_start, line_end);
        float valueK = 1 + (Common.SCREEN_WIDTH - lineLenght) / Common.SCREEN_WIDTH / 3;
        
        float oldVelocityAbs = CGPoint.ccpLength(player.velocity);
        
        if (realAlpha < 0) {
            realAlpha = 0.5f;
        }
        player.velocity = CGPoint.ccp( (float)(oldVelocityAbs * Math.cos(realAlpha)), (float)(oldVelocityAbs * Math.sin(realAlpha)  * valueK) );
        
        if (player.velocity.y > 400 * Common.kYForIPhone) {
            player.velocity = CGPoint.ccp(player.velocity.x, 400 * Common.kYForIPhone);
        }
        
        float nextTime = -player.velocity.y / Panda.GRAVITY * 2;
        float deltaDistance = player.velocity.x * nextTime;
        CGPoint nextPoint = CGPoint.ccpAdd(player.getPosition(), CGPoint.ccp(deltaDistance, 0));
        
        if (nextPoint.x < 0) {
            nextPoint  = CGPoint.ccp(-deltaDistance - player.getPosition().x, player.getPosition().y);
        }
        
        switch (tipNum) {
            case 0:
                tipNum = 1;
                drawHere.setVisible(false);
                hand.stopAllActions();
                hand.setVisible(false);
                tryAgain.setPosition(nextPoint);
                tryAgain.setVisible(true);
                break;
            case 1:
                tipNum = 2;
                tryAgain.setVisible(false);
                keepOn.setPosition(nextPoint);
                keepOn.setVisible(true);
                break;
            case 2:
                tipNum = 3;
                keepOn.setVisible(false);
                goolLuck.setPosition(nextPoint);
                goolLuck.setVisible(true);
                break;
            case 3:
                tipNum = 4;
                goolLuck.setVisible(false);
                break;

                
            default:
                break;
        }
    }

    public void collisionScan()
    {
    // with dots and lines
        for (int i = 0; i < dotArrayCount; i ++) {
            BoardLine dot_line = board_lines.get(i);
            if (dot_line.getVisible() == true && dot_line.touchedCount == 0) {
                
                CCSprite dot_start = board_dots_starts.get(i);
                CCSprite dot_end = board_dots_ends.get(i);
                

                boolean isDotTopLine = false;
                isDotTopLine = dotIntersectWithLine(player, dot_start.getPosition(), dot_end.getPosition());
       
                if (isDotTopLine == true) {
                    
                    line.runAction(Common.ani_line.copy());
                    
                    playerBounsed(dot_start.getPosition(), dot_end.getPosition());
                    dot_line.touchedCount = 1;
                    
                    
                    if (dot_line.getOpacity() == 255) {
                        dot_line.runAction(CCFadeOut.action(0.5f));
                        
                    }
                    if (dot_start.getOpacity() == 255) {
                        dot_start.runAction(CCFadeOut.action(0.5f));
                        
                    }
                    if (dot_end.getOpacity() == 255) {
                        dot_end.runAction(CCFadeOut.action(0.5f));
                        
                    }

                }
            }
        }
    }

    public void restartGame()
    {
        m_length = 0;
        
        float vel_Y = Common.SCREEN_WIDTH / 2 / DEFAULT_MOVE_VEL_X * Panda.GRAVITY / 2 * (-1);
        
        player.velocity = CGPoint.ccp(DEFAULT_MOVE_VEL_X, vel_Y);
        player.startTime = player.endTime = 0.0f;
        schedule("startCountTime", 0.1f);
        
        schedule("onTimePlayerMove");
    }
    
    public void startCountTime( float delta ) {
    	player.endTime += delta;
    }

    public void collisionScanWithFoods()
    {
        int arrayCount = moveLayer.foods.size();
        for (int i = 0; i < arrayCount; i ++) {
            Food food = moveLayer.foods.get(i);
            
            if (food.isEnable == false) {
                continue;
            }
            
            CGRect rect = player.rect();
            if (player.state == Panda.PandaState_fast) {
                rect.origin.x -= food.rect().size.width;
                rect.size.width += food.rect().size.width*2;
            }
            if ( CGRect.intersects(rect, food.rect()) ) {
                
                food.setVisible(false);
                food.isEnable = false;
                
                switch (food.type) {
                    case FoodType_bomb:
                        if (player.state == Panda.PandaState_protect) {
                            break;
                        }
                        
                        player.collisionWithBomb();
                        break;
                    case FoodType_protect:
                        player.collisionWithProtect();
                        break;
                    case FoodType_rainbow:
                        player.collisionWithRainbow();
                        break;
                    case FoodType_fast:
                        player.collisionWithFast();
                        break;
                    case FoodType_sun:
                        player.sunCount ++;
                        break;

                    default:
                        break;
                }
            }
        }
        
        arrayCount = moveLayer.birds.size();
        for (int i = 0; i < arrayCount ; i ++) {
            Bird bird = moveLayer.birds.get(i);
            
            if (CGRect.intersects(bird.rect(), player.rect())) {
                switch (bird.foodType) {
                    case FoodType_fast:
                        player.collisionWithFast();                    
                        break;
                    case FoodType_protect:
                        player.collisionWithProtect();
                        break;
                        
                    default:
                        break;
                }
                
                
                bird.foodSprite.setVisible(false);
            }
        }

    }

    private void updateFlayLengthLabel()
    {
        String string = String.format("%d", player.flayLength / 3);
        lblScoreLength.setString(string);

    }

    public void onTimePlayerMove( float delta )
    {
        gameTime += delta;
        
        if (gameTime > TIME_BIERD_GEN * genBirdCount) {
            moveLayer.generateBird();
            genBirdCount ++;
        }
        
        player.move(delta);
        moveLayer.birdsMoveOnTime(delta);
        
        if (player.flayLength / 3 >= STATE_COMPLETE_LENGTH * 4) {
        }
        else if (player.flayLength / 3 >= STATE_COMPLETE_LENGTH * 3  && Common.g_stageNum == 2)
        {
        	Common.g_stageNum = 3;
            moveLayer.updateStageImages();
            backLayer.updateBackground();
        }
        else if (player.flayLength / 3 >= STATE_COMPLETE_LENGTH * 2  && Common.g_stageNum == 1)
        {
        	Common.g_stageNum = 2;
            moveLayer.updateStageImages();
            backLayer.updateBackground();
        }
        else if (player.flayLength / 3 >= STATE_COMPLETE_LENGTH * 1 && Common.g_stageNum == 0)
        {
        	Common.g_stageNum = 1;
            moveLayer.updateStageImages();
            backLayer.updateBackground();
        }
        
        updateFlayLengthLabel();
        
        collisionScan();
        collisionScanWithFoods();
    }

    public void cameraMove()
    {
        float cameraMoveDelta = player.realHeight - player.getPosition().y;
        moveLayer.setPosition(moveLayer.getPosition().x, moveLayer.originHeight - cameraMoveDelta);

        moveLayer.setScaleX(1 / ( 1 + cameraMoveDelta * 1.5f / Common.SCREEN_HEIGHT));
        moveLayer.setScaleY(1 / ( 1 + cameraMoveDelta * 1.5f / Common.SCREEN_HEIGHT));

        tryAgain.setScale(moveLayer.getScaleX() * Common.kXForIPhone);
        keepOn.setScale(moveLayer.getScaleX() * Common.kXForIPhone);
        goolLuck.setScale(moveLayer.getScaleX() * Common.kXForIPhone);
    }

    public void gameOver()
    {
        drawHere.setVisible(false);
        tryAgain.setVisible(false);
        keepOn.setVisible(false);
        goolLuck.setVisible(false);
        hand.setVisible(false);
        hand.stopAllActions();

        Common.effectPlay(R.raw.death);
        
        unschedule("startCountTime");
        gameState = Common.GameState.GameState_gameover;
        player.totalDistance += player.flayLength / 3;
        player.setVisible(false);
        player.stopAllActions();
        
        for (int i = 0; i < dotArrayCount; i ++) {
            CCSprite boar_line = board_lines.get(i);
            boar_line.stopAllActions();
            boar_line.setVisible(false);
            boar_line.setOpacity(255);
            
            CCSprite board_start_dot = board_dots_starts.get(i);
            board_start_dot.stopAllActions();
            board_start_dot.setVisible(false);
            board_start_dot.setOpacity(255);
            
            CCSprite board_end_dot = board_dots_ends.get(i);
            board_end_dot.stopAllActions();
            board_end_dot.setVisible(false);
            board_end_dot.setOpacity(255);
            
        }    
        
        int arrayCount = moveLayer.birds.size();
        
        for (int i = 0; i < arrayCount; i ++) {
            Bird bird = moveLayer.birds.get(i);
            bird.stopAllActions();
            bird.removeFromParentAndCleanup(true);
        }
        moveLayer.birds.removeAll(moveLayer.birds);


        if (line != null) {
            line.touchedCount = 0;
            line.stopAllActions();
            line.setOpacity(255);
            line.setVisible(false);
        }
        if (startDot != null) {
            startDot.stopAllActions();
            startDot.setOpacity(255);
            startDot.setVisible(false);
        }
        if (endDot != null) {
            endDot.stopAllActions();
            endDot.setOpacity(255);
            endDot.setVisible(false);
        }

        player.calcRank();
        
        unschedule("onTimePlayerMove");
        gameoverLayer.setVisible(true);
        gameoverLayer.retry.setVisible(true);
        gameoverLayer.menu.setVisible(true);
        gameoverLayer.updateLabels();
        
        pauseMenuItem.setVisible(false);
        lblScoreLength.setVisible(false);
        
        gameoverLayer.retry.setIsEnabled(true);
        gameoverLayer.menu.setIsEnabled(true);
    }

    public void tutorialMove(float moveDelta)
    {
        if (tryAgain.getVisible() == true ) {
            tryAgain.setPosition(CGPoint.ccpAdd(tryAgain.getPosition(), CGPoint.ccp(moveDelta, 0)));
            if (tryAgain.getPosition().x < 0) {
                tryAgain.setPosition(0, tryAgain.getPosition().y);
            }
        }
        
        if (keepOn.getVisible() == true) {
            keepOn.setPosition(CGPoint.ccpAdd(keepOn.getPosition(), CGPoint.ccp(moveDelta, 0)));
            if (keepOn.getPosition().x < 0) {
                keepOn.setPosition(0, keepOn.getPosition().y);
            }
        }
        
        if (goolLuck.getVisible() == true) {
            goolLuck.setPosition(CGPoint.ccpAdd(goolLuck.getPosition(), CGPoint.ccp(moveDelta, 0)));
            if (goolLuck.getPosition().x < 0) {
                goolLuck.setPosition(0, goolLuck.getPosition().y);
            }
        }
    }

    public void dotMove(float moveDelta)
    {    
        for ( int i = 0; i < dotArrayCount; i ++) {
            BoardLine dot_line = board_lines.get(i);
            if (dot_line.getVisible() == false) {
                continue;
            }
            if (!(dot_line == line && isTouched  == true)) {
                dot_line.setPosition(dot_line.getPosition().x + moveDelta, dot_line.getPosition().y);

            }
            
            CCSprite dot_start = board_dots_starts.get(i);
            if (!(dot_start == startDot && isTouched == true)) {
                dot_start.setPosition(dot_start.getPosition().x + moveDelta, dot_start.getPosition().y);

            }
            CCSprite dot_end = board_dots_ends.get(i);
            if (!(dot_end == endDot && isTouched == true)) {
                dot_end.setPosition(dot_end.getPosition().x + moveDelta, dot_end.getPosition().y);

            }
            
            if (dot_line.getOpacity() < 50) {
                dot_line.setOpacity(255);
                dot_line.setVisible(false);
                dot_start.setOpacity(255);
                dot_start.setVisible(false);
                dot_end.setOpacity(255);
                dot_end.setVisible(false);
            }
        }
    }

    public void onPause( Object sender )
    {
    	if( gameState != GameState.GameState_gameover ){
            CCDirector.sharedDirector().pause();
            unschedule("onTimePlayerMove");
            
            resumeMenuItem.setVisible(true);
            retryMenuItem.setVisible(true);
            menuMenuItem.setVisible(true);
    	}
    }

    public void onResume( Object sender )
    {
        CCDirector.sharedDirector().resume();
        schedule("onTimePlayerMove");
        
        resumeMenuItem.setVisible(false);
        retryMenuItem.setVisible(false);
        menuMenuItem.setVisible(false);

    }

    public void onRetry( Object sender )
    {
        CCDirector.sharedDirector().resume();
        resumeMenuItem.setVisible(false);
        retryMenuItem.setVisible(false);
        menuMenuItem.setVisible(false);
     
        gameoverLayer.setVisible(false);
        pauseMenuItem.setVisible(true);
        lblScoreLength.setVisible(true);

        initGame();
    }
    
    public void onMenu( Object sender )
    {
        CCDirector.sharedDirector().resume();
        resumeMenuItem.setVisible(false);
        retryMenuItem.setVisible(false);
        menuMenuItem.setVisible(false);

		CCScene scene = CCScene.node();
	 	scene.addChild(new MenuLayer(), 1);
	 	CCDirector.sharedDirector().replaceScene(scene);
    }

	@Override
	public boolean ccTouchesBegan(MotionEvent event)
	{
	    if (gameState == GameState.GameState_gameover) {
	    	return CCTouchDispatcher.kEventHandled;
	    }
	    
	    isTouched = true;

	    if ( gameState == GameState.GameState_gaming) {
	        if (line.touchedCount == 0) {
	            line.runAction(CCFadeOut.action(0.5f));
	            startDot.runAction(CCFadeOut.action(0.5f));
	            endDot.runAction(CCFadeOut.action(0.5f));

	        }

	    }

		CGPoint touchPoint = CGPoint.make(event.getX(), event.getY());
		touchPoint = CCDirector.sharedDirector().convertToGL(touchPoint);

	    if (gameState == GameState.GameState_start) {
	        if (touchPoint.x < Common.SCREEN_WIDTH / 2) {
	            isStartNeed = true;
	        }
	    }
	    
	    touchStartPoint = touchPoint;

	    for (int i = 0; i < dotArrayCount; i ++) {
	        line = board_lines.get(i);
	        if (line.getVisible() == false) {
	            line.stopAllActions();
	            line.touchedCount = 0;
	            line.setOpacity(255);
	            line.setVisible(true);
	            line.setScaleX(0.01f);
	            line.setScaleY(Common. kYForIPhone);
	            line.setPosition(touchPoint.x, touchPoint.y);
	            
	            if (startDot != null)
	            	startDot.stopAllActions();
	            startDot = board_dots_starts.get(i);
	            startDot.setOpacity(255);
	            startDot.setVisible(true);
	            startDot.setPosition(touchPoint.x, touchPoint.y);
	            
	            if(endDot != null)
	            	endDot.stopAllActions();
	            endDot = board_dots_ends.get(i);;
	            endDot.setOpacity(255);
	            endDot.setVisible(true);
	            endDot.setPosition(touchPoint.x, touchPoint.y);
	            break;
	        }
	    }    
	    return CCTouchDispatcher.kEventHandled;	    
	}
	
	@Override
	public boolean ccTouchesMoved(MotionEvent event)
	{
	    if (gameState == GameState.GameState_gameover) {
	        return CCTouchDispatcher.kEventIgnored;
	    }

	    isTouched = true;

		CGPoint touchPoint = CGPoint.make(event.getX(), event.getY());
		touchPoint = CCDirector.sharedDirector().convertToGL(touchPoint);

	    float distance = CGPoint.ccpDistance(touchStartPoint, touchPoint);
	    
	    float deltaX = (touchPoint.x - touchStartPoint.x);
	    float deltaY = (touchPoint.y - touchStartPoint.y);
	    float alpha = (float) Math.atan2(deltaY, deltaX);

	    line.setRotation((float) (- alpha * 180 / Math.PI));
	    line.setScaleX(distance / (line.getContentSize().width));
	    endDot.setPosition(touchPoint.x, touchPoint.y);

	    return CCTouchDispatcher.kEventIgnored;
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event)
	{
	    if (gameState == GameState.GameState_gameover) {
	    	return CCTouchDispatcher.kEventIgnored;
	    }

	    isTouched = false;

		CGPoint touchPoint = CGPoint.make(event.getX(), event.getY());
		touchPoint = CCDirector.sharedDirector().convertToGL(touchPoint);
	    
	    if (gameState == GameState.GameState_start) {
	        if (isStartNeed == true) {
	            if (touchPoint.x > Common.SCREEN_WIDTH / 2) {
	                gameState = GameState.GameState_gaming;
	                restartGame();
	                return CCTouchDispatcher.kEventIgnored;
	            }
	        }

	        line.setVisible(false);
	        startDot.setVisible(false);
	        endDot.setVisible(false);
	    }

	    return CCTouchDispatcher.kEventIgnored;
	}

	private void removeSprite(CCSprite sp)
	{
	    CCTexture2D tex = sp.getTexture();
	    CCTextureCache.sharedTextureCache().removeTexture(tex);
	    sp.removeFromParentAndCleanup(true);
	}

	private void removeCache()
	{
	    backLayer.removeFromParentAndCleanup(true);
	    
	    moveLayer.removeFromParentAndCleanup(true);
	    
	    gameoverLayer.removeFromParentAndCleanup(true);
	    
	    CCTexture2D tex = lblScoreLength.getTexture();
	    CCTextureCache.sharedTextureCache().removeTexture(tex);
	    lblScoreLength.removeFromParentAndCleanup(true);

	    removeSprite((CCSprite)pauseMenuItem.getNormalImage());
	    removeSprite((CCSprite)pauseMenuItem.getSelectedImage());
	    pauseMenuItem.removeFromParentAndCleanup(true);

	    removeSprite((CCSprite)resumeMenuItem.getNormalImage());
	    removeSprite((CCSprite)resumeMenuItem.getSelectedImage());
	    resumeMenuItem.removeFromParentAndCleanup(true);

	    removeSprite((CCSprite)retryMenuItem.getNormalImage());
	    removeSprite((CCSprite)retryMenuItem.getSelectedImage());
	    retryMenuItem.removeFromParentAndCleanup(true);

	    removeSprite((CCSprite)menuMenuItem.getNormalImage());
	    removeSprite((CCSprite)menuMenuItem.getSelectedImage());
	    menuMenuItem.removeFromParentAndCleanup(true);

	    int arrayCount = board_dots_starts.size();
	    for (int i = 0; i < arrayCount; i ++) {
	        CCSprite sprite = board_dots_starts.get(i);
	        removeSprite(sprite);
	    }
	    board_dots_starts.removeAll(board_dots_starts);
	    
	    arrayCount = board_dots_ends.size();
	    for (int i = 0; i < arrayCount; i ++) {
	        CCSprite sprite = board_dots_ends.get(i);
	        removeSprite(sprite);
	    }
	    board_dots_ends.removeAll(board_dots_ends);
	    
	    arrayCount = board_lines.size();
	    for (int i = 0; i < arrayCount; i ++) {
	        CCSprite sprite = board_lines.get(i);
	        sprite.stopAllActions();
	        removeSprite(sprite);
	    }
	    board_lines.removeAll(board_lines);
	    
	    player.removeFromParentAndCleanup(true);
	    
	    tex = streak.texture();
	    CCTextureCache.sharedTextureCache().removeTexture(tex);
	    streak.removeFromParentAndCleanup(true);

	    removeSprite(drawHere);
	    removeSprite(tryAgain);
	    removeSprite(keepOn);
	    removeSprite(goolLuck);
	    hand.stopAllActions();
	    removeSprite(hand);
	}
	
	@Override
	public void onExit() {
		removeCache();
		super.onExit();
	}
}




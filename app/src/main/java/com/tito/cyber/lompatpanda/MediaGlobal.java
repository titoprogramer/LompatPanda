package com.tito.cyber.lompatpanda;

import org.cocos2d.nodes.CCDirector;

import android.media.MediaPlayer;

public class MediaGlobal {
	
	private MediaPlayer media = null;
	private static MediaGlobal _global = null;

	public static MediaGlobal _shared() {
		// TODO Auto-generated constructor stub
		if( _global == null )
			_global = new MediaGlobal();
		return _global;
	}
	
	public void playMusic( int resId, boolean loop ) {
		media = MediaPlayer.create(CCDirector.sharedDirector().getActivity(), resId);
		if( loop )
			media.setLooping(true);
		media.setVolume(3, 3);
		media.start();
	}
	
	public void stopMusic() {
		if( media == null )
			return;
		media.stop();
		media.release();
		media = null;
	}
	
	public void pauseMusic() {
		if( media == null )
			return;
		media.pause();
	}
	
	public void resumeMusic() {
		if( media == null )
			return;
		media.start();
	}
	
	public void setMute( boolean mute ) {
		if( mute )
			media.setVolume(0, 0);
		else
			media.setVolume(3, 3);
	}

}

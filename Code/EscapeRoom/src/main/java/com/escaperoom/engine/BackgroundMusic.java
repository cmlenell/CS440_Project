package com.escaperoom.engine;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class BackgroundMusic
{
	public Clip c;
	double gain = 0.1d; // 0 - 1 range
	long clipTime = 0;
	
	public void playMusic(File soundFile) 
	{
		// This method plays music on loop - the game's background music
		try 
		{
			AudioInputStream music = AudioSystem.getAudioInputStream(soundFile);
			c = AudioSystem.getClip();
			c.open(music);
			
			//volume
			FloatControl gainer = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
			float decibal = (float) (Math.log(gain) / Math.log(10) * 20);
			gainer.setValue(decibal);
			
			c.loop(Clip.LOOP_CONTINUOUSLY);
			c.setMicrosecondPosition(clipTime);
			c.start();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
			
	}
	public void pause()
	{
		setClipTime(c.getMicrosecondPosition());
		c.stop();
	}

	public void setGain(double d)
	{
		if(d < 0.0)
			gain = 0;
		if(d > 100.0)
			gain = 100.0;
		else
			gain = d / 100.0;
	}
	public double getGain()
	{
		return gain;
	}
	public void setClipTime(long t)
	{
		clipTime = t;
	}
	public long getClipTime()
	{
		return clipTime;
	}
}

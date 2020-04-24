package com.escaperoom.engine;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class bgmTest 
{
	BackgroundMusic b; 
	
	@BeforeEach
	void init() 
	{
		b =  new BackgroundMusic();
	}
	
	@Test
	void testInit()
	{
		assertEquals("com.escaperoom.engine.BackgroundMusic", b.getClass().getName(), "test 1 fail");
	}
	@Test
	void testSetGain1()
	{
		b.setGain(50.0);
		assertEquals(0.50, b.getGain(), "test 2 fail: did not change properly");
	}
	@Test 
	void testSetGain2()
	{
		b.setGain(-10.0);
		assertEquals(0.0, b.getGain(), "test 3 fail: does not check for values < 0");
	}
	@Test
	void testSetGain3()
	{
		b.setGain(130.0);
		assertEquals(1.0, b.getGain(), "test 4 fail: does not check if over 100");
	}
	/*@Test
	void testPause()
	{
		b.setGain(0);
		b.playMusic(new File("src\\main\\resources\\doom.wav"));
		try // wait some time then pause
		{
			TimeUnit.SECONDS.sleep(5);
		} 
		catch (InterruptedException e) 
		{	
			e.printStackTrace();
		}
		b.pause();
		assertEquals(5, (int)b.getClipTime(), "test 5 fails: Does not pause properly");
	}*/

}

package com.escaperoom.engine;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {

	public static void playSound(File soundFile) {
		// This method is just to be used for pickup item sound effect to warn the
		// player they have the item now
		Clip clip = null;
		AudioInputStream sound = null;
		try {
			sound = AudioSystem.getAudioInputStream(soundFile);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			clip = AudioSystem.getClip();
			clip.open(sound);
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
		assert clip != null;
		clip.setFramePosition(0);
		clip.start();
	}
}

package com.terminal.engine.sfx;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundClip {

	private Clip clip;
	private FloatControl gainControl;
	
	
	public SoundClip(String path) {
		try {
			InputStream audioSource = SoundClip.class.getResourceAsStream(path);
			InputStream bufferedIn = new BufferedInputStream(audioSource);
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(bufferedIn);
			AudioFormat baseFormat = audioInput.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(), 16,
					baseFormat.getChannels(), baseFormat.getChannels() *2,
					baseFormat.getSampleRate(), false);
			AudioInputStream decoded = AudioSystem.getAudioInputStream(decodeFormat, audioInput);
			clip = AudioSystem.getClip();
			clip.open(decoded);
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void play() {
		if (clip == null) return;
		stop();
		clip.setFramePosition(0);
		while(!clip.isRunning()) {
			clip.start();
		}
	}

	public void stop() {
		if (clip == null) return;
		if (clip.isRunning()) {
			clip.stop();
		}
	}
	
	public void close() {
		stop();
		clip.drain();
		clip.close();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		play();
	}
	
	public void setVolume(float volume) {
		gainControl.setValue(volume);
	}
	
	public boolean isRunning() {
		return clip.isRunning();
	}
	
}

package bomb.main;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class Sound {
	public static final int BG_MENU=0;
	public static final int BG_GAME=1;
	
	public static final int ACT_SETBOMB=0;
	public static final int ACT_BLOWUP=1;

	private URL mus_bg_menu;
	private URL mus_bg_game;
	private URL mus_act_setbomb;
	private URL mus_act_blowup;
	
	private AudioClip audio_bg_menu;
	private AudioClip audio_bg_game;
	private AudioClip audio_act_setbomb;
	private AudioClip audio_act_blowup;
	
	public Sound(){
		loadMusic();
	}
	
	private void loadMusic(){
		//background music
		mus_bg_menu=this.getClass().getResource("/sound/bg/menu.wav");
		mus_bg_game=this.getClass().getResource("/sound/bg/game.wav");
		audio_bg_menu=Applet.newAudioClip(mus_bg_menu);
		audio_bg_game=Applet.newAudioClip(mus_bg_game);
		
		//action music
		mus_act_setbomb=this.getClass().getResource("/sound/act/setbomb.wav");
		mus_act_blowup=this.getClass().getResource("/sound/act/blowup.wav");
		audio_act_setbomb=Applet.newAudioClip(mus_act_setbomb);
		audio_act_blowup=Applet.newAudioClip(mus_act_blowup);
	}
	public void stop(){
		if(audio_bg_menu!=null)audio_bg_menu.stop();
		if(audio_bg_game!=null)audio_bg_game.stop();
		if(audio_act_setbomb!=null)audio_act_setbomb.stop();
		if(audio_act_blowup!=null)audio_act_blowup.stop();
	}
	public void playBackground(int bgMusic){
		switch (bgMusic) {
			case BG_MENU:audio_bg_menu.loop();break;
			case BG_GAME:audio_bg_game.loop();break;
			default:break;
		}
	}
	
	public void playAct(int actMusic){
		switch (actMusic) {
			case ACT_SETBOMB:audio_act_setbomb.play();break;
			case ACT_BLOWUP:audio_act_blowup.play();break;
			default:break;
		}
	}
	
}

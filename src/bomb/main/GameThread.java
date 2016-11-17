package bomb.main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import bomb.game.BombPool;
import bomb.game.ItemPool;
import bomb.game.Map;
import bomb.game.Painter_Game;
import bomb.game.Player;
import bomb.game.PlayerPool;
import bomb.menu.Data_Menu;
import bomb.menu.Painter_Menu;


public class GameThread implements Runnable{
	Map map;
	GUI gui;
	Sound sound;
	Thread tMenu;
	Thread tGame;
	Thread tBombPool;
	Thread tItemPool;
	Painter_Menu pMenu;
	Painter_Game pGame;
	Data_Menu dMenu;
	
	private boolean isRunning;
	PlayerPool playerPool;
	BombPool bombPool;
	ItemPool itemPool;
	KeyListener lis_menu,lis_game;
	
	public GameThread(){
		//initial
		gui=new GUI();
		initListener();
		initSound();
		startMenu();
	}
	
	private void initSound() {
		sound=new Sound();
	}

	private void startMenu(){
		//sound
		sound.stop();
		sound.playBackground(Sound.BG_MENU);
		
		//set menu data
		dMenu=new Data_Menu();
		
		//set menu painter
		pMenu=new Painter_Menu();
		pMenu.setDataMenu(dMenu);
		gui.frame.add(pMenu);
		gui.frame.addKeyListener(lis_menu);
		gui.frame.setVisible(true);
		
		//remove game painter
		if(pGame!=null)gui.frame.remove(pGame);
		gui.frame.removeKeyListener(lis_game);
		

		
		//start thread
		tMenu=new Thread(pMenu);
		tMenu.start();
	}
	public void startGame(int mapMode,int playerMode){
		//sound
		sound.stop();
		sound.playBackground(Sound.BG_GAME);
		
		//new game painter
		pGame=new Painter_Game();
		gui.frame.add(pGame);
		gui.frame.addKeyListener(lis_game);
		
		//remove menu painter
		gui.frame.remove(pMenu);
		gui.frame.removeKeyListener(lis_menu);
		
		//show
		gui.frame.setVisible(true);
		
		//set Map
		map=new Map(mapMode);
		pGame.setMapBlock(map.getBlock());
		
		//set Player pool
		playerPool=new PlayerPool();
		playerPool.setMap(map);
		
		//set Item pool
		itemPool=new ItemPool();
		itemPool.setPlayerPool(playerPool);
		if(mapMode==Map.MODE_MODE3){
			for (int i = 9; i <=10; i++)for (int j = 9; j <=10; j++)itemPool.createItem(i, j, ItemPool.ITEM_STAR);
			itemPool.createItem(9, 7, ItemPool.ITEM_POISON);
			itemPool.createItem(10, 7, ItemPool.ITEM_POISON);
			itemPool.createItem(7, 9, ItemPool.ITEM_POISON);
			itemPool.createItem(7, 10, ItemPool.ITEM_POISON);
			itemPool.createItem(12, 9, ItemPool.ITEM_POISON);
			itemPool.createItem(12, 10, ItemPool.ITEM_POISON);
			itemPool.createItem(9, 12, ItemPool.ITEM_POISON);
			itemPool.createItem(10, 12, ItemPool.ITEM_POISON);
		}
		
		
		
		
		
		//set Bomb pool
		bombPool=new BombPool();
		bombPool.setMap(map);
		bombPool.setPlayerPool(playerPool);
		bombPool.setItemPool(itemPool);
		bombPool.setSound(sound);
		playerPool.setBombPool(bombPool);
		
		

		
		
		//set GamePainter
		pGame.setPlayerPool(playerPool);
		pGame.setBombPool(bombPool);
		pGame.setItemPool(itemPool);
		
		
		//creat player
		playerPool.creatPlayer(playerMode);
		if (mapMode==Map.MODE_MODE2) {
			for (int i = 0; i < 4; i++) {
				Player player=playerPool.getPlayer(i);
				if(player==null)continue;
				player.incBombMax();player.incFireMax();player.incSpeedMax();
			}
		}
		
		//start thread
		tGame=new Thread(pGame);
		tGame.start();
		tBombPool=new Thread(bombPool);
		tBombPool.start();
		tItemPool=new Thread(itemPool);
		tItemPool.start();
		
		
		//start GameThread
		isRunning=true;
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		
		while (isRunning) {
			int counter=0;
			int winner=-1;
			for (int i = 0; i < 4; i++) {
				Player player=playerPool.getPlayer(i);
				if(player!=null&&player.getAlive()){
					counter++;
					winner=i;
				}
			}
			if (counter<=1) {
				endGame(winner);
			}
			try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	private void endGame(int winner){
		String message="";
		switch (winner) {
		case -1:message="平手 !";break;
		case -0:message="1P獲勝 !";break;
		case 1:message="2P獲勝 !";break;
		case 2:message="3P獲勝 !";break;
		case 3:message="4P獲勝 !";break;
		default:break;
		}
		JOptionPane.showMessageDialog(pGame, message);
		isRunning=false;
		bombPool.stop();
		itemPool.stop();
		playerPool.stop();
		pGame.exit();
		startMenu();
	}
	
	
	
	public void initListener(){
		lis_menu=new KeyListener() {
			@Override public void keyTyped(KeyEvent e) {}
			@Override public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				//System.out.println(e.getKeyCode());
				switch (e.getKeyCode()) {
				case 38://up
					dMenu.pressUP();break;
				case 40://down
					dMenu.pressDown();break;
				case 10://enter
					int customData[]=dMenu.pressENTER();
					if (customData==null)break;
					if (customData[0]==Data_Menu.PAGE_GAME) {
						pMenu.exit();
						changeToGame(customData[1],customData[2]);
					}else if (customData[0]==Data_Menu.PAGE_EXIT) {
						pMenu.exit();
						exitProgram();
					}
					break;
				case 27://esc
					dMenu.pressESC();
				case 37:// <-
					dMenu.pressESC();
				default:break;
				}
			}
		};
		lis_game=new KeyListener() {
			@Override public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {

				//System.out.println(e.getKeyCode());
				switch (e.getKeyCode()) {
				case 37:playerPool.setKeyPress(PlayerPool.PLAYER_1,Player.DIRECT_LEFT, true);break;//LEFT
				case 38:playerPool.setKeyPress(PlayerPool.PLAYER_1,Player.DIRECT_UP, true);break;//UP
				case 39:playerPool.setKeyPress(PlayerPool.PLAYER_1,Player.DIRECT_RIGHT, true);break;//RIGHT
				case 40:playerPool.setKeyPress(PlayerPool.PLAYER_1,Player.DIRECT_DOWN, true);break;//DOWN
				case 68:playerPool.setKeyPress(PlayerPool.PLAYER_2,Player.DIRECT_LEFT, true);break;//LEFT
				case 82:playerPool.setKeyPress(PlayerPool.PLAYER_2,Player.DIRECT_UP, true);break;//UP
				case 71:playerPool.setKeyPress(PlayerPool.PLAYER_2,Player.DIRECT_RIGHT, true);break;//RIGHT
				case 70:playerPool.setKeyPress(PlayerPool.PLAYER_2,Player.DIRECT_DOWN, true);break;//DOWN
				
				case 47:
					Player player1=playerPool.getPlayer(PlayerPool.PLAYER_1);
					if(player1.getAlive()){
						bombPool.crateBomb(PlayerPool.PLAYER_1, player1.getFire(), player1.getPosition());
					}
					break;//player1 pub bomb
				case 90:					
					Player player2=playerPool.getPlayer(PlayerPool.PLAYER_2);
					if(player2.getAlive()){
						bombPool.crateBomb(PlayerPool.PLAYER_2, player2.getFire(), player2.getPosition());
					}
					break;//player2 pub bomb
				case 27:endGame(-1);
				default:break;
				}
			}
			@Override 
			public void keyReleased(KeyEvent e) { 
				//System.out.println(e.getKeyCode());
				switch (e.getKeyCode()) {
				case 37:playerPool.setKeyPress(PlayerPool.PLAYER_1,Player.DIRECT_LEFT, false);break;//LEFT
				case 38:playerPool.setKeyPress(PlayerPool.PLAYER_1,Player.DIRECT_UP, false);break;//UP
				case 39:playerPool.setKeyPress(PlayerPool.PLAYER_1,Player.DIRECT_RIGHT, false);break;//RIGHT
				case 40:playerPool.setKeyPress(PlayerPool.PLAYER_1,Player.DIRECT_DOWN, false);break;//DOWN
				case 68:playerPool.setKeyPress(PlayerPool.PLAYER_2,Player.DIRECT_LEFT, false);break;//LEFT
				case 82:playerPool.setKeyPress(PlayerPool.PLAYER_2,Player.DIRECT_UP, false);break;//UP
				case 71:playerPool.setKeyPress(PlayerPool.PLAYER_2,Player.DIRECT_RIGHT, false);break;//RIGHT
				case 70:playerPool.setKeyPress(PlayerPool.PLAYER_2,Player.DIRECT_DOWN, false);break;//DOWN
				default:break;
				}
			}
		};
	}
	private void changeToGame(int mapMode,int palyerMode) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {Thread.sleep(300);} 
				catch (InterruptedException e) {e.printStackTrace();}
				startGame(mapMode,palyerMode);
			}
			
		}).start();
	}
	private void exitProgram(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {Thread.sleep(400);} 
				catch (InterruptedException e) {e.printStackTrace();}
				sound.stop();
				System.exit(0);
			}
		}).start();
	}



}

package bomb.game;
import java.util.Random;

import bomb.menu.Data_Menu;


public class PlayerPool {
	public static final int PLAYER_1=0;
	public static final int PLAYER_2=1;
	public static final int PLAYER_3=2;
	public static final int PLAYER_4=3;
	
	private BombPool bombPool;
	private Player player[]=new Player[4];
	private Map map;
	
	
	public void stop(){
		for (int i = 0; i < 4; i++) {
			Player player=getPlayer(i);
			if (player!=null)player.stop();
		}
	}
	public void setBombPool(BombPool bombPool){this.bombPool=bombPool;}
	public void setMap(Map map){this.map=map;}
	public Player getPlayer(int PLAYER){return player[PLAYER];}
	public void setKeyPress(int PLAYER,int key,boolean isPress){
		player[PLAYER].setKeyPress(key, isPress);
	}
	public void creatPlayer(int Mode){
		creatPlayer(PlayerPool.PLAYER_1, 18, 18);
		creatPlayer(PlayerPool.PLAYER_2, 1, 1);
		switch (Mode) {
		case Data_Menu.PLAYER_2P:break;
		case Data_Menu.PLAYER_3P:
			creatAI(PLAYER_3, 18, 1);
			
		break;
		case Data_Menu.PLAYER_4P:
			creatAI(PLAYER_3, 18, 1);
			creatAI(PLAYER_4, 1, 18);
		break;
		default:break;
		}
	}
	public void creatPlayer(int PLAYER,int blockY,int blockX){
		if(player[PLAYER]==null){
			player[PLAYER]=new Player(blockY, blockX);
			player[PLAYER].setMap(map);
		}
	}
	public void creatAI(int PLAYER,int blockY,int blockX){
		if(player[PLAYER]==null){
			player[PLAYER]=new Player(blockY, blockX);
			player[PLAYER].setMap(map);
			creatAIThread(PLAYER,player[PLAYER]);
		}
	}
	private void  creatAIThread(int num,Player player) {
		Random random=new Random();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (player.getAlive()) {
					for (int i = 0; i < 4; i++)player.setKeyPress(i, false);
					int position[]=player.getPosition();
					int action=random.nextInt(10);
					if(action==0)bombPool.crateBomb(num, player.getFire(), position);
					int direction=-1;
					int bY=(position[1]+16)/32;
					int bX=(position[0]+16)/32;
					do {
						action=random.nextInt(4);
						switch (action) {
						case Player.DIRECT_UP:if(map.getBlock()[bY-1][bX]==Map.BLOCK_SPACE)direction=Player.DIRECT_UP;break;
						case Player.DIRECT_DOWN:if(map.getBlock()[bY+1][bX]==Map.BLOCK_SPACE)direction=Player.DIRECT_DOWN;break;
						case Player.DIRECT_RIGHT:if(map.getBlock()[bY][bX+1]==Map.BLOCK_SPACE)direction=Player.DIRECT_RIGHT;break;
						case Player.DIRECT_LEFT:if(map.getBlock()[bY][bX-1]==Map.BLOCK_SPACE)direction=Player.DIRECT_LEFT;break;
						default:break;
						}
					} while (direction==-1);
					player.setKeyPress(direction, true);
					
/*					switch (action) {
						case 0:bombPool.crateBomb(num, player.getFire(), player.getPosition());break;//bomb
						default:player.setKeyPress(random.nextInt(4), true);break;
					}*/
					try {Thread.sleep(500+random.nextInt(1000));} catch (InterruptedException e) {e.printStackTrace();}
				}
				
			}
		}).start();
		

	}
}

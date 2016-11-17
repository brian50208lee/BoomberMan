package bomb.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JComponent;

import bomb.main.Counter;

public class Painter_Game extends JComponent implements Runnable{
	
	public static final int STATE_START=0;
	public static final int STATE_MAIN=1;	
	public static final int STATE_EXIT=2;
	
	
	public final int FPS = 40;
	public boolean isRunning=true;
	private int state=0;
	private Counter counter_dark;
	private Counter counter_palyer;
	private Counter counter_bomb;
	private Counter counter_item;
	
	
	private Image im_bg_bg1;
	private Image im_anim_start_dark;
	private Image im_anim_exit_dark;
	private Image im_map_space;
	private Image im_map_box;
	private Image im_map_firebox;
	private Image im_map_stone;
	private Image im_map_bomb;
	private Image im_map_fire;
	private Image im_item_speed;
	private Image im_item_bomb;
	private Image im_item_fire;
	private Image im_item_poison;
	private Image im_item_star;
	private Image im_player[]=new Image[4];
	private Image im_playername[]=new Image[4];
	private Image im_playertomb;
	private Image im_bomb;
	
	private int block[][];//y,x
	private PlayerPool playerPool;
	private BombPool bombPool;
	private ItemPool itemPool;
	
	public Painter_Game(){
		loadIMG();
	}
	
	
	
	public void setPlayerPool(PlayerPool playerPool){this.playerPool=playerPool;}
	public void setBombPool(BombPool bombPool){this.bombPool=bombPool;}
	public void setItemPool(ItemPool itemPool){this.itemPool=itemPool;}
	public void setMapBlock(int block[][]){this.block=block;}
	public void updatePainter(){repaint();}
	
	@Override
	public void run() {
		start();
		while (isRunning) {
			try {
				repaint();
				Thread.sleep(1000/FPS);
			} 
			catch (InterruptedException e) {System.out.println(e);}
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		mainAnim(g);
		switch (state) {
		case STATE_START:startAnim(g);break;
		case STATE_MAIN:break;
		case STATE_EXIT:exitAnim(g);break;
		default:break;
		}


	}
	public void loadIMG(){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		im_bg_bg1=toolkit.createImage(ClassLoader.getSystemResource("img/game/background/bg1.png"));
		im_anim_start_dark=toolkit.createImage(ClassLoader.getSystemResource("img/menu/anim/start/dark.png"));
		im_anim_exit_dark=toolkit.createImage(ClassLoader.getSystemResource("img/menu/anim/exit/dark.png"));
		im_map_space=toolkit.createImage(ClassLoader.getSystemResource("img/game/map/space.png"));
		im_map_box=toolkit.createImage(ClassLoader.getSystemResource("img/game/map/box.png"));
		im_map_firebox=toolkit.createImage(ClassLoader.getSystemResource("img/game/map/firebox.png"));
		im_map_stone=toolkit.createImage(ClassLoader.getSystemResource("img/game/map/stone.png"));
		im_map_bomb=toolkit.createImage(ClassLoader.getSystemResource("img/game/map/obstacle.png"));
		im_map_fire=toolkit.createImage(ClassLoader.getSystemResource("img/game/map/fire.png"));
		im_item_speed=toolkit.createImage(ClassLoader.getSystemResource("img/game/item/speed.png"));
		im_item_bomb=toolkit.createImage(ClassLoader.getSystemResource("img/game/item/bomb.png"));
		im_item_fire=toolkit.createImage(ClassLoader.getSystemResource("img/game/item/fire.png"));
		im_item_poison=toolkit.createImage(ClassLoader.getSystemResource("img/game/item/poison.png"));
		im_item_star=toolkit.createImage(ClassLoader.getSystemResource("img/game/item/star.png"));
		im_player[0]=toolkit.createImage(ClassLoader.getSystemResource("img/game/player/player1.png"));
		im_player[1]=toolkit.createImage(ClassLoader.getSystemResource("img/game/player/player2.png"));
		im_player[2]=toolkit.createImage(ClassLoader.getSystemResource("img/game/player/player3.png"));
		im_player[3]=toolkit.createImage(ClassLoader.getSystemResource("img/game/player/player4.png"));
		im_playername[0]=toolkit.createImage(ClassLoader.getSystemResource("img/game/player/1p.png"));
		im_playername[1]=toolkit.createImage(ClassLoader.getSystemResource("img/game/player/2p.png"));
		im_playername[2]=toolkit.createImage(ClassLoader.getSystemResource("img/game/player/3p.png"));
		im_playername[3]=toolkit.createImage(ClassLoader.getSystemResource("img/game/player/4p.png"));
		im_playertomb=toolkit.createImage(ClassLoader.getSystemResource("img/game/player/tomb.png"));
		im_bomb=toolkit.createImage(ClassLoader.getSystemResource("img/game/bomb/bomb1.png"));
		
		
		/*		
  		try {
			im_bg_bg1=ImageIO.read(new File("img/game/background/bg1.png"));
			im_anim_start_dark=ImageIO.read(new File("img/menu/anim/start/dark.png"));
			im_anim_exit_dark=ImageIO.read(new File("img/menu/anim/exit/dark.png"));
			im_map_space=ImageIO.read(new File("img/game/map/space.png"));
			im_map_box=ImageIO.read(new File("img/game/map/box.png"));
			im_map_firebox=ImageIO.read(new File("img/game/map/firebox.png"));
			im_map_stone=ImageIO.read(new File("img/game/map/stone.png"));
			im_map_bomb=ImageIO.read(new File("img/game/map/obstacle.png"));
			im_map_fire=ImageIO.read(new File("img/game/map/fire.png"));
			im_item_speed=ImageIO.read(new File("img/game/item/speed.png"));
			im_item_bomb=ImageIO.read(new File("img/game/item/bomb.png"));
			im_item_fire=ImageIO.read(new File("img/game/item/fire.png"));
			im_player[0]=ImageIO.read(new File("img/game/player/player1.png"));
			im_player[1]=ImageIO.read(new File("img/game/player/player2.png"));
			im_player[2]=ImageIO.read(new File("img/game/player/player3.png"));
			im_player[3]=ImageIO.read(new File("img/game/player/player4.png"));
			im_playername[0]=ImageIO.read(new File("img/game/player/1p.png"));
			im_playername[1]=ImageIO.read(new File("img/game/player/2p.png"));
			im_playername[2]=ImageIO.read(new File("img/game/player/3p.png"));
			im_playername[3]=ImageIO.read(new File("img/game/player/4p.png"));
			im_playertomb=ImageIO.read(new File("img/game/player/tomb.png"));
			im_bomb=ImageIO.read(new File("img/game/bomb/bomb1.png"));
		} 
		catch (IOException e) {e.printStackTrace();}
		*/
	}
	
	private void startAnim(Graphics g){
		if(counter_dark==null)return;
		int step=counter_dark.getStep();
		if (step!=-1) {
			g.drawImage(im_anim_start_dark,		0, 		0,		640,	640,
					0,		0+step*100, 		100, 	100+	step*100, 	null);
		}else {
			state=STATE_MAIN;
		}
	}
	
	private void mainAnim(Graphics g){
		drawBackground(g);
		drawBomb(g);
		drawItem(g);
		drawPlayer(g);
	}
	private void drawItem(Graphics g) {
		if(counter_item==null||itemPool==null||itemPool.isEmpty())return;
		
		int size=itemPool.getPoolSize();
		Item item;
		Image image=null;
		int bPosition[];
		for (int i = 0; i < size; i++) {
			item=itemPool.getItem(i);
			bPosition=item.getBPosition();
			switch (item.getType()) {
				case ItemPool.ITEM_BOMB:image=im_item_bomb;break;
				case ItemPool.ITEM_FIRE:image=im_item_fire;break;
				case ItemPool.ITEM_SPEED:image=im_item_speed;break;
				case ItemPool.ITEM_POISON:image=im_item_poison;break;
				case ItemPool.ITEM_STAR:image=im_item_star;break;
				default:break;
			}
			g.drawImage(image,			bPosition[1]*32, 				bPosition[0]*32-10,					
										bPosition[1]*32+32,				bPosition[0]*32+32,
										counter_item.getStep()*32,		0, 		
										32+counter_item.getStep()*32,	32,
										null);
		}
	}
	
	private void drawBomb(Graphics g) {
		if(counter_bomb==null||bombPool==null||bombPool.isEmpty())return;
		
		int size=bombPool.getPoolSize();
		Bomb bomb;
		int bPosition[];
		for (int i = 0; i < size; i++) {
			bomb=bombPool.getBomb(i);
			bPosition=bomb.getBPosition();
			g.drawImage(im_bomb,		bPosition[1]*32, 				bPosition[0]*32,					
										bPosition[1]*32+32,				bPosition[0]*32+32,
										counter_bomb.getStep()*32,		0, 		
										32+counter_bomb.getStep()*32,	32,
										null);
		}
	}

	private void drawPlayer(Graphics g) {
		if(counter_palyer==null)return;
		Image image=im_playertomb;
		for (int i = 0; i < 4; i++) {
			Player player=playerPool.getPlayer(i);
			if(player==null)continue;
			if(player.getAlive())continue;
			int position1[]=player.getPosition();
			g.drawImage(image,			position1[0], 					position1[1]-10,					
										position1[0]+32,				position1[1]+32,
										counter_palyer.getStep()*32,	player.getDirect()*32, 		
										32+counter_palyer.getStep()*32,	32+player.getDirect()*32,
										null);
			g.drawImage(im_playername[i],		position1[0]+6, 					position1[1]-10-10,					
												position1[0]+26,				position1[1]-2-10,
												0,0,32,16,null);
		}
		for (int i = 0; i < 4; i++) {
			Player player=playerPool.getPlayer(i);
			if(player==null)continue;
			if(!player.getAlive())continue;
			int position1[]=player.getPosition();
			image=im_player[i];
			g.drawImage(image,			position1[0], 					position1[1]-10,					
										position1[0]+32,				position1[1]+32,
										counter_palyer.getStep()*32,	player.getDirect()*32, 		
										32+counter_palyer.getStep()*32,	32+player.getDirect()*32,
										null);
			g.drawImage(im_playername[i],		position1[0]+6, 					position1[1]-10-10,					
												position1[0]+26,				position1[1]-2-10,
												0,0,32,16,null);
		}
		
		
		
		
	}

	private void drawBackground(Graphics g) {
		if(block==null||counter_bomb==null)return;
		g.drawImage(im_bg_bg1,		0, 		0,		640,	640,0,		0, 		640, 	640, 	null);
		
		Image image = null;
		for (int y = 0; y < block[0].length; y++) {
			for (int x = 0; x < block.length; x++) {
				int img_dx=0;
				switch (block[y][x]) {
				case Map.BLOCK_SPACE:image=im_map_space;break;
				case Map.BLOCK_BOX:image=im_map_box;break;
				case Map.BLOCK_FIREBOX:image=im_map_firebox;break;
				case Map.BLOCK_STONE:image=im_map_stone;break;
				case Map.BLOCK_BOMB:image=im_map_bomb;break;
				case Map.BLOCK_FIRE:image=im_map_fire;img_dx=counter_bomb.getStep();break;
				default:break;
				}
				g.drawImage(image,	x*32, 		y*32,		x*32+32,	y*32+32,0+img_dx*32,		0, 		32+img_dx*32, 	32, 	null);
			}
		}
	}
	
	private void exitAnim(Graphics g){
		if(counter_dark==null)return;
		int step=counter_dark.getStep();
		if (step!=-1) {
			g.drawImage(im_anim_exit_dark,		0, 		0,		640,	640,
					0,		0+step*100, 		100, 	100+	step*100, 	null);
		}else {
			isRunning=false;
		}
	}
	
	public void start(){
		//start anim counter
		counter_dark=new Counter(20,8,false);
		new Thread(counter_dark).start();
		state=STATE_START;
		
		//player anim counter
		counter_palyer=new Counter(4,4,true);
		new Thread(counter_palyer).start();
		
		//bomb counter
		counter_bomb=new Counter(8,8,true);
		new Thread(counter_bomb).start();
		
		//item counter
		counter_item=new Counter(4,4,true);
		new Thread(counter_item).start();
		
	}
	public void exit(){
		counter_dark=new Counter(20,8,false);
		new Thread(counter_dark).start();
		state=STATE_EXIT;
	}
}

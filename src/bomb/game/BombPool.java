package bomb.game;
import bomb.main.Sound;

import java.util.ArrayList;



public class BombPool implements Runnable{
	
	private final int FIREMAP_NONE=0;
	private final int FIREMAP_FIRE=1;
	private final int FIREMAP_FIREBOX=2;
	
	private PlayerPool playerPool;
	private ItemPool itemPool;
	private Sound sound;
	
	private boolean isRunning;
	private ArrayList<Bomb> pool=new ArrayList<Bomb>();
	private Bomb bombMap[][]=new Bomb[20][20];
	private Map map;

	public void stop(){isRunning=false;}
	public void setSound(Sound sound){this.sound= sound;}
	public void setPlayerPool(PlayerPool playerPool){this.playerPool=playerPool;}
	public void setItemPool(ItemPool itemPool){this.itemPool=itemPool;}
	public void setMap(Map map){this.map=map;}
	public boolean isEmpty(){return pool.isEmpty();}
	public int getPoolSize(){return pool.size();}
	public void crateBomb(int master,int fire,int Position[]){
		if (playerPool.getPlayer(master).getBomb()==0)return;
		int bPosition[]={(Position[1]+16)/32,(Position[0]+16)/32};
		if (bombMap[bPosition[0]][bPosition[1]]!=null)return;
		
		//sound
		sound.playAct(Sound.ACT_SETBOMB);
		
		//create bomb
		Bomb bomb=new Bomb(master, fire, bPosition);
		pool.add(bomb);
		playerPool.getPlayer(master).decBomb();
		int block[][]=map.getBlock();
		block[bPosition[0]][bPosition[1]]=Map.BLOCK_BOMB;
		bombMap[bPosition[0]][bPosition[1]]=bomb;
		
		bomb.startCountDown();
	}
	public Bomb getBomb(int poolIndex){
		if(poolIndex<pool.size())return pool.get(poolIndex);
		else return null;
	}
	
	
	
	public BombPool(){
		isRunning=true;
		bombMap=new Bomb[20][20];
	}
	
	@Override
	public void run() {
		while (isRunning) {
			try {Thread.sleep(200);} 
			catch (InterruptedException e) {e.printStackTrace();}
			
			if (!pool.isEmpty()) {
				boolean keepCheck;
				int fireMap[][]=null;
				do {
					keepCheck=false;
					for (int i = 0; i < pool.size(); i++) {
						Bomb bomb=pool.get(i);
						if (!bomb.getAlive()){
							removeBomb(bomb, i);
							if(fireMap==null)fireMap=new int[20][20];
							setFireMap(bomb,fireMap);
							keepCheck=true;
						}
						else i++;
					}
				} while (keepCheck);
				if (fireMap!=null) {
					//printMap(fireMap);
					blowUp(fireMap);
				}
			}
			
		}
		
	}
	private void blowUp(int fireMap[][]) {
		//sound
		sound.playAct(Sound.ACT_BLOWUP);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				int mapBlock[][]=map.getBlock();
				for (int k = 0; k < 5; k++) {
					for (int i = 0; i < 4; i++) {
						Player player=playerPool.getPlayer(i);
						if (player==null)continue;
						if (!player.getAlive())continue;
						boolean b1=false,b2=false,b3=false,b4=false;
						int bX1=(player.getPosition()[0]+16-5)/32;//left
						int bY1=(player.getPosition()[1]+16  )/32;
						int bX2=(player.getPosition()[0]+16+5)/32;//right
						int bY2=(player.getPosition()[1]+16  )/32;
						int bX3=(player.getPosition()[0]+16  )/32;//bottom
						int bY3=(player.getPosition()[1]+16+8)/32;
						int bX4=(player.getPosition()[0]+16  )/32;//top
						int bY4=(player.getPosition()[1]+16-0)/32;
						b1=fireMap[bY1][bX1]==FIREMAP_FIRE;
						b2=fireMap[bY2][bX2]==FIREMAP_FIRE;
						b3=fireMap[bY3][bX3]==FIREMAP_FIRE;
						b4=fireMap[bY4][bX4]==FIREMAP_FIRE;
						if(b1&&b2&&b3&&b4)player.die();
					}
					for (int i = 0; i < 20; i++) {
						for (int j = 0; j < 20; j++) {
							switch (fireMap[i][j]) {
							case FIREMAP_NONE:break;
							case FIREMAP_FIRE:mapBlock[i][j]=Map.BLOCK_FIRE;break;
							case FIREMAP_FIREBOX:mapBlock[i][j]=Map.BLOCK_FIREBOX;break;
							default:break;
							}
						}
					}
					try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
				}
				for (int i = 0; i < 20; i++) {
					for (int j = 0; j < 20; j++) {
						switch (fireMap[i][j]) {
						case FIREMAP_NONE:break;
						case FIREMAP_FIRE:mapBlock[i][j]=Map.BLOCK_SPACE;break;
						case FIREMAP_FIREBOX:
							mapBlock[i][j]=Map.BLOCK_SPACE;
							itemPool.createItem( i, j);
							break;
						default:break;
						}
					}
				}
				
			}
		}).start();
		
	}

	private void printMap(int map[][]) {
		for (int i = 0; i <20; i++) {
			System.out.print("[ ");
			for (int j = 0; j < 20; j++) {
				System.out.print(map[i][j]+" ");
			}
			System.out.println("]");
		}
	}
	
	
	private void setFireMap(Bomb bomb,int fireMap[][]) {
		int bPosition[]=bomb.getBPosition();
		
		int fire=bomb.getFire();
		fireMap[bPosition[0]][bPosition[1]]=FIREMAP_FIRE;
		for (int i = 1; i <= fire && setFireMap2(fireMap,bPosition[0],bPosition[1]-i); i++);
		for (int i = 1; i <= fire && setFireMap2(fireMap,bPosition[0],bPosition[1]+i); i++);
		for (int i = 1; i <= fire && setFireMap2(fireMap,bPosition[0]-i,bPosition[1]); i++);
		for (int i = 1; i <= fire && setFireMap2(fireMap,bPosition[0]+i,bPosition[1]); i++);
	}
	private boolean setFireMap2(int[][] fireMap,int bY,int bX){
		int mapBlock[][]=map.getBlock();
		if (bombMap[bY][bX]!=null) {
			bombMap[bY][bX].setAlive(false);
			return true;
		}
		if(Map.canFirePass(mapBlock[bY][bX])){
			fireMap[bY][bX]=FIREMAP_FIRE;
			if (itemPool.getItem(bY, bX)!=null) {
				itemPool.removeItem(itemPool.getItem(bY, bX));
			}
			return true;
		}else{
			if(mapBlock[bY][bX]==Map.BLOCK_BOX)fireMap[bY][bX]=FIREMAP_FIREBOX;
			return false;
		}
	}
	private void removeBomb(Bomb bomb,int index){
		int bPosition[]=bomb.getBPosition();
		int block[][]=map.getBlock();
		bombMap[bPosition[0]][bPosition[1]]=null;
		block[bPosition[0]][bPosition[1]]=Map.BLOCK_SPACE;
		pool.remove(index);
		playerPool.getPlayer(bomb.getMaster()).incBomb();
	}
	

	
	
	
}

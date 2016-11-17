package bomb.game;

import java.util.ArrayList;
import java.util.Random;

public class ItemPool implements Runnable{
	public static final int ITEM_SPEED=0;
	public static final int ITEM_BOMB=1;
	public static final int ITEM_FIRE=2;
	public static final int ITEM_STAR=3;
	public static final int ITEM_POISON=4;
	
	private PlayerPool playerPool;
	private boolean isRunning;
	Item itemMap[][];
	
	ArrayList<Item> pool=new ArrayList<Item>();
	public void stop(){isRunning=false;}
	public boolean isEmpty(){return pool.isEmpty();}
	public void setPlayerPool(PlayerPool playerPool){this.playerPool=playerPool;}
	public Item getItem(int bY,int bX){return itemMap[bY][bX];}
	public ArrayList<Item> getPool(){return pool;}
	public int getPoolSize(){return pool.size();}
	public Item getItem(int poolIndex){return pool.get(poolIndex);}
	
	public ItemPool(){
		itemMap=new Item[20][20];
		isRunning=true;
	}
	public void createItem(int bY,int bX){
		int type=new Random().nextInt(10);
		if(type==ITEM_STAR)type=new Random().nextInt(3)==0?type:new Random().nextInt(10);
		if(itemMap[bY][bX]!=null || type<0 || type>4)return;
		itemMap[bY][bX]=new Item(type, bY, bX);
		pool.add(itemMap[bY][bX]);
	}
	public void createItem(int bY,int bX,int type){
		if(itemMap[bY][bX]!=null || type<0 || type>4)return;
		itemMap[bY][bX]=new Item(type, bY, bX);
		pool.add(itemMap[bY][bX]);
	}
	public void removeItem(Item item){
		int bPosition[]=item.getBPosition();
		itemMap[bPosition[0]][bPosition[1]]=null;
		pool.remove(item);
	}
	
	
	@Override
	public void run() {
		while (isRunning) {
			Player player;
			int bY,bX;
			for (int i = 0; i < 4; i++) {
				player=playerPool.getPlayer(i);
				if (player==null||!player.getAlive())continue;
				bY=(player.getPosition()[1]+16)/32;
				bX=(player.getPosition()[0]+16)/32;
				Item item=itemMap[bY][bX];
				if (item!=null) {
					switch (item.getType()) {
					case ITEM_BOMB:removeItem(item);player.incBomb();break;
					case ITEM_FIRE:removeItem(item);player.incFire();break;
					case ITEM_SPEED:removeItem(item);player.incSpeed();break;
					case ITEM_POISON:removeItem(item);player.decSpeedMin();;break;
					case ITEM_STAR:removeItem(item);player.incSpeedMax();player.incBombMax();player.incFireMax();break;
					default:break;
					}
				}
			}
			
			
			
			try {Thread.sleep(120);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
}

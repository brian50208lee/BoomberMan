package bomb.game;

import java.util.LinkedList;

import bomb.main.Counter;

public class Player {

	public static final int DIRECT_DOWN=0;
	public static final int DIRECT_LEFT=1;
	public static final int DIRECT_RIGHT=2;
	public static final int DIRECT_UP=3;

	private final int MaxBomb = 8;
	private final int MaxFire = 10;
	private final int MaxSpeed = 6;
	
	private final int InitBomb = 1;
	private final int InitFire = 2;
	private final int InitSpeed = 3;
	
	
	public void stop(){isRunning=false;}
	private boolean alive;
	private boolean isRunning;
	private boolean keyPress[]=new boolean[5];//DOWN,LEFT,RIGHT,UP,SHIFT
	private LinkedList<Integer> keyPriority=new LinkedList<Integer>();
	private Map map;
	private int position[]=new int[2];//x,y
	private int direct;
	private int speed;
	private int fire;
	private int bomb;
	
	
	public int getBomb(){return bomb;}
	public int getFire(){return fire;}
	
	public void incFire(){if(fire<MaxFire)fire++;}
	public void incBomb(){if(bomb<MaxBomb)bomb++;}
	public void incSpeed(){if(speed<MaxSpeed)speed++;}
	
	public void decFire(){if(fire>1)fire--;}
	public void decBomb(){if(bomb>0)bomb--;}
	public void decSpeed(){if(speed>1)speed--;}
	
	public void incFireMax(){fire=MaxFire;}
	public void incBombMax(){bomb=MaxBomb;}
	public void incSpeedMax(){speed=MaxSpeed;}

	public void decSpeedMin(){
		int tempSpeed=speed;
		speed=1;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {Thread.sleep(8000);} catch (InterruptedException e) {e.printStackTrace();}
				speed=tempSpeed;
			}
		}).start();
	}

	public boolean getAlive(){return alive;}
	public int[] getPosition(){return position;}
	public int getDirect(){return direct;}
	public void setMap(Map map){this.map=map;}
	
	public void setKeyPress(int key,boolean isPress){
		if(isPress){
			keyPriority.remove(keyPriority.indexOf(key));
			keyPriority.addFirst(key);
		}
		keyPress[key]=isPress;
	}
	
	
	public Player(int blockY,int blockX){
		alive=true;
		isRunning=true;
		direct=DIRECT_DOWN;
		speed=InitSpeed;fire=InitFire;bomb=InitBomb;
		position[0]=blockY*32;
		position[1]=blockX*32;
		keyPriority.add(DIRECT_DOWN);
		keyPriority.add(DIRECT_LEFT);
		keyPriority.add(DIRECT_RIGHT);
		keyPriority.add(DIRECT_UP);
		moveThread();
	}

	
	private void moveThread(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (isRunning) {
					for (int i = 0; i < 4; i++) {
						if(keyPress[keyPriority.get(i)]){
							tryMove(keyPriority.get(i));
							break;
						}
					}
					if(keyPress[4]){;}//press bomb
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {e.printStackTrace();}
				}
			}
		}).start();
	}
	
	private void tryMove(int direct){
		int block[][]=map.getBlock();
		int bx1=0,by1=0,bx2,by2;
		boolean b1,b2;
		//System.out.println(position[0]+","+position[1]);
		this.direct=direct;
		if (!alive)return;
		switch (direct) {
		case DIRECT_DOWN:
			if((position[1]+31)/32==(position[1]+speed+31)/32){
				position[1]+=speed;
				break;
			}
			bx1=(position[0]+31)/32;
			by1=(position[1]+speed+31)/32;
			bx2=position[0]/32;
			by2=by1;
			b1=Map.canPass(block[by1][bx1]);
			b2=Map.canPass(block[by2][bx2]);
			if(b1&&b2)position[1]+=speed;
			else {
				position[1]=(by1-1)*32;
				if(b1&&!b2&&(position[0]+24)/32==bx1)position[0]+=1;
				if(!b1&&b2&&(position[0]+8)/32==bx2)position[0]-=1;
			}
			break;
		case DIRECT_LEFT:
			if ((position[0])/32==(position[0]-speed)/32) {
				position[0]-=speed;
				break;
			}
			bx1=(position[0]-speed)/32;
			by1=(position[1]+31)/32;
			bx2=bx1;
			by2=position[1]/32;
			b1=Map.canPass(block[by1][bx1]);
			b2=Map.canPass(block[by2][bx2]);
			if(b1&&b2)position[0]-=speed;
			else {
				position[0]=(bx1+1)*32;
				if(b1&&!b2&&(position[1]+24)/32==by1)position[1]+=1;
				if(!b1&&b2&&(position[1]+8)/32==by2)position[1]-=1;
			}
			break;
		case DIRECT_RIGHT:
			if ((position[0]+31)/32==(position[0]+speed+31)/32) {
				position[0]+=speed;
				break;
			}
			bx1=(position[0]+speed+31)/32;
			by1=position[1]/32;
			bx2=bx1;
			by2=(position[1]+31)/32;
			b1=Map.canPass(block[by1][bx1]);
			b2=Map.canPass(block[by2][bx2]);
			if(b1&&b2)position[0]+=speed;
			else {
				position[0]=(bx1-1)*32;
				if(b1&&!b2&&(position[1]+8)/32==by1)position[1]-=1;
				if(!b1&&b2&&(position[1]+24)/32==by2)position[1]+=1;
			}

			
			break;
		case DIRECT_UP:
			if ((position[1])/32==(position[1]-speed)/32) {
				position[1]-=speed;
				break;
			}
			bx1=position[0]/32;
			by1=(position[1]-speed)/32;
			bx2=(position[0]+31)/32;
			by2=by1;
			b1=Map.canPass(block[by1][bx1]);
			b2=Map.canPass(block[by2][bx2]);
			if(b1&&b2)position[1]-=speed;
			else {
				position[1]=(by1+1)*32;
				if(b1&&!b2&&(position[0]+8)/32==bx1)position[0]-=1;
				if(!b1&&b2&&(position[0]+24)/32==bx2)position[0]+=1;
			}
			break;
		default:break;
		}
	}
	
	public void die(){
		alive=false;
	}
	
}

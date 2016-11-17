package bomb.game;


import bomb.main.Counter;

public class Bomb implements Runnable{
	private final int TIME=3;
	private boolean alive;
	private int master;
	private int fire;
	private int bPosition[];
	private Counter counter;
	
	public void setAlive(Boolean alive){this.alive=alive;}
	public int getMaster(){return master;}
	public int getFire(){return fire;}
	public int[] getBPosition(){return bPosition;}
	public boolean getAlive(){return alive;}
	public Bomb(int master,int fire,int bPosition[]) {
		this.master=master;
		this.fire=fire;
		this.bPosition=bPosition;
	}
	public void startCountDown(){
		alive=true;
		counter=new Counter(1, TIME, false);
		new Thread(counter).start();
		new Thread(this).start();
	}
	@Override
	public void run() {
		while (counter.getStep()>=0) {
			try {Thread.sleep(200);} 
			catch (InterruptedException e) {e.printStackTrace();}
		}
		alive=false;
	}
	
	

}

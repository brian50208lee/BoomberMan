package bomb.main;

public class Counter implements Runnable{
	
	private int fps;
	private int maxStep;
	private int step;
	private boolean loop;
	
	public Counter(int fps,int maxStep,boolean loop) {
		this.fps=fps;
		this.maxStep=maxStep-1;
		this.step=this.maxStep;
		this.loop=loop;
	}
	
	
	@Override
	public void run() {
		while (step>=0) {
			try {
				Thread.sleep(1000/fps);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			step--;
			if (step==-1&&loop)step=maxStep;
		}
	}
	public int getStep(){return step;}
	public void stop(){step=-1;loop=false;}
}


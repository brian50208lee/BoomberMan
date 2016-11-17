package bomb.game;


public class Item {
	private int type;
	private int bPisition[]=new int[2];
	public int getType(){return type;}
	public int[] getBPosition(){return bPisition;}
	public Item(int type,int bY,int bX) {
		this.type=type;
		bPisition[0]=bY;
		bPisition[1]=bX;
	}
}

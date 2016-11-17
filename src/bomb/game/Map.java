package bomb.game;

import java.util.Random;

public class Map {
	public static final int MODE_RANDOM=0;
	public static final int MODE_MODE1=1;
	public static final int MODE_MODE2=2;
	public static final int MODE_MODE3=3;
	
	
	public static final int BLOCK_SPACE=0;
	public static final int BLOCK_STONE=1;
	public static final int BLOCK_BOX=2;
	public static final int BLOCK_FIREBOX=3;
	public static final int BLOCK_BOMB=4;
	public static final int BLOCK_FIRE=5;
	
	public final int BLOCK_SIZE=32;
	
	private int h,w;
	private int block[][];
	

	public Map(int Mode){
		this.h=20;
		this.w=20;
		createMap(Mode);
	}

	public static boolean canFirePass(int BLOCK){
		switch (BLOCK) {
		case BLOCK_SPACE:	return true;
		case BLOCK_STONE:	return false;
		case BLOCK_BOX:		return false;
		case BLOCK_FIREBOX:	return false;
		case BLOCK_BOMB:	return true;
		case BLOCK_FIRE:	return true;
		default:			return false;
		}
	}
	public static boolean canPass(int BLOCK){
		switch (BLOCK) {
		case BLOCK_SPACE:	return true;
		case BLOCK_STONE:	return false;
		case BLOCK_BOX:		return false;
		case BLOCK_FIREBOX:	return false;
		case BLOCK_BOMB:	return false;
		case BLOCK_FIRE:	return true;
		default:			return false;
		}
	}
	
	public int getHeight(){return this.h;}
	public int getWidth(){return this.w;}
	public int[][] getBlock(){return this.block;}
	
	public void setBlock(int i,int j,int BLOCK){
		if(i<0||i>=block.length)return;
		if(j<0||j>=block[0].length)return;
		block[i][j]=BLOCK;
	}
	
	private void createMap(int mode) {
		switch (mode) {
		case MODE_RANDOM:this.block=randomMap();break;
		case MODE_MODE1:
			int block1[][]={	
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
					{1,0,0,0,0,0,2,0,0,0,0,0,1,2,2,2,2,0,0,1},
					{1,0,1,2,1,2,1,0,0,0,0,0,0,1,2,1,2,1,0,1},
					{1,2,2,2,2,2,2,0,0,0,0,0,1,2,2,2,2,2,2,1},
					{1,2,1,2,1,2,1,0,0,0,0,0,0,1,2,1,2,1,2,1},
					{1,2,2,2,2,2,2,0,0,0,0,0,1,2,2,2,2,2,2,1},
					{1,2,1,2,1,2,1,0,0,0,0,0,0,1,2,1,2,1,2,1},
					{1,2,2,2,2,2,2,0,0,0,0,0,0,2,2,2,2,2,2,1},
					{1,1,1,1,1,1,1,0,0,0,0,0,0,1,2,1,2,1,2,1},
					{1,2,2,2,2,2,2,0,0,0,0,0,0,1,1,2,1,2,1,1},
					{1,1,2,1,2,1,1,0,0,0,0,0,0,2,2,2,2,2,2,1},
					{1,2,1,2,1,2,1,0,0,0,0,0,0,1,1,1,1,1,1,1},
					{1,2,2,2,2,2,2,0,0,0,0,0,0,2,2,2,2,2,2,1},
					{1,2,1,2,1,2,1,0,0,0,0,0,0,1,2,1,2,1,2,1},
					{1,2,2,2,2,2,2,1,0,0,0,0,0,2,2,2,2,2,2,1},
					{1,2,1,2,1,2,1,0,0,0,0,0,0,1,2,1,2,1,2,1},
					{1,2,2,2,2,2,2,1,0,0,0,0,0,2,2,2,2,2,2,1},
					{1,0,1,2,1,2,1,0,0,0,0,0,0,1,2,1,2,1,0,1},
					{1,0,0,2,2,2,2,1,0,0,0,0,0,2,0,0,0,0,0,1},
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
			this.block=block1;
			break;
		case MODE_MODE2:
			int block2[][]={
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};

			this.block=block2;
			break;
		case MODE_MODE3:
			int block3[][]={
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
					{1,0,0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0,0,1},
					{1,0,1,1,1,1,1,1,1,2,2,1,1,1,1,1,1,1,0,1},
					{1,0,1,0,0,0,0,0,2,2,2,2,0,0,0,0,0,1,0,1},
					{1,0,1,0,1,2,0,0,2,2,2,2,0,0,2,1,0,1,0,1},
					{1,0,1,0,2,2,2,1,1,1,1,1,1,2,2,2,0,1,0,1},
					{1,0,1,0,0,2,0,0,2,2,2,2,0,0,2,0,0,1,0,1},
					{1,0,1,0,0,1,0,1,1,0,0,1,1,0,1,0,0,1,0,1},
					{1,0,1,2,2,1,2,1,2,2,2,2,1,2,1,2,2,1,0,1},
					{1,2,2,2,2,1,2,0,2,0,0,2,0,2,1,2,2,2,2,1},
					{1,2,2,2,2,1,2,0,2,0,0,2,0,2,1,2,2,2,2,1},
					{1,0,1,2,2,1,2,1,2,2,2,2,1,2,1,2,2,1,0,1},
					{1,0,1,0,0,1,0,1,1,0,0,1,1,0,1,0,0,1,0,1},
					{1,0,1,0,0,2,0,0,2,2,2,2,0,0,2,0,0,1,0,1},
					{1,0,1,0,2,2,2,1,1,1,1,1,1,2,2,2,0,1,0,1},
					{1,0,1,0,1,2,0,0,2,2,2,2,0,0,2,1,0,1,0,1},
					{1,0,1,0,0,0,0,0,2,2,2,2,0,0,0,0,0,1,0,1},
					{1,0,1,1,1,1,1,1,1,2,2,1,1,1,1,1,1,1,0,1},
					{1,0,0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0,0,1},
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
			this.block=block3;
			break;
		default:this.block=randomMap();break;
		}
		
	}
	private int[][] randomMap() {
		Random random=new Random();
		int rdMap[][]=new int[20][20];
		for (int i = 0; i < 20; i++)rdMap[0][i]=Map.BLOCK_STONE;
		for (int i = 0; i < 20; i++)rdMap[19][i]=Map.BLOCK_STONE;
		for (int i = 0; i < 20; i++)rdMap[i][0]=Map.BLOCK_STONE;
		for (int i = 0; i < 20; i++)rdMap[i][19]=Map.BLOCK_STONE;
		for (int i = 1; i < 19; i++) {
			for (int j = 1; j < 19; j++) {
				int rd=random.nextInt(6);
				if(rd==BLOCK_BOX)rdMap[i][j]=BLOCK_BOX;
				else if(rd==BLOCK_STONE)rdMap[i][j]=BLOCK_STONE;
				else rdMap[i][j]=BLOCK_SPACE;
			}
		}
		

		
		//lefe up
		for (int i = 1; i <=3; i++)for (int j = 1; j <=3; j++)rdMap[i][j]=BLOCK_BOX;
		for (int i = 1; i <=2; i++)for (int j = 1; j <=2; j++)rdMap[i][j]=BLOCK_SPACE;
		//right up
		for (int i = 1; i <=3; i++)for (int j = 16; j <=18; j++)rdMap[i][j]=BLOCK_BOX;
		for (int i = 1; i <=2; i++)for (int j = 17; j <=18; j++)rdMap[i][j]=BLOCK_SPACE;
		//right down
		for (int i = 16; i <=18; i++)for (int j = 16; j <=18; j++)rdMap[i][j]=BLOCK_BOX;
		for (int i = 17; i <=18; i++)for (int j = 17; j <=18; j++)rdMap[i][j]=BLOCK_SPACE;
		//lefe down
		for (int i = 16; i <=18; i++)for (int j = 1; j <=3; j++)rdMap[i][j]=BLOCK_BOX;
		for (int i = 17; i <=18; i++)for (int j = 1; j <=2; j++)rdMap[i][j]=BLOCK_SPACE;
		
		return rdMap;
	}

}

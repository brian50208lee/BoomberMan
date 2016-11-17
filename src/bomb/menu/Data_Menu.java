package bomb.menu;


public class Data_Menu {
	
	
	public static final int PAGE_START=0;
	public static final int PAGE_MAP=1;
	public static final int PAGE_PLAYER=2;
	public static final int PAGE_EXIT=3;
	public static final int PAGE_GAME=4;
	public static final int PAGE_GUIDE=5;
	
	public static final int START_START=0;
	public static final int START_GUIDE=1;
	public static final int START_EXIT=2;
	
	public static final int MAP_RANDOM=0;
	public static final int MAP_MODE1=1;
	public static final int MAP_MODE2=2;
	public static final int MAP_MODE3=3;

	
	public static final int PLAYER_2P=0;
	public static final int PLAYER_3P=1;
	public static final int PLAYER_4P=2;
	
	private int page;
	private int option;
	private int[] customData=new int[3];//PAGE,MAP_MODE,PLAYER
	
	public Data_Menu(){
		this.page=PAGE_START;
		this.option=START_START;
	}
	
	public int getPage(){return page;}
	public int getOption(){return option;}
	public void pressDown(){
		switch (page) {
			case PAGE_START:option=++option%3;break;
			case PAGE_MAP:option=++option%4;break;
			case PAGE_PLAYER:option=++option%3;break;
			default:break;
		}
	}
	public void pressUP(){
		switch (page) {
			case PAGE_START:option=(--option+3)%3;break;
			case PAGE_MAP:option=(--option+4)%4;break;
			case PAGE_PLAYER:option=(--option+3)%3;break;
		default:break;
	}
	}
	public void pressESC(){

		switch (page) {
		case PAGE_MAP:
			page=PAGE_START;
			option=0;
			break;
		case PAGE_PLAYER:
			page=PAGE_MAP;
			option=0;
			break;
		case PAGE_GUIDE:
			page=PAGE_START;
			option=0;
			break;
		default:break;
		}
	}
	public int[] pressENTER(){
		switch (page) {
			case PAGE_START:
				switch (option) {
					case START_START:page=PAGE_MAP;option=0;break;
					case START_GUIDE:page=PAGE_GUIDE;option=0;break;
					case START_EXIT:page=PAGE_EXIT;break;
					default:break;
				}
				break;
			case PAGE_MAP:
				customData[1]=option;
				page=PAGE_PLAYER;
				option=0;
				break;
			case PAGE_PLAYER:
				customData[2]=option;
				page=PAGE_GAME;
				break;
			case PAGE_GAME:
				return null;
			default:break;
		}
		
		customData[0]=page;
		return customData;
	}
}

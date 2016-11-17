package bomb.menu;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JComponent;

import bomb.main.Counter;


public class Painter_Menu extends JComponent implements Runnable{
	public static final int STATE_START=0;
	public static final int STATE_MAIN=1;	
	public static final int STATE_EXIT=2;
	
	public final int FPS = 40;
	public boolean isRunning=true;
	private int state=0;
	private Counter counter;
	private Data_Menu data_Menu;
	
	private Image im_bg[]=new Image[4];
	private Image im_op_start[]=new Image[3];
	private Image im_op_map[]=new Image[4];
	private Image im_op_player[]=new Image[3];
	private Image im_op_blank;
	private Image im_op_arrow;
	private Image im_anim_start_dark;
	private Image im_anim_exit_dark;
	
	
	public void setDataMenu(Data_Menu data_Menu){this.data_Menu=data_Menu;}
	public Painter_Menu(){
		loadIMG();
	}
	
	public void run() {
		start();
		while (isRunning) {
			try {
				repaint();
				Thread.sleep(1000/FPS);
			} catch (InterruptedException e) {}
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
		
			
			//im_bg[0]=ImageIO.read(this.getClass().getResource("/img/menu/background/bg1.png"));
			im_bg[0]=toolkit.createImage(ClassLoader.getSystemResource("img/menu/background/bg1.png"));
			im_bg[1]=toolkit.createImage(ClassLoader.getSystemResource("img/menu/background/bg2.png"));
			im_bg[2]=toolkit.createImage(ClassLoader.getSystemResource("img/menu/background/bg3.png"));
			im_bg[3]=toolkit.createImage(ClassLoader.getSystemResource("img/menu/background/bg4.png"));
			im_op_start[0]=toolkit.createImage(ClassLoader.getSystemResource("img/menu/option/start.png"));
			im_op_start[1]=toolkit.createImage(ClassLoader.getSystemResource("img/menu/option/guide.png"));
			im_op_start[2]=toolkit.createImage(ClassLoader.getSystemResource("img/menu/option/exit.png"));
			im_op_map[0]=toolkit.createImage(ClassLoader.getSystemResource("img/menu/option/random.png"));
			im_op_map[1]=toolkit.createImage(ClassLoader.getSystemResource("img/menu/option/map1.png"));
			im_op_map[2]=toolkit.createImage(ClassLoader.getSystemResource("img/menu/option/map2.png"));
			im_op_map[3]=toolkit.createImage(ClassLoader.getSystemResource("img/menu/option/map3.png"));
			im_op_player[0]=toolkit.createImage(ClassLoader.getSystemResource("img/menu/option/2p.png"));
			im_op_player[1]=toolkit.createImage(ClassLoader.getSystemResource("img/menu/option/3p.png"));
			im_op_player[2]=toolkit.createImage(ClassLoader.getSystemResource("img/menu/option/4p.png"));
			im_op_blank=toolkit.createImage(ClassLoader.getSystemResource("img/menu/option/blank.png"));
			im_op_arrow=toolkit.createImage(ClassLoader.getSystemResource("img/menu/option/arrow.png"));
			im_anim_start_dark=toolkit.createImage(ClassLoader.getSystemResource("img/menu/anim/start/dark.png"));
			im_anim_exit_dark=toolkit.createImage(ClassLoader.getSystemResource("img/menu/anim/exit/dark.png"));
			/*		
 			try {
			im_bg_bg1=ImageIO.read(new File("img/menu/background/bg1.png"));
			im_op_start=ImageIO.read(new File("img/menu/option/start.png"));
			im_op_exit=ImageIO.read(new File("img/menu/option/exit.png"));
			im_anim_start_dark=ImageIO.read(new File("img/menu/anim/start/dark.png"));
			im_anim_exit_dark=ImageIO.read(new File("img/menu/anim/exit/dark.png"));
			} catch (IOException e) {e.printStackTrace();}
			 */
	}
	

	
	private void startAnim(Graphics g){
		if(counter==null)return;
		int step=counter.getStep();
		if (step!=-1) {
			g.drawImage(im_anim_start_dark,		0, 		0,		640,	640,
					0,		0+step*100, 		100, 	100+	step*100, 	null);
		}else {
			state=STATE_MAIN;
		}
	}
	
	private void mainAnim(Graphics g){
		int page=data_Menu.getPage();
		int option=data_Menu.getOption();
		Image image1=null,image2=null,image3=null,image4=null;
		switch (page) {
			case Data_Menu.PAGE_START:image1=im_bg[0];break;
			case Data_Menu.PAGE_MAP:image1=im_bg[1];break;
			case Data_Menu.PAGE_PLAYER:image1=im_bg[2];break;
			case Data_Menu.PAGE_GAME:image1=im_bg[2];break;
			case Data_Menu.PAGE_EXIT:image1=im_bg[0];break;
			case Data_Menu.PAGE_GUIDE:image1=im_bg[3];break;
			default:break;
		}
		g.drawImage(image1	,		0, 		0,		640,	640,
									0,		0, 		640, 	640, 	null);
		switch (page) {
			case Data_Menu.PAGE_START:image1=im_bg[0];
				image1=im_op_start[0];
				image2=im_op_start[1];
				image3=im_op_start[2];
				image4=im_op_blank;break;
			case Data_Menu.PAGE_MAP:image1=im_bg[1];
				image1=im_op_map[0];
				image2=im_op_map[1];
				image3=im_op_map[2];
				image4=im_op_map[3];break;
			case Data_Menu.PAGE_PLAYER:image1=im_bg[2];
				image1=im_op_player[0];
				image2=im_op_player[1];
				image3=im_op_player[2];
				image4=im_op_blank;break;
			case Data_Menu.PAGE_GAME:image1=im_bg[2];
				image1=im_op_player[0];
				image2=im_op_player[1];
				image3=im_op_player[2];
				image4=im_op_blank;break;
			case Data_Menu.PAGE_EXIT:image1=im_bg[0];
				image1=im_op_start[0];
				image2=im_op_start[1];
				image3=im_op_start[2];
				image4=im_op_blank;break;
			case Data_Menu.PAGE_GUIDE:image1=im_bg[0];
				image1=im_op_blank;
				image2=im_op_blank;
				image3=im_op_blank;
				image4=im_op_blank;break;
			default:break;
		}
		
		if (page==Data_Menu.PAGE_GUIDE)return;
		
		g.drawImage(image1,		210,250,410,280,	0,0,200,28, null);
		g.drawImage(image2,		210,300,410,330,	0,0,200,28, null);
		g.drawImage(image3,		210,350,410,380,	0,0,200,28, null);
		g.drawImage(image4,		210,400,410,430,	0,0,200,28, null);
		g.drawImage(im_op_arrow,210,	250+option*50,	410,	280+option*50,	
								0,	0,	200,28, null);
	}
	
	private void exitAnim(Graphics g){
		if(counter==null)return;
		int step=counter.getStep();
		if (step!=-1) {
			g.drawImage(im_anim_exit_dark,		0, 		0,		640,	640,
					0,		0+step*100, 		100, 	100+	step*100, 	null);
		}else {
			isRunning=false;
		}
	}

	public void start(){
		counter=new Counter(20,8,false);
		new Thread(counter).start();
		state=STATE_START;
		
	}
	public void exit(){
		counter=new Counter(15,8,false);
		new Thread(counter).start();
		state=STATE_EXIT;
	}

}

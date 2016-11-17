package bomb.main;
import javax.swing.JFrame;


public class GUI {
	JFrame frame;
	public GUI() {
		// set Frame
		frame = new JFrame();
		frame.setTitle("Game A");
		frame.setSize(640+10, 640+30);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}

package pkg;

 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Animation4Thread extends JFrame {

	final int frameCount = 10;
    BufferedImage[] pics;
    int xloc = 100;
    int yloc = 100;
    final int xIncr = 1;
    final int yIncr = 1;
    final int picSize = 165;
    final int frameStartSize = 800;
    final int drawDelay = 30; //msec
    final static int bx = 200;
    final static int by = 200;
    final static int bwidth = 200;
    final static int bheight = 200;
    static boolean flag = true;
    
    DrawPanel drawPanel = new DrawPanel();
    Action drawAction;
    static JButton b;

    public Animation4Thread() {
    	drawAction = new AbstractAction(){
    		public void actionPerformed(ActionEvent e){
    			drawPanel.repaint();
    		}
    	};
    	
    	add(drawPanel);
    	BufferedImage img = createImage();
    	pics = new BufferedImage[frameCount];//get this dynamically
    	for(int i = 0; i < frameCount; i++) {
    		pics[i] = img.getSubimage(picSize*i, 0, picSize, picSize);
    	}
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setSize(frameStartSize, frameStartSize);
    	setVisible(true);
    	pack();
    }
	
    @SuppressWarnings("serial")
	private class DrawPanel extends JPanel {
    	int picNum = 0;
    	
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.gray);
	    	picNum = (picNum + 1) % frameCount;
	    	g.drawImage(pics[picNum], xloc+=xIncr, yloc+=yIncr, Color.gray, this);
	    	add(b);
		}

		public Dimension getPreferredSize() {
			return new Dimension(frameStartSize, frameStartSize);
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				Animation4Thread a = new Animation4Thread();
				Timer t = new Timer(a.drawDelay, a.drawAction);
				b = new JButton("stop");
		    	b.setBounds (bx,by,bwidth,bheight);
		    	b.addActionListener(new ActionListener() {
		    		public void actionPerformed(ActionEvent e) {
		    			flag = !flag;
		    			if(flag) {
		    				b.setText("stop");
		    				t.start();
		    			}else {
		    				b.setText("resume");
		    				t.stop();
		    			}
		    		}
		    	});
				t.start();
			}
		});
	}
    
    //Read image from file and return
    private BufferedImage createImage(){
    	BufferedImage bufferedImage;
    	try {
    		bufferedImage = ImageIO.read(new File("images/orc/orc_forward_southeast.png"));
    		return bufferedImage;
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
}


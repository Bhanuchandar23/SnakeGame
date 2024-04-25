package snake;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.*;
import  javax.swing.*;
import java.util.Random;
public class GamePanel extends JPanel implements ActionListener {
      static final int Screen_Width =600;
      static final int Screen_Height =600;
      static final int UNIT_SIZE =15;
      static final int GAME_UNIT =(Screen_Width*Screen_Height)/UNIT_SIZE;
      static final int DELAY =75;
      static final int X[] =new int[GAME_UNIT];
      static final int Y[]=new int[GAME_UNIT];
      int bodyParts = 6;
      int appleEaten;
      int appleX;
      int appleY;
      int direction ='R';
      boolean running =false;
      Timer timer;
      Random random;
      
      
     
      
	GamePanel(){
		
		random =new Random();
		this.setPreferredSize(new Dimension(Screen_Width,Screen_Height));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
	}
	public void startGame() {
		newApple();
		running = true;
		timer =new Timer(DELAY,this);
		timer.start();
		
		
		
	}
	
	public void  paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
	public void draw (Graphics g) {
		if(running) {
			for(int i =0;i<Screen_Height/UNIT_SIZE;i++) {
				
				g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,Screen_Height);
				g.drawLine(0,i*UNIT_SIZE,Screen_Width,i*UNIT_SIZE);
			}
			g.setColor(Color.YELLOW);
			g.fillOval(appleX, appleY,UNIT_SIZE,UNIT_SIZE);
			//draw snake body below
			for(int i= 0; i<bodyParts;i++) {
				if(i==0) {
					g.setColor(Color.green);
					g.fillRect(X[i],Y[i],UNIT_SIZE,UNIT_SIZE);
					
				}
				else {
					g.setColor(new Color(45,245,0));
					g.fillRect(X[i],Y[i],UNIT_SIZE,UNIT_SIZE);
					
				}
			}
			g.setColor(Color.BLUE);
			g.setFont(new Font("ink Free",Font.BOLD,40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+ appleEaten,(Screen_Width-metrics.stringWidth("score:"+ appleEaten))/2,g.getFont().getSize());
			
		}
		else {
			gameOver(g);
			
		}
		
	}
	public void newApple() {
		appleX =random.nextInt((int)(Screen_Width/UNIT_SIZE))*UNIT_SIZE;
		appleY=random.nextInt((int)(Screen_Height/UNIT_SIZE))*UNIT_SIZE;
		
	}
	public void move() {
		
		for(int i=bodyParts;i>0;i--) {
			X[i]=X[i-1];
			Y[i]=Y[i-1];
			
			
		}
	   switch(direction) {
	     case 'U':
		    Y[0] = Y[0] - UNIT_SIZE;
		   break;
	     case 'D':
		    Y[0] = Y[0] + UNIT_SIZE;
		   break;
	     case'L':
		    X[0] = X[0] - UNIT_SIZE;
		   break;
		   
	    case 'R':
		    X[0] = X[0] + UNIT_SIZE;
		    break;
		   
	   }
		
	}
	public void checkApple() {
		if((X[0] == appleX) && (Y[0] == appleY)) {
			bodyParts++;
			appleEaten++;
			newApple();
		}
		
	}
	
	public void checkCollision() {
		
		 // Check if head touches left border or right border
	    if (X[0] < 0 || X[0] >= Screen_Width) {
	        running = false;
	    }
	    // Check if head touches top border or bottom border
	    if (Y[0] < 0 || Y[0] >= Screen_Height) {
	        running = false;
	    }
	    // Check if head collides with body
	    for (int i = bodyParts; i > 0; i--) {
	        if (X[0] == X[i] && Y[0] == Y[i]) {
	            running = false;
	            break; // No need to continue checking if collision detected
	        }
	    }
		
			if(!running) {
				timer.stop();
			}
	}
		
			
			
		
		
	
	public void gameOver(Graphics g) {
		//game over text
		g.setColor(Color.BLUE);
		g.setFont(new Font("ink Free",Font.BOLD,40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Game over ", (Screen_Width -metrics1.stringWidth("Game Over"))/2,Screen_Height/2);

		g.setColor(Color.BLUE);
		g.setFont(new Font("ink Free",Font.BOLD,40));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Score: "+ appleEaten,(Screen_Width-metrics2.stringWidth("score:"+ appleEaten))/2,g.getFont().getSize());
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	
		if(running) {
			move();
			checkApple();
			checkCollision();
		}
		repaint();
		
	}



public class MyKeyAdapter extends KeyAdapter{
	 public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()){
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
					
				}
				break;
			case KeyEvent.VK_UP:
				if(direction !='D'){
					direction ='U';
					
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction !='U'){
					direction ='D';
					
				}
				break;
				
				
				
			}
			
		}
		
	}
	

	

 }


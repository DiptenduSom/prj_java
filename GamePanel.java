package project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
   static final int SCREEN_WIDTH = 600;
   // for the size of the game screen
   static final int SCREEN_HEIGHT = 600;
   // for the size of the game width
   static final int UNIT_SIZE = 20;
   // for the size of the units of the screen
   static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
   // for the total number of game units in the screen
   static final int DELAY = 80;
   // greater the delay, slower the game ; 80 being faairly quick
   final int x[] = new int[GAME_UNITS];// snake wont be bigger than the game itself
   // this will hold all of the x coordinates of the body parts of the snake
   // it includes the head of the snake
   final int y[] = new int[GAME_UNITS];
   // this will hold all of the y coordinated of the body parts of the snake
   int bodyParts = 6;// initially beginning with 6 body parts of the snake
   int applesEaten = 0;// holds the numbers of apples eaten by the snake
   int appleX;// holds the x coordinate of the apples
   int appleY;// holds the y coordinate of the apples
   char direction = 'D';
   // for changing the direction of the snake during the game
   boolean running = false;
   // for the game to start
   Timer timer;
   // the timer running in the back
   Random random;

   GamePanel() {
      // constructor to initialize
      random = new Random();
      this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
      this.setBackground(Color.black);
      this.setFocusable(true);
      this.addKeyListener(new MyKeyAdapter());
      startGame();
   }// initializing for the game complete

   public void startGame() {
      // for the components required for the game to start
      newApple();
      running = true;
      timer = new Timer(DELAY, this);
      // delay cause for the player to understand
      // to capture the real time acess
      timer.start();
   }

   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      draw(g);
      // for the snake to be executed in each block
   }

   public void draw(Graphics g) {
      // for the real time acess of the game for the program
      if (running) {
         for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
         }
         // for forming the grind for the better visualization of the blocks
         g.setColor(Color.red);
         g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
         // for creating the apple and filling it up with the desired color
         for (int i = 0; i < bodyParts; i++) {
            if (i == 0) {
               g.setColor(Color.green);
               g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            } else {
               g.setColor(new Color(45, 180, 0));
               g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
               g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
         }
         // for the displaying of the score
         g.setColor(Color.white);
         g.setFont(new Font("Ink Free", Font.BOLD, 40));
         // font matrix used for linning up the text in the screen
         FontMetrics metrics = getFontMetrics(g.getFont());
         g.drawString("Score : " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score : " + applesEaten)) / 2,
               g.getFont().getSize());
      } else {
         gameOver(g);
      }
   }

   public void newApple() {
      // for choosing the place where the apple will occur
      appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
      // for holding the X coordinates for the apples that will occur
      appleX = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
      // for holding the Y coordinates for the apples that will occur
   }

   public void move() {
      // for moving the snake
      for (int i = bodyParts; i > 0; i--) {
         x[i] = x[i - 1];
         y[i] = y[i - 1];
         // thus shifting the body parts of the snake by one
      } // for shifting of the body parts of the snake around
      switch (direction) {
         // the W and S keys are for the y coordinates
         // the A and D keys are for the x coordinates
         case 'W':
         case 'w':
            y[0] = y[0] - UNIT_SIZE;
            // for going to the next position
            break;
         case 'S':
         case 's':
            y[0] = y[0] + UNIT_SIZE;
            // for going to the next position
            break;
         case 'A':
         case 'a':
            x[0] = x[0] - UNIT_SIZE;
            // for going to the next position
            break;
         case 'D':
         case 'd':
            x[0] = x[0] + UNIT_SIZE;
            // for going to the next position
            break;
      }// thus movement of the snake created
   }

   public void checkApple() {
      // for checking the score if the user is playing the game
      if ((x[0] == appleX) && (y[0] == appleY)) {
         bodyParts++;
         // for the snake size increasing after eating an apple
         applesEaten++;
         // for the score to be counted
         newApple();
      }
   }

   public void checkCollisions() {
      // for checking whether the snake is colliding with anything or not
      for (int i = bodyParts; i > 0; i--) {// for the ckeching of the head o the snake
         if ((x[0] == x[i]) && (y[0] == y[i])) {// thus thus is the case for the head colliding with the body
            running = false;
         }
      }
      // for cheking of the head of the snake if it touches left border
      if (x[0] < 0) {
         running = false;
      }
      // for checking of the head of the snake if it touches the right border
      if (x[0] > SCREEN_WIDTH) {
         running = false;
      }
      // for checking of the head of the snake if it touches the top border
      if (y[0] < 0) {
         running = false;
      }
      // for checking of the head of the snake if it touches the bottom border
      if (y[0] > SCREEN_HEIGHT) {
         running = false;
      }
      if (!running) {
         timer.stop();
      }
   }

   public void gameOver(Graphics g) {
      // for displayinf the score in the ending screen
      g.setColor(Color.white);
      g.setFont(new Font("Ink Free", Font.BOLD, 40));
      // font matrix used for linning up the text in the screen
      FontMetrics metrics1 = getFontMetrics(g.getFont());
      g.drawString("Score : " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score : " + applesEaten)) / 2,
            g.getFont().getSize());
      // for the message to display after the game is over
      g.setColor(Color.red);
      g.setFont(new Font("Ink Free", Font.BOLD, 75));
      // font matrix used for linning up the text in the screen
      FontMetrics metrics2 = getFontMetrics(g.getFont());
      g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
      // for displaying the message in the centre of the screen
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      // for the snake to move across the grid created
      // this is called for the runtime acess
      if (running) {
         move();
         checkApple();
         checkCollisions();
      }
      repaint(ABORT, appleX, DELAY, WIDTH, HEIGHT);
   }

   public class MyKeyAdapter extends KeyAdapter {
      @Override
      public void keyPressed(KeyEvent e) {
         // for the user to control the snake
         switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
               // for the user turning the snake to the left direction
               if (direction != 'D' || direction != 'd') {
                  direction = 'A';
               } // to go left
               break;
            case KeyEvent.VK_RIGHT:
               // for the user turning the snake to the left direction
               if (direction != 'A' || direction != 'a') {
                  direction = 'D';
               } // to go right
               break;
            case KeyEvent.VK_UP:
               // for the user turning the snake to the left direction
               if (direction != 'S' || direction != 's') {
                  direction = 'W';
               } // to go up
               break;
            case KeyEvent.VK_DOWN:
               // for the user turning the snake to the left direction
               if (direction != 'W' || direction != 'w') {
                  direction = 'S';
               } // to go down
               break;
         }
      }
   }
}

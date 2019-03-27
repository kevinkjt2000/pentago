import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class PentagoGUI extends JFrame
{
   private boolean COMPUTER_PLAYER = true;
   private JButton[] marbleButtons;
   private MarbleButtonListener marbleButtonListener;
   private Graphics graphics;
   
   //game variables
   private int[] marbles;
   private int turn;
   
   private void drawGameBoard(Graphics2D g)
   {
      for(int i=0; i<36; i++) {
         if(marbles[i] == 0) {
            marbleButtons[i].setEnabled(true);
            marbleButtons[i].setBackground(Color.red);
         } else if(marbles[i] == 1) {
            marbleButtons[i].setEnabled(false);
            marbleButtons[i].setBackground(Color.white);
         } else {
            marbleButtons[i].setEnabled(false);
            marbleButtons[i].setBackground(Color.black);
         }
      }
      if(checkForWins(1, marbles)!=0 || checkForWins(2, marbles)!=0)
         displayWinMsg();
   }

   public PentagoGUI()
   {
      super("Pentago!"); setBounds(100, 100, 500, 500);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      init(); //initialize game variables
      graphics = getGraphics();
      setLayout(new GridLayout(6, 6));
      marbleButtons = new JButton[36];
      marbleButtonListener = new MarbleButtonListener();
      for(int i=0; i<36; i++)
      {
         marbleButtons[i] = new RoundButton("");
         add(marbleButtons[i]);
         marbleButtons[i].setBackground(Color.red);
         marbleButtons[i].setForeground(Color.black);
         marbleButtons[i].addActionListener(marbleButtonListener);
      }
      setVisible(true);
   }
   
   private void init()
   {
      marbles = new int[36];
      for(int i=0; i<36; i++)
         marbles[i] = 0;
      turn = 0;
   }
   
   protected class MarbleButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e) {
         JButton pressedButton = (RoundButton)e.getSource();
         for(int i=0; i<36; i++)
            if(marbleButtons[i].getX() == pressedButton.getX() && 
               marbleButtons[i].getY() == pressedButton.getY())
               if(turn%2 == 0)
                  marbles[i] = 1;
               else
                  marbles[i] = 2;
         turn++;
         Object[] possibleValues = {"UL CW", "UL CCW", "UR CW", "UR CCW", 
                                    "DL CW", "DL CCW", "DR CW", "DR CCW"};
         Object selectedValue;
         //check for wins needs to be done before and after rotation
         drawGameBoard((Graphics2D)graphics);
         do { selectedValue = JOptionPane.showInputDialog(null, 
            "rotate which part of the board?", "Input", JOptionPane.INFORMATION_MESSAGE, null,
            possibleValues, possibleValues[0]);
         } while (selectedValue == null);
         for(int i=0; i<8; i++)
            if(selectedValue == possibleValues[i])
               marbles = rotate(i/2, i%2, marbles);
         drawGameBoard((Graphics2D)graphics);
         if(COMPUTER_PLAYER == true && turn%2 == 1){ //this if lets a computer move for black
            makeCompMove(2, marbles);
            turn++;
            drawGameBoard((Graphics2D)graphics);
         }
      }
   }
   
   private void makeCompMove(int playerID, int[] array)
   {
      int[] m = array.clone();
      int otherPlayerID = ( playerID == 1 ) ? 2 : 1;
      ArrayList<Integer> emptyMovesList = new ArrayList<Integer>();
      for( int i = 0; i < 36; i++ )
      {
         if( m[ i ] == 0 )
         {
            emptyMovesList.add( i );
         }
      }
      int move = minMax( playerID, otherPlayerID, m, emptyMovesList );
      m[ move ] = playerID;
      marbles = m;
   }
   
   private int alphabeta()
   {
      return 0;
   }
   
   private int minMax( int playerID, int otherPlayerID, int[] array,
                       ArrayList<Integer> emptyMovesList )
   {
      int size = emptyMovesList.size();
      ArrayList<Integer> max = new ArrayList<Integer>( size ),
                         min = new ArrayList<Integer>( size );
      
      return emptyMovesList.get( 0 );
   }
   
   private int miniMaxInternal(int playerID, int[] array)
   {
      return 0;
   }
   
   private int[] miniMax(int playerID, int[] array)
   {
      int[] m = array.clone(), playerScore = new int[36], otherPlayerScore = new int[36],
            min_max_val = new int[36];
      int temp = 0, otherPlayerID = (playerID==1) ? 2 : 1;
      for(int i=0; i<36; i++) {
         if (array[i] == '0') { // loop all the empty positions
            m[i] = playerID;
            temp = checkForWins(playerID, m);
            playerScore[i] = temp;
            temp = checkForWins(otherPlayerID, m);
            otherPlayerScore[i] = temp;
            
            for(int quadrant=0; quadrant<4; quadrant++) {
               for(int rotation=0; rotation<2; rotation++) {
                  temp = checkForWins(otherPlayerID, rotate(quadrant, rotation, m));
                  if (temp > playerScore[i])
                     playerScore[i] = temp;
                  temp = checkForWins(playerID, rotate(quadrant, rotation, m));
                  if (temp < otherPlayerScore[i])
                     otherPlayerScore[i] = temp;
               }
            }
            
            min_max_val[i] = playerScore[i] - otherPlayerScore[i];
         } else
            min_max_val[i] = -99999;
         min_max_val[i] += miniMaxInternal(playerID, m);
      }
      return min_max_val;
   }
   
   private int[] rotate(int quadrant, int direction, int[] array) {
      int temp = 0;
      int[] m = array.clone();
      int[][] r = {{0, 12, 14, 2, 1, 6, 13, 8}, {0, 2, 14, 12, 1, 8, 13, 6},
                   {3, 15, 17, 5, 4, 9, 16, 11}, {3, 5, 17, 15, 4, 11, 16, 9},
                   {18, 30, 32, 20, 19, 24, 31, 26}, {18, 20, 32, 30, 19, 26, 31, 24},
                   {21, 33, 35, 23, 22, 27, 34, 29}, {21, 23, 35, 33, 22, 29, 34, 27}};
      int i = 2*quadrant + direction;
      temp = m[r[i][0]];
      m[r[i][0]] = m[r[i][1]];
      m[r[i][1]] = m[r[i][2]];
      m[r[i][2]] = m[r[i][3]];
      m[r[i][3]] = temp;
      temp = m[r[i][4]];
      m[r[i][4]] = m[r[i][5]];
      m[r[i][5]] = m[r[i][6]];
      m[r[i][6]] = m[r[i][7]];
      m[r[i][7]] = temp;
      return m;
   }
   
   private void displayWinMsg() {
      if(checkForWins(1, marbles)!=0 && checkForWins(2, marbles)==0)
         JOptionPane.showMessageDialog(null, "White wins!", "White wins!",
            JOptionPane.PLAIN_MESSAGE);
      else if(checkForWins(1, marbles)==0 && checkForWins(2, marbles)!=0)
         JOptionPane.showMessageDialog(null, "Black wins!", "Black wins!",
            JOptionPane.PLAIN_MESSAGE);
      else
         JOptionPane.showMessageDialog(null, "White and Black wins?", "White and Black wins?",
            JOptionPane.PLAIN_MESSAGE);
      init();
      drawGameBoard((Graphics2D)graphics);
   }
   
   private int checkForWins(int playerID, int[] m) {
      int whiteWins = 0, blackWins = 0;
      int[][] wins = {{0, 6, 12, 18, 24}, {6, 12, 18, 24, 30}, //vertical wins
                      {1, 7, 13, 19, 25}, {7, 13, 19, 25, 31},
                      {2, 8, 14, 20, 26}, {8, 14, 20, 26, 32},
                      {3, 9, 15, 21, 27}, {9, 15, 21, 27, 33},
                      {4, 10, 16, 22, 28}, {10, 16, 22, 28, 34},
                      {5, 11, 17, 23, 29}, {11, 17, 23, 29, 35},
                      {0, 1, 2, 3, 4}, {1, 2, 3, 4, 5}, //horizontal wins
                      {6, 7, 8, 9, 10}, {7, 8, 9, 10, 11},
                      {12, 13, 14, 15, 16}, {13, 14, 15, 16, 17},
                      {18, 19, 20, 21, 22}, {19, 20, 21, 22, 23},
                      {24, 25, 26, 27, 28}, {25, 26, 27, 28, 29},
                      {30, 31, 32, 33, 34}, {31, 32, 33, 34, 35},
                      {0, 7, 14, 21, 28}, {7, 14, 21, 28, 35}, //diagonal wins
                      {5, 10, 15, 20, 25}, {10, 15, 20, 25, 30},
                      {1, 8, 15, 22, 29}, {6, 13, 20, 27, 34}, //3-board wins
                      {4, 9, 14, 19, 24}, {11, 16, 21, 26, 31}};
      for(int i=0; i<32; i++) //there are 32 possible 5-in-a-rows
         if(m[wins[i][0]] == m[wins[i][1]] && m[wins[i][1]] == m[wins[i][2]] &&
            m[wins[i][2]] == m[wins[i][3]] && m[wins[i][3]] == m[wins[i][4]] &&
            m[wins[i][0]] != 0)
            if(m[wins[i][0]] == 1)
               whiteWins++;
            else
               blackWins++;
      return (playerID==1) ? whiteWins : blackWins;
   }
   
   protected void paintComponent(Graphics g1)
   {
      Graphics2D g = (Graphics2D) g1;
      drawGameBoard(g);
   }

   public static void main(String[] args) { new PentagoGUI(); }
}

/* This Pentago class extends the Game class and implements the
 * abstract methods reset, draw, and obtainInput.
 * Pentago is a game that is played by two players.  The default
 * constructor for Pentago lets the game be played by a human and an
 * AI.  Also, the default constructor sets the human to have the first move.
 * Pentago is won when a player gets five marbles in a row (horizontally, 
 * vertically, or diagonally).  On each turn the active player places a marble
 * and rotates one of the quadrants of the six by six playing board.
 * The rotation must be 90° counterclockwise or clockwise, and can not be 0°, 
 * 180°, 360°, etc.  Each quadrant of the board is a three by three subsection of
 * the main six by six board.  
 */

public class Pentago extends Game implements ComputerPlayer{
   
   
   Pentago() {
      super(6, 6, true);
      reset();
   }
   
   public boolean obtainInput() {
      
      return true;
   }
   
   public int[] getMove(int[][] board, boolean side) {
      
      return new int[2];
   }
   
   public String draw() {
      
      return super.toString();
   }
   
   protected String reset() {
      for(int i=0; i<6; i++)
         for(int j=0; j<6; j++)
            board[i][j] = 0;
      return super.toString();
   }
}
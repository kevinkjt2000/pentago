public abstract class Game
{
	protected int[][] board;
	protected final int ROWS, COLS;
	protected int turn=0, player1Wins=0, player2Wins=0, ties=0;
	abstract boolean obtainInput();
	abstract String draw();
	abstract String reset();
	protected ComputerPlayer player;
	protected boolean isHumanStartingPlayer;
	public Game(int rows, int cols, boolean isHumanStartingPlayer)
	{
		ROWS = rows;
		COLS = cols;
		board = new int[ROWS][COLS];
		this.isHumanStartingPlayer = isHumanStartingPlayer;
	}
	public int[][] copyBoard()
	{
		int[][] temp = new int[ROWS][COLS];
		for(int i=0; i<ROWS; i++)
		{
			for(int j=0; j<COLS; j++)
			{
				temp[i][j] = board[i][j];
			}
		}
		return temp;
	}
	public void play()
	{
		String prompt = "y"; //Keyboard.readString("Ready to play? (y/n) ");
		do {
			System.out.println(draw());
         turn = 1;
			while(!obtainInput())
			{
				System.out.println(draw());
			}
			System.out.println(draw());
			System.out.println(reset());
			prompt = "y"; //Keyboard.readString("Ready to play? (y/n) ");
		} while(prompt.equals("y"));
	}
	public String toString()
	{
		String str = "\r\n\r\nPlayer 1 Wins: " + player1Wins;
		str += "\r\nPlayer 2 Wins: " + player2Wins;
		str += "\r\nTies: " + ties + "\r\n\r\n";
		return str;
	}
}
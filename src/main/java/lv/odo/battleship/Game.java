package lv.odo.battleship;

public class Game {
	
	private int id;

	//true if battle is in process
	//false if players place ships
	private boolean active = false;

	//0 if my turn
	//1 if enemy turn
	private int turn;
	
	private Player me;
	
	private Player enemy;

	public Game(int id, Player me, Player enemy) {
		super();
		this.id = id;
		this.me = me;
		this.enemy = enemy;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Player getMe() {
		return me;
	}

	public void setMe(Player me) {
		this.me = me;
	}

	public Player getEnemy() {
		return enemy;
	}

	public void setEnemy(Player enemy) {
		this.enemy = enemy;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", me=" + me + ", enemy=" + enemy + ", active=" + active + ", turn=" + turn + "]";
	}

}


package lv.odo.battleship;

public class Game {
	
	private int id;
	
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

	@Override
	public String toString() {
		return "Game [id=" + id + ", me=" + me + ", enemy=" + enemy + "]";
	}

}


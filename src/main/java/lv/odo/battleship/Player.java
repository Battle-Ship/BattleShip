package lv.odo.battleship;

public class Player {
	
	private String name;
	
	private Field field;	

	public Player(String name, Field field) {
		super();
		this.name = name;
		this.field = field;
	}

	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

}


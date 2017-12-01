package clueGame;

public class Solution {
	public String person;
	public String room;
	public String weapon;
	
	public Solution(String person, String room, String weapon) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	
	public boolean equals(Solution solution) {
		if (person.equals(solution.person) && weapon.equals(solution.weapon) && room.equals(solution.room)) {
			return true;
		}
		else {
			return false;
		}
	}
	
}

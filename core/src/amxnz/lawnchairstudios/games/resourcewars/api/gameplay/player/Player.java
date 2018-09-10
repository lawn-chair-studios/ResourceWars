package amxnz.lawnchairstudios.games.resourcewars.api.gameplay.player;

import amxnz.lawnchairstudios.games.resourcewars.api.gameplay.entities.AbstractEntity;

public class Player {

	public Player(AbstractEntity inGameCharacter) {
		this.inGameCharacter = inGameCharacter;
	}

	public void setInGameCharacter(AbstractEntity inGameCharacter) {
		this.inGameCharacter = inGameCharacter;
	}

	private AbstractEntity inGameCharacter;

	public AbstractEntity getInGameCharacter() {
		return inGameCharacter;
	}
}

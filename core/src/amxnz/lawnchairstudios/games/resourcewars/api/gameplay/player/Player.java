package amxnz.lawnchairstudios.games.resourcewars.api.gameplay.player;

import amxnz.lawnchairstudios.games.resourcewars.api.gameplay.entities.AbstractEntity;

public class Player {

	public Player(AbstractEntity inGameCharacter) {
		setInGameCharacter(inGameCharacter);
	}

	public void setInGameCharacter(AbstractEntity inGameCharacter) {
		if (inGameCharacter == null)
			throw new RuntimeException("A Player's entity cannot be null.");
		this.inGameCharacter = inGameCharacter;
	}

	private AbstractEntity inGameCharacter;

	public AbstractEntity getInGameCharacter() {
		return inGameCharacter;
	}
}

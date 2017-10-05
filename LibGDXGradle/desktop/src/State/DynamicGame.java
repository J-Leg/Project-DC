package State;


//temporary class for Game, 
// showing interface necessary for higher level input and step processing
// modifies GameState in a dynamic way, ALL GAME LOGIC HERE
// Probably needs a better name :/
public class DynamicGame {
	private int steps;
	private State activeState;
	
	public DynamicGame() {
		steps = 0;
	}
	
	// GENERAL FUNCTIONALITY
	public void initialise(State startState) {
		// take in a new GameState, and execute any other preamble
		this.activeState = startState;
	}
	
	public void step() {
		// execute each game step (for any realtime enemies)
		
		// get all gameworld objects
		// iterate upon these objects running their step() 
		
		
		//System.out.print("Game step.\n");
		steps++;
		if (steps >= 30) {
			System.out.print("1 second of game time.\n");
			steps = 0;
		}
	}
	
	public State getState() {
		return activeState;
	}
	
	
	
	// Player Actions:
	// 	Movement and attack
	// false means action can not be made and no changes are made to the state
	
	public boolean makeAction(Action a) {
		int[] curr = activeState.findPlayer();
		switch (a) {
		case ATTACK:
			System.out.print("USER INPUT: ATTACK\n");
			break;
		case MOVE_DOWN:
			activeState.movePlayer(new int[] {curr[0], curr[1] + 1});
			System.out.print("USER INPUT: DOWN\n");
			break;
		case MOVE_LEFT:
			activeState.movePlayer(new int[] {curr[0] - 1, curr[1]});
			System.out.print("USER INPUT: LEFT\n");
			break;
		case MOVE_RIGHT:
			activeState.movePlayer(new int[] {curr[0] + 1, curr[1]});
			System.out.print("USER INPUT: RIGHT\n");
			break;
		case MOVE_UP:
			activeState.movePlayer(new int[] {curr[0] - 1, curr[1]});
			System.out.print("USER INPUT: UP\n");
			break;
		default:
			break;
		
		}
		return false;
	}
	
	
	
}

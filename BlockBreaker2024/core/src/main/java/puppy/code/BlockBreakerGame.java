package puppy.code;

import com.badlogic.gdx.Game;

public class BlockBreakerGame extends Game {
    
		@Override
		public void create () {	
			this.setScreen(new MainMenuScreen(this));
		}

		public void render() {
			super.render();
		}
		
		@Override
		public void dispose () {
			super.dispose();
		}
}

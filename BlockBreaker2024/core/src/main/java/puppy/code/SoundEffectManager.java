package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

public class SoundEffectManager {
    private Sound blockCollisionSound;
    private Sound paddleCollisionSound;
    private Sound gameOverSound;

    public SoundEffectManager() {
        blockCollisionSound = Gdx.audio.newSound(Gdx.files.internal("block_collision.mp3"));
        paddleCollisionSound = Gdx.audio.newSound(Gdx.files.internal("paddle_collision.mp3"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("GameOver.mp3"));
    }

    public void playBlockCollisionSound() {
        blockCollisionSound.play();
    }

    public void playPaddleCollisionSound() {
        paddleCollisionSound.play();
    }
    
    public void playGameOversound() {
    	gameOverSound.play();
    }

    public void dispose() {
        blockCollisionSound.dispose();
        paddleCollisionSound.dispose();
        gameOverSound.dispose();
    }
}

package puppy.code;

import com.badlogic.gdx.utils.Timer;

public class SpeedUpEffect implements BlockEffect{
	public void applyEffect(PingBall ball, Paddle paddle) {
		double originalSpeed = ball.getSpeed();
        ball.setSpeed(ball.getSpeed() * 1.2f); // Aumenta la velocidad en un 20%
        GameScreen.setEffectMessage("¡Efecto Aplicado:  Increase Ball Speed!");
        
        //revertir luego de 5s
        Timer.schedule(new Timer.Task() {
        	public void run() {
        		ball.setSpeed(originalSpeed);
        	}
        }, 5); //restaura luego de 5s
    }
}
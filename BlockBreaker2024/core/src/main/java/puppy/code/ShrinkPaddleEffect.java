package puppy.code;

import com.badlogic.gdx.utils.Timer;

public class ShrinkPaddleEffect implements BlockEffect{
	public void applyEffect(PingBall ball, Paddle paddle) {
		float originalWidth = paddle.getWidth();
        paddle.setWidth(paddle.getWidth() * 0.8f); // Reduce el tamaño en un 20%
        
        //revertir luego de cierto tiempo
        Timer.schedule(new Timer.Task() {;
        	public void run() {
        		paddle.setWidth(originalWidth); //restaura el tamaño de paddle
        	}
        }, 5); //se restaura luego de 5 segundos
    }
}

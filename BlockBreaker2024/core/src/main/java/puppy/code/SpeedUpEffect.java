package puppy.code;

public class SpeedUpEffect implements BlockEffect{
	public void applyEffect(PingBall ball, Paddle paddle) {
        ball.setSpeed(ball.getSpeed() * 1.2f); // Aumenta la velocidad en un 20%
    }
}

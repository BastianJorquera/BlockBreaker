package puppy.code;

public class ShrinkPaddleEffect implements BlockEffect{
	public void applyEffect(PingBall ball, Paddle paddle) {
        paddle.setWidth(paddle.getWidth() * 0.8f); // Reduce el tamaño en un 20%
    }
}

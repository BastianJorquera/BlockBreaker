package puppy.code;

public class RandomEffectBlockFactory implements BlockFactory {

    public Block createBlock(float x, float y, float width, float height) {
        BlockEffect effect = getRandomEffect();
        return new Block(x, y, width, height, effect);
    }

    private BlockEffect getRandomEffect() {
        double random = Math.random();
        if (random < 0.5) return null; // 50% sin efecto
        if (random < 0.75) return new SpeedUpEffect(); // 25% velocidad
        return new ShrinkPaddleEffect(); // 25% reducir paddle
    }
}
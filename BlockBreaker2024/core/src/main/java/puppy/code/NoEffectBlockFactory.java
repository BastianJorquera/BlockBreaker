package puppy.code;

public class NoEffectBlockFactory implements BlockFactory {
    @Override
    public Block createBlock(float x, float y, float width, float height) {
        return new Block(x, y, width, height, null); // Sin efecto
    }
}
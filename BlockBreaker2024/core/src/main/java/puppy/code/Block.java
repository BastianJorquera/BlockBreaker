package puppy.code;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;


public class Block extends GameObject implements Collidable {
    Color cc;
    boolean destroyed;
    private BlockEffect effect; //nuevo atributo para efectos
    
    public Block(float x, float y, float width, float height, BlockEffect effect) {
    	super(x, y, width, height); //constructor de clase super
    	
        destroyed = false;
        Random r = new Random((long) (x+y));
        
        cc = new Color(0.1f+r.nextFloat(), r.nextFloat(), r.nextFloat(), 1);
        this.effect = effect;
    }
    public void draw(ShapeRenderer shape){
    	shape.setColor(cc);
        shape.rect(x, y, width, height);
    }
    
    public boolean collidesWith(GameObject obj) {
    	
    	//colision con blocke
    	boolean intersectsX = (obj.x + obj.width >= x) && (obj.x <= x + width);
        boolean intersectsY = (obj.y + obj.height >= y) && (obj.y <= y + height);
        return intersectsX && intersectsY;
    }
        
    public void onCollision(GameObject obj1, GameObject obj2) {
        if (obj1 instanceof PingBall) {
            destroyed = true; // Bloque se marca como destruido al colisionar con la bola
            if(effect != null) {
            	effect.applyEffect((PingBall)obj1, (Paddle)obj2);
            }
        }
    }
	@Override
	public void onCollision(GameObject obj) {
		
	}
}
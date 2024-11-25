package puppy.code;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class GameObject{
	protected float x, y, width, height;
	
	public GameObject(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	//secuencia Template Method
	public void performActions(ShapeRenderer shape) {
		update();
		draw(shape);
	}
	
	//metodos que deben usar las clases hijas
	protected abstract void update();
	protected abstract void draw(ShapeRenderer shape);
	
	
	// Getters y Setters
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
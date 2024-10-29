package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle extends GameObject {
    public Paddle(int x, int y, int width, int height) {
    	super(x, y, width, height);
    }
    
    //dibujar plataforma y actualizar su posicion segun las teclas presionadas
	public void draw(ShapeRenderer shape){
		shape.setColor(Color.BLUE);
        int newX = x;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) newX = x - 15; //mover plataforma a la izquierda
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) newX = x + 15;//mover plataforma a la derecha

        //evitar que la platforma no se salga de la pantalla
        if (newX > 0 && newX + width < Gdx.graphics.getWidth()) { 
            x = newX;
        }
        shape.rect(x, y, width, height); //dibuja paddle en la nuevo posicion
    }
    
    
}

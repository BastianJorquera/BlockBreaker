package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PingBall extends GameObject implements Collidable{
    private double speed;
    private double direction; // Dirección en grados (0-360)
    private Color color = Color.WHITE;
    private boolean estaQuieto; //estado inicial
    private SoundEffectManager soundManager; //administrar efectos de sonido

    public PingBall(int x, int y, int size, double speed, double direction, boolean iniciaQuieto) {
        super(x, y, size, size); // size ahora es ancho y alto de la pelota
        this.speed = speed;
        this.direction = direction;
        estaQuieto = iniciaQuieto;
        soundManager = new SoundEffectManager();
    }
    
    //verificar si esta quieto
    public boolean estaQuieto() {
    	return estaQuieto;
    }
    
    //actualizar posicion X e Y
    public void setXY (int newX, int newY) {
    	this.x = newX;
    	this.y = newY;
    }
    
    public void setEstaQuieto(boolean estado) {
    	this.estaQuieto = estado;
    }
    
    //dibuja la pelota
    public void draw(ShapeRenderer shape) {
        shape.setColor(color);
        shape.circle(x, y, width / 2); //width es ahora el radio por lo que eran 2 datos, se parte por 2
    }


    public boolean collidesWith(GameObject obj) {
        boolean intersectsX = (obj.x + obj.width >= x - width) && (obj.x <= x + width);
        boolean intersectsY = (obj.y + obj.height >= y - width) && (obj.y <= y + width);
        return intersectsX && intersectsY;
    }

    @Override
    public void onCollision(GameObject obj) {
    	if (obj instanceof Paddle) {
    		soundManager.playPaddleCollisionSound();
            // cambia la direccion de la bola de forma aleatoria
            double randomAngle = (Math.random() * 90); // angulo aleatorio
            direction = (direction + randomAngle) % 360; // Ajusta la direccion y asegura que este en el rango [0, 360]
            if (direction > 180 || direction < 0) {
            	direction-= 180; //mantiene direccion hacia arriba
            }
            color = Color.GREEN; // cambia el color para indicar la colision con el paddle
        } else if (obj instanceof Block) {
        	soundManager.playBlockCollisionSound();
            direction = -direction; // rebote vertical al chocar con el bloque
            color = Color.RED; // cambia el color para indicar la colision con un bloque
        }
    }
    
    //actualiza posicion de la pelota segun velocidad y direccion
    public void update() {
        if (estaQuieto) return;

        int xSpeed = (int) (speed * Math.cos(Math.toRadians(direction)));
        int ySpeed = (int) (speed * Math.sin(Math.toRadians(direction)));

        x += xSpeed;
        y += ySpeed;
        
        //verificar choque con bordes de pantalla
        if (x - width/2 < 0 || x + width > Gdx.graphics.getWidth()) {
            direction = 180 - direction; // choca en bordes
        }
        if (y + width/2 > Gdx.graphics.getHeight()) {
            direction = -direction; //choca en la parte superior
        }
    }
}

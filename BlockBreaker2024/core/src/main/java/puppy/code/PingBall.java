package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PingBall extends GameObject implements Collidable{
    private double speed;
    private double direction; // Dirección en grados (0-360)
    private Color color = Color.WHITE;
    private boolean estaQuieto;
    private SoundEffectManager soundManager;

    public PingBall(float x, float y, float size, double speed, double direction, boolean iniciaQuieto) {
        super(x, y, size, size); // size ahora es ancho y alto de la pelota
        this.speed = speed;
        this.direction = direction; // Inicializa la dirección
        this.estaQuieto = iniciaQuieto;
        this.soundManager = SoundEffectManager.getInstance(); //usa instancia Singleton
    }
    
    //verificar si esta quieto
    public boolean estaQuieto() {
    	return estaQuieto;
    }
    
    //actualizar posicion X e Y
    public void setXY (float newX, float newY) {
    	this.x = newX;
    	this.y = newY;
    }
    
    public void setEstaQuieto(boolean estado) {
    	this.estaQuieto = estado;
    }
    
    public void draw(ShapeRenderer shape) {
        shape.setColor(color);
        shape.circle(x, y, width / 2); //width es ahora el radio por lo que eran 2 datos, se parte por 2
    }
    
    public void setSpeed(double speed) {
    	this.speed=speed;
    }
    
    public double getSpeed() {
    	return speed;
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

            // Impacto relativo en el Paddle
            float paddleCenterX = obj.getX() + obj.getWidth() / 2;
            float impactRelative = (getX() - paddleCenterX) / (obj.getWidth() / 2);
            float maxBounceAngle = 75; // Ángulo máximo de rebote
            float bounceAngle = impactRelative * maxBounceAngle;
            direction = 90 - bounceAngle;

            // Cambia color de la pelota
            color = Color.GREEN;

        } else if (obj instanceof Block) {
            soundManager.playBlockCollisionSound();

            // Determinar lado de impacto
            if (getY() > obj.getY() + obj.getHeight()) {
                direction = -Math.abs(direction); // Rebote hacia arriba
            } else if (getY() + getHeight() < obj.getY()) {
                direction = Math.abs(direction); // Rebote hacia abajo
            } else if (getX() + getWidth() < obj.getX()) {
                direction = 180 - direction; // Rebote desde la izquierda
            } else {
                direction = 180 - direction; // Rebote desde la derecha
            }

            // Cambia color de la pelota
            color = Color.RED;

            // Incrementa velocidad tras un rebote
            speed += 0.05f;
        }

        // Asegura que la dirección no sea perfectamente vertical
        if (direction > 85 && direction < 95) {
            direction = 85;
        } else if (direction > 265 && direction < 275) {
            direction = 275;
        }
    }

    public void update() {
        if (estaQuieto) return;

        int xSpeed = (int) (speed * Math.cos(Math.toRadians(direction)));
        int ySpeed = (int) (speed * Math.sin(Math.toRadians(direction)));

        x += xSpeed;
        y += ySpeed;
        
        //verificar choque con bordes de pantalla
        if (x - width/2 < 0 || x + width > Gdx.graphics.getWidth()) {
            direction = 180 - direction; // Rebote en bordes
        }
        if (y + width/2 > Gdx.graphics.getHeight()) {
            direction = -direction; //choca en la parte superior
        }
    }
}

package puppy.code;

public interface Collidable {
	//verifica si un objeto colisiona con otro
    boolean collidesWith(GameObject obj);
    
    //ve que acciones se toman cuando hay colision
    void onCollision(GameObject obj);
    
}
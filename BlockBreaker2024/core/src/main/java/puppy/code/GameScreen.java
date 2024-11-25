package puppy.code;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen{
	private BlockBreakerGame game;
	private OrthographicCamera camera;
	private SpriteBatch batch;	   
	private BitmapFont font;
	private ShapeRenderer shape;
	private PingBall ball;
	private Paddle paddle;
	private ArrayList<Block> blocks = new ArrayList<>();
	private int vidas;
	private int puntaje;
	private int nivel;
	private SoundEffectManager soundManager;
	private boolean isPaused; // Estado de pausa
    private Stage pauseStage; // Escenario para el menú de pausa
    private Skin skin; // Skin para los botones
    
    //Mensaje temporal
    private static String effectMessage = ""; // Mensaje que se mostrará
    private static float messageTimer = 0;    // Tiempo restante para mostrar el mensaje
    private static final float MESSAGE_DURATION = 2.0f; // Duración del mensaje en segundos


    public GameScreen(BlockBreakerGame game) {
        this.game = game;
        // Inicializa aquí los objetos y lógica del juego
        camera = new OrthographicCamera();
	    camera.setToOrtho(false, 800, 480);
	    batch = new SpriteBatch();
	    font = new BitmapFont();
	    font.getData().setScale(3, 2);
	    nivel = 1;
	    crearBloques(2+nivel);
		
	    shape = new ShapeRenderer();
	    ball = new PingBall(Gdx.graphics.getWidth()/2-10, 41, 10, 6, 90, true);
	    paddle = new Paddle(Gdx.graphics.getWidth()/2-50,40,100,10);
	    vidas = 3;
	    puntaje = 0;    
	    soundManager = SoundEffectManager.getInstance(); // usa instancia Singleton
	    
	    isPaused = false;
        setupPauseMenu();
    }
    
    public static void setEffectMessage(String message) {
        effectMessage = message;
        messageTimer = MESSAGE_DURATION;
    }
    
	public void crearBloques(int filas) {
		blocks.clear();
		int blockWidth = 70;
	    int blockHeight = 26;
	    int y = Gdx.graphics.getHeight();
	    
	    BlockFactory noEffectFactory = new NoEffectBlockFactory();
	    BlockFactory randomEffectFactory = new RandomEffectBlockFactory();
	    
	    
	    for (int cont = 0; cont<filas; cont++ ) {
	    	y -= blockHeight+10;
	    	for (int x = 5; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
	    		Block block;
	    		if (Math.random() < 0.7) {
	    			block= noEffectFactory.createBlock(x,y, blockWidth, blockHeight);
	    		}
	    		else {
	    			block = randomEffectFactory.createBlock(x,y,blockWidth, blockHeight);
	    		}
	    		
	    		blocks.add(block);
	        }
	    }
	}
	
	public void dibujaTextos() {
		//actualizar matrices de la cámara
		camera.update();
		//actualizar 
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//dibujar textos
		font.draw(batch, "Puntos: " + puntaje, 10, 25);
		font.draw(batch, "Vidas : " + vidas, Gdx.graphics.getWidth()-20, 25);
		batch.end();
	}
	
	private void setupPauseMenu() {
        // Configurar el escenario de pausa
        pauseStage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json")); // Asegúrate de tener un archivo skin JSON para los estilos de los botones

        Table table = new Table();
        table.setFillParent(true);

        TextButton continueButton = new TextButton("Continuar", skin);
        TextButton exitButton = new TextButton("Volver al Menu", skin);
        TextButton exitGameButton = new TextButton("Cerrar Juego", skin);

        // Acción al presionar "Continuar"
        continueButton.addListener(event -> {
            if (event.isHandled()) {
                isPaused = false; // Salir de la pausa
                Gdx.input.setInputProcessor(null); // Desactivar el menú de pausa
            }
            return true;
        });

        // Acción al presionar "Salir"
        exitButton.addListener(event -> {
            if (event.isHandled()) {
                game.setScreen(new MainMenuScreen(game)); // Volver al menú principal
            }
            return true;
        });
        
     // Listener para cerrar el juego completamente
        exitGameButton.addListener(event -> {
            if (event.isHandled()) {
                Gdx.app.exit(); // Cerrar la aplicación
            }
            return true;
        });

        // Añadir botones a la tabla
        table.add(continueButton).pad(10).row();
        table.add(exitButton).pad(10).row();
        table.add(exitGameButton).pad(10);

        pauseStage.addActor(table);
    }
    
    

    public void show() {}

    public void render(float delta) {
    	
    	// Manejar entrada para pausar el juego
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isPaused = !isPaused; // Alternar estado de pausa
            if (isPaused) {
                Gdx.input.setInputProcessor(pauseStage); // Activar el menú de pausa
            } else {
                Gdx.input.setInputProcessor(null); // Retomar el juego
            }
        }
        
        if (isPaused) {
            // Dibujar menú de pausa
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            pauseStage.act(delta);
            pauseStage.draw();
            return; // Detener la actualización del juego mientras está en pausa
        }
        
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 		
	        shape.begin(ShapeRenderer.ShapeType.Filled);
	        paddle.draw(shape);
	        paddle.update();
	        // monitorear inicio del juego
	        if (ball.estaQuieto()) {
	        	ball.setXY(paddle.getX()+paddle.getWidth()/2-5, paddle.getY()+paddle.getHeight()+11);
	        	if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
	        		ball.setEstaQuieto(false);
	        	}
	        }else ball.update();
	        
	        //verificar si se fue la bola x abajo
	        if (ball.getY()<0) {
	        	vidas--;
	        	//nivel = 1;
	        	ball = new PingBall(paddle.getX()+paddle.getWidth()/2-5, paddle.getY()+paddle.getHeight()+11, 10, 6, 90, true);
	        }
	        // verificar game over
	        if (vidas<=0) {
	        	soundManager.playGameOversound();
	        	resetGame();   	
	        }
	        // verificar si el nivel se terminó
	        if (blocks.isEmpty()) {
	        	nivel++;
	        	crearBloques(2+nivel);
	        	ball = new PingBall(paddle.getX()+paddle.getWidth()/2-5, paddle.getY()+paddle.getHeight()+11, 10, 6, 10, true);
	        }    	
	        //dibujar bloques y ver colisiones
	        for (Block block : blocks) {
	            block.draw(shape);
	            if (ball.collidesWith(block)) {
	                ball.onCollision(block);
	                block.onCollision(ball, paddle);
	                if (block.destroyed) {
	                    puntaje++;
	                }
	            }
	        }
	        
	        blocks.removeIf(block -> block.destroyed);
	        
	        //si pelota colisiona con paddle
	        if (ball.collidesWith(paddle)) {
	            ball.onCollision(paddle);
	        }
	        
	        ball.draw(shape);
	        shape.end();
	        dibujaTextos();
	        
	     // Actualizar el temporizador del mensaje
	        if (messageTimer > 0) {
	            messageTimer -= Gdx.graphics.getDeltaTime();
	        }

	        // Dibujar el mensaje si el temporizador es mayor a cero
	        if (messageTimer > 0) {
	            batch.begin();
	            font.setColor(1, 1, 1, 1); // Color blanco
	            font.draw(batch, effectMessage, Gdx.graphics.getWidth() -600 , 300);
	            batch.end();
	        }
	        
		}
    
    private void resetGame() {
		vidas = 3;
		nivel = 1;
		puntaje = 0;
		crearBloques(2+nivel);
	}
	
	

    public void resize(int width, int height) {}

    public void pause() {}

    public void resume() {}

    public void hide() {}

    public void dispose() {
    	batch.dispose();
        font.dispose();
        shape.dispose();
    }
}

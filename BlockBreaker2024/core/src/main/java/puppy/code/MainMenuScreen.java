package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScreen implements Screen {

    private BlockBreakerGame game; // Referencia al juego principal
    private SpriteBatch batch;
    private BitmapFont font;

    public MainMenuScreen(BlockBreakerGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        font.getData().setScale(2); // Tamaño del texto
    }

    public void show() {}

    public void render(float delta) {
        // Limpiar pantalla
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "BLOCK BREAKER", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 + 100);
        font.draw(batch, "Presiona CUALQUIER tecla para empezar", Gdx.graphics.getWidth() / 2 - 250, Gdx.graphics.getHeight() / 2 + 50);
        font.draw(batch, "Use Flecha Izq. y Der. para moverse", Gdx.graphics.getWidth() / 2 - 250, Gdx.graphics.getHeight() / 2 - 100);
        font.draw(batch, "Y ESPACIO para lanzar la pelota", Gdx.graphics.getWidth() / 2 - 250, Gdx.graphics.getHeight() / 2 - 150);
        batch.end();

        // Detectar la tecla ENTER para iniciar el juego
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ANY_KEY)) {
            game.setScreen(new GameScreen(game)); // Cambia a la pantalla del juego
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}

package me.lihq.game.screen.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.lihq.game.GameMain;

/**
 * Reusable Main initMenu UI, can be used for the pause screen as well.
 */
public class WonGame {
    // The background colour of the menu.
    private static final Color BACKGROUND_COLOR = Color.GRAY;

    // The width of the menu.
    private static final int WIDTH = Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8;

    /**
     * The stage to render the menu to.
     */
    public Stage stage;

    // The default button skins.
    private Skin buttonSkin;

    // This is the camera for the menu.
    private OrthographicCamera camera;

    // This is the sprite batch of the menu that elements are rendered on.
    private SpriteBatch batch;

    // The label used to display the score.
    private Label score;

    /**
     * Constructor for the menu.
     *
     * @param game The game object the menu is being loaded for.
     */
    public WonGame(final GameMain game) {
        //Initialising new stage
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        //Initialising the skin made for the buttons
        initButtonSkin();

        //Loading the menu or pause screen
        initMenu(game);
    }

    // This method is called if you want to initialise the main menu.
    private void initMenu(final GameMain game) {
        //Creating the buttons using the button skin
        //An if statement that lets the same class be used for both the pause and main menu
        //screens. It also prints an error message to the console if called using an incorrect argument

        BitmapFont font = new BitmapFont();

        LabelStyle textStyle = new LabelStyle(font, Color.RED);

        //Creating the label containing text and determining  its size and location on screen
        Label text;

        TextButton newStartButton = new TextButton("New game", buttonSkin);

        text = new Label("Well Done! You won the Game!", textStyle);
        text.setFontScale(2, 2);

        float x1 = Gdx.graphics.getWidth() / 2 - text.getWidth();
        float y1a = Gdx.graphics.getHeight() / 2 + Gdx.graphics.getHeight() / 3;
        float y1b = Gdx.graphics.getHeight() / 16;
        text.setBounds(x1, y1a + y1b, text.getWidth(), text.getHeight());

        String finalScore;

        this.score = new Label("Score : NA", textStyle);
        this.score.setAlignment(1, 1);
        this.score.setFontScale(2, 2);

        x1 = Gdx.graphics.getWidth() / 2 - this.score.getWidth() / 2;
        y1a = Gdx.graphics.getHeight() / 2 + Gdx.graphics.getHeight() / 4;
        this.score.setBounds(x1, y1a, this.score.getWidth(), this.score.getHeight());

        //Making the "New Game" button clickable and causing it to start the game
        newStartButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Open creation screen clicked");
                game.setScreen(game.creationScreen);
            }
        });

        newStartButton.setPosition(WIDTH, Gdx.graphics.getHeight() / 2 - Gdx.graphics.getHeight() / 5);

        TextButton quit = new TextButton("Quit", buttonSkin);
        quit.setPosition(WIDTH, Gdx.graphics.getHeight() / 2 - Gdx.graphics.getHeight() / 3);

        //Loading the buttons onto the stage
        this.stage.addActor(newStartButton);
        this.stage.addActor(text);
        this.stage.addActor(score);
        this.stage.addActor(quit);



        //Making the "Quit" button clickable and causing it to close the game
        quit.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }
        });
    }


    // This method creates the skins for the buttons.
    private void initButtonSkin() {
        //Create a font
        BitmapFont font = new BitmapFont();
        buttonSkin = new Skin();
        buttonSkin.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() / 4, (int) Gdx.graphics.getHeight() / 10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.ORANGE);
        pixmap.fill();
        buttonSkin.add("background", new Texture(pixmap));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = buttonSkin.newDrawable("background", BACKGROUND_COLOR);
        textButtonStyle.down = buttonSkin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = buttonSkin.newDrawable("background", BACKGROUND_COLOR);
        textButtonStyle.over = buttonSkin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = buttonSkin.getFont("default");
        buttonSkin.add("default", textButtonStyle);
    }

    public void update(){
        String finalScore = String.format("\nFinal Score: \n\n %1$d", GameMain.me.scoreTracker.collectScore((x, y, z, i) -> {
            return -y + z + i;
        }));

        this.score.setText(finalScore);
    }

    /**
     * This method is called to render the main menu to the stage.
     */
    public void render() {
        //Determining the background colour of the menu.
        Gdx.gl.glClearColor(135, 206, 235, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Rendering the buttons
        stage.act();
        stage.draw();
    }

    /**
     * This method disposes of all elements.
     */
    public void dispose() {
        //Called when disposing the main menu.
        stage.dispose();
        batch.dispose();
    }

    /**
     * This method is called when the window is resized.
     *
     * @param width The new width.
     * @param height The new height.
     */
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}

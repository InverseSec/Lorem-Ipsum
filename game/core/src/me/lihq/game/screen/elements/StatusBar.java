package me.lihq.game.screen.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.lihq.game.GameMain;
import me.lihq.game.screen.PauseScreen;

/**
 * The status bar shown throughout the game.
 * <p>
 * Contains UI controls for presenting the game status to the player.
 * </p>
 */
public class StatusBar {
    /**
     * The height of the StatusBar
     */
    public static final int HEIGHT = 50;

    // The amount of items that are in the StatusBar. It is used to set width of controls on bar.
    private static final int ITEM_COUNT = 4;

    // The width of the StatusBar.
    private static final int WIDTH = (int) Gdx.graphics.getWidth() / ITEM_COUNT;

    // The background color of the StatusBar.
    private static final Color BACKGROUND_COLOR = Color.GRAY;

    /**
     * The stage to render the elements to.
     */
    public Stage stage;

    // The different skins for different elements.
    private Skin buttonSkin;
    private Skin labelSkin;
    private PauseScreen pauseScreen;
    private Label scoreLabel;
    private Label personalityLabel;
    private GameMain game;

    /**
     * The initializer for the StatusBar.
     * <p>
     * Sets up UI controls and adds them to the stage ready for rendering.
     *
     * @param game The game object of this status bar.
     * </p>
     */
    public StatusBar(final GameMain game) {
        this.game = game;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        this.pauseScreen = new PauseScreen(game);
        initSkins();

        Table statusBar = new Table();
        statusBar.setSize(Gdx.graphics.getWidth(), HEIGHT);
        statusBar.setPosition(0, 0);
        statusBar.row().height(HEIGHT);
        statusBar.defaults().width(WIDTH);

        this.scoreLabel = new Label("", labelSkin);
        this.scoreLabel.setAlignment(Align.center, Align.center);
        statusBar.add(scoreLabel).uniform();

        this.personalityLabel = new Label("", labelSkin);
        this.personalityLabel.setAlignment(Align.center, Align.center);
        statusBar.add(personalityLabel).uniform();

        TextButton inventoryButton = new TextButton("Journal", buttonSkin);
        inventoryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.journalScreen);
            }
        });
        statusBar.add(inventoryButton).uniform();

        TextButton pauseButton = new TextButton("Pause", buttonSkin);
        statusBar.add(pauseButton).uniform();
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(pauseScreen);
            }
        });

        stage.addActor(statusBar);
    }

    /**
     * Updates the status bar.
     */
    public void updateMain() {
        this.scoreLabel.setText(String.format("Score: %1$d", this.game.scoreTracker.collectScore((x, y, z, i) -> {
            return -y + z + i;
        })));
        this.personalityLabel.setText(String.format("Personality: %1$d", this.game.player.getPersonalityLevel()));
    }

    /**
     * Renders the status bar.
     */
    public void renderMain() {
        stage.act();
        stage.draw();
    }

    /**
     * This method is called on a window resize.
     *
     * @param width the new width.
     * @param height the new height.
     */
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * This disposes all the elements.
     */
    public void dispose() {
        stage.dispose();
    }

    // Sets up skin variables used for defining UI control styles.
    private void initSkins() {
        initButtonSkin();
        initLabelSkin();
    }

    // Sets up the skin for buttons on the status bar.
    private void initButtonSkin() {
        //Create a font
        BitmapFont font = new BitmapFont();
        buttonSkin = new Skin();
        buttonSkin.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        buttonSkin.add("background", new Texture(pixmap));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = buttonSkin.newDrawable("background", BACKGROUND_COLOR);
        textButtonStyle.down = buttonSkin.newDrawable("background", Color.BLACK);
        textButtonStyle.checked = buttonSkin.newDrawable("background", BACKGROUND_COLOR);
        textButtonStyle.over = buttonSkin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.font = buttonSkin.getFont("default");
        buttonSkin.add("default", textButtonStyle);

    }

    // Sets up the skin for labels on the status bar.
    private void initLabelSkin()
    {
        //Create a font
        BitmapFont font = new BitmapFont();
        labelSkin = new Skin();

        //Create a texture
        Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Pixmap.Format.RGB888);
        pixmap.setColor(BACKGROUND_COLOR);
        pixmap.fill();
        labelSkin.add("background", new Texture(pixmap));

        //Create a button style
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        labelStyle.background = labelSkin.getDrawable("background");
        labelSkin.add("default", labelStyle);

    }
}

package me.lihq.game.models;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by joeshuff on 20/11/2016.
 */
public abstract class Character {

    //Storing the characters coordinates on the map
    protected int x = 0;
    protected int y = 0;
    protected int offsetX = 0;
    protected int offsetY = 0;

    //Stores the location path of the characters source image sprite sheet
    private String imagePath = "";

    public void setX(int x){this.x = x;}

    public void setY(int y){this.y = y;}

    public int getX() {return this.x;}

    public int getY() {return this.y;}

    public void setOffsetX(int offsetX) {this.offsetX = offsetX;}

    public void setOffsetY(int offsetY) {this.offsetY = offsetY;}

    public int getOffsetX() {return this.offsetX;}

    public int getOffsetY() {return this.offsetY;}

    public void setImagePath(String path) {this.imagePath = path;}

    public String getImagePath() {return this.imagePath;}

    abstract void move();
}

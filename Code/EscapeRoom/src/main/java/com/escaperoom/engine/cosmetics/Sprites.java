package com.escaperoom.engine.cosmetics;
public class Sprites {
    public double x;
    public double y;
    public Textures texture;
    public boolean isSolid; // flag for if player can move through the sprite
    public int uDiv,vDiv;
    public double uMove;

    public Sprites(double _x, double _y, Textures tex, boolean solid,int _uDiv, int _vDiv, double _uMove) {
        x = _x;
        y = _y;
        texture = tex;
        isSolid = solid;
        uDiv = _uDiv;
        vDiv = _vDiv;
        uMove = _uMove;
    }

}

package com.base.engine.game;

import com.base.engine.core.CoreEngine;

public class Main
{
    public static void main(String[] args)
    {
        CoreEngine engine = new CoreEngine(1240, 768, 60, new TestGame());
        engine.createWindow("3D Game Engine");
        engine.start();
    }
}

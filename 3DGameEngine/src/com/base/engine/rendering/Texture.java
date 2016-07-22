package com.base.engine.rendering;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;

import org.newdawn.slick.opengl.TextureLoader;

public class Texture
{
    private int id;

    public Texture(String filename)
    {
        this(loadTexture(filename));
    }

    public Texture(int id)
    {
        this.id = id;
    }

    private static int loadTexture(String filename)
    {
        String[] splitArray = filename.split("\\.");
        String ext = splitArray[splitArray.length - 1];

        try
        {
            int id = TextureLoader.getTexture(ext, new FileInputStream(new File("./res/textures/" + filename))).getTextureID();
            return id;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        return 0;
    }

    public void bind()
    {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public int getID()
    {
        return id;
    }
}

package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Window;

public class FreeLook extends GameComponent
{
    public static final Vector3f yAxis = new Vector3f(0, 1, 0);
    public static final Vector2f centerPosition = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);

    private boolean mouseLocked = false;
    private float sensitivity;
    private int unlockMouseKey;

    public FreeLook(float sensitivity)
    {
        this(sensitivity, Input.KEY_ESCAPE);
    }

    public FreeLook(float sensitivity, int unlockMouseKey)
    {
        this.sensitivity = sensitivity;
        this.unlockMouseKey = unlockMouseKey;
    }

    public void input(float delta)
    {
        if (Input.getKey(Input.KEY_ESCAPE))
        {
            Input.setCursor(true);
            mouseLocked = false;
        }
        if (Input.getMouseDown(0))
        {
            Input.setMousePosition(centerPosition);
            Input.setCursor(false);
            mouseLocked = true;
        }

        if (mouseLocked)
        {
            Vector2f deltaPosition = Input.getMousePosition().sub(centerPosition);

            boolean rotY = deltaPosition.getX() != 0;
            boolean rotX = deltaPosition.getY() != 0;

            if (rotY)
                getTransform().rotate((float)Math.toRadians(deltaPosition.getX() * sensitivity), yAxis);
            if (rotX)
                getTransform().rotate((float)Math.toRadians(-deltaPosition.getY() * sensitivity), getTransform().getRot().getRight());

            if (rotY || rotX)
                Input.setMousePosition(new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2));
        }
    }
}

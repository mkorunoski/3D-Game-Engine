package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.Vector3f;

public class FreeMove extends GameComponent
{
    private float speed;
    private int forwardKey;
    private int backKey;
    private int leftKey;
    private int rightKey;

    public FreeMove(float speed)
    {
        this(speed, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D);
    }

    public FreeMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey)
    {
        this.speed = speed;
        this.forwardKey = forwardKey;
        this.backKey = backKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
    }

    public void input(float delta)
    {
        float moveAmount = speed * delta;

        if (Input.getKey(Input.KEY_W))
            move(getTransform().getRot().getForward(), moveAmount);
        if (Input.getKey(Input.KEY_S))
            move(getTransform().getRot().getBack(), moveAmount);
        if (Input.getKey(Input.KEY_A))
            move(getTransform().getRot().getLeft(), moveAmount);
        if (Input.getKey(Input.KEY_D))
            move(getTransform().getRot().getRight(), moveAmount);
    }

    public void move(Vector3f direction, float amount)
    {
        getTransform().setPos(getTransform().getPos().add(direction.mul(amount)));
    }
}

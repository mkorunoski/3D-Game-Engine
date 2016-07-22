package com.base.engine.rendering;

import com.base.engine.core.*;

public class Camera
{
    public static final Vector3f yAxis = new Vector3f(0, 1, 0);
    boolean mouseLocked = false;
    Vector2f centerPosition = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);
    private Vector3f pos;
    private Vector3f forward;
    private Vector3f up;
    private Matrix4f projection;

    public Camera(float fov, float aspect, float zNear, float zFar)
    {
        this.pos = new Vector3f(0, 0, 0);
        this.forward = new Vector3f(0, 0, 1).normalized();
        this.up = new Vector3f(0, 1, 0).normalized();
        this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
    }

    public Matrix4f getViewProjection()
    {
        Matrix4f cameraRotation = new Matrix4f().initRotation(forward, up);
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(-pos.getX(), -pos.getY(), -pos.getZ());

        return projection.mul(cameraRotation.mul(cameraTranslation));
    }

    public void input(float delta)
    {
        float moveAmount = 10 * delta;
        float sensitivity = 0.5f;

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

        if (Input.getKey(Input.KEY_W))
        {
            move(getForward(), moveAmount);
        }
        if (Input.getKey(Input.KEY_S))
        {
            move(getForward(), -moveAmount);
        }
        if (Input.getKey(Input.KEY_A))
        {
            move(getLeft(), moveAmount);
        }
        if (Input.getKey(Input.KEY_D))
        {
            move(getRight(), moveAmount);
        }

        if (mouseLocked)
        {
            Vector2f deltaPosition = Input.getMousePosition().sub(centerPosition);

            boolean rotY = deltaPosition.getX() != 0;
            boolean rotX = deltaPosition.getY() != 0;

            if (rotY)
            {
                rotateY(deltaPosition.getX() * sensitivity);
            }
            if (rotX)
            {
                rotateX(-deltaPosition.getY() * sensitivity);
            }

            if (rotY || rotX)
            {
                Input.setMousePosition(new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2));
            }
        }
    }

    public void move(Vector3f direction, float amount)
    {
        pos = pos.add(direction.mul(amount));
    }

    public void rotateY(float angle)
    {
        Vector3f Haxis = yAxis.cross(forward).normalized();

        forward.rotate(angle, yAxis).normalized();

        up = forward.cross(Haxis).normalized();
    }

    public void rotateX(float angle)
    {
        Vector3f Haxis = yAxis.cross(forward).normalized();

        forward.rotate(angle, Haxis).normalized();

        up = forward.cross(Haxis).normalized();
    }

    public Vector3f getLeft()
    {
        Vector3f left = forward.cross(up).normalized();
        return left;
    }

    public Vector3f getRight()
    {
        Vector3f right = up.cross(forward).normalized();
        return right;
    }

    public Vector3f getPos()
    {
        return pos;
    }

    public void setPos(Vector3f pos)
    {
        this.pos = pos;
    }

    public Vector3f getForward()
    {
        return forward;
    }

    public void setForward(Vector3f forward)
    {
        this.forward = forward;
    }

    public Vector3f getUp()
    {
        return up;
    }

    public void setUp(Vector3f up)
    {
        this.up = up;
    }

}

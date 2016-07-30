package com.base.engine.components;

import com.base.engine.core.*;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Window;

public class Camera extends GameComponent
{
    public static final Vector3f yAxis = new Vector3f(0, 1, 0);
    boolean mouseLocked = false;
    Vector2f centerPosition = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);
    private Matrix4f projection;

    public Camera(float fov, float aspect, float zNear, float zFar)
    {
        this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
    }

    public Matrix4f getViewProjection()
    {
        Matrix4f cameraRotation = getTransform().getTransformedRot().conjugate().toRotationMatrix();
        Vector3f cameraPos = getTransform().getTransformedPos().mul(-1);
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());

        return projection.mul(cameraRotation.mul(cameraTranslation));
    }

    @Override
    public void addToRenderingEngine(RenderingEngine renderingEngine)
    {
        renderingEngine.addCamera(this);
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
            move(getTransform().getRot().getForward(), moveAmount);
        }
        if (Input.getKey(Input.KEY_S))
        {
            move(getTransform().getRot().getBack(), moveAmount);
        }
        if (Input.getKey(Input.KEY_A))
        {
            move(getTransform().getRot().getLeft(), moveAmount);
        }
        if (Input.getKey(Input.KEY_D))
        {
            move(getTransform().getRot().getRight(), moveAmount);
        }

        if (mouseLocked)
        {
            Vector2f deltaPosition = Input.getMousePosition().sub(centerPosition);

            boolean rotY = deltaPosition.getX() != 0;
            boolean rotX = deltaPosition.getY() != 0;

            if (rotY)
            {
                getTransform().rotate((float)Math.toRadians(deltaPosition.getX() * sensitivity), yAxis);
//                getTransform().setRot(getTransform().getRot().mul(new Quaternion((float)Math.toRadians(deltaPosition.getX() * sensitivity), yAxis)).normalized());
            }
            if (rotX)
            {
                getTransform().rotate((float)Math.toRadians(-deltaPosition.getY() * sensitivity), getTransform().getRot().getRight());
//                getTransform().setRot(getTransform().getRot().mul(new Quaternion((float)Math.toRadians(-deltaPosition.getY() * sensitivity), getTransform().getTransformedRot().getRight())).normalized());
            }

            if (rotY || rotX)
            {
                Input.setMousePosition(new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2));
            }
        }
    }

    public void move(Vector3f direction, float amount)
    {
        getTransform().setPos(getTransform().getPos().add(direction.mul(amount)));
    }

//    public void rotateY(float angle)
//    {
//        Vector3f Haxis = yAxis.cross(forward).normalized();
//
//        forward = forward.rotate(angle, yAxis).normalized();
//
//        up = forward.cross(Haxis).normalized();
//    }
//
//    public void rotateX(float angle)
//    {
//        Vector3f Haxis = yAxis.cross(forward).normalized();
//
//        forward = forward.rotate(angle, Haxis).normalized();
//
//        up = forward.cross(Haxis).normalized();
//    }
//
//    public Vector3f getLeft()
//    {
//        Vector3f left = forward.cross(up).normalized();
//        return left;
//    }
//
//    public Vector3f getRight()
//    {
//        Vector3f right = up.cross(forward).normalized();
//        return right;
//    }
//
//    public Vector3f getPos()
//    {
//        return pos;
//    }
//
//    public void setPos(Vector3f pos)
//    {
//        this.pos = pos;
//    }
//
//    public Vector3f getForward()
//    {
//        return forward;
//    }
//
//    public void setForward(Vector3f forward)
//    {
//        this.forward = forward;
//    }
//
//    public Vector3f getUp()
//    {
//        return up;
//    }
//
//    public void setUp(Vector3f up)
//    {
//        this.up = up;
//    }
}

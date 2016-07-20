package com.base.engine.rendering;

import com.base.engine.core.Input;
import com.base.engine.core.Time;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;

public class Camera
{
	public static final Vector3f yAxis = new Vector3f(0, 1, 0);
	
	private Vector3f position;
	private Vector3f forward;
	private Vector3f up; 
	
	public Camera()
	{
		this(new Vector3f(0, 0, 0), new Vector3f(0, 0, 1), new Vector3f(0, 1, 0));
	}
	
	public Camera(Vector3f position, Vector3f forward, Vector3f up)
	{
		this.position = position;
		this.forward = forward.normalized();
		this.up = up.normalized();
	}
	
	boolean mouseLocked = false;
	Vector2f centerPosition = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);
	
	public void input()
	{
		float moveAmount = (float)(10 * Time.getDelta());
		float sensitivity = 0.5f;
		
		if(Input.getKey(Input.KEY_ESCAPE))
		{
			Input.setCursor(true);
			mouseLocked = false;
		}
		if(Input.getMouseDown(0))
		{
			Input.setMousePosition(centerPosition);
			Input.setCursor(false);
			mouseLocked = true;
		}
		
		if(Input.getKey(Input.KEY_W))
		{
			move(getForward(), moveAmount);
		}
		if(Input.getKey(Input.KEY_S))
		{
			move(getForward(), -moveAmount);
		}
		if(Input.getKey(Input.KEY_A))
		{
			move(getLeft(), moveAmount);
		}
		if(Input.getKey(Input.KEY_D))
		{
			move(getRight(), moveAmount);
		}
		
		if(mouseLocked)
		{
			Vector2f deltaPosition = Input.getMousePosition().sub(centerPosition);
			
			boolean rotY = deltaPosition.getX() != 0;
			boolean rotX = deltaPosition.getY() != 0;
			
			if(rotY)
			{
				rotateY(deltaPosition.getX() * sensitivity);
			}
			if(rotX)
			{
				rotateX(-deltaPosition.getY() * sensitivity);
			}
			
			if(rotY || rotX)
			{
				Input.setMousePosition(new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2));
			}
		}
	}
	
	public void move(Vector3f direction, float amount)
	{
		position = position.add(direction.mul(amount));		
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

	public Vector3f getPosition()
	{
		return position;
	}

	public void setPosition(Vector3f position)
	{
		this.position = position;
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
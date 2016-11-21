package com.base.engine.core;

import com.base.engine.components.GameComponent;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;

import java.util.ArrayList;

public class GameObject
{
    private ArrayList<GameObject> children;
    private ArrayList<GameComponent> components;
    private Transform transform;
    private CoreEngine engine;

    public GameObject()
    {
        children = new ArrayList<>();
        components = new ArrayList<>();
        transform = new Transform();
        engine = null;
    }

    public void addChild(GameObject child)
    {
        children.add(child);
        child.setEngine(engine);
        child.getTransform().setParent(transform);
    }

    public GameObject addComponent(GameComponent component)
    {
        component.setParent(this);
        components.add(component);

        return this;
    }

    public void inputAll(float delta)
    {
        for (GameObject child : children)
            child.inputAll(delta);

        input(delta);
    }

    public void updateAll(float delta)
    {
        for (GameObject child : children)
            child.updateAll(delta);

        update(delta);
    }

    public void renderAll(Shader shader, RenderingEngine renderingEngine)
    {
        for (GameObject child : children)
            child.renderAll(shader, renderingEngine);

        render(shader, renderingEngine);
    }

    public void input(float delta)
    {
        transform.update();

        for (GameComponent component : components)
            component.input(delta);
    }

    public void update(float delta)
    {
        for (GameComponent component : components)
            component.update(delta);
    }

    public void render(Shader shader, RenderingEngine renderingEngine)
    {
        for (GameComponent component : components)
            component.render(shader, renderingEngine);
    }

    public ArrayList<GameObject> getAllAtached()
    {
        ArrayList<GameObject> result = new ArrayList<>();

        result.add(this);

        for(GameObject child : children)
        {
            result.addAll(child.getAllAtached());
        }

        return result;
    }

    public Transform getTransform()
    {
        return transform;
    }

    public void setEngine(CoreEngine engine)
    {
        if(this.engine != engine)
        {
            this.engine = engine;
            for (GameComponent component : components)
                component.addToEngine(engine);

            for (GameObject child : children)
                child.setEngine(engine);
        }
    }
}

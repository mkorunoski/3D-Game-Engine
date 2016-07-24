package com.base.engine.game;

import com.base.engine.core.Game;
import com.base.engine.core.GameObject;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;
import com.base.engine.rendering.Vertex;

public class TestGame extends Game
{
    public void init()
    {
        Material materialPlane = new Material(new Texture("checkerboard.png"), new Vector3f(1.0f, 1.0f, 1.0f), 1, 8);
//        Material materialMonkey = new Material(new Texture("checkerboard.png"), new Vector3f(1.0f, 1.0f, 1.0f), 1, 256);

        Vertex[] vertices = new Vertex[]
        {
            new Vertex(new Vector3f(-10, 0, -10), new Vector2f(0, 0)),
            new Vertex(new Vector3f(+10, 0, -10), new Vector2f(1, 0)),
            new Vertex(new Vector3f(+10, 0, +10), new Vector2f(1, 1)),
            new Vertex(new Vector3f(-10, 0, +10), new Vector2f(0, 1))
        };
        int[] indices = new int[] {2, 1, 0, 3, 2, 0};
        Mesh planeMesh = new Mesh(vertices, indices, true);
        MeshRenderer planeMeshRenderer = new MeshRenderer(planeMesh, materialPlane);

        GameObject planeObject = new GameObject();
        planeObject.addComponent(planeMeshRenderer);
        planeObject.getTransform().setTranslation(0, -1, 0);

//        Mesh monkeyMesh = new Mesh("monkey.obj");
//        MeshRenderer monkeyMeshRenderer = new MeshRenderer(monkeyMesh, materialMonkey);
//        GameObject monkeyObject = new GameObject();
//        monkeyObject.addComponent(monkeyMeshRenderer);

        getRootObject().addChild(planeObject);
//        getRootObject().addChild(monkeyObject);
    }
}

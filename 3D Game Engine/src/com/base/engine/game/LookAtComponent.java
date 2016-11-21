package com.base.engine.game;

import com.base.engine.components.GameComponent;
import com.base.engine.core.Quaternion;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;

public class LookAtComponent extends GameComponent
{
    private RenderingEngine renderingEngine;

    @Override
    public void update(float delta)
    {
        if(renderingEngine != null)
        {
            Quaternion newRotation = getTransform().getLookAtDirection(renderingEngine.getMainCamera().getTransform().getTransformedPos(), new Vector3f(0,1,0));
            //getTransform().setRot(getTransform().getRot().nlerp(newRotation, 2 * delta, true));
            getTransform().setRot(getTransform().getRot().slerp(newRotation, 2 * delta, true));
        }
    }

    @Override
    public void render(Shader shader, RenderingEngine renderingEngine)
    {
        this.renderingEngine = renderingEngine;
    }
}

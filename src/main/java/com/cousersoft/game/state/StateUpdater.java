package com.cousersoft.game.state;

import com.cousersoft.game.GameContext;
import com.cousersoft.game.input.InputManager;

public interface StateUpdater {
    void update(GameContext ctx, InputManager inputManager);
}

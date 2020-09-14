package edu.chalmers.view.main;

import com.almasb.fxgl.app.scene.*;
import javafx.beans.binding.StringBinding;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import org.jetbrains.annotations.NotNull;

public class MainMenu extends FXGLMenu {

    public MainMenu() {
        super(MenuType.MAIN_MENU);
    }

    @NotNull
    @Override
    protected Button createActionButton(@NotNull StringBinding name, @NotNull Runnable action) {
        return new Button(name.get());
    }

    @NotNull
    @Override
    protected Button createActionButton(@NotNull String name, @NotNull Runnable action) {
        return new Button(name);
    }

    @NotNull
    @Override
    protected Node createBackground(double width, double height) {
        return new Rectangle(width, height, Color.GRAY);
    }

    @NotNull
    @Override
    protected Node createProfileView(@NotNull String profileName) {
        return new Text(profileName);
    }

    @NotNull
    @Override
    protected Node createTitleView(@NotNull String title) {
        return new Text(title);
    }

    @NotNull
    @Override
    protected Node createVersionView(@NotNull String version) {
        return new Text(version);
    }
}

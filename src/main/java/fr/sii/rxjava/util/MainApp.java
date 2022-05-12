package fr.sii.rxjava.util;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static App app;

    public static void startApp(App myApp) {
        app = myApp;
        Application.launch(MainApp.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(app.getClass().getSimpleName());
        MainNode mainNode = new MainNode(stage::close);
        Scene scene = new Scene(mainNode, 800, 800);
        stage.setMinHeight(800);
        stage.setMinWidth(800);
        stage.setMaxHeight(800);
        stage.setMinWidth(800);
        stage.setScene(scene);
        stage.show();
        stage.toFront();
        mainNode.startApp(app);

        stage.setOnCloseRequest(event -> {
            mainNode.dispose();
        });
    }
}

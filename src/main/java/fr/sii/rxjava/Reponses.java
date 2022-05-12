package fr.sii.rxjava;

import com.google.common.collect.Lists;
import fr.sii.rxjava.util.App;
import fr.sii.rxjava.util.MainNode;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;

import static java.lang.Class.forName;
import static java.util.Comparator.comparing;

public class Reponses extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        ObservableList<App> appList = FXCollections.observableArrayList();
        ListView<App> listView = new ListView<>(appList);
        listView.setCellFactory(param -> new AppListCell());
        Scene scene = new Scene(listView);
        primaryStage.setScene(scene);
        Lists.newArrayList("fr.sii.rxjava.exercice.Ex000_DrawFixedPoints",
                        "fr.sii.rxjava.exercice.Ex001_DrawFixedPoints1D",
                        "fr.sii.rxjava.exercice.Ex004_DrawFixedPoints1DAnimated",
                        "fr.sii.rxjava.exercice.Ex006_DrawFixedPoints2D",
                        "fr.sii.rxjava.exercice.Ex010_DrawPointsOnMouseLocation",
                        "fr.sii.rxjava.exercice.Ex015_DrawPointsOnMouseLocationEverySecond",
                        "fr.sii.rxjava.exercice.Ex017_DrawPointsWithClear",
                        "fr.sii.rxjava.exercice.Ex020_IsAllMoviesAreAfter70s",
                        "fr.sii.rxjava.exercice.Ex021_ListOfMoviesWithActresses",
                        "fr.sii.rxjava.exercice.Ex040_DrawFixedPoints2DWithDelay",
                        "fr.sii.rxjava.exercice.Ex050_DrawColoredEvery3Points",
                        "fr.sii.rxjava.exercice.Ex052_LettersOrFigures",
                        "fr.sii.rxjava.exercice.Ex060_ClickPolyLine",
                        "fr.sii.rxjava.exercice.Ex070_Waves",
                        "fr.sii.rxjava.exercice.Ex080_MonoPong",
                        "fr.sii.rxjava.exercice.Ex100_SplitWords",
                        "fr.sii.rxjava.exercice.Ex101_SplitWordsByMouse",
                        "fr.sii.rxjava.exercice.Ex110_ClickColorNames",
                        "fr.sii.rxjava.exercice.Ex120_Distance",
                        "fr.sii.rxjava.exercice.Ex130_ClickCountBySlice",
                        "fr.sii.rxjava.exercice.Ex150_DrawPointBetweenLettersAndFigures",
                        "fr.sii.rxjava.exercice.Ex200_SameLetters",
                        "fr.sii.rxjava.exercice.Ex210_DoubleKeyDuration",
                        "fr.sii.rxjava.exercice.Ex230_ClicksThenDrawPolyline",
                        "fr.sii.rxjava.exercice.Ex250_ColoredLetters",
                        "fr.sii.rxjava.exercice.Ex251_ColoredLetters2",
                        "fr.sii.rxjava.exercice.Ex260_ColoredSnow",
                        "fr.sii.rxjava.exercice.Ex400_ListOfMoviesByDirector",
                        "fr.sii.rxjava.exercice.Ex410_ActorsWithCommonDirector",
                        "fr.sii.rxjava.exercice.Ex410_ActorsWithCommonDirectorV2",
                        "fr.sii.rxjava.exercice.Ex420_SearchMovies",
                        "fr.sii.rxjava.exercice.Ex500_Extrapolation",
                        "fr.sii.rxjava.exercice.Ex700_ClickThenKey",
                        "fr.sii.rxjava.exercice.Ex710_NumbersLikeMusicScore",
                        "fr.sii.rxjava.exercice.Ex720_Pendu",
                        "fr.sii.rxjava.exercice.Ex1000_FlappyBird")
                .stream()
                .map(this::createApp)
                .sorted(comparing(m -> m.getClass().getName()))
                .forEach(appList::add);

        listView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getClickCount() == 2 && listView.getSelectionModel().getSelectedItem() != null){
                open(listView.getSelectionModel().getSelectedItem());
            }
        });
        primaryStage.show();
        primaryStage.toFront();
    }

    private void open(App app){
        Stage newStage = new Stage();
        newStage.setTitle(app.getClass().getSimpleName());
        MainNode node =new MainNode(newStage::close);
        Scene scene = new Scene(node);
        newStage.setScene(scene);
        node.startApp(app);
        newStage.show();
        newStage.toFront();
        newStage.setOnCloseRequest(event -> node.dispose());
    }

    private App createApp(String appName) {
        try {
            Class<?> clazz = forName(appName);
            return (App) clazz.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }


    private class AppListCell extends ListCell<App> {

        @Override
        protected void updateItem(App app, boolean b) {
            super.updateItem(app, b);
            if (!b) {
                setText(app.getClass().getSimpleName());
            } else {
                setText(null);
            }
        }
    }

}

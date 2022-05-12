package fr.sii.rxjava.util;

import fr.sii.rxjava.util.cmds.Drawings;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.jetbrains.annotations.Contract;
import org.pdfsam.rxjavafx.observables.JavaFxObservable;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import static fr.sii.rxjava.util.Pt.pt;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class MainNode extends StackPane implements Inputs {

    private final Canvas canvas;
    private final Runnable close;
    private Disposable appSubscription;

    public MainNode(Runnable close) {
        this.close = close;
        this.minWidth(800);
        this.minHeight(800);
        canvas = new Canvas(800, 800);
        getChildren().setAll(canvas);

    }



    private void draw(Drawings drawings) {
        System.out.println("drawings = " + drawings.values().size());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 800, 800);
        gc.setFont(Font.font(gc.getFont().getFamily(), FontWeight.BOLD, 18));
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(3);
        drawings.drawAll(drawing -> drawing.accept(canvas.getGraphicsContext2D()));
    }

    public void startApp(App app) {
        System.out.println("Demarrage de l'application: " + app.getClass().getSimpleName() + "...");
        final Services services = new ServicesImpl();
        appSubscription = Observable.defer(() ->app.commands(this, services, JavaFxScheduler.platform()))
                .subscribeOn(Schedulers.computation())
                .startWith(Observable.fromIterable(app.description()).map(Cmd::addLog))
                .concatWith(Observable.timer(3, SECONDS).flatMap(v -> Observable.empty()))
                .serialize()
                .scan(Drawings.empty(), (drawings, cmd) -> cmd.apply(drawings, cmd.getDrawing()))
                .sample(40, MILLISECONDS)
                .observeOn(JavaFxScheduler.platform())
                .subscribe(this::draw,
                        ex -> {
                            System.out.println("Error ! :-(");
                            ex.printStackTrace();
                        },
                        () -> {
                            System.out.println("\nCompleted !");
                            close.run();
                        });

    }

    public void dispose() {
        appSubscription.dispose();
    }


    private Observable<javafx.scene.input.MouseEvent> mousePressed() {
        return JavaFxObservable.eventsOf(this, javafx.scene.input.MouseEvent.MOUSE_PRESSED)
                .throttleWithTimeout(150, MILLISECONDS);
    }

    @Override
    @Contract(pure = true)
    public Observable<Pt> mouseXY() {
        return JavaFxObservable.eventsOf(this, MouseEvent.MOUSE_MOVED)
                .map(p -> pt((int) p.getX(), (int) p.getY()));
    }

    @Override
    @Contract(pure = true)
    public Observable<Integer> mouseLeftClickCount() {
        return mousePressed()
                .filter(e -> e.getButton() == MouseButton.PRIMARY)
                .map(javafx.scene.input.MouseEvent::getClickCount);
    }

    @Override
    @Contract(pure = true)
    public Observable<Integer> mouseRightClickCount() {
        return mousePressed()
                .filter(e -> e.getButton() == MouseButton.SECONDARY)
                .map(javafx.scene.input.MouseEvent::getClickCount);
    }

    @Override
    @Contract(pure = true)
    public Observable<Character> keys() {
        return JavaFxObservable.eventsOf(this.getScene(), KeyEvent.KEY_PRESSED)
                .map(e -> e.getText().charAt(0));
    }


}

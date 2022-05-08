package fr.sii.rxjava.util;

import fr.sii.rxjava.util.cmds.Command;
import fr.sii.rxjava.util.cmds.Drawings;
import org.jetbrains.annotations.Contract;
import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.subjects.BehaviorSubject;
import rx.swing.sources.KeyEventSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import static fr.sii.rxjava.util.Pt.pt;
import static fr.sii.rxjava.util.Utils.imList;
import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.JOIN_ROUND;
import static java.awt.BorderLayout.CENTER;
import static java.awt.Color.WHITE;
import static java.awt.RenderingHints.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.empty;
import static rx.Observable.from;
import static rx.Observable.timer;
import static rx.schedulers.Schedulers.immediate;
import static rx.subjects.BehaviorSubject.create;
import static rx.swing.sources.MouseEventSource.fromMouseEventsOf;
import static rx.swing.sources.MouseEventSource.fromMouseMotionEventsOf;

public class MainFrame extends JFrame implements Inputs {

    private final BehaviorSubject<Drawings> drawingsSubject = create();

    public final JPanel wall = new JPanel() {

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(WHITE);
            g2d.fill(getBounds());

            if (drawingsSubject.hasValue()) {
                g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);

                g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 18));
                g2d.setStroke(new BasicStroke(3, CAP_ROUND, JOIN_ROUND));

                drawingsSubject.getValue().drawAll(drawing -> drawing.call(g2d));
            }
        }
    };

    public MainFrame(String title) {
        setTitle(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add(wall, CENTER);
    }

    public static void startApp(App app) {
        System.out.println("Demarrage de l'application: " + app.getClass().getSimpleName() + "...");

        final MainFrame frame = new MainFrame(app.getClass().getSimpleName());
        final Services services = new ServicesImpl();

        final Subscription appSubscription = app.commands(frame, services, immediate())
                .startWith(from(app.description()).map(Cmd::addLog))
                .concatWith(timer(3, SECONDS).flatMap(v -> empty()))
                .serialize()
                .scan(Drawings.empty(), (drawings, cmd) -> {
//                    System.out.println("Exec: " + cmd + " " +drawings.values().size());
                    return cmd.call(drawings, cmd.getDrawing());
                })
                .sample(40, MILLISECONDS)
                .subscribe(drawings -> {
                            frame.drawingsSubject.onNext(drawings);
                            frame.repaint();
                        },
                        ex -> {
                            System.out.println("Error ! :-(");
                            ex.printStackTrace();
                            frame.dispose();
                        },
                        () -> {
                            System.out.println("\nCompleted !");
                            frame.setVisible(false);
                            frame.dispose();
                        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("L'application va fermer: appSubscription.unsubscribe()");

                appSubscription.unsubscribe();
            }
        });

        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private Observable<MouseEvent> mousePressed() {
        return fromMouseEventsOf(wall)
                .filter(e -> e.getID() == MouseEvent.MOUSE_PRESSED)
                .throttleWithTimeout(150, MILLISECONDS);
    }

    @Override
    @Contract(pure = true)
    public Observable<Pt> mouseXY() {
        return fromMouseMotionEventsOf(wall).map(MouseEvent::getPoint).map(p -> pt((int) p.x, (int) p.y));
    }

    @Override
    @Contract(pure = true)
    public Observable<Integer> mouseLeftClickCount() {
        return mousePressed().filter(SwingUtilities::isLeftMouseButton).map(MouseEvent::getClickCount);
    }

    @Override
    @Contract(pure = true)
    public Observable<Integer> mouseRightClickCount() {
        return mousePressed().filter(SwingUtilities::isRightMouseButton).map(MouseEvent::getClickCount);
    }

    @Override
    @Contract(pure = true)
    public Observable<Character> keys() {
        return KeyEventSource.fromKeyEventsOf(this).filter(e -> e.getID() == KeyEvent.KEY_TYPED).map(e -> e.getKeyChar());
    }

    public interface App {
        Observable<Command> commands(Inputs in, Services services, Scheduler scheduler);

        default List<String> description() {
            return imList();
        }
    }
}

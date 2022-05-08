package fr.sii.rxjava.util.rxplayground;

import org.slf4j.Logger;
import rx.Observable;
import rx.Observer;
import rx.subscriptions.Subscriptions;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static fr.sii.rxjava.util.rxplayground.Data.*;
import static java.awt.event.MouseEvent.MOUSE_CLICKED;
import static java.lang.Math.max;
import static java.lang.Thread.currentThread;
import static java.util.Optional.ofNullable;
import static java.util.stream.IntStream.rangeClosed;
import static org.slf4j.LoggerFactory.getLogger;
import static rx.Observable.create;
import static rx.Observable.interval;
import static rx.swing.sources.AbstractButtonSource.fromActionOf;
import static rx.swing.sources.MouseEventSource.fromMouseEventsOf;

public class GuiRx {

    private static final Logger LOGGER = getLogger(GuiRx.class);

    private GuiRx() {
    }

    public static <T> void createObserver(final int x, final int y, final String title, final Color color, final Observable<T> observable) {
        createObserver(x, y, title, color, observable, "1");
    }

    private static <T> void createObserver(final int x, final int y, final String title, final Color color, final Observable<T> observable,
                                           final String prefix) {
        final AtomicInteger created = new AtomicInteger(0);
        final AtomicReference<ObserverFrame> observerFrame = new AtomicReference<>();

        final ObserverFrame frame = new ObserverFrame(color, title + "(" + prefix + ")", () -> {
            final ObserverFrame previousFrame = observerFrame.get();
            final String path = prefix + "." + created.incrementAndGet();

            createObserver(previousFrame.getX() + 10, previousFrame.getY() + 10, title, color, observable, path);
        });

        observerFrame.set(frame);

        frame.setSubscription(observable.subscribe(
                vv -> frame.append("onNext: " + vv + ", on " + currentThread().getName()),
                ex -> frame.append("onError: " + getErrorMessage(ex) + " !").log(ex).end("X"),
                () -> frame.append("onCompleted !").end("!")));

        frame.setLocation(x, y);
        frame.setSize(400, 200);
        frame.setVisible(true);
    }

    private static String getErrorMessage(Throwable ex) {
        return ex.getClass().getSimpleName() + " - " + ofNullable(ex.getMessage())
                .orElseGet(() -> ofNullable(ex.getCause())
                        .map(Throwable::getMessage)
                        .orElse("No error message"));
    }

    public static Observable<Integer> createColdIntsObservable(final int x, final int y, final String title, final Color color, final int start, final int end) {
        return createColdDataIntsObservable(x, y, title, color, start, end).map(Data::value);
    }

    public static Observable<Data<Integer>> createColdDataIntsObservable(final int x, final int y, final String title, final Color color, final int start, final int end) {
        final AtomicInteger created = new AtomicInteger(0);

        return create(pusher -> {
            final String titleWithIndex = title + "(" + (created.get() + 1) + ")";
            LOGGER.trace("createColdDataIntsObservable " + titleWithIndex);

            final JPanel panel = createGUI(titleWithIndex, start, end, pusher);

            final ObservableFrame<? super Data<Integer>> observableFrame = new ObservableFrame<>(color, titleWithIndex, panel, pusher);

            final int offset = 10 * created.getAndIncrement();
            observableFrame.setSize(150, 300);
            observableFrame.setLocation(x + offset, y + offset);
            observableFrame.setVisible(true);

            pusher.add(Subscriptions.create(() -> {
                LOGGER.trace("Unsubscribed " + observableFrame.getTitle());
                observableFrame.end("Up");
            }));
        });
    }

    private static JPanel createGUI(final String title, final int start, final int end, final Observer<? super Data<Integer>> pusher) {
        final DefaultListModel<Integer> model = createModel(start, end);

        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        final JList<Integer> list = new JList<>(model);

        fromMouseEventsOf(list)
                .filter(e -> e.getID() == MOUSE_CLICKED)
                .filter(e -> e.getClickCount() > 1)
                .map(__ -> data(list.getSelectedValue()))
                .subscribe(pusher);

        final JButton pushValues = new JButton("Push 5 values");

        fromActionOf(pushValues)
                .flatMap(e -> interval(1, TimeUnit.SECONDS)
                        .take(5)
                        .map(__ -> max(0, (list.getSelectedIndex() + 1) % model.getSize()))
                        .doOnNext(list::setSelectedIndex)
                        .<Data<Integer>>map(i -> data(model.getElementAt(i))))
                .subscribe(pusher::onNext);

        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        panel.add(pushValues, BorderLayout.NORTH);
        return panel;
    }

    private static DefaultListModel<Integer> createModel(final int start, final int end) {
        final DefaultListModel<Integer> model = new DefaultListModel<>();

        rangeClosed(start, end).forEach(model::addElement);

        return model;
    }
}

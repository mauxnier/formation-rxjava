package fr.sii.rxjava.gui;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.SwingScheduler;
import rx.subscriptions.Subscriptions;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import static rx.Observable.empty;
import static rx.Observable.just;

public class GuiUtils {
    public static Observable<Integer> valueOf(final JSlider component) {
        return Observable.<Integer>create(pusher -> {
            final ChangeListener listener = e -> pusher.onNext(component.getValue());

            component.addChangeListener(listener);

            pusher.add(Subscriptions.create(() -> component.removeChangeListener(listener)));
        })
                .subscribeOn(SwingScheduler.getInstance())
                .unsubscribeOn(SwingScheduler.getInstance());
    }

    public static Observable<String> valueOf(final JTextField component) {
        return Observable.<String>create(pusher -> {

            DocumentListener listener = new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    pusher.onNext(component.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    pusher.onNext(component.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    pusher.onNext(component.getText());
                }
            };
            component.getDocument().addDocumentListener(listener);

            pusher.add(Subscriptions.create(() -> component.getDocument().removeDocumentListener(listener)));
        })
                .subscribeOn(SwingScheduler.getInstance())
                .unsubscribeOn(SwingScheduler.getInstance());
    }

    public static Observable<Integer> valueOf(final JSpinner component) {
        return Observable.<Integer>create(pusher -> {

            ChangeListener listener = e -> pusher.onNext((int) component.getValue());

            component.addChangeListener(listener);

            pusher.add(Subscriptions.create(() -> component.removeChangeListener(listener)));
        })
                .subscribeOn(SwingScheduler.getInstance())
                .unsubscribeOn(SwingScheduler.getInstance());
    }

    static Func1<String, Observable<Integer>> parseInt() {
        return s -> {
            try {
                return just(Integer.parseInt(s));
            } catch (NumberFormatException e) {
                return empty();
            }
        };
    }
}

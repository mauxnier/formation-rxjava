package fr.sii.rxjava.util.rxplayground;

import rx.Subscription;

import javax.swing.*;
import java.awt.*;

public class ObserverFrame extends ColorFrame {

    protected JTextArea out = new JTextArea();

    protected JButton clear = new JButton("Clear");
    protected JButton unsubscribe = new JButton("Unsubscribe");
    protected JButton addObserver = new JButton("Add Observer");

    private Subscription subscription;

    public ObserverFrame(final Color color, final String title, final Runnable doOnAddObserver) {
        super(title);

        final JPanel panel = new JPanel();
        {
            panel.setLayout(new BorderLayout());

            out.setBackground(color);
            out.setBorder(createBorder(color, 10));

            final JPanel buttons = new JPanel();
            buttons.setLayout(new GridLayout());
            buttons.add(clear);
            buttons.add(unsubscribe);
            buttons.add(addObserver);

            panel.add(new JScrollPane(out), BorderLayout.CENTER);
            panel.add(buttons, BorderLayout.SOUTH);
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);

        clear.addActionListener(e -> out.setText(""));

        unsubscribe.addActionListener(e -> {
            subscription.unsubscribe();
            append("Unsubscribed !");
            end("Up");
        });

        addObserver.addActionListener(e -> doOnAddObserver.run());

        addWindowListener();
    }

    public ObserverFrame append(final String string) {
        out.append(string + "\n");
        out.setCaretPosition(out.getText().length());
        return this;
    }

    @Override
    protected ObserverFrame end(final String endingCause) {
        super.end(endingCause);
        unsubscribe.setEnabled(false);
        return this;
    }

    public void setSubscription(final Subscription subscription) {
        this.subscription = subscription;
    }
}

package fr.sii.rxjava.util.rxplayground;

import rx.Observer;

import javax.swing.*;
import java.awt.*;

public class ObservableFrame<T> extends ColorFrame {

    private final JComponent obsComponent;

    private final JButton completed = new JButton("Completed");
    private final JButton error = new JButton("Error");

    private JLabel titleLabel;

    public ObservableFrame(final Color color, final String title, final JComponent obsComponent, final Observer<T> pusher) {
        super(title);

        this.obsComponent = obsComponent;

        final JPanel panel = new JPanel();
        {
            panel.setLayout(new BorderLayout());

            final JPanel buttons = new JPanel();
            buttons.setLayout(new GridLayout(0, 1));
            buttons.add(completed);
            buttons.add(error);

            obsComponent.setBorder(createBorder(color, 10));

            titleLabel = new JLabel(title, SwingConstants.CENTER);
            titleLabel.setOpaque(true);
            titleLabel.setBackground(color);

            panel.add(titleLabel, BorderLayout.NORTH);
            panel.add(obsComponent, BorderLayout.CENTER);
            panel.add(buttons, BorderLayout.SOUTH);
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);

        completed.addActionListener(e -> pusher.onCompleted());

        error.addActionListener(e -> pusher.onError(new RuntimeException("CLICK 'Error' button on " + title)));

        addWindowListener();
    }

    @Override
    protected ObserverFrame end(final String endingCause) {
        super.end(endingCause);

        obsComponent.setEnabled(false);
        completed.setEnabled(false);
        error.setEnabled(false);
        return null;
    }

    @Override
    public void setTitle(final String title) {
        super.setTitle(title);
        titleLabel.setText(title);
    }
}

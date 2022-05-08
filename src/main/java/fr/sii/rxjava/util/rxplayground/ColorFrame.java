package fr.sii.rxjava.util.rxplayground;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.BorderFactory.createLineBorder;

public abstract class ColorFrame extends JFrame {

    protected boolean finished = false;

    protected ColorFrame(final String title) {
        super(title);
    }

    @NotNull
    @Contract(pure = true)
    public static Border createBorder(final Color color, final int insets) {
        return createLineBorder(color, insets);
    }

    protected void addWindowListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                if (!finished) {
                    System.exit(0);
                }
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    protected ColorFrame end(final String endingCause) {
        finished = true;
        setTitle("[" + endingCause + "]-" + getTitle());
        return this;
    }

    public ColorFrame log(Throwable ex) {
        ex.printStackTrace();
        return this;
    }
}

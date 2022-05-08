package fr.sii.rxjava.gui;

import rx.subjects.BehaviorSubject;

import javax.swing.*;

import static fr.sii.rxjava.gui.GuiUtils.parseInt;
import static fr.sii.rxjava.gui.GuiUtils.valueOf;
import static fr.sii.rxjava.gui.SourcedValue.forUpdating;
import static fr.sii.rxjava.gui.SourcedValue.fromComp;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static rx.Observable.merge;

public class ExGui {

    public static void main(String... args) {
        JFrame frame = new JFrame("Bidirectional");

        JSlider slider = new JSlider(0, 100);
        JTextField textField = new JTextField();
        JSpinner spinner = new JSpinner();
        spinner.setModel(new SpinnerNumberModel(0, 0, 100, 1));

        JPanel panel = new JPanel();
        panel.add(slider);
        panel.add(textField);
        panel.add(spinner);

        frame.getContentPane().add(panel);

        initBidir(slider, textField, spinner);

        frame.setSize(300, 100);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    static void initBidir(JSlider slider, JTextField textField, JSpinner spinner) {
        final BehaviorSubject<SourcedValue<Integer>> valueSubject = BehaviorSubject.create(new SourcedValue<Integer>(slider, 42));

        valueSubject.compose(forUpdating(slider)).subscribe(slider::setValue);
        valueSubject.compose(forUpdating(textField)).map(String::valueOf).subscribe(textField::setText);
        valueSubject.compose(forUpdating(spinner)).subscribe(spinner::setValue);

        merge(
                valueOf(slider).compose(fromComp(slider)),
                valueOf(textField).flatMap(parseInt()).compose(fromComp(textField)),
                valueOf(spinner).compose(fromComp(spinner)))
                .distinctUntilChanged(SourcedValue::value)
//                .debounce(100, MILLISECONDS)
                .subscribe(valueSubject);
    }
}

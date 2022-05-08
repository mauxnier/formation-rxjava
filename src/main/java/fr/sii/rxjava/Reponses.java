package fr.sii.rxjava;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import static com.google.common.base.Throwables.propagate;
import static java.lang.Class.forName;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Comparator.comparing;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Reponses {
    public static void main(String... args) throws IOException, ClassNotFoundException, NoSuchMethodException {
        JFrame frame = new JFrame("Réponses");

        DefaultListModel<Method> model = new DefaultListModel<>();


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
                .flatMap(ci -> {
                    try {
                        return Stream.of(forName(ci).getDeclaredMethod("main", String[].class))
                                .filter(m -> isStatic(m.getModifiers()))
                                .filter(m -> isPublic(m.getModifiers()));
                    } catch (Throwable e) {
                        return Stream.empty();
                    }
                })
                .sorted(comparing(m -> m.getDeclaringClass().getName()))
                .forEach(model::addElement);


        JList<Method> list = new JList<>(model);
        frame.setContentPane(new JScrollPane(list));

        list.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    Method methodToRun = list.getSelectedValue();

                    try {
                        methodToRun.invoke(null, new Object[]{new String[]{}});
                    } catch (Throwable e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        DefaultListCellRenderer defaultRender = new DefaultListCellRenderer();

        list.setCellRenderer((list1, value, index, isSelected, cellHasFocus) -> {
            Component c = defaultRender.getListCellRendererComponent(list1, value.getDeclaringClass().getSimpleName(), index, isSelected, cellHasFocus);
            c.setFont(c.getFont().deriveFont(16f));
            return c;
        });

        frame.setMinimumSize(new Dimension(10, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    @FunctionalInterface
    interface ExFunction<T, R> {
        R apply(T t) throws Throwable;
    }

    @FunctionalInterface
    interface ExPredicate<T> {
        boolean apply(T input) throws Throwable;
    }

    static <T> Predicate<T> propag(ExPredicate<T> p) {
        return t -> {
            try {
                return p.apply(t);
            } catch (Throwable ex) {
                throw propagate(ex);
            }
        };
    }

    static <A, B> Function<A, B> propagF(ExFunction<A, B> f) {
        return a -> {
            try {
                return f.apply(a);
            } catch (Throwable ex) {
                throw propagate(ex);
            }
        };
    }
}

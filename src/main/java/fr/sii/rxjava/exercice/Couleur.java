package fr.sii.rxjava.exercice;

import javafx.scene.paint.Color;
import org.jetbrains.annotations.Contract;

import static javafx.scene.paint.Color.*;


enum Couleur {
    rouge(RED), vert(GREEN), bleu(BLUE), rose(PINK), noir(BLACK), magenta(MAGENTA), orange(ORANGE), gris(DARKGRAY);

    final Color color;

    Couleur(Color color) { this.color = color; }

    @Contract(pure = true)
    public Color color() { return color; }
}

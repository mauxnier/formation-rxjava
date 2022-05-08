package fr.sii.rxjava.exercice;

import org.jetbrains.annotations.Contract;

import java.awt.*;

import static java.awt.Color.*;

enum Couleur {
    rouge(RED), vert(GREEN), bleu(BLUE), rose(PINK), noir(BLACK), magenta(MAGENTA), orange(ORANGE), gris(DARK_GRAY);

    final Color color;

    Couleur(Color color) { this.color = color; }

    @Contract(pure = true)
    public Color color() { return color; }
}

package fr.sii.rxjava.util;


import io.reactivex.rxjava3.core.Observable;

public interface Inputs {

    Observable<Pt> mouseXY();

    Observable<Integer> mouseLeftClickCount();

    Observable<Integer> mouseRightClickCount();

    Observable<Character> keys();
}

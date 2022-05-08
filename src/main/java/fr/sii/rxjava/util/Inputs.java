package fr.sii.rxjava.util;

import rx.Observable;

public interface Inputs {

    Observable<Pt> mouseXY();

    Observable<Integer> mouseLeftClickCount();

    Observable<Integer> mouseRightClickCount();

    Observable<Character> keys();
}

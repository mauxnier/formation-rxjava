package fr.sii.rxjava.util;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.TestScheduler;
import io.reactivex.rxjava3.subjects.PublishSubject;

import java.util.concurrent.TimeUnit;

import static fr.sii.rxjava.util.Pt.pt;

public final class TestInputs implements Inputs, InputPusher {

    private static final String PUSH = "   Test >> Push ";

    final PublishSubject<Pt> mouseXY = PublishSubject.create();
    final PublishSubject<Integer> mouseLeftClicks = PublishSubject.create();
    final PublishSubject<Integer> mouseRightClicks = PublishSubject.create();
    final PublishSubject<Character> keys = PublishSubject.create();
    final TestScheduler testScheduler;

    private TestInputs(TestScheduler testScheduler) {
        this.testScheduler = testScheduler;
    }

    public static TestInputs create(TestScheduler testScheduler) {
        return new TestInputs(testScheduler);
    }

    @Override
    public Observable<Pt> mouseXY() {
        return mouseXY;
    }

    @Override
    public Observable<Integer> mouseLeftClickCount() {
        return mouseLeftClicks;
    }

    @Override
    public Observable<Integer> mouseRightClickCount() {
        return mouseRightClicks;
    }

    @Override
    public Observable<Character> keys() {
        return keys;
    }

    @Override
    public TestInputs pushMouseXY(double x, double y) {
        return pushMouseXY(pt(x, y));
    }

    @Override
    public TestInputs pushMouseXY(Pt p) {
        System.out.println(PUSH + p);
        mouseXY.onNext(p);
        return this;
    }

    @Override
    public TestInputs pushMouseLeftClick(int c) {
        System.out.println(PUSH + c + " left clic");
        mouseLeftClicks.onNext(c);
        return this;
    }

    @Override
    public TestInputs pushMouseRightClick(int c) {
        System.out.println(PUSH + c + " right clic");
        mouseRightClicks.onNext(c);
        return this;
    }

    @Override
    public TestInputs pushKey(String s) {
        System.out.println(PUSH + "'" + s + "'");
        s.chars().forEach(c -> keys.onNext((char) c));
        return this;
    }

    @Override
    public TestInputs advanceTimeBy(long delayTime, TimeUnit unit) {
        testScheduler.advanceTimeBy(delayTime, unit);
        return this;
    }
}

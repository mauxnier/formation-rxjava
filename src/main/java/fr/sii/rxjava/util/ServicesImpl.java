package fr.sii.rxjava.util;

import rx.Observable;

import java.util.Random;

import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.*;
import static rx.schedulers.Schedulers.computation;

public class ServicesImpl implements Services {

    private final Random rnd = new Random();

    @Override
    public Observable<String> words() {
        return just("mots", "complex", "simple");
    }

    @Override
    public Observable<Character> randomChars() {
        Random rnd = new Random();

        return interval(1, SECONDS, computation()).map(__ -> (char) ('a' + rnd.nextInt(26)));
    }

    @Override
    public Observable<Double> randoms() { return create(pusher -> pusher.onNext(rnd.nextDouble())); }
}

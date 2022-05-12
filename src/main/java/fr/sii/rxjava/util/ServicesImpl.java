package fr.sii.rxjava.util;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.Random;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ServicesImpl implements Services {

    private final Random rnd = new Random();

    @Override
    public Observable<String> words() {
        return Observable.just("mots", "complex", "simple");
    }

    @Override
    public Observable<Character> randomChars() {
        Random rnd = new Random();

        return Observable.interval(1, SECONDS, Schedulers.computation()).map(__ -> (char) ('a' + rnd.nextInt(26)));
    }

    @Override
    public Observable<Double> randoms() {
        return Observable.create(pusher -> pusher.onNext(rnd.nextDouble()));
    }
}

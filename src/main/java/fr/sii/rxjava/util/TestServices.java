package fr.sii.rxjava.util;

import rx.Observable;
import rx.schedulers.TestScheduler;

import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.from;
import static rx.Observable.interval;

public class TestServices implements Services {

    private TestScheduler testScheduler;

    private Observable<Double> randoms;
    private String randomChars;
    private List<String> words;

    public TestServices(TestScheduler testScheduler) {
        this.testScheduler = testScheduler;
    }

    public void setRandomChars(String randomChars) { this.randomChars = randomChars; }

    public void setRandoms(Observable<Double> randoms) { this.randoms = randoms; }

    public void setWords(List<String> words) { this.words = words; }

    @Override
    public Observable<Double> randoms() {
        return randoms;
    }

    @Override
    public Observable<String> words() {
        return from(words);
    }

    @Override
    public Observable<Character> randomChars() {
        return interval(1, SECONDS, testScheduler)
                .map(Long::intValue)
                .take(randomChars.length())
                .map(randomChars::charAt);
    }
}

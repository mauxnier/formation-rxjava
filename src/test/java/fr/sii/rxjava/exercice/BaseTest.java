package fr.sii.rxjava.exercice;

import com.google.common.collect.ImmutableList;
import fr.sii.rxjava.util.InputPusher;
import fr.sii.rxjava.util.MainFrame.App;
import fr.sii.rxjava.util.TestInputs;
import fr.sii.rxjava.util.TestServices;
import fr.sii.rxjava.util.cmds.Command;
import org.junit.Before;
import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action1;
import rx.internal.schedulers.EventLoopsScheduler;
import rx.internal.util.ScalarSynchronousObservable;
import rx.observers.TestSubscriber;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Throwables.*;
import static fr.sii.rxjava.util.T3.t3;
import static fr.sii.rxjava.util.Utils.*;
import static java.lang.Integer.MAX_VALUE;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static rx.Observable.from;
import static rx.Observable.range;

public class BaseTest {

    protected static TestScheduler testScheduler = new TestScheduler();

    private TestInputs inputs;

    private ImmutableList<Command> cumulativeCmds = imList();

    protected InputPusher inputPusher;
    protected TestSubscriber<Command> testSubscriber;
    protected TestServices testServices;

    static {
        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
            @Override
            public Scheduler getComputationScheduler() { return testScheduler; }

            @Override
            public Scheduler getIOScheduler() { return testScheduler; }

            @Override
            public Scheduler getNewThreadScheduler() { return testScheduler; }
        });
    }

    @Before
    public void setup() {
        inputs = TestInputs.create(testScheduler);
        inputPusher = inputs;
        testSubscriber = new TestSubscriber<>();
        testServices = new TestServices(testScheduler);
    }

    protected void testSetupFor(Class<? extends App> sut) throws IllegalAccessException, InstantiationException {
        testSetupFor(sut, ts -> {
        });
    }

    protected void testSetupFor(Class<? extends App> sut, Action1<TestServices> initTestService) throws IllegalAccessException, InstantiationException {
        initTestService.call(testServices);

        sut.newInstance().commands(inputs, testServices, testScheduler)
                // .doOnEach(notif -> System.out.println("<< Cmd  " + notif))
                .doOnCompleted(() -> System.out.println("Completed !"))
                .subscribe(testSubscriber);
    }

    protected void assertStepValuesObs(Observable<Command> expectedStepCmds) { assertStepValues(expectedStepCmds.toList().toBlocking().single()); }

    protected void assertStepValues(Stream<Command>... expectedStepCmds) { assertStepValues(stream(expectedStepCmds).flatMap(v -> v).collect(Collectors.toList())); }

    protected void assertStepValues(Command... expectedStepCmds) { assertStepValues(asList(expectedStepCmds)); }

    protected void assertStepWithoutValues() { assertStepValues(imList()); }

    protected void assertStepValues(List<Command>... expectedStepCmds) {
        displayAndAssertAllValues(imConcat(cumulativeCmds, expectedStepCmds));
    }

    private void displayAndAssertAllValues(Command... allExpectedCmds) { displayAndAssertAllValues(asList(allExpectedCmds)); }

    private void displayAndAssertAllValues(List<Command> allExpectedCmds) {
        displayCommands(allExpectedCmds);

        testSubscriber.assertValues(allExpectedCmds.stream().toArray(Command[]::new));

        cumulativeCmds = imCopyOf(allExpectedCmds);
    }

    private void displayCommands(List<Command> expectedCmd) {
        String actual = listing(" Actual  ", testSubscriber.getOnNextEvents(), i -> i == 0, '⎡');
        String expected = listing("Expected ", expectedCmd, i -> i == expectedCmd.size() - cumulativeCmds.size() - 1, '⎣');

        System.out.println(actual + "⎢===⸮¿?===⎢\n" + expected);
    }

    private String listing(String prefix, List<Command> cmds, IntPredicate needSpecialSep, char specialSep) {
        int previousCmdCount = cumulativeCmds.size();

        return from(cmds)
                .skip(previousCmdCount)
                .zipWith(range(0, MAX_VALUE), (v, i) -> t3(v, i + previousCmdCount, needSpecialSep.test(i) ? specialSep : '⎢'))
                .map(v_i_sep -> v_i_sep._3 + prefix + v_i_sep._3 + " " + v_i_sep._2 + " - " + v_i_sep._1 + "\n")
                .collect(StringBuilder::new, StringBuilder::append)
                .map(StringBuilder::toString)
                .filter(s -> !s.isEmpty())
                .defaultIfEmpty(specialSep + prefix + specialSep + " ∅ No command !\n")
                .toBlocking().single();
    }

    protected <T> List<T> captureDuring(Observable<? extends T> obs, long delayTime, TimeUnit unit) {
        Capture<T> capture = capture(obs);
        testScheduler.advanceTimeBy(delayTime, unit);
        return capture.values();
    }

    protected <T> Capture<T> capture(Observable<? extends T> obs) {
        Capture<T> objectCapture = new Capture<>();

        objectCapture.setSubscription(obs.subscribe(objectCapture.onNexts()));

        return objectCapture;
    }

    protected void assertCompleted() {testSubscriber.assertCompleted();}

    protected static class Capture<T> {
        private final ImmutableList.Builder<T> mutableValues = ImmutableList.<T>builder();
        private Subscription subscription;

        Action1<T> onNexts() {return mutableValues::add;}

        public ImmutableList<T> values() {
            subscription.unsubscribe();
            return mutableValues.build();
        }

        public void setSubscription(Subscription subscription) { this.subscription = subscription; }
    }
}

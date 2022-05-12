package fr.sii.rxjava.exercice;

import com.google.common.collect.ImmutableList;
import fr.sii.rxjava.util.App;
import fr.sii.rxjava.util.InputPusher;
import fr.sii.rxjava.util.TestInputs;
import fr.sii.rxjava.util.TestServices;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.TestScheduler;
import org.junit.Before;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fr.sii.rxjava.util.T3.t3;
import static fr.sii.rxjava.util.Utils.*;
import static io.reactivex.rxjava3.core.Observable.fromIterable;
import static io.reactivex.rxjava3.core.Observable.range;
import static java.lang.Integer.MAX_VALUE;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

public class BaseTest {

    protected static TestScheduler testScheduler = new TestScheduler();

    private TestInputs inputs;

    private ImmutableList<Command> cumulativeCmds = imList();

    protected InputPusher inputPusher;
    protected TestObserver<Command> testSubscriber;
    protected TestServices testServices;

    static {
        try {
            RxJavaPlugins.setInitNewThreadSchedulerHandler(schedulerSupplier -> testScheduler);
            RxJavaPlugins.setInitIoSchedulerHandler(schedulerSupplier -> testScheduler);
            RxJavaPlugins.setComputationSchedulerHandler(schedulerSupplier -> testScheduler);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }

    @Before
    public void setup() {
        inputs = TestInputs.create(testScheduler);
        inputPusher = inputs;
        testSubscriber = new TestObserver<>();
        testServices = new TestServices(testScheduler);
    }

    protected void testSetupFor(Class<? extends App> sut) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        testSetupFor(sut, ts -> {
        });
    }

    protected void testSetupFor(Class<? extends App> sut, Consumer<TestServices> initTestService) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        initTestService.accept(testServices);

        sut.getDeclaredConstructor().newInstance().commands(inputs, testServices, testScheduler)
                // .doOnEach(notif -> System.out.println("<< Cmd  " + notif))
                .doOnComplete(() -> System.out.println("Completed !"))
                .subscribe(testSubscriber);
    }

    protected void assertStepValuesObs(Observable<Command> expectedStepCmds) {
        assertStepValues(expectedStepCmds.toList().blockingGet());
    }

    protected void assertStepValues(Stream<Command>... expectedStepCmds) {
        assertStepValues(stream(expectedStepCmds).flatMap(v -> v).collect(Collectors.toList()));
    }

    protected void assertStepValues(Command... expectedStepCmds) {
        assertStepValues(asList(expectedStepCmds));
    }

    protected void assertStepWithoutValues() {
        assertStepValues(imList());
    }

    protected void assertStepValues(List<Command>... expectedStepCmds) {
        displayAndAssertAllValues(imConcat(cumulativeCmds, expectedStepCmds));
    }

    private void displayAndAssertAllValues(Command... allExpectedCmds) {
        displayAndAssertAllValues(asList(allExpectedCmds));
    }

    private void displayAndAssertAllValues(List<Command> allExpectedCmds) {
        displayCommands(allExpectedCmds);

        testSubscriber.assertValues(allExpectedCmds.stream().toArray(Command[]::new));

        cumulativeCmds = imCopyOf(allExpectedCmds);
    }

    private void displayCommands(List<Command> expectedCmd) {
        String actual = listing(" Actual  ", testSubscriber.values(), i -> i == 0, '⎡');
        String expected = listing("Expected ", expectedCmd, i -> i == expectedCmd.size() - cumulativeCmds.size() - 1, '⎣');

        System.out.println(actual + "⎢===⸮¿?===⎢\n" + expected);
    }

    private String listing(String prefix, List<Command> cmds, IntPredicate needSpecialSep, char specialSep) {
        int previousCmdCount = cumulativeCmds.size();

        return fromIterable(cmds)
                .skip(previousCmdCount)
                .zipWith(range(0, MAX_VALUE), (v, i) -> t3(v, i + previousCmdCount, needSpecialSep.test(i) ? specialSep : '⎢'))
                .map(v_i_sep -> v_i_sep._3 + prefix + v_i_sep._3 + " " + v_i_sep._2 + " - " + v_i_sep._1 + "\n")
                .collect(StringBuilder::new, StringBuilder::append)
                .map(StringBuilder::toString)
                .filter(s -> !s.isEmpty())
                .defaultIfEmpty(specialSep + prefix + specialSep + " ∅ No command !\n")
                .blockingGet();
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

    protected void assertCompleted() {
        testSubscriber.assertComplete();
    }

    protected static class Capture<T> {
        private final ImmutableList.Builder<T> mutableValues = ImmutableList.<T>builder();
        private Disposable subscription;

        io.reactivex.rxjava3.functions.Consumer<T> onNexts() {
            return mutableValues::add;
        }

        public ImmutableList<T> values() {
            subscription.dispose();
            return mutableValues.build();
        }

        public void setSubscription(Disposable subscription) {
            this.subscription = subscription;
        }
    }
}

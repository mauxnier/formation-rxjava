package fr.sii.rxjava.exercice;

import com.google.common.collect.Lists;
import fr.sii.rxjava.util.*;
import fr.sii.rxjava.util.cmds.Command;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static fr.sii.rxjava.util.Cmd.clear;
import static fr.sii.rxjava.util.MainApp.startApp;
import static java.util.Collections.singletonList;

public class Ex1000_FlappyBird implements App, Consts {


    private static final int H = 784;
    private static final int W = 755;
    private static final int CENTER_W = W / 2;
    private static final int CENTER_H = H / 2;
    private static final double FPS = 60;
    private static final double FPS_TIME = 1D / FPS * 1000D;
    private static final int PIPE_MIN_MAX_LIMIT_POSITION = 100;
    private static final int HOLE_SIZE = 150;
    private static final int DROPPING_SPEED_ACC = -1;
    private static final int MAX_DROPPING_SPEED = -3;
    private static final int MAX_DROPPING_ACC = -3;
    private static final int INITIAL_SPEED = 0;
    private static final int INITIAL_ACC = -3;
    private static final int BOOST_SPEED = 20;
    private static final int BOOST_ACC = 50;


    public static void main(String... args) {
        startApp(new Ex1000_FlappyBird());
    }

    @NotNull
    private static Observable<Boolean> input(Inputs in) {
        return in.keys()
                .filter(v -> v.equals(' '))
                .flatMap(v -> Observable.just(true, false));
    }

    @Override
    @Contract(pure = true)
    public Observable<Command> commands(Inputs in, Services services, Scheduler scheduler) {
        return Observable.never();
    }

    private boolean isGameFinished(GameContext gc) {
        Boolean hitPipe = gc.getPipes().stream()
                .filter(p -> p.getY() > CENTER_W - 5 && p.getY() < CENTER_W + 5)
                .map(p -> gc.getBird().getPosition() < p.getHole() - PIPE_MIN_MAX_LIMIT_POSITION / 2 ||
                        gc.getBird().getPosition() > p.getHole() + PIPE_MIN_MAX_LIMIT_POSITION / 2)
                .findAny()
                .orElse(false);

        return gc.getBird().getPosition() < 0 || gc.getBird().getPosition() > H || hitPipe;
    }

    @Override
    public List<String> description() {
        return singletonList("Espace pour commencer...");
    }

    public static class PositionAndSpeed {
        private final double speed;
        private final double acc;
        private final int position;

        public PositionAndSpeed(double acc, double speed, int position) {
            this.acc = acc;
            this.speed = speed;
            this.position = position;
        }

        public double getSpeed() {
            return speed;
        }

        public double getAcc() {
            return acc;
        }

        public int getPosition() {
            return position;
        }
    }

    public static class Pipe {
        private final int hole;
        private int y = W;

        public Pipe(int hole) {
            this.hole = hole;
        }

        public static Pipe generate() {
            return new Pipe((int) (PIPE_MIN_MAX_LIMIT_POSITION + new Random().nextDouble() * (H - PIPE_MIN_MAX_LIMIT_POSITION * 2)));
        }

        public int getHole() {
            return hole;
        }

        public int getY() {
            return y;
        }

        public void move() {
            y = y - 2;
        }

        public Observable<Command> generateCmd() {
            return Observable.just(Cmd.addLine(y, 0, y, hole - HOLE_SIZE / 2),
                    Cmd.addLine(y, hole + HOLE_SIZE / 2, y, H));
        }
    }

    public static class GameContext {
        private final List<Pipe> pipes;
        private final PositionAndSpeed bird;

        public GameContext(Collection<Pipe> pipes, PositionAndSpeed bird) {
            this.pipes = Lists.newArrayList(pipes);
            this.bird = bird;
        }

        public List<Pipe> getPipes() {
            return pipes;
        }

        public PositionAndSpeed getBird() {
            return bird;
        }
    }
}

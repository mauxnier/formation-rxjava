package fr.sii.rxjava.util;

import java.util.concurrent.TimeUnit;

public interface InputPusher {

    InputPusher pushMouseXY(double x, double y);

    InputPusher pushMouseXY(Pt p);

    InputPusher pushMouseLeftClick(int c);

    InputPusher pushMouseRightClick(int c);

    InputPusher pushKey(String s);

    TestInputs advanceTimeBy(long delayTime, TimeUnit unit);
}

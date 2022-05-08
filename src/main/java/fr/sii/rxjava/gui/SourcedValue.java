package fr.sii.rxjava.gui;

import rx.Observable;

import javax.swing.*;

import static com.google.common.base.Preconditions.checkNotNull;

class SourcedValue<T> {
    final JComponent source;
    final T value;

    public SourcedValue(JComponent source, T value) {
        this.source = checkNotNull(source);
        this.value = checkNotNull(value);
    }

    static public <T> Observable.Transformer<T, SourcedValue<T>> fromComp(JComponent c) {
        return obs -> obs
                .distinctUntilChanged()
                .map(v -> new SourcedValue<>(c, v));
    }

    static public <T> Observable.Transformer<SourcedValue<T>, T> forUpdating(JComponent c) {
        return obs -> obs
                .filter(v -> !v.source.equals(c))
                .map(SourcedValue::value)
                .distinctUntilChanged();
    }

    public JComponent source() {
        return source;
    }

    public T value() {
        return value;
    }

    @Override
    public String toString() {
        return "SourcedValue{val=" + value + ", source= " + source + '}';
    }
}

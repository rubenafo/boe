package com.example.demo.common;

public class ExecMeter {

    private long start;
    private long end;

    public ExecMeter() {
        this.start = System.currentTimeMillis();
    }

    public long stop() {
        this.end = System.currentTimeMillis();
        return (end - start) / 1000;
    }

    public static ExecMeter start () {
        return new ExecMeter();
    }
}

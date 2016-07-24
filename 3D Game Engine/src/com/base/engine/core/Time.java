package com.base.engine.core;

public class Time
{
    private static final long SECOND = 1000000000;

    private static double delta;

    public static double getTime()
    {
        return (double) System.nanoTime() / (double) SECOND;
    }
}

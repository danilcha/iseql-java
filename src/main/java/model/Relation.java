package model;

import java.util.ArrayList;
import java.util.Random;

import algorithms.iterators.Iterator;


@SuppressWarnings("ClassExtendsConcreteCollection")
public final class Relation extends ArrayList<Tuple>
{
    private Index index;

    public Relation(int initialCapacity)
    {
        super(initialCapacity);
    }

    public Relation()
    {
    }


    static int uniform(Random random, int min, int max)
    {
        return random.nextInt(max - min + 1) + min;
    }

    public static Relation generateUniform(int n, int durationMin, int durationMax,
                                           int domainMin, int domainMax, long seed)
    {
        Relation result = new Relation(n);

        assert (durationMin >= 1);
        assert (durationMin <= durationMax);
        assert (durationMax <= domainMax - domainMin + 1);
        assert (domainMin <= domainMax);

        Random random = new Random(seed);

        for (int i = 0; i < n; i++)
        {
            int duration = uniform(random, durationMin, durationMax);
            int begin = uniform(random, domainMin, domainMax - duration + 1);
            int end = begin + duration - 1;
            result.add(new Tuple(begin, end));
        }

        return result;
    }

    public Index getIndex()
    {
        return index;
    }

    public void setIndex(Index index)
    {
        this.index = index;
    }
}

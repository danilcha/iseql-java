package algorithms.joins;

import java.util.function.BiConsumer;

import model.Relation;
import model.Tuple;


public final class Joins
{
    public static final int UNBOUND = Integer.MAX_VALUE;

    static BiConsumer<Tuple, Tuple> makeReversingConsumer(BiConsumer<Tuple, Tuple> consumer)
    {
        return (r, s) -> consumer.accept(s, r);
    }


    public static void startPrecedingJoin(Relation R, Relation S, int delta, BiConsumer<Tuple, Tuple> consumer)
    {
        Joins2.startPrecedingJoin(R, S, (r, s) ->
        {
            if (s.start - r.start <= delta)
                consumer.accept(r, s);
        });
    }



    public static void reverseStartPrecedingJoin(Relation R, Relation S, BiConsumer<Tuple, Tuple> consumer)
    {
        Joins2.startPrecedingJoin(S, R, makeReversingConsumer(consumer));
    }



    public static void endFollowingJoin(Relation R, Relation S, int epsilon, BiConsumer<Tuple, Tuple> consumer)
    {
        Joins2.endFollowingJoin(R, S, (r, s) ->
        {
            if (r.end - s.end <= epsilon)
                consumer.accept(r, s);
        });
    }



    public static void reverseEndFollowingJoin(Relation R, Relation S, BiConsumer<Tuple, Tuple> consumer)
    {
        Joins2.endFollowingJoin(S, R, makeReversingConsumer(consumer));
    }



    public static void leftOverlapJoin(Relation R, Relation S, BiConsumer<Tuple, Tuple> consumer)
    {
        Joins2.startPrecedingJoin(R, S, (r, s) ->
        {
            if (r.end <= s.end)
                consumer.accept(r, s);
        });
    }



    public static void leftOverlapJoin(Relation R, Relation S, int delta, int epsilon, BiConsumer<Tuple, Tuple> consumer)
    {
        leftOverlapJoin(R, S, (r, s) ->
        {
            if (s.start - r.start <= delta && s.end - r.end <= epsilon)
                consumer.accept(r, s);
        });
    }



    public static void rightOverlapJoin(Relation R, Relation S, BiConsumer<Tuple, Tuple> consumer)
    {
        leftOverlapJoin(S, R, makeReversingConsumer(consumer));
    }



    public static void rightOverlapJoin(Relation R, Relation S, int delta, int epsilon, BiConsumer<Tuple, Tuple> consumer)
    {
        leftOverlapJoin(S, R, delta, epsilon, makeReversingConsumer(consumer));
    }



    public static void duringJoin(Relation R, Relation S, BiConsumer<Tuple, Tuple> consumer)
    {
        reverseStartPrecedingJoin(R, S, (r, s) ->
        {
            if (r.end <= s.end)
                consumer.accept(r, s);
        });
    }



    public static void duringJoin(Relation R, Relation S, int delta, int epsilon, BiConsumer<Tuple, Tuple> consumer)
    {
        duringJoin(R, S, (r, s) ->
        {
            if (r.start - s.start <= delta && s.end - r.end <= epsilon)
               consumer.accept(r, s);
        });
    }



    public static void reverseDuringJoin(Relation R, Relation S, BiConsumer<Tuple, Tuple> consumer)
    {
        duringJoin(S, R, makeReversingConsumer(consumer));
    }



    public static void reverseDuringJoin(Relation R, Relation S, int delta, int epsilon, BiConsumer<Tuple, Tuple> consumer)
    {
        duringJoin(S, R, delta, epsilon, makeReversingConsumer(consumer));
    }



    public static void afterJoin(Relation R, Relation S, int delta, BiConsumer<Tuple, Tuple> consumer)
    {
        Joins2.beforeJoin(S, R, delta, makeReversingConsumer(consumer));
    }

}

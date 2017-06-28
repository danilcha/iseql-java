package algorithms.joins;

import java.util.function.BiConsumer;

import algorithms.iterators.FilteringIterator;
import algorithms.iterators.Iterator;
import model.Endpoint;
import model.Relation;
import model.Tuple;


public final class Joins1
{
    static void joinBySStart(Relation R, Relation S, Iterator itR, BiConsumer<Tuple, Tuple> consumer)
    {
        Joins0.joinByS(
            R,
            S,
            itR,
            new FilteringIterator(S.getIndex(), Endpoint.Type.START),
            (r, s) -> r.getTimestampAndType() <= s.getTimestampAndType(),
            consumer
        );
    }

    static void joinBySEnd(Relation R, Relation S, Iterator itR, BiConsumer<Tuple, Tuple> consumer)
    {
        Joins0.joinByS(
            R,
            S,
            itR,
            new FilteringIterator(S.getIndex(), Endpoint.Type.END),
            (r, s) -> r.getTimestampAndType() <  s.getTimestampAndType(),
            consumer
        );
    }
}

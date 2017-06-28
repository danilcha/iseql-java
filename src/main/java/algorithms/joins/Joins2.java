package algorithms.joins;

import java.util.function.BiConsumer;

import algorithms.iterators.FilteringIterator;
import algorithms.iterators.IndexIterator;
import algorithms.iterators.MergingIterator;
import algorithms.iterators.ShiftingIterator;
import model.Endpoint;
import model.Index;
import model.Relation;
import model.Tuple;


public final class Joins2
{
    public static void startPrecedingJoin(Relation R, Relation S, BiConsumer<Tuple, Tuple> consumer)
    {
        Joins1.joinBySStart(R, S, new IndexIterator(R.getIndex()), consumer);
    }


    public static void endFollowingJoin(Relation R, Relation S, BiConsumer<Tuple, Tuple> consumer)
    {
        Joins1.joinBySEnd(R, S, new IndexIterator(R.getIndex()), consumer);
    }


    public static void beforeJoin(Relation R, Relation S, int delta, BiConsumer<Tuple, Tuple> consumer)
    {
        Endpoint.Type START = Endpoint.Type.START;
        Endpoint.Type END   = Endpoint.Type.END;

        assert delta > 0;

        Index index = R.getIndex();

        Joins1.joinBySStart(
            R,
            S,
            new MergingIterator(
                new ShiftingIterator(new FilteringIterator(index, END), 1, END, START), // r.end + 1 -> r.start
                new ShiftingIterator(new FilteringIterator(index, END), delta)          // r.end + delta -> r.end
            ),
            consumer
        );
    }
}

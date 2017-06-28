import java.util.Arrays;
import java.util.HashSet;
import java.util.function.BiConsumer;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Index;
import model.Relation;
import model.Tuple;

import static algorithms.joins.Joins.afterJoin;
import static algorithms.joins.Joins.duringJoin;
import static algorithms.joins.Joins.endFollowingJoin;
import static algorithms.joins.Joins.leftOverlapJoin;
import static algorithms.joins.Joins.reverseDuringJoin;
import static algorithms.joins.Joins.reverseEndFollowingJoin;
import static algorithms.joins.Joins.reverseStartPrecedingJoin;
import static algorithms.joins.Joins.rightOverlapJoin;
import static algorithms.joins.Joins.startPrecedingJoin;
import static algorithms.joins.Joins.UNBOUND;
import static algorithms.joins.Joins2.beforeJoin;
import static algorithms.joins.Joins2.endFollowingJoin;
import static algorithms.joins.Joins2.startPrecedingJoin;
import static org.junit.Assert.assertEquals;


public final class JoinsTest
{
    private static Relation R;
    private static Relation S;

    @BeforeClass
    public static void setUpClass()
    {
        R = new Relation();
        S = new Relation();

        R.add(new Tuple(1, 5, 1));
        R.add(new Tuple(1, 10, 2));
        R.add(new Tuple(7, 11, 3));
        R.add(new Tuple(9, 10, 4));

        S.add(new Tuple(2, 2, 1));
        S.add(new Tuple(3, 12, 2));
        S.add(new Tuple(4, 5, 3));
        S.add(new Tuple(5, 6, 4));
        S.add(new Tuple(8, 9, 5));

        R.setIndex(new Index(R));
        S.setIndex(new Index(S));
    }

    private final HashSet<Integer> result = new HashSet<>();

    private final BiConsumer<Tuple, Tuple> consumer = (r, s) -> result.add(r.id * 10 + s.id);

    private static HashSet<Integer> setOf(Integer... values)
    {
        return new HashSet<>(Arrays.asList(values));
    }


    @Test
    public void startPreceding()
    {
        startPrecedingJoin(R, S, consumer);

        assertEquals(setOf(11, 12, 13, 14, 21, 22, 23, 24, 25, 35), result);
    }


    @Test
    public void reverseStartPreceding()
    {
        reverseStartPrecedingJoin(S, R, consumer);

        assertEquals(setOf(11, 21, 31, 41, 12, 22, 32, 42, 52, 53), result);
    }


    @Test
    public void startPrecedingWithDelta()
    {
        startPrecedingJoin(R, S, 1, consumer);

        assertEquals(setOf(11, 21, 35), result);
    }


    @Test
    public void endFollowing()
    {
        endFollowingJoin(R, S, consumer);

        assertEquals(setOf(11, 13, 21, 23, 24, 25, 35, 45), result);
    }


    @Test
    public void reverseEndFollowing()
    {
        reverseEndFollowingJoin(S, R, consumer);

        assertEquals(setOf(11, 31, 12, 32, 42, 52, 53, 54), result);
    }


    @Test
    public void endFollowingWithEpsilon()
    {
        endFollowingJoin(R, S, 1, consumer);

        assertEquals(setOf(13, 25, 45), result);
    }


    @Test
    public void leftOverlap()
    {
        leftOverlapJoin(R, S, consumer);

        assertEquals(setOf(12, 13, 14, 22), result);
    }


    @Test
    public void leftOverlapWithDelta()
    {
        leftOverlapJoin(R, S, 2, UNBOUND, consumer);

        assertEquals(setOf(12, 22), result);
    }


    @Test
    public void rightOverlap()
    {
        rightOverlapJoin(R, S, consumer);

        assertEquals(setOf(45), result);
    }


    @Test
    public void rightOverlapWithEpsilon()
    {
        rightOverlapJoin(R, S, UNBOUND, 0, consumer);

        assertEquals(setOf(), result);
    }


    @Test
    public void during()
    {
        duringJoin(R, S, consumer);

        assertEquals(setOf(32, 42), result);
    }


    @Test
    public void duringWithDelta()
    {
        duringJoin(R, S, 4, UNBOUND, consumer);

        assertEquals(setOf(32), result);
    }


    @Test
    public void reverseDuring()
    {
        reverseDuringJoin(R, S, consumer);

        assertEquals(setOf(11, 13, 21, 23, 24, 25, 35), result);
    }


    @Test
    public void reverseDuringWithDeltaAndEpsilon()
    {
        reverseDuringJoin(R, S, 4, 3, consumer);

        assertEquals(setOf(11, 13, 35), result);
    }


    @Test
    public void before()
    {
        beforeJoin(R, S, 3, consumer);

        assertEquals(setOf(15), result);
    }


    @Test
    public void after()
    {
        afterJoin(R, S, 2, consumer);

        assertEquals(setOf(33, 34), result);
    }

}

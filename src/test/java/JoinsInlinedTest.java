import java.util.function.BiConsumer;
import org.junit.BeforeClass;
import org.junit.Test;

import algorithms.joins.Joins2;
import algorithms.joins.JoinsInlined;
import model.Index;
import model.Relation;
import model.Tuple;

import static algorithms.joins.Joins2.beforeJoin;
import static algorithms.joins.JoinsInlined.beforeJoinInlined;
import static org.junit.Assert.assertEquals;


public final class JoinsInlinedTest
{
    private static Relation R;
    private static Relation S;

    @BeforeClass
    public static void setUpClass()
    {
        R = Relation.generateUniform(1000, 1, 100, 1, 10000, 453565455);
        S = Relation.generateUniform(1000, 1, 100, 1, 10000, 585);

        R.setIndex(new Index(R));
        S.setIndex(new Index(S));
    }

    private int result;

    private final BiConsumer<Tuple, Tuple> consumer = (r, s) -> result += r.start + s.end;


    @Test
    public void before()
    {
        for (int delta = 1; delta < 10; delta++)
        {
            result = 0;

            beforeJoin(R, S, delta, consumer);

            int inlinedResult = beforeJoinInlined(R, S, delta);

            assertEquals(result, inlinedResult);
        }

    }
}

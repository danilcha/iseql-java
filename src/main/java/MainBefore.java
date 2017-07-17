import java.util.function.Supplier;

import model.Index;
import model.Relation;

import static algorithms.joins.Joins2.beforeJoin;
import static algorithms.joins.JoinsInlined.beforeJoinInlined;


public final class MainBefore
{
    @SuppressWarnings("PublicField")
    private static class MutableInteger
    {
        public int value;
    }


    private static void timer(Supplier<Integer> code)
    {
        long start = System.nanoTime();

        Integer result = code.get();

        long stop = System.nanoTime();

        System.out.printf("%d\t", result);
        System.out.printf("%f\t", (stop - start) / 1.0e9);
    }


    public static void main(String[] args)
    {
        Relation R = Relation.generateUniform(1_000_000, 1, 10000, 1, 100_000, 123123);
        Relation S = Relation.generateUniform(1_000_000, 1, 10000, 1, 100_000, 123);

        R.setIndex(new Index(R));
        S.setIndex(new Index(S));

        for (int i = 1; i < 50; i++)
        {
            System.out.printf("%d\t", i);

            if ("normal".equals(args[0]))
            {
                timer(() ->
                {
                    MutableInteger result = new MutableInteger();
                    beforeJoin(R, S, 3, (r, s) -> result.value += r.start + s.end);
                    return result.value;
                });
            }

            if ("inlined".equals(args[0]))
            {
                timer(() -> beforeJoinInlined(R, S, 3));
            }


            System.out.println();
        }

    }
}


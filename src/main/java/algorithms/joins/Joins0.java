package algorithms.joins;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

import algorithms.iterators.Iterator;
import model.Endpoint;
import model.Relation;
import model.Tuple;


public final class Joins0
{
    static void joinByS(Relation R, Relation S, Iterator itR, Iterator itS,
                        BiPredicate<Endpoint, Endpoint> comp,
                        BiConsumer<Tuple, Tuple> consumer)
    {
        Map<Integer, Tuple> activeR = new HashMap<>();

        while (true)
        {
            if (comp.test(itR.getEndpoint(), itS.getEndpoint()))
            {
                Endpoint endpointR = itR.getEndpoint();
                int tid = endpointR.getTID();

                if (endpointR.isStart())
                    activeR.put(tid, R.get(tid));
                else
                    activeR.remove(tid);

                itR.moveToNextEndpoint();
                if (itR.isFinished())
                    break;
            }
            else
            {
                Tuple s = S.get(itS.getEndpoint().getTID());
                for (Tuple r : activeR.values())
                {
                    consumer.accept(r, s);
                }

                itS.moveToNextEndpoint();
                if (itS.isFinished())
                    break;
            }
        }
    }
}

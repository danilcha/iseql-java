package algorithms.joins;

import java.util.HashMap;
import java.util.Map;

import model.Endpoint;
import model.Index;
import model.Relation;
import model.Tuple;


public final class JoinsInlined
{
    public static int beforeJoinInlined(Relation R, Relation S, int delta)
    {
        int result = 0;

        Map<Integer, Tuple> activeR = new HashMap<>();

        Index indexR = R.getIndex();
        Index indexS = S.getIndex();

        int indexREnd = indexR.size();
        int indexSEnd = indexS.size();

        int itR1 = 0;
        int itR2 = 0;
        int itS = 0;

        int itR1Shift = Endpoint.calculateShiftArgument(1, Endpoint.Type.END, Endpoint.Type.START);
        int itR2Shift = Endpoint.calculateShiftArgument(delta);

        while (indexR.get(itR1).isStart())
            ++itR1;
        Endpoint endpointR = indexR.get(itR1).shiftedBy(itR1Shift);
        do
            ++itR1;
        while (indexR.get(itR1).isStart());

        while (indexR.get(itR2).isStart())
            ++itR2;

        while (indexS.get(itS).isEnd()) // useless, because any index starts with a 'start' endpoint
            ++itS;

        while (true)
        {
            if (endpointR.getTimestampAndType() <= indexS.get(itS).getTimestampAndType())
            {
                int tid = endpointR.getTID();

                if (endpointR.isStart())
                    activeR.put(tid, R.get(tid));
                else
                    activeR.remove(tid);

                Endpoint endpointR1 = indexR.get(itR1).shiftedBy(itR1Shift);
                Endpoint endpointR2 = indexR.get(itR2).shiftedBy(itR2Shift);

                if (itR1 != indexREnd && endpointR1.getTimestampAndType() < endpointR2.getTimestampAndType())
                {
                    endpointR = endpointR1;
                    do
                        ++itR1;
                    while (itR1 != indexREnd && indexR.get(itR1).isStart());
                }
                else
                {
                    endpointR = endpointR2;
                    do
                        ++itR2;
                    while (itR2 != indexREnd && indexR.get(itR2).isStart());
                }

                if (itR2 == indexREnd)
                    break;
            }
            else
            {
                Tuple s = S.get(indexS.get(itS).getTID());

                for (Tuple r : activeR.values())
                {
                    result += r.start + s.end;
                }

                do
                    ++itS;
                while (itS != indexSEnd && indexS.get(itS).isEnd());

                if (itS == indexSEnd)
                    break;
            }
        }

        return result;
    }
}

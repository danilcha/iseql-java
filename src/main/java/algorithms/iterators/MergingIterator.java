package algorithms.iterators;

import model.Endpoint;


public final class MergingIterator extends Iterator
{
    private final Iterator iterator1;
    private final Iterator iterator2;
    Endpoint endpoint;

    public MergingIterator(Iterator iterator1, Iterator iterator2)
    {
        this.iterator1 = iterator1;
        this.iterator2 = iterator2;

        moveToNextEndpoint();
    }

    @Override
    public void moveToNextEndpoint()
    {
        Endpoint endpoint1 = iterator1.getEndpoint();
        Endpoint endpoint2 = iterator2.getEndpoint();

        if (!iterator1.isFinished() && endpoint1.getTimestampAndType() < endpoint2.getTimestampAndType())
        {
            endpoint = endpoint1;
            iterator1.moveToNextEndpoint();
        }
        else
        {
            endpoint = endpoint2;
            iterator2.moveToNextEndpoint();
        }
    }

    @Override
    public boolean isFinished()
    {
        return iterator2.isFinished();
    }

    @Override
    public Endpoint getEndpoint()
    {
        return endpoint;
    }
}

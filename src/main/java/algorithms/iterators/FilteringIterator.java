package algorithms.iterators;

import model.Endpoint;
import model.Index;


public final class FilteringIterator extends Iterator
{
    private final Iterator iterator;
    private final Endpoint.Type type;

    public FilteringIterator(Index index, Endpoint.Type type)
    {
        this(new IndexIterator(index), type);
    }

    public FilteringIterator(Iterator iterator, Endpoint.Type type)
    {
        this.iterator = iterator;
        this.type = type;

        while (isWrongType())
            this.iterator.moveToNextEndpoint();
    }

    @Override
    public void moveToNextEndpoint()
    {
        do
            iterator.moveToNextEndpoint();
        while (!isFinished() && isWrongType());
    }

    @Override
    public boolean isFinished()
    {
        return iterator.isFinished();
    }

    @Override
    public Endpoint getEndpoint()
    {
        return iterator.getEndpoint();
    }

    private boolean isWrongType()
    {
        return getEndpoint().getType() != type;
    }
}

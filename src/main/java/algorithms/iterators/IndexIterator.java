package algorithms.iterators;

import model.Endpoint;
import model.Index;


public final class IndexIterator extends Iterator
{
    private final Index index;
    private int pos;

    public IndexIterator(Index index)
    {
        this.index = index;
    }

    @Override
    public void moveToNextEndpoint()
    {
        pos++;
    }

    @Override
    public boolean isFinished()
    {
        return pos == index.size();
    }

    @Override
    public Endpoint getEndpoint()
    {
        return index.get(pos);
    }
}

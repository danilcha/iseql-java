package algorithms.iterators;

import model.Endpoint;


public abstract class Iterator
{
    public abstract void moveToNextEndpoint();

    public abstract boolean isFinished();

    public abstract Endpoint getEndpoint();
}

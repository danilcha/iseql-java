package algorithms.iterators;

import model.Endpoint;


public final class ShiftingIterator extends Iterator
{
    private final Iterator iterator;
    private final int shiftArgument;

    public ShiftingIterator(Iterator iterator, int delta)
    {
        this.iterator = iterator;
        shiftArgument = Endpoint.calculateShiftArgument(delta);
    }

    public ShiftingIterator(Iterator iterator, int delta, Endpoint.Type fromType, Endpoint.Type toType)
    {
        this.iterator = iterator;
        shiftArgument = Endpoint.calculateShiftArgument(delta, fromType, toType);
    }

    @Override
    public void moveToNextEndpoint()
    {
        iterator.moveToNextEndpoint();
    }

    @Override
    public boolean isFinished()
    {
        return iterator.isFinished();
    }

    @Override
    public Endpoint getEndpoint()
    {
        return iterator.getEndpoint().shiftedBy(shiftArgument);
    }
}

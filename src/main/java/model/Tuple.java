package model;

public final class Tuple
{
    public final int start;
    public final int end;
    public final int id;

    public Tuple(int start, int end, int id)
    {
        this.start = start;
        this.end = end;
        this.id = id;
    }

    public Tuple(int start, int end)
    {
        this(start, end, 0);
    }
}


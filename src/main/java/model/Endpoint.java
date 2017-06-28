package model;

public final class Endpoint
{
    public enum Type { START, END }

    private final int timestampAndType;
    private final int tid;

    public Endpoint(int timestampAndType, int tid)
    {
        this.timestampAndType = timestampAndType;
        this.tid = tid;
    }


    public Endpoint(int timestamp, Type type, int tid)
    {
        timestampAndType = timestamp << 1 | type.ordinal();
        this.tid = tid;
    }

    public int getTID()
    {
        return tid;
    }

    public int getTimestamp()
    {
        return timestampAndType >>> 1;
    }

    public int getTimestampAndType()
    {
        return timestampAndType;
    }

    public Type getType()
    {
        return (timestampAndType & 1) == 0 ? Type.START : Type.END;
    }

    public boolean isStart()
    {
        return getType() == Type.START;
    }

    public boolean isEnd()
    {
        return getType() == Type.END;
    }

    public static int calculateShiftArgument(int delta)
    {
        return delta << 1;
    }

    public static int calculateShiftArgument(int delta, Type fromType, Type toType)
    {
        return calculateShiftArgument(delta) + toType.ordinal() - fromType.ordinal();
    }

    public Endpoint shiftedBy(int shiftArgument)
    {
        return new Endpoint(timestampAndType + shiftArgument, tid);
    }

}

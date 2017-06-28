package model;

import java.util.ArrayList;
import java.util.Comparator;


@SuppressWarnings("ClassExtendsConcreteCollection")
public final class Index extends ArrayList<Endpoint>
{
    public Index()
    {

    }

    public Index(Relation r)
    {
        buildFor(r);
    }


    void buildFor(Relation r)
    {
        ensureCapacity(r.size() * 2);

        for (int i = 0; i < r.size(); i++)
        {
            add(new Endpoint(r.get(i).start, Endpoint.Type.START, i));
            add(new Endpoint(r.get(i).end,   Endpoint.Type.END,   i));
        }

        sort(Comparator.comparingInt(Endpoint::getTimestampAndType));
    }

}

package uet.ppvan.mangareader.services.impl;

import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import uet.ppvan.mangareader.dtos.MangaDetails;

import java.util.List;

public class FakeTuple implements Tuple {

    private MangaDetails details;

    public FakeTuple(MangaDetails details) {
        this.details = details;
    }

    public FakeTuple() {

    }


    @Override
    public <X> X get(TupleElement<X> tupleElement) {
        return null;
    }

    /**
     * This is just a dummy object for testing.
     * So I think it's ok to ignore uncheck warning.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <X> X get(String alias, Class<X> type) {
        try {
            return switch (alias) {
                case "name" -> (X) details.name();
                case "other_name" -> (X) details.otherName();
                case "cover" -> (X) details.cover();
                case "description" -> (X) details.description();
                case "author" -> (X) details.author();
                case "status" -> (X) details.status();
                case "last_update" -> (X) details.lastUpdate();
                default -> null;
            };
        } catch (Exception ex) {
            throw new IllegalArgumentException("The no such field: " + alias, ex);
        }
    }

    @Override
    public Object get(String alias) {
        return switch (alias) {
            case "name" -> details.name();
            case "cover" -> details.cover();
            case "description" -> details.description();
            case "author" -> details.author();
            case "status" -> details.status();
            case "last_update" -> details.lastUpdate();
            default -> null;
        };
    }

    @Override
    public <X> X get(int i, Class<X> type) {
        return null;
    }

    @Override
    public Object get(int i) {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public List<TupleElement<?>> getElements() {
        return null;
    }
}

package com.geniusver.util.objectstorage;

/**
 * ObjectWrapper
 *
 * @author GeniusV
 */

public class ObjectWrapper<T> {
    private String topic;
    private String id;
    private T obj;

    public ObjectWrapper(String topic, String id, T obj) {
        this.topic = topic;
        this.id = id;
        this.obj = obj;
    }

    public String getTopic() {
        return topic;
    }

    public String getId() {
        return id;
    }

    public T getObj() {
        return obj;
    }

    @Override
    public String toString() {
        return "ObjectWrapper{" +
                "topic='" + topic + '\'' +
                ", id='" + id + '\'' +
                ", obj=" + obj +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObjectWrapper<?> that = (ObjectWrapper<?>) o;

        if (topic != null ? !topic.equals(that.topic) : that.topic != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return obj != null ? obj.equals(that.obj) : that.obj == null;
    }

    @Override
    public int hashCode() {
        int result = topic != null ? topic.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (obj != null ? obj.hashCode() : 0);
        return result;
    }
}



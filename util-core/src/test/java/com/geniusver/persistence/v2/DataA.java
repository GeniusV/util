package com.geniusver.persistence.v2;

/**
 * DataA
 *
 * @author GeniusV
 */
public class DataA {
    private Long id;
    private String data1;
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataA dataA = (DataA) o;

        if (id != null ? !id.equals(dataA.id) : dataA.id != null) return false;
        if (data1 != null ? !data1.equals(dataA.data1) : dataA.data1 != null) return false;
        return version != null ? version.equals(dataA.version) : dataA.version == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (data1 != null ? data1.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}

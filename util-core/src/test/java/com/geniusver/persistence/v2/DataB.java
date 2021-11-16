package com.geniusver.persistence.v2;

/**
 * DataB
 *
 * @author GeniusV
 */
public class DataB {
    private Long id;
    private String data2;
    private Long version;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
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

        DataB dataB = (DataB) o;

        if (id != null ? !id.equals(dataB.id) : dataB.id != null) return false;
        if (data2 != null ? !data2.equals(dataB.data2) : dataB.data2 != null) return false;
        return version != null ? version.equals(dataB.version) : dataB.version == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (data2 != null ? data2.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}

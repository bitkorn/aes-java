package de.bitkorn.aes.entity;

public abstract class AbstractManufacturer {
    protected String raw;

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    abstract public int getEncOffset();
    abstract public String getLField();

    /**
     * @return L-Field (length of data)
     */
    abstract public int getLFieldPlain();

    abstract public String getCField();
    /**
     * @return C-Field (message type)
     */
    abstract public String getCFieldPlain();

    abstract public String getMField();

    /**
     * @return M-Field (manufacturer code)
     */
    abstract public String getMFieldPlain();

    abstract public String getAField();

    /**
     * @return A-Field (3x Ident No, version, Device type)
     */
    abstract public String getAFieldPlain();

    abstract public String getCIField();
    abstract public String getAccessNo();
}

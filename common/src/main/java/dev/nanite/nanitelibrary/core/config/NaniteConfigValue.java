package dev.nanite.nanitelibrary.core.config;

public class NaniteConfigValue<T> {

    private T value;
    private final String path;
    private final String comment;
    private final T defaultValue;
    private boolean invalidated;

    private final NaniteConfig naniteConfig;

    private NaniteConfigValue(NaniteConfig naniteConfig, String path, T defaultValue, String comment){
        this.naniteConfig = naniteConfig;
        this.path = path;
        this.defaultValue = defaultValue;
        this.invalidated = true;
        this.comment = comment;
        naniteConfig.registerValue(this);
    }

    protected boolean hasComment(){
        return this.comment != null;
    }

    protected String getComment(){
        return this.comment;
    }

    public String getPath(){
        return this.path;
    }

    public T getValue(){
        if(invalidated){
            this.value = naniteConfig.get(path, defaultValue);
            invalidated = false;
        }
        return this.value;
    }

    public void invalidate(){
        this.invalidated = true;
    }

    public static class Builder<T> {
        private final NaniteConfig naniteConfig;
        private String path;
        private String comment;
        private T defaultValue;

        public Builder(NaniteConfig parent){
            this.naniteConfig = parent;
        }

        public Builder<T> defaultValue(T defaultValue){
            this.defaultValue = defaultValue;
            return this;
        }

        public Builder<T> path(String path){
            this.path = path;
            return this;
        }

        public Builder<T> comment(String comment){
            this.comment = comment;
            return this;
        }

        public NaniteConfigValue<T> build(){
            return new NaniteConfigValue<>(naniteConfig, path, defaultValue, comment);
        }
    }

}

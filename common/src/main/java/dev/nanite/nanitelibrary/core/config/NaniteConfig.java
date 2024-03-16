package dev.nanite.nanitelibrary.core.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.FileNotFoundAction;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.google.common.base.Preconditions;
import dev.nanite.nanitelibrary.platform.Services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class NaniteConfig {

    private static final TomlFormat TOML_FORMAT = TomlFormat.instance();

    private CommentedConfig backingConfig;
    private Path configPath;
    private List<NaniteConfigValue<?>> configValues = new LinkedList<>();

    private NaniteConfig(String name){
        configPath = Services.PLATFORM.gamePath().resolve("config/" + name + ".toml");
    }

    public <T> void registerValue(NaniteConfigValue<T> value){
        configValues.add(value);
    }

    public <T> T get(String path, T defaultValue){
        return backingConfig.getOrElse(path, defaultValue);
    }

    public <T> void set(String path, T value){
        backingConfig.set(path, value);
    }

    public void load() {
        boolean shouldSave = !Files.exists(configPath);
        backingConfig = TOML_FORMAT.createParser().parse(configPath, FileNotFoundAction.CREATE_EMPTY);
        for (NaniteConfigValue<?> configValue : configValues) {
            if(!backingConfig.contains(configValue.getPath())){
                backingConfig.set(configValue.getPath(), configValue.getValue());
                shouldSave = true;
            }
        }
        if(shouldSave){
            save();
        }
    }

    public void save() {
        if(backingConfig != null){
            TOML_FORMAT.createWriter().write(backingConfig, configPath, WritingMode.REPLACE);
        }
    }

    public void invalidate(){
        configValues.forEach(NaniteConfigValue::invalidate);
    }

    public void fullReload() {
        load();
        invalidate();
    }

    public <T> NaniteConfigValue.Builder<T> newProperty(){
        return new NaniteConfigValue.Builder<>(this);
    }

    public NaniteConfigValue<String> newStringProperty(String path, String defaultValue){
        return new NaniteConfigValue.Builder<String>(this).path(path).defaultValue(defaultValue).build();
    }

    public NaniteConfigValue<Float> newFloatProperty(String path, float defaultValue){
        return new NaniteConfigValue.Builder<Float>(this).path(path).defaultValue(defaultValue).build();
    }

    public NaniteConfigValue<Integer> newIntegerProperty(String path, int defaultValue){
        return new NaniteConfigValue.Builder<Integer>(this).path(path).defaultValue(defaultValue).build();
    }

    public NaniteConfigValue<Double> newDoubleProperty(String path, double defaultValue){
        return new NaniteConfigValue.Builder<Double>(this).path(path).defaultValue(defaultValue).build();
    }

    public static class Builder {
        private String configName;

        public Builder(){
        }

        public Builder name(String name){
            this.configName = name;
            return this;
        }

        public NaniteConfig build(){
            Preconditions.checkNotNull(configName);
            return new NaniteConfig(this.configName);
        }
    }
}

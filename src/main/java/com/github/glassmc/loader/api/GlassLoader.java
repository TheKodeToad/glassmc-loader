package com.github.glassmc.loader.api;

import com.github.glassmc.loader.api.loader.Transformer;
import com.github.glassmc.loader.api.loader.TransformerOrder;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public interface GlassLoader {

    AtomicReference<GlassLoader> loader = new AtomicReference<>(null);

    static GlassLoader getInstance() {
        GlassLoader loader = GlassLoader.loader.get();
        if (loader == null) {
            try {
                String className = IOUtils.toString(Objects.requireNonNull(GlassLoader.class.getClassLoader().getResource("glassmc.loader.impl")), StandardCharsets.UTF_8);
                loader = (GlassLoader) Class.forName(className).getConstructor().newInstance();
                GlassLoader.loader.set(loader);
            } catch (IOException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return loader;
    }

    void runHooks(String hook);

    void registerAPI(Object api);

    <T> T getAPI(Class<T> apiClass);

    <T> void registerInterface(Class<T> interfaceClass, T implementer);

    <T> T getInterface(Class<T> interfaceClass);

    default void registerTransformer(Class<? extends Transformer> transformer) {
        this.registerTransformer(transformer, TransformerOrder.DEFAULT);
    }

    void registerTransformer(Class<? extends Transformer> transformer, TransformerOrder order);

    byte[] getClassBytes(String name) throws ClassNotFoundException;

    String getShardVersion(String id);

}

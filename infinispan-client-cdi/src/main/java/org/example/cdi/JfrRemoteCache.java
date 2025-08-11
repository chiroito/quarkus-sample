package org.example.cdi;

import io.quarkus.arc.Unremovable;
import io.quarkus.infinispan.client.Remote;
import jakarta.annotation.Priority;
import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Decorated;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptor;
import org.infinispan.client.hotrod.*;
import org.infinispan.client.hotrod.jmx.RemoteCacheClientStatisticsMXBean;
import org.infinispan.commons.api.query.ContinuousQuery;
import org.infinispan.commons.util.CloseableIterator;
import org.infinispan.commons.util.CloseableIteratorCollection;
import org.infinispan.commons.util.CloseableIteratorSet;
import org.infinispan.commons.util.IntSet;
import org.infinispan.query.dsl.Query;
import org.reactivestreams.Publisher;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;

@Decorator
@Priority(10)
@Unremovable
public class JfrRemoteCache<K, V> implements RemoteCache<K, V> {

    @Inject
    @Any
    @Delegate
    RemoteCache<K, V> cache;

    @Inject
    @Decorated
    Bean<RemoteCache<K,V>> delegateInfo;

    @Override
    public boolean removeWithVersion(K key, long version) {
        return cache.removeWithVersion(key, version);
    }

    @Override
    public V remove(Object key) {
        return cache.remove(key);
    }

    @Override
    public <T> org.infinispan.commons.api.query.Query<T> query(String query) {
        return cache.query(query);
    }

    @Override
    public ContinuousQuery<K, V> continuousQuery() {
        return cache.continuousQuery();
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return cache.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return cache.remove(key, value);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return cache.replace(key, oldValue, newValue);
    }

    @Override
    public V replace(K key, V value) {
        return cache.replace(key, value);
    }

    @Override
    public boolean replace(K key, V oldValue, V value, long lifespan, TimeUnit unit) {
        return cache.replace(key, oldValue, value, lifespan, unit);
    }

    @Override
    public V put(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        return cache.put(key, value, lifespan, lifespanUnit, maxIdleTime, maxIdleTimeUnit);
    }

    @Override
    public V putIfAbsent(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        return cache.putIfAbsent(key, value, lifespan, lifespanUnit, maxIdleTime, maxIdleTimeUnit);
    }

    @Override
    public boolean replace(K key, V oldValue, V value, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        return cache.replace(key, oldValue, value, lifespan, lifespanUnit, maxIdleTime, maxIdleTimeUnit);
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction, long lifespan, TimeUnit lifespanUnit) {
        return cache.merge(key, value, remappingFunction, lifespan, lifespanUnit);
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        return cache.merge(key, value, remappingFunction, lifespan, lifespanUnit, maxIdleTime, maxIdleTimeUnit);
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction, long lifespan, TimeUnit lifespanUnit) {
        return cache.compute(key, remappingFunction, lifespan, lifespanUnit);
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        return cache.compute(key, remappingFunction, lifespan, lifespanUnit, maxIdleTime, maxIdleTimeUnit);
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction, long lifespan, TimeUnit lifespanUnit) {
        return cache.computeIfPresent(key, remappingFunction, lifespan, lifespanUnit);
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        return cache.computeIfPresent(key, remappingFunction, lifespan, lifespanUnit, maxIdleTime, maxIdleTimeUnit);
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction, long lifespan, TimeUnit lifespanUnit) {
        return cache.computeIfAbsent(key, mappingFunction, lifespan, lifespanUnit);
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        return cache.computeIfAbsent(key, mappingFunction, lifespan, lifespanUnit, maxIdleTime, maxIdleTimeUnit);
    }

    @Override
    public CompletableFuture<Boolean> removeWithVersionAsync(K key, long version) {
        return cache.removeWithVersionAsync(key, version);
    }

    @Override
    public boolean replaceWithVersion(K key, V newValue, long version) {
        return cache.replaceWithVersion(key, newValue, version);
    }

    @Override
    public boolean replaceWithVersion(K key, V newValue, long version, int lifespanSeconds) {
        return cache.replaceWithVersion(key, newValue, version, lifespanSeconds);
    }

    @Override
    public boolean replaceWithVersion(K key, V newValue, long version, int lifespanSeconds, int maxIdleTimeSeconds) {
        return cache.replaceWithVersion(key, newValue, version, lifespanSeconds, maxIdleTimeSeconds);
    }

    @Override
    public boolean replaceWithVersion(K key, V newValue, long version, long lifespan, TimeUnit lifespanTimeUnit, long maxIdle, TimeUnit maxIdleTimeUnit) {
        return cache.replaceWithVersion(key, newValue, version, lifespan, lifespanTimeUnit, maxIdle, maxIdleTimeUnit);
    }

    @Override
    public CompletableFuture<Boolean> replaceWithVersionAsync(K key, V newValue, long version) {
        return cache.replaceWithVersionAsync(key, newValue, version);
    }

    @Override
    public CompletableFuture<Boolean> replaceWithVersionAsync(K key, V newValue, long version, int lifespanSeconds) {
        return cache.replaceWithVersionAsync(key, newValue, version, lifespanSeconds);
    }

    @Override
    public CompletableFuture<Boolean> replaceWithVersionAsync(K key, V newValue, long version, int lifespanSeconds, int maxIdleSeconds) {
        return cache.replaceWithVersionAsync(key, newValue, version, lifespanSeconds, maxIdleSeconds);
    }

    @Override
    public CompletableFuture<Boolean> replaceWithVersionAsync(K key, V newValue, long version, long lifespanSeconds, TimeUnit lifespanTimeUnit, long maxIdle, TimeUnit maxIdleTimeUnit) {
        return cache.replaceWithVersionAsync(key, newValue, version, lifespanSeconds, lifespanTimeUnit, maxIdle, maxIdleTimeUnit);
    }

    @Override
    public CloseableIterator<Entry<Object, Object>> retrieveEntries(String filterConverterFactory, Object[] filterConverterParams, Set<Integer> segments, int batchSize) {
        return cache.retrieveEntries(filterConverterFactory, filterConverterParams, segments, batchSize);
    }

    @Override
    public <E> Publisher<Entry<K, E>> publishEntries(String filterConverterFactory, Object[] filterConverterParams, Set<Integer> segments, int batchSize) {
        return cache.publishEntries(filterConverterFactory, filterConverterParams, segments, batchSize);
    }

    @Override
    public CloseableIterator<Entry<Object, Object>> retrieveEntriesByQuery(Query<?> filterQuery, Set<Integer> segments, int batchSize) {
        return cache.retrieveEntriesByQuery(filterQuery, segments, batchSize);
    }

    @Override
    public <E> Publisher<Entry<K, E>> publishEntriesByQuery(Query<?> filterQuery, Set<Integer> segments, int batchSize) {
        return cache.publishEntriesByQuery(filterQuery, segments, batchSize);
    }

    @Override
    public CloseableIterator<Entry<Object, MetadataValue<Object>>> retrieveEntriesWithMetadata(Set<Integer> segments, int batchSize) {
        return cache.retrieveEntriesWithMetadata(segments, batchSize);
    }

    @Override
    public Publisher<Entry<K, MetadataValue<V>>> publishEntriesWithMetadata(Set<Integer> segments, int batchSize) {
        return cache.publishEntriesWithMetadata(segments, batchSize);
    }

    @Override
    public MetadataValue<V> getWithMetadata(K key) {
        return cache.getWithMetadata(key);
    }

    @Override
    public CompletableFuture<MetadataValue<V>> getWithMetadataAsync(K key) {
        return cache.getWithMetadataAsync(key);
    }

    @Override
    public CloseableIteratorSet<K> keySet(IntSet segments) {
        return cache.keySet(segments);
    }

    @Override
    public CloseableIteratorCollection<V> values(IntSet segments) {
        return cache.values(segments);
    }

    @Override
    public CloseableIteratorSet<Entry<K, V>> entrySet(IntSet segments) {
        return cache.entrySet(segments);
    }

    @Override
    public String getName() {
        return cache.getName();
    }

    @Override
    public String getVersion() {
        return cache.getVersion();
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return cache.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return cache.containsValue(value);
    }

    @Override
    public V get(Object key) {
        System.out.println("JfrRemoteCache.get: " + key + " -> " + cache.get(key));
        return cache.get(key);
    }

    @Override
    public V put(K key, V value) {
        return cache.put(key, value);
    }

    @Override
    public V put(K key, V value, long lifespan, TimeUnit unit) {
        return cache.put(key, value, lifespan, unit);
    }

    @Override
    public V putIfAbsent(K key, V value, long lifespan, TimeUnit unit) {
        return cache.putIfAbsent(key, value, lifespan, unit);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map, long lifespan, TimeUnit unit) {
        cache.putAll(map, lifespan, unit);
    }

    @Override
    public V replace(K key, V value, long lifespan, TimeUnit unit) {
        return cache.replace(key, value, lifespan, unit);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        cache.putAll(map, lifespan, lifespanUnit, maxIdleTime, maxIdleTimeUnit);
    }

    @Override
    public V replace(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        return cache.replace(key, value, lifespan, lifespanUnit, maxIdleTime, maxIdleTimeUnit);
    }

    @Override
    public CompletableFuture<V> putAsync(K key, V value) {
        return cache.putAsync(key, value);
    }

    @Override
    public CompletableFuture<V> putAsync(K key, V value, long lifespan, TimeUnit unit) {
        return cache.putAsync(key, value, lifespan, unit);
    }

    @Override
    public CompletableFuture<V> putAsync(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        return cache.putAsync(key, value, lifespan, lifespanUnit, maxIdle, maxIdleUnit);
    }

    @Override
    public CompletableFuture<Void> putAllAsync(Map<? extends K, ? extends V> data) {
        return cache.putAllAsync(data);
    }

    @Override
    public CompletableFuture<Void> putAllAsync(Map<? extends K, ? extends V> data, long lifespan, TimeUnit unit) {
        return cache.putAllAsync(data, lifespan, unit);
    }

    @Override
    public CompletableFuture<Void> putAllAsync(Map<? extends K, ? extends V> data, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        return cache.putAllAsync(data, lifespan, lifespanUnit, maxIdle, maxIdleUnit);
    }

    @Override
    public CompletableFuture<Void> clearAsync() {
        return cache.clearAsync();
    }

    @Override
    public CompletableFuture<Long> sizeAsync() {
        return cache.sizeAsync();
    }

    @Override
    public CompletableFuture<V> putIfAbsentAsync(K key, V value) {
        return cache.putIfAbsentAsync(key, value);
    }

    @Override
    public CompletableFuture<V> putIfAbsentAsync(K key, V value, long lifespan, TimeUnit unit) {
        return cache.putIfAbsentAsync(key, value, lifespan, unit);
    }

    @Override
    public CompletableFuture<V> putIfAbsentAsync(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        return cache.putIfAbsentAsync(key, value, lifespan, lifespanUnit, maxIdle, maxIdleUnit);
    }

    @Override
    public CompletableFuture<V> removeAsync(Object key) {
        return cache.removeAsync(key);
    }

    @Override
    public CompletableFuture<Boolean> removeAsync(Object key, Object value) {
        return cache.removeAsync(key, value);
    }

    @Override
    public CompletableFuture<V> replaceAsync(K key, V value) {
        return cache.replaceAsync(key, value);
    }

    @Override
    public CompletableFuture<V> replaceAsync(K key, V value, long lifespan, TimeUnit unit) {
        return cache.replaceAsync(key, value, lifespan, unit);
    }

    @Override
    public CompletableFuture<V> replaceAsync(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        return cache.replaceAsync(key, value, lifespan, lifespanUnit, maxIdle, maxIdleUnit);
    }

    @Override
    public CompletableFuture<Boolean> replaceAsync(K key, V oldValue, V newValue) {
        return cache.replaceAsync(key, oldValue, newValue);
    }

    @Override
    public CompletableFuture<Boolean> replaceAsync(K key, V oldValue, V newValue, long lifespan, TimeUnit unit) {
        return cache.replaceAsync(key, oldValue, newValue, lifespan, unit);
    }

    @Override
    public CompletableFuture<Boolean> replaceAsync(K key, V oldValue, V newValue, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        return cache.replaceAsync(key, oldValue, newValue, lifespan, lifespanUnit, maxIdle, maxIdleUnit);
    }

    @Override
    public CompletableFuture<V> getAsync(K key) {
        return cache.getAsync(key);
    }

    @Override
    public CompletableFuture<V> computeAsync(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return cache.computeAsync(key, remappingFunction);
    }

    @Override
    public CompletableFuture<V> computeAsync(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction, long lifespan, TimeUnit lifespanUnit) {
        return cache.computeAsync(key, remappingFunction, lifespan, lifespanUnit);
    }

    @Override
    public CompletableFuture<V> computeAsync(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        return cache.computeAsync(key, remappingFunction, lifespan, lifespanUnit, maxIdle, maxIdleUnit);
    }

    @Override
    public CompletableFuture<V> computeIfAbsentAsync(K key, Function<? super K, ? extends V> mappingFunction) {
        return cache.computeIfAbsentAsync(key, mappingFunction);
    }

    @Override
    public CompletableFuture<V> computeIfAbsentAsync(K key, Function<? super K, ? extends V> mappingFunction, long lifespan, TimeUnit lifespanUnit) {
        return cache.computeIfAbsentAsync(key, mappingFunction, lifespan, lifespanUnit);
    }

    @Override
    public CompletableFuture<V> computeIfAbsentAsync(K key, Function<? super K, ? extends V> mappingFunction, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        return cache.computeIfAbsentAsync(key, mappingFunction, lifespan, lifespanUnit, maxIdle, maxIdleUnit);
    }

    @Override
    public CompletableFuture<V> computeIfPresentAsync(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return cache.computeIfPresentAsync(key, remappingFunction);
    }

    @Override
    public CompletableFuture<V> computeIfPresentAsync(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction, long lifespan, TimeUnit lifespanUnit) {
        return cache.computeIfPresentAsync(key, remappingFunction, lifespan, lifespanUnit);
    }

    @Override
    public CompletableFuture<V> computeIfPresentAsync(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        return cache.computeIfPresentAsync(key, remappingFunction, lifespan, lifespanUnit, maxIdle, maxIdleUnit);
    }

    @Override
    public CompletableFuture<V> mergeAsync(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return cache.mergeAsync(key, value, remappingFunction);
    }

    @Override
    public CompletableFuture<V> mergeAsync(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction, long lifespan, TimeUnit lifespanUnit) {
        return cache.mergeAsync(key, value, remappingFunction, lifespan, lifespanUnit);
    }

    @Override
    public CompletableFuture<V> mergeAsync(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        return cache.mergeAsync(key, value, remappingFunction, lifespan, lifespanUnit, maxIdle, maxIdleUnit);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        cache.putAll(m);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public RemoteCacheClientStatisticsMXBean clientStatistics() {
        return cache.clientStatistics();
    }

    @Override
    public ServerStatistics serverStatistics() {
        return cache.serverStatistics();
    }

    @Override
    public CompletionStage<ServerStatistics> serverStatisticsAsync() {
        return cache.serverStatisticsAsync();
    }

    @Override
    public RemoteCache<K, V> withFlags(Flag... flags) {
        return cache.withFlags(flags);
    }

    @Override
    public RemoteCacheContainer getRemoteCacheContainer() {
        return cache.getRemoteCacheContainer();
    }

    @Override
    public Map<K, V> getAll(Set<? extends K> keys) {
        return cache.getAll(keys);
    }

    @Override
    public String getProtocolVersion() {
        return cache.getProtocolVersion();
    }

    @Override
    public void addClientListener(Object listener) {
        cache.addClientListener(listener);
    }

    @Override
    public void addClientListener(Object listener, Object[] filterFactoryParams, Object[] converterFactoryParams) {
        cache.addClientListener(listener, filterFactoryParams, converterFactoryParams);
    }

    @Override
    public void removeClientListener(Object listener) {
        cache.removeClientListener(listener);
    }

    @Override
    public <T> T execute(String taskName, Map<String, ?> params) {
        return cache.execute(taskName, params);
    }

    @Override
    public CacheTopologyInfo getCacheTopologyInfo() {
        return cache.getCacheTopologyInfo();
    }

    @Override
    public StreamingRemoteCache<K> streaming() {
        return cache.streaming();
    }

    @Override
    public <T, U> RemoteCache<T, U> withDataFormat(DataFormat dataFormat) {
        return cache.withDataFormat(dataFormat);
    }

    @Override
    public DataFormat getDataFormat() {
        return cache.getDataFormat();
    }

    @Override
    public boolean isTransactional() {
        return cache.isTransactional();
    }

    @Override
    public void start() {
        cache.start();
    }

    @Override
    public void stop() {
        cache.stop();
    }
}
package org.example.cdi;

import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.ProtoSchema;

@ProtoSchema(includeClasses = Greeting.class)
public interface GreetingSchema extends GeneratedSchema {
}
/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.parser.java;

import java.util.List;

import org.jboss.forge.parser.Internal;
import org.jboss.forge.parser.Origin;

/**
 * Represents one of the constant members of a {@link JavaEnum}.
 */
public interface EnumConstant<O extends JavaEnum<O>> extends Internal, Origin<O>,
         AnnotationTarget<O>, Named
{
   /**
    * Represents the anonymous subclass "body" of a {@link EnumConstant}.
    */
   public interface ReadBody<O extends ReadBody<O>> extends JavaType<O>, FieldHolder<O>, MethodHolder<O>
   {
   }

   /**
    * Get the constructor arguments of this enum constant.
    */
   List<String> getConstructorArguments();

   /**
    * Get the {@link Body} of this enum constant.
    */
   ReadBody<?> getBody();
}

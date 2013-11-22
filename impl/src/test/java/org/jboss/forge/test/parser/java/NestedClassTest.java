package org.jboss.forge.test.parser.java;

import java.util.List;

import org.jboss.forge.parser.JavaParser;
import org.jboss.forge.parser.java.source.Import;
import org.jboss.forge.parser.java.source.JavaAnnotationSource;
import org.jboss.forge.parser.java.source.JavaClassSource;
import org.jboss.forge.parser.java.source.JavaEnumSource;
import org.jboss.forge.parser.java.source.JavaInterfaceSource;
import org.jboss.forge.parser.java.source.JavaSource;
import org.junit.Assert;
import org.junit.Test;

public class NestedClassTest
{

   @Test
   public void testImportNestedClass()
   {
      JavaClassSource javaClass = JavaParser.create(JavaClassSource.class);
      Import imprt = javaClass.addImport(NestedClass.class);

      Assert.assertEquals("org.jboss.forge.test.parser.java.NestedClassTest.NestedClass",
               imprt.getQualifiedName());
   }

   @Test
   public void testGetNestedClasses()
   {
      JavaClassSource javaClass = JavaParser
               .parse(JavaClassSource.class, "package org.example; public class OuterClass { " +
                        "  public class InnerClass1{ " +
                        "    public class InnerClass3{}" +
                        "  } " +
                        "  public class InnerClass2{} " +
                        "}");

      Assert.assertEquals("org.example.OuterClass", javaClass.getCanonicalName());
      List<JavaSource<?>> nestedClasses = javaClass.getNestedClasses();
      JavaSource<?> inner1 = nestedClasses.get(0);
      JavaSource<?> inner2 = nestedClasses.get(1);
      Assert.assertEquals(javaClass, inner1.getEnclosingType());
      Assert.assertEquals("org.example.OuterClass.InnerClass1", inner1.getCanonicalName());
      Assert.assertEquals("org.example.OuterClass$InnerClass1", inner1.getQualifiedName());
      Assert.assertEquals(javaClass, inner2.getEnclosingType());
      Assert.assertEquals("InnerClass1", inner1.getName());
      Assert.assertEquals("org.example.OuterClass.InnerClass2", inner2.getCanonicalName());
      Assert.assertEquals("org.example.OuterClass$InnerClass2", inner2.getQualifiedName());
      Assert.assertEquals("InnerClass2", inner2.getName());
      Assert.assertEquals(2, nestedClasses.size());
   }

   @Test
   public void testModifyNestedClassModifiesParentSource()
   {
      JavaClassSource javaClass = JavaParser
               .parse(JavaClassSource.class, "package org.example; public class OuterClass { " +
                        "  public class InnerClass1{ " +
                        "    public class InnerClass3{}" +
                        "  } " +
                        "  public class InnerClass2{} " +
                        "}");

      List<JavaSource<?>> nestedClasses = javaClass.getNestedClasses();
      JavaSource<?> inner1 = nestedClasses.get(0);
      inner1.addAnnotation(Deprecated.class);

      Assert.assertTrue(javaClass.toString().contains("@Deprecated"));
   }

   @Test
   public void testInterfaceWithNestedClass()
   {
      JavaInterfaceSource javaInterface = JavaParser
               .parse(JavaInterfaceSource.class, "package org.example; public interface OuterInterface { " +
                        "  public class InnerClass1{ " +
                        "    public class InnerClass3{}" +
                        "  } " +
                        "  public class InnerClass2{} " +
                        "}");

      Assert.assertEquals("org.example.OuterInterface", javaInterface.getCanonicalName());
      List<JavaSource<?>> nestedClasses = javaInterface.getNestedClasses();
      JavaSource<?> inner1 = nestedClasses.get(0);
      JavaSource<?> inner2 = nestedClasses.get(1);
      Assert.assertEquals(javaInterface, inner1.getEnclosingType());
      Assert.assertEquals("org.example.OuterInterface.InnerClass1", inner1.getCanonicalName());
      Assert.assertEquals("org.example.OuterInterface$InnerClass1", inner1.getQualifiedName());
      Assert.assertEquals(javaInterface, inner2.getEnclosingType());
      Assert.assertEquals("InnerClass1", inner1.getName());
      Assert.assertEquals("org.example.OuterInterface.InnerClass2", inner2.getCanonicalName());
      Assert.assertEquals("org.example.OuterInterface$InnerClass2", inner2.getQualifiedName());
      Assert.assertEquals("InnerClass2", inner2.getName());
      Assert.assertEquals(2, nestedClasses.size());
   }

   @Test
   public void testEnumWithNestedClass()
   {
      JavaEnumSource javaEnum = JavaParser
               .parse(JavaEnumSource.class, "package org.example; public enum OuterEnum { " +
                        "  FOO, BAR, BAZ; " +
                        "  public class InnerClass1{ " +
                        "    public class InnerClass3{}" +
                        "  } " +
                        "  public class InnerClass2{} " +
                        "}");

      Assert.assertEquals("org.example.OuterEnum", javaEnum.getCanonicalName());
      List<JavaSource<?>> nestedClasses = javaEnum.getNestedClasses();
      JavaSource<?> inner1 = nestedClasses.get(0);
      JavaSource<?> inner2 = nestedClasses.get(1);
      Assert.assertEquals(javaEnum, inner1.getEnclosingType());
      Assert.assertEquals("org.example.OuterEnum.InnerClass1", inner1.getCanonicalName());
      Assert.assertEquals("org.example.OuterEnum$InnerClass1", inner1.getQualifiedName());
      Assert.assertEquals(javaEnum, inner2.getEnclosingType());
      Assert.assertEquals("InnerClass1", inner1.getName());
      Assert.assertEquals("org.example.OuterEnum.InnerClass2", inner2.getCanonicalName());
      Assert.assertEquals("org.example.OuterEnum$InnerClass2", inner2.getQualifiedName());
      Assert.assertEquals("InnerClass2", inner2.getName());
      Assert.assertEquals(2, nestedClasses.size());
   }

   @Test
   public void testClassWithNestedEnum()
   {
      JavaClassSource javaClass = JavaParser
               .parse(JavaClassSource.class, "package org.example; "
                        + "public class OuterClass { " +
                        "  public enum InnerEnum{A,B,C;} " +
                        "}");

      Assert.assertEquals("org.example.OuterClass", javaClass.getCanonicalName());
      List<JavaSource<?>> nestedClasses = javaClass.getNestedClasses();
      JavaSource<?> inner1 = nestedClasses.get(0);
      Assert.assertEquals(javaClass, inner1.getEnclosingType());
      Assert.assertEquals("org.example.OuterClass.InnerEnum", inner1.getCanonicalName());
      Assert.assertEquals("org.example.OuterClass$InnerEnum", inner1.getQualifiedName());
      Assert.assertEquals("InnerEnum", inner1.getName());
      Assert.assertEquals(1, nestedClasses.size());
   }

   @Test
   public void testAnnotationWithNestedClass()
   {
      JavaAnnotationSource javaAnnotation = JavaParser
               .parse(JavaAnnotationSource.class, "package org.example; public @interface OuterAnnotation { " +
                        "  public class InnerClass1{ " +
                        "    public class InnerClass3{}" +
                        "  } " +
                        "  public class InnerClass2{} " +
                        "}");

      Assert.assertEquals("org.example.OuterAnnotation", javaAnnotation.getCanonicalName());
      List<JavaSource<?>> nestedClasses = javaAnnotation.getNestedClasses();
      JavaSource<?> inner1 = nestedClasses.get(0);
      JavaSource<?> inner2 = nestedClasses.get(1);
      Assert.assertEquals(javaAnnotation, inner1.getEnclosingType());
      Assert.assertEquals("org.example.OuterAnnotation.InnerClass1", inner1.getCanonicalName());
      Assert.assertEquals("org.example.OuterAnnotation$InnerClass1", inner1.getQualifiedName());
      Assert.assertEquals(javaAnnotation, inner2.getEnclosingType());
      Assert.assertEquals("InnerClass1", inner1.getName());
      Assert.assertEquals("org.example.OuterAnnotation.InnerClass2", inner2.getCanonicalName());
      Assert.assertEquals("org.example.OuterAnnotation$InnerClass2", inner2.getQualifiedName());
      Assert.assertEquals("InnerClass2", inner2.getName());
      Assert.assertEquals(2, nestedClasses.size());
   }

   public class NestedClass
   {
   }

}

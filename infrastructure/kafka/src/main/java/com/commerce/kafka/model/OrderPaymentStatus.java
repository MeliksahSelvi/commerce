/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.commerce.kafka.model;
@org.apache.avro.specific.AvroGenerated
public enum OrderPaymentStatus implements org.apache.avro.generic.GenericEnumSymbol<OrderPaymentStatus> {
  PENDING, CANCELLED  ;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"enum\",\"name\":\"OrderPaymentStatus\",\"namespace\":\"com.commerce.kafka.model\",\"symbols\":[\"PENDING\",\"CANCELLED\"]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
}

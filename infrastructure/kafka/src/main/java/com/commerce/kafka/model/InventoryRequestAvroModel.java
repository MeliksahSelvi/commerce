/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.commerce.kafka.model;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class InventoryRequestAvroModel extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -6497456069628339330L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"InventoryRequestAvroModel\",\"namespace\":\"com.commerce.kafka.model\",\"fields\":[{\"name\":\"sagaId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"customerId\",\"type\":\"long\"},{\"name\":\"orderId\",\"type\":\"long\"},{\"name\":\"cost\",\"type\":{\"type\":\"bytes\",\"logicalType\":\"decimal\",\"precision\":10,\"scale\":2}},{\"name\":\"orderInventoryStatus\",\"type\":{\"type\":\"enum\",\"name\":\"OrderInventoryStatus\",\"symbols\":[\"CHECKING\",\"CHECKING_ROLLBACK\",\"UPDATING\",\"UPDATING_ROLLBACK\"]}},{\"name\":\"items\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"OrderItemPayload\",\"fields\":[{\"name\":\"id\",\"type\":\"long\"},{\"name\":\"orderId\",\"type\":\"long\"},{\"name\":\"productId\",\"type\":\"long\"},{\"name\":\"quantity\",\"type\":\"int\"},{\"name\":\"price\",\"type\":{\"type\":\"bytes\",\"logicalType\":\"decimal\",\"precision\":10,\"scale\":2}},{\"name\":\"totalPrice\",\"type\":{\"type\":\"bytes\",\"logicalType\":\"decimal\",\"precision\":10,\"scale\":2}}]}}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();
  static {
    MODEL$.addLogicalTypeConversion(new org.apache.avro.Conversions.DecimalConversion());
  }

  private static final BinaryMessageEncoder<InventoryRequestAvroModel> ENCODER =
      new BinaryMessageEncoder<>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<InventoryRequestAvroModel> DECODER =
      new BinaryMessageDecoder<>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<InventoryRequestAvroModel> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<InventoryRequestAvroModel> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<InventoryRequestAvroModel> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this InventoryRequestAvroModel to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a InventoryRequestAvroModel from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a InventoryRequestAvroModel instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static InventoryRequestAvroModel fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.String sagaId;
  private long customerId;
  private long orderId;
  private java.math.BigDecimal cost;
  private com.commerce.kafka.model.OrderInventoryStatus orderInventoryStatus;
  private java.util.List<com.commerce.kafka.model.OrderItemPayload> items;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public InventoryRequestAvroModel() {}

  /**
   * All-args constructor.
   * @param sagaId The new value for sagaId
   * @param customerId The new value for customerId
   * @param orderId The new value for orderId
   * @param cost The new value for cost
   * @param orderInventoryStatus The new value for orderInventoryStatus
   * @param items The new value for items
   */
  public InventoryRequestAvroModel(java.lang.String sagaId, java.lang.Long customerId, java.lang.Long orderId, java.math.BigDecimal cost, com.commerce.kafka.model.OrderInventoryStatus orderInventoryStatus, java.util.List<com.commerce.kafka.model.OrderItemPayload> items) {
    this.sagaId = sagaId;
    this.customerId = customerId;
    this.orderId = orderId;
    this.cost = cost;
    this.orderInventoryStatus = orderInventoryStatus;
    this.items = items;
  }

  @Override
  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }

  // Used by DatumWriter.  Applications should not call.
  @Override
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return sagaId;
    case 1: return customerId;
    case 2: return orderId;
    case 3: return cost;
    case 4: return orderInventoryStatus;
    case 5: return items;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  private static final org.apache.avro.Conversion<?>[] conversions =
      new org.apache.avro.Conversion<?>[] {
      null,
      null,
      null,
      new org.apache.avro.Conversions.DecimalConversion(),
      null,
      null,
      null
  };

  @Override
  public org.apache.avro.Conversion<?> getConversion(int field) {
    return conversions[field];
  }

  // Used by DatumReader.  Applications should not call.
  @Override
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: sagaId = value$ != null ? value$.toString() : null; break;
    case 1: customerId = (java.lang.Long)value$; break;
    case 2: orderId = (java.lang.Long)value$; break;
    case 3: cost = (java.math.BigDecimal)value$; break;
    case 4: orderInventoryStatus = (com.commerce.kafka.model.OrderInventoryStatus)value$; break;
    case 5: items = (java.util.List<com.commerce.kafka.model.OrderItemPayload>)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'sagaId' field.
   * @return The value of the 'sagaId' field.
   */
  public java.lang.String getSagaId() {
    return sagaId;
  }


  /**
   * Sets the value of the 'sagaId' field.
   * @param value the value to set.
   */
  public void setSagaId(java.lang.String value) {
    this.sagaId = value;
  }

  /**
   * Gets the value of the 'customerId' field.
   * @return The value of the 'customerId' field.
   */
  public long getCustomerId() {
    return customerId;
  }


  /**
   * Sets the value of the 'customerId' field.
   * @param value the value to set.
   */
  public void setCustomerId(long value) {
    this.customerId = value;
  }

  /**
   * Gets the value of the 'orderId' field.
   * @return The value of the 'orderId' field.
   */
  public long getOrderId() {
    return orderId;
  }


  /**
   * Sets the value of the 'orderId' field.
   * @param value the value to set.
   */
  public void setOrderId(long value) {
    this.orderId = value;
  }

  /**
   * Gets the value of the 'cost' field.
   * @return The value of the 'cost' field.
   */
  public java.math.BigDecimal getCost() {
    return cost;
  }


  /**
   * Sets the value of the 'cost' field.
   * @param value the value to set.
   */
  public void setCost(java.math.BigDecimal value) {
    this.cost = value;
  }

  /**
   * Gets the value of the 'orderInventoryStatus' field.
   * @return The value of the 'orderInventoryStatus' field.
   */
  public com.commerce.kafka.model.OrderInventoryStatus getOrderInventoryStatus() {
    return orderInventoryStatus;
  }


  /**
   * Sets the value of the 'orderInventoryStatus' field.
   * @param value the value to set.
   */
  public void setOrderInventoryStatus(com.commerce.kafka.model.OrderInventoryStatus value) {
    this.orderInventoryStatus = value;
  }

  /**
   * Gets the value of the 'items' field.
   * @return The value of the 'items' field.
   */
  public java.util.List<com.commerce.kafka.model.OrderItemPayload> getItems() {
    return items;
  }


  /**
   * Sets the value of the 'items' field.
   * @param value the value to set.
   */
  public void setItems(java.util.List<com.commerce.kafka.model.OrderItemPayload> value) {
    this.items = value;
  }

  /**
   * Creates a new InventoryRequestAvroModel RecordBuilder.
   * @return A new InventoryRequestAvroModel RecordBuilder
   */
  public static com.commerce.kafka.model.InventoryRequestAvroModel.Builder newBuilder() {
    return new com.commerce.kafka.model.InventoryRequestAvroModel.Builder();
  }

  /**
   * Creates a new InventoryRequestAvroModel RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new InventoryRequestAvroModel RecordBuilder
   */
  public static com.commerce.kafka.model.InventoryRequestAvroModel.Builder newBuilder(com.commerce.kafka.model.InventoryRequestAvroModel.Builder other) {
    if (other == null) {
      return new com.commerce.kafka.model.InventoryRequestAvroModel.Builder();
    } else {
      return new com.commerce.kafka.model.InventoryRequestAvroModel.Builder(other);
    }
  }

  /**
   * Creates a new InventoryRequestAvroModel RecordBuilder by copying an existing InventoryRequestAvroModel instance.
   * @param other The existing instance to copy.
   * @return A new InventoryRequestAvroModel RecordBuilder
   */
  public static com.commerce.kafka.model.InventoryRequestAvroModel.Builder newBuilder(com.commerce.kafka.model.InventoryRequestAvroModel other) {
    if (other == null) {
      return new com.commerce.kafka.model.InventoryRequestAvroModel.Builder();
    } else {
      return new com.commerce.kafka.model.InventoryRequestAvroModel.Builder(other);
    }
  }

  /**
   * RecordBuilder for InventoryRequestAvroModel instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<InventoryRequestAvroModel>
    implements org.apache.avro.data.RecordBuilder<InventoryRequestAvroModel> {

    private java.lang.String sagaId;
    private long customerId;
    private long orderId;
    private java.math.BigDecimal cost;
    private com.commerce.kafka.model.OrderInventoryStatus orderInventoryStatus;
    private java.util.List<com.commerce.kafka.model.OrderItemPayload> items;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.commerce.kafka.model.InventoryRequestAvroModel.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.sagaId)) {
        this.sagaId = data().deepCopy(fields()[0].schema(), other.sagaId);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.customerId)) {
        this.customerId = data().deepCopy(fields()[1].schema(), other.customerId);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.orderId)) {
        this.orderId = data().deepCopy(fields()[2].schema(), other.orderId);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.cost)) {
        this.cost = data().deepCopy(fields()[3].schema(), other.cost);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.orderInventoryStatus)) {
        this.orderInventoryStatus = data().deepCopy(fields()[4].schema(), other.orderInventoryStatus);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (isValidValue(fields()[5], other.items)) {
        this.items = data().deepCopy(fields()[5].schema(), other.items);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
    }

    /**
     * Creates a Builder by copying an existing InventoryRequestAvroModel instance
     * @param other The existing instance to copy.
     */
    private Builder(com.commerce.kafka.model.InventoryRequestAvroModel other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.sagaId)) {
        this.sagaId = data().deepCopy(fields()[0].schema(), other.sagaId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.customerId)) {
        this.customerId = data().deepCopy(fields()[1].schema(), other.customerId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.orderId)) {
        this.orderId = data().deepCopy(fields()[2].schema(), other.orderId);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.cost)) {
        this.cost = data().deepCopy(fields()[3].schema(), other.cost);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.orderInventoryStatus)) {
        this.orderInventoryStatus = data().deepCopy(fields()[4].schema(), other.orderInventoryStatus);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.items)) {
        this.items = data().deepCopy(fields()[5].schema(), other.items);
        fieldSetFlags()[5] = true;
      }
    }

    /**
      * Gets the value of the 'sagaId' field.
      * @return The value.
      */
    public java.lang.String getSagaId() {
      return sagaId;
    }


    /**
      * Sets the value of the 'sagaId' field.
      * @param value The value of 'sagaId'.
      * @return This builder.
      */
    public com.commerce.kafka.model.InventoryRequestAvroModel.Builder setSagaId(java.lang.String value) {
      validate(fields()[0], value);
      this.sagaId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'sagaId' field has been set.
      * @return True if the 'sagaId' field has been set, false otherwise.
      */
    public boolean hasSagaId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'sagaId' field.
      * @return This builder.
      */
    public com.commerce.kafka.model.InventoryRequestAvroModel.Builder clearSagaId() {
      sagaId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'customerId' field.
      * @return The value.
      */
    public long getCustomerId() {
      return customerId;
    }


    /**
      * Sets the value of the 'customerId' field.
      * @param value The value of 'customerId'.
      * @return This builder.
      */
    public com.commerce.kafka.model.InventoryRequestAvroModel.Builder setCustomerId(long value) {
      validate(fields()[1], value);
      this.customerId = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'customerId' field has been set.
      * @return True if the 'customerId' field has been set, false otherwise.
      */
    public boolean hasCustomerId() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'customerId' field.
      * @return This builder.
      */
    public com.commerce.kafka.model.InventoryRequestAvroModel.Builder clearCustomerId() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'orderId' field.
      * @return The value.
      */
    public long getOrderId() {
      return orderId;
    }


    /**
      * Sets the value of the 'orderId' field.
      * @param value The value of 'orderId'.
      * @return This builder.
      */
    public com.commerce.kafka.model.InventoryRequestAvroModel.Builder setOrderId(long value) {
      validate(fields()[2], value);
      this.orderId = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'orderId' field has been set.
      * @return True if the 'orderId' field has been set, false otherwise.
      */
    public boolean hasOrderId() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'orderId' field.
      * @return This builder.
      */
    public com.commerce.kafka.model.InventoryRequestAvroModel.Builder clearOrderId() {
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'cost' field.
      * @return The value.
      */
    public java.math.BigDecimal getCost() {
      return cost;
    }


    /**
      * Sets the value of the 'cost' field.
      * @param value The value of 'cost'.
      * @return This builder.
      */
    public com.commerce.kafka.model.InventoryRequestAvroModel.Builder setCost(java.math.BigDecimal value) {
      validate(fields()[3], value);
      this.cost = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'cost' field has been set.
      * @return True if the 'cost' field has been set, false otherwise.
      */
    public boolean hasCost() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'cost' field.
      * @return This builder.
      */
    public com.commerce.kafka.model.InventoryRequestAvroModel.Builder clearCost() {
      cost = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'orderInventoryStatus' field.
      * @return The value.
      */
    public com.commerce.kafka.model.OrderInventoryStatus getOrderInventoryStatus() {
      return orderInventoryStatus;
    }


    /**
      * Sets the value of the 'orderInventoryStatus' field.
      * @param value The value of 'orderInventoryStatus'.
      * @return This builder.
      */
    public com.commerce.kafka.model.InventoryRequestAvroModel.Builder setOrderInventoryStatus(com.commerce.kafka.model.OrderInventoryStatus value) {
      validate(fields()[4], value);
      this.orderInventoryStatus = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'orderInventoryStatus' field has been set.
      * @return True if the 'orderInventoryStatus' field has been set, false otherwise.
      */
    public boolean hasOrderInventoryStatus() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'orderInventoryStatus' field.
      * @return This builder.
      */
    public com.commerce.kafka.model.InventoryRequestAvroModel.Builder clearOrderInventoryStatus() {
      orderInventoryStatus = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'items' field.
      * @return The value.
      */
    public java.util.List<com.commerce.kafka.model.OrderItemPayload> getItems() {
      return items;
    }


    /**
      * Sets the value of the 'items' field.
      * @param value The value of 'items'.
      * @return This builder.
      */
    public com.commerce.kafka.model.InventoryRequestAvroModel.Builder setItems(java.util.List<com.commerce.kafka.model.OrderItemPayload> value) {
      validate(fields()[5], value);
      this.items = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'items' field has been set.
      * @return True if the 'items' field has been set, false otherwise.
      */
    public boolean hasItems() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'items' field.
      * @return This builder.
      */
    public com.commerce.kafka.model.InventoryRequestAvroModel.Builder clearItems() {
      items = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public InventoryRequestAvroModel build() {
      try {
        InventoryRequestAvroModel record = new InventoryRequestAvroModel();
        record.sagaId = fieldSetFlags()[0] ? this.sagaId : (java.lang.String) defaultValue(fields()[0]);
        record.customerId = fieldSetFlags()[1] ? this.customerId : (java.lang.Long) defaultValue(fields()[1]);
        record.orderId = fieldSetFlags()[2] ? this.orderId : (java.lang.Long) defaultValue(fields()[2]);
        record.cost = fieldSetFlags()[3] ? this.cost : (java.math.BigDecimal) defaultValue(fields()[3]);
        record.orderInventoryStatus = fieldSetFlags()[4] ? this.orderInventoryStatus : (com.commerce.kafka.model.OrderInventoryStatus) defaultValue(fields()[4]);
        record.items = fieldSetFlags()[5] ? this.items : (java.util.List<com.commerce.kafka.model.OrderItemPayload>) defaultValue(fields()[5]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<InventoryRequestAvroModel>
    WRITER$ = (org.apache.avro.io.DatumWriter<InventoryRequestAvroModel>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<InventoryRequestAvroModel>
    READER$ = (org.apache.avro.io.DatumReader<InventoryRequestAvroModel>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}











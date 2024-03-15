/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.commerce.order.service.common.messaging.kafka.model;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class OrderItemPayload extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -6048237530262462867L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"OrderItemPayload\",\"namespace\":\"com.commerce.order.service.common.messaging.kafka.model\",\"fields\":[{\"name\":\"id\",\"type\":\"long\"},{\"name\":\"orderId\",\"type\":\"long\"},{\"name\":\"productId\",\"type\":\"long\"},{\"name\":\"quantity\",\"type\":\"int\"},{\"name\":\"price\",\"type\":{\"type\":\"bytes\",\"logicalType\":\"decimal\",\"precision\":10,\"scale\":2}},{\"name\":\"totalPrice\",\"type\":{\"type\":\"bytes\",\"logicalType\":\"decimal\",\"precision\":10,\"scale\":2}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();
  static {
    MODEL$.addLogicalTypeConversion(new org.apache.avro.Conversions.DecimalConversion());
  }

  private static final BinaryMessageEncoder<OrderItemPayload> ENCODER =
      new BinaryMessageEncoder<OrderItemPayload>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<OrderItemPayload> DECODER =
      new BinaryMessageDecoder<OrderItemPayload>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<OrderItemPayload> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<OrderItemPayload> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<OrderItemPayload> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<OrderItemPayload>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this OrderItemPayload to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a OrderItemPayload from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a OrderItemPayload instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static OrderItemPayload fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private long id;
  private long orderId;
  private long productId;
  private int quantity;
  private java.math.BigDecimal price;
  private java.math.BigDecimal totalPrice;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public OrderItemPayload() {}

  /**
   * All-args constructor.
   * @param id The new value for id
   * @param orderId The new value for orderId
   * @param productId The new value for productId
   * @param quantity The new value for quantity
   * @param price The new value for price
   * @param totalPrice The new value for totalPrice
   */
  public OrderItemPayload(java.lang.Long id, java.lang.Long orderId, java.lang.Long productId, java.lang.Integer quantity, java.math.BigDecimal price, java.math.BigDecimal totalPrice) {
    this.id = id;
    this.orderId = orderId;
    this.productId = productId;
    this.quantity = quantity;
    this.price = price;
    this.totalPrice = totalPrice;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return orderId;
    case 2: return productId;
    case 3: return quantity;
    case 4: return price;
    case 5: return totalPrice;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  private static final org.apache.avro.Conversion<?>[] conversions =
      new org.apache.avro.Conversion<?>[] {
      null,
      null,
      null,
      null,
      new org.apache.avro.Conversions.DecimalConversion(),
      new org.apache.avro.Conversions.DecimalConversion(),
      null
  };

  @Override
  public org.apache.avro.Conversion<?> getConversion(int field) {
    return conversions[field];
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = (java.lang.Long)value$; break;
    case 1: orderId = (java.lang.Long)value$; break;
    case 2: productId = (java.lang.Long)value$; break;
    case 3: quantity = (java.lang.Integer)value$; break;
    case 4: price = (java.math.BigDecimal)value$; break;
    case 5: totalPrice = (java.math.BigDecimal)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'id' field.
   * @return The value of the 'id' field.
   */
  public long getId() {
    return id;
  }


  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(long value) {
    this.id = value;
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
   * Gets the value of the 'productId' field.
   * @return The value of the 'productId' field.
   */
  public long getProductId() {
    return productId;
  }


  /**
   * Sets the value of the 'productId' field.
   * @param value the value to set.
   */
  public void setProductId(long value) {
    this.productId = value;
  }

  /**
   * Gets the value of the 'quantity' field.
   * @return The value of the 'quantity' field.
   */
  public int getQuantity() {
    return quantity;
  }


  /**
   * Sets the value of the 'quantity' field.
   * @param value the value to set.
   */
  public void setQuantity(int value) {
    this.quantity = value;
  }

  /**
   * Gets the value of the 'price' field.
   * @return The value of the 'price' field.
   */
  public java.math.BigDecimal getPrice() {
    return price;
  }


  /**
   * Sets the value of the 'price' field.
   * @param value the value to set.
   */
  public void setPrice(java.math.BigDecimal value) {
    this.price = value;
  }

  /**
   * Gets the value of the 'totalPrice' field.
   * @return The value of the 'totalPrice' field.
   */
  public java.math.BigDecimal getTotalPrice() {
    return totalPrice;
  }


  /**
   * Sets the value of the 'totalPrice' field.
   * @param value the value to set.
   */
  public void setTotalPrice(java.math.BigDecimal value) {
    this.totalPrice = value;
  }

  /**
   * Creates a new OrderItemPayload RecordBuilder.
   * @return A new OrderItemPayload RecordBuilder
   */
  public static com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder newBuilder() {
    return new com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder();
  }

  /**
   * Creates a new OrderItemPayload RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new OrderItemPayload RecordBuilder
   */
  public static com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder newBuilder(com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder other) {
    if (other == null) {
      return new com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder();
    } else {
      return new com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder(other);
    }
  }

  /**
   * Creates a new OrderItemPayload RecordBuilder by copying an existing OrderItemPayload instance.
   * @param other The existing instance to copy.
   * @return A new OrderItemPayload RecordBuilder
   */
  public static com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder newBuilder(com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload other) {
    if (other == null) {
      return new com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder();
    } else {
      return new com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder(other);
    }
  }

  /**
   * RecordBuilder for OrderItemPayload instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<OrderItemPayload>
    implements org.apache.avro.data.RecordBuilder<OrderItemPayload> {

    private long id;
    private long orderId;
    private long productId;
    private int quantity;
    private java.math.BigDecimal price;
    private java.math.BigDecimal totalPrice;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.orderId)) {
        this.orderId = data().deepCopy(fields()[1].schema(), other.orderId);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.productId)) {
        this.productId = data().deepCopy(fields()[2].schema(), other.productId);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.quantity)) {
        this.quantity = data().deepCopy(fields()[3].schema(), other.quantity);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.price)) {
        this.price = data().deepCopy(fields()[4].schema(), other.price);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (isValidValue(fields()[5], other.totalPrice)) {
        this.totalPrice = data().deepCopy(fields()[5].schema(), other.totalPrice);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
    }

    /**
     * Creates a Builder by copying an existing OrderItemPayload instance
     * @param other The existing instance to copy.
     */
    private Builder(com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.orderId)) {
        this.orderId = data().deepCopy(fields()[1].schema(), other.orderId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.productId)) {
        this.productId = data().deepCopy(fields()[2].schema(), other.productId);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.quantity)) {
        this.quantity = data().deepCopy(fields()[3].schema(), other.quantity);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.price)) {
        this.price = data().deepCopy(fields()[4].schema(), other.price);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.totalPrice)) {
        this.totalPrice = data().deepCopy(fields()[5].schema(), other.totalPrice);
        fieldSetFlags()[5] = true;
      }
    }

    /**
      * Gets the value of the 'id' field.
      * @return The value.
      */
    public long getId() {
      return id;
    }


    /**
      * Sets the value of the 'id' field.
      * @param value The value of 'id'.
      * @return This builder.
      */
    public com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder setId(long value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'id' field has been set.
      * @return True if the 'id' field has been set, false otherwise.
      */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'id' field.
      * @return This builder.
      */
    public com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder clearId() {
      fieldSetFlags()[0] = false;
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
    public com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder setOrderId(long value) {
      validate(fields()[1], value);
      this.orderId = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'orderId' field has been set.
      * @return True if the 'orderId' field has been set, false otherwise.
      */
    public boolean hasOrderId() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'orderId' field.
      * @return This builder.
      */
    public com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder clearOrderId() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'productId' field.
      * @return The value.
      */
    public long getProductId() {
      return productId;
    }


    /**
      * Sets the value of the 'productId' field.
      * @param value The value of 'productId'.
      * @return This builder.
      */
    public com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder setProductId(long value) {
      validate(fields()[2], value);
      this.productId = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'productId' field has been set.
      * @return True if the 'productId' field has been set, false otherwise.
      */
    public boolean hasProductId() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'productId' field.
      * @return This builder.
      */
    public com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder clearProductId() {
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'quantity' field.
      * @return The value.
      */
    public int getQuantity() {
      return quantity;
    }


    /**
      * Sets the value of the 'quantity' field.
      * @param value The value of 'quantity'.
      * @return This builder.
      */
    public com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder setQuantity(int value) {
      validate(fields()[3], value);
      this.quantity = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'quantity' field has been set.
      * @return True if the 'quantity' field has been set, false otherwise.
      */
    public boolean hasQuantity() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'quantity' field.
      * @return This builder.
      */
    public com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder clearQuantity() {
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'price' field.
      * @return The value.
      */
    public java.math.BigDecimal getPrice() {
      return price;
    }


    /**
      * Sets the value of the 'price' field.
      * @param value The value of 'price'.
      * @return This builder.
      */
    public com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder setPrice(java.math.BigDecimal value) {
      validate(fields()[4], value);
      this.price = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'price' field has been set.
      * @return True if the 'price' field has been set, false otherwise.
      */
    public boolean hasPrice() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'price' field.
      * @return This builder.
      */
    public com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder clearPrice() {
      price = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'totalPrice' field.
      * @return The value.
      */
    public java.math.BigDecimal getTotalPrice() {
      return totalPrice;
    }


    /**
      * Sets the value of the 'totalPrice' field.
      * @param value The value of 'totalPrice'.
      * @return This builder.
      */
    public com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder setTotalPrice(java.math.BigDecimal value) {
      validate(fields()[5], value);
      this.totalPrice = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'totalPrice' field has been set.
      * @return True if the 'totalPrice' field has been set, false otherwise.
      */
    public boolean hasTotalPrice() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'totalPrice' field.
      * @return This builder.
      */
    public com.commerce.order.service.common.messaging.kafka.model.OrderItemPayload.Builder clearTotalPrice() {
      totalPrice = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public OrderItemPayload build() {
      try {
        OrderItemPayload record = new OrderItemPayload();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.Long) defaultValue(fields()[0]);
        record.orderId = fieldSetFlags()[1] ? this.orderId : (java.lang.Long) defaultValue(fields()[1]);
        record.productId = fieldSetFlags()[2] ? this.productId : (java.lang.Long) defaultValue(fields()[2]);
        record.quantity = fieldSetFlags()[3] ? this.quantity : (java.lang.Integer) defaultValue(fields()[3]);
        record.price = fieldSetFlags()[4] ? this.price : (java.math.BigDecimal) defaultValue(fields()[4]);
        record.totalPrice = fieldSetFlags()[5] ? this.totalPrice : (java.math.BigDecimal) defaultValue(fields()[5]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<OrderItemPayload>
    WRITER$ = (org.apache.avro.io.DatumWriter<OrderItemPayload>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<OrderItemPayload>
    READER$ = (org.apache.avro.io.DatumReader<OrderItemPayload>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}











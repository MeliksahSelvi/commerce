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
public class AddressPayload extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 8827293517278065811L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AddressPayload\",\"namespace\":\"com.commerce.kafka.model\",\"fields\":[{\"name\":\"id\",\"type\":\"long\"},{\"name\":\"city\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"county\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"neighborhood\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"street\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"postalCode\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<AddressPayload> ENCODER =
      new BinaryMessageEncoder<>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<AddressPayload> DECODER =
      new BinaryMessageDecoder<>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<AddressPayload> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<AddressPayload> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<AddressPayload> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this AddressPayload to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a AddressPayload from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a AddressPayload instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static AddressPayload fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private long id;
  private java.lang.String city;
  private java.lang.String county;
  private java.lang.String neighborhood;
  private java.lang.String street;
  private java.lang.String postalCode;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public AddressPayload() {}

  /**
   * All-args constructor.
   * @param id The new value for id
   * @param city The new value for city
   * @param county The new value for county
   * @param neighborhood The new value for neighborhood
   * @param street The new value for street
   * @param postalCode The new value for postalCode
   */
  public AddressPayload(java.lang.Long id, java.lang.String city, java.lang.String county, java.lang.String neighborhood, java.lang.String street, java.lang.String postalCode) {
    this.id = id;
    this.city = city;
    this.county = county;
    this.neighborhood = neighborhood;
    this.street = street;
    this.postalCode = postalCode;
  }

  @Override
  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }

  // Used by DatumWriter.  Applications should not call.
  @Override
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return city;
    case 2: return county;
    case 3: return neighborhood;
    case 4: return street;
    case 5: return postalCode;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @Override
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = (java.lang.Long)value$; break;
    case 1: city = value$ != null ? value$.toString() : null; break;
    case 2: county = value$ != null ? value$.toString() : null; break;
    case 3: neighborhood = value$ != null ? value$.toString() : null; break;
    case 4: street = value$ != null ? value$.toString() : null; break;
    case 5: postalCode = value$ != null ? value$.toString() : null; break;
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
   * Gets the value of the 'city' field.
   * @return The value of the 'city' field.
   */
  public java.lang.String getCity() {
    return city;
  }


  /**
   * Sets the value of the 'city' field.
   * @param value the value to set.
   */
  public void setCity(java.lang.String value) {
    this.city = value;
  }

  /**
   * Gets the value of the 'county' field.
   * @return The value of the 'county' field.
   */
  public java.lang.String getCounty() {
    return county;
  }


  /**
   * Sets the value of the 'county' field.
   * @param value the value to set.
   */
  public void setCounty(java.lang.String value) {
    this.county = value;
  }

  /**
   * Gets the value of the 'neighborhood' field.
   * @return The value of the 'neighborhood' field.
   */
  public java.lang.String getNeighborhood() {
    return neighborhood;
  }


  /**
   * Sets the value of the 'neighborhood' field.
   * @param value the value to set.
   */
  public void setNeighborhood(java.lang.String value) {
    this.neighborhood = value;
  }

  /**
   * Gets the value of the 'street' field.
   * @return The value of the 'street' field.
   */
  public java.lang.String getStreet() {
    return street;
  }


  /**
   * Sets the value of the 'street' field.
   * @param value the value to set.
   */
  public void setStreet(java.lang.String value) {
    this.street = value;
  }

  /**
   * Gets the value of the 'postalCode' field.
   * @return The value of the 'postalCode' field.
   */
  public java.lang.String getPostalCode() {
    return postalCode;
  }


  /**
   * Sets the value of the 'postalCode' field.
   * @param value the value to set.
   */
  public void setPostalCode(java.lang.String value) {
    this.postalCode = value;
  }

  /**
   * Creates a new AddressPayload RecordBuilder.
   * @return A new AddressPayload RecordBuilder
   */
  public static com.commerce.kafka.model.AddressPayload.Builder newBuilder() {
    return new com.commerce.kafka.model.AddressPayload.Builder();
  }

  /**
   * Creates a new AddressPayload RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new AddressPayload RecordBuilder
   */
  public static com.commerce.kafka.model.AddressPayload.Builder newBuilder(com.commerce.kafka.model.AddressPayload.Builder other) {
    if (other == null) {
      return new com.commerce.kafka.model.AddressPayload.Builder();
    } else {
      return new com.commerce.kafka.model.AddressPayload.Builder(other);
    }
  }

  /**
   * Creates a new AddressPayload RecordBuilder by copying an existing AddressPayload instance.
   * @param other The existing instance to copy.
   * @return A new AddressPayload RecordBuilder
   */
  public static com.commerce.kafka.model.AddressPayload.Builder newBuilder(com.commerce.kafka.model.AddressPayload other) {
    if (other == null) {
      return new com.commerce.kafka.model.AddressPayload.Builder();
    } else {
      return new com.commerce.kafka.model.AddressPayload.Builder(other);
    }
  }

  /**
   * RecordBuilder for AddressPayload instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<AddressPayload>
    implements org.apache.avro.data.RecordBuilder<AddressPayload> {

    private long id;
    private java.lang.String city;
    private java.lang.String county;
    private java.lang.String neighborhood;
    private java.lang.String street;
    private java.lang.String postalCode;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.commerce.kafka.model.AddressPayload.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.city)) {
        this.city = data().deepCopy(fields()[1].schema(), other.city);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.county)) {
        this.county = data().deepCopy(fields()[2].schema(), other.county);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.neighborhood)) {
        this.neighborhood = data().deepCopy(fields()[3].schema(), other.neighborhood);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.street)) {
        this.street = data().deepCopy(fields()[4].schema(), other.street);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (isValidValue(fields()[5], other.postalCode)) {
        this.postalCode = data().deepCopy(fields()[5].schema(), other.postalCode);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
    }

    /**
     * Creates a Builder by copying an existing AddressPayload instance
     * @param other The existing instance to copy.
     */
    private Builder(com.commerce.kafka.model.AddressPayload other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.city)) {
        this.city = data().deepCopy(fields()[1].schema(), other.city);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.county)) {
        this.county = data().deepCopy(fields()[2].schema(), other.county);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.neighborhood)) {
        this.neighborhood = data().deepCopy(fields()[3].schema(), other.neighborhood);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.street)) {
        this.street = data().deepCopy(fields()[4].schema(), other.street);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.postalCode)) {
        this.postalCode = data().deepCopy(fields()[5].schema(), other.postalCode);
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
    public com.commerce.kafka.model.AddressPayload.Builder setId(long value) {
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
    public com.commerce.kafka.model.AddressPayload.Builder clearId() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'city' field.
      * @return The value.
      */
    public java.lang.String getCity() {
      return city;
    }


    /**
      * Sets the value of the 'city' field.
      * @param value The value of 'city'.
      * @return This builder.
      */
    public com.commerce.kafka.model.AddressPayload.Builder setCity(java.lang.String value) {
      validate(fields()[1], value);
      this.city = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'city' field has been set.
      * @return True if the 'city' field has been set, false otherwise.
      */
    public boolean hasCity() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'city' field.
      * @return This builder.
      */
    public com.commerce.kafka.model.AddressPayload.Builder clearCity() {
      city = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'county' field.
      * @return The value.
      */
    public java.lang.String getCounty() {
      return county;
    }


    /**
      * Sets the value of the 'county' field.
      * @param value The value of 'county'.
      * @return This builder.
      */
    public com.commerce.kafka.model.AddressPayload.Builder setCounty(java.lang.String value) {
      validate(fields()[2], value);
      this.county = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'county' field has been set.
      * @return True if the 'county' field has been set, false otherwise.
      */
    public boolean hasCounty() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'county' field.
      * @return This builder.
      */
    public com.commerce.kafka.model.AddressPayload.Builder clearCounty() {
      county = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'neighborhood' field.
      * @return The value.
      */
    public java.lang.String getNeighborhood() {
      return neighborhood;
    }


    /**
      * Sets the value of the 'neighborhood' field.
      * @param value The value of 'neighborhood'.
      * @return This builder.
      */
    public com.commerce.kafka.model.AddressPayload.Builder setNeighborhood(java.lang.String value) {
      validate(fields()[3], value);
      this.neighborhood = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'neighborhood' field has been set.
      * @return True if the 'neighborhood' field has been set, false otherwise.
      */
    public boolean hasNeighborhood() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'neighborhood' field.
      * @return This builder.
      */
    public com.commerce.kafka.model.AddressPayload.Builder clearNeighborhood() {
      neighborhood = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'street' field.
      * @return The value.
      */
    public java.lang.String getStreet() {
      return street;
    }


    /**
      * Sets the value of the 'street' field.
      * @param value The value of 'street'.
      * @return This builder.
      */
    public com.commerce.kafka.model.AddressPayload.Builder setStreet(java.lang.String value) {
      validate(fields()[4], value);
      this.street = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'street' field has been set.
      * @return True if the 'street' field has been set, false otherwise.
      */
    public boolean hasStreet() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'street' field.
      * @return This builder.
      */
    public com.commerce.kafka.model.AddressPayload.Builder clearStreet() {
      street = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'postalCode' field.
      * @return The value.
      */
    public java.lang.String getPostalCode() {
      return postalCode;
    }


    /**
      * Sets the value of the 'postalCode' field.
      * @param value The value of 'postalCode'.
      * @return This builder.
      */
    public com.commerce.kafka.model.AddressPayload.Builder setPostalCode(java.lang.String value) {
      validate(fields()[5], value);
      this.postalCode = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'postalCode' field has been set.
      * @return True if the 'postalCode' field has been set, false otherwise.
      */
    public boolean hasPostalCode() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'postalCode' field.
      * @return This builder.
      */
    public com.commerce.kafka.model.AddressPayload.Builder clearPostalCode() {
      postalCode = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public AddressPayload build() {
      try {
        AddressPayload record = new AddressPayload();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.Long) defaultValue(fields()[0]);
        record.city = fieldSetFlags()[1] ? this.city : (java.lang.String) defaultValue(fields()[1]);
        record.county = fieldSetFlags()[2] ? this.county : (java.lang.String) defaultValue(fields()[2]);
        record.neighborhood = fieldSetFlags()[3] ? this.neighborhood : (java.lang.String) defaultValue(fields()[3]);
        record.street = fieldSetFlags()[4] ? this.street : (java.lang.String) defaultValue(fields()[4]);
        record.postalCode = fieldSetFlags()[5] ? this.postalCode : (java.lang.String) defaultValue(fields()[5]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<AddressPayload>
    WRITER$ = (org.apache.avro.io.DatumWriter<AddressPayload>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<AddressPayload>
    READER$ = (org.apache.avro.io.DatumReader<AddressPayload>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeLong(this.id);

    out.writeString(this.city);

    out.writeString(this.county);

    out.writeString(this.neighborhood);

    out.writeString(this.street);

    out.writeString(this.postalCode);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.id = in.readLong();

      this.city = in.readString();

      this.county = in.readString();

      this.neighborhood = in.readString();

      this.street = in.readString();

      this.postalCode = in.readString();

    } else {
      for (int i = 0; i < 6; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.id = in.readLong();
          break;

        case 1:
          this.city = in.readString();
          break;

        case 2:
          this.county = in.readString();
          break;

        case 3:
          this.neighborhood = in.readString();
          break;

        case 4:
          this.street = in.readString();
          break;

        case 5:
          this.postalCode = in.readString();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.ppdai.das.service;


@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)", date = "2019-10-25")
public enum DasParameterDirection implements org.apache.thrift.TEnum {
  input(0),
  output(1),
  inputOutput(2);

  private final int value;

  private DasParameterDirection(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  @Override
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  @org.apache.thrift.annotation.Nullable
  public static DasParameterDirection findByValue(int value) { 
    switch (value) {
      case 0:
        return input;
      case 1:
        return output;
      case 2:
        return inputOutput;
      default:
        return null;
    }
  }
}

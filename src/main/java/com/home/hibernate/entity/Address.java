package com.home.hibernate.entity;

import javax.persistence.Embeddable;

/**
 * @author preetham
 */
@Embeddable
public class Address
{

   private String street;
   private String city;
   private String state;
   private String pincode;

   public Address()
   {

   }

   public Address( String street, String city, String state, String pincode )
   {
      this.street = street;
      this.state = state;
      this.city = city;
      this.pincode = pincode;
   }

   public String getStreet()
   {
      return street;
   }

   public void setStreet( String street )
   {
      this.street = street;
   }

   public String getCity()
   {
      return city;
   }

   public void setCity( String city )
   {
      this.city = city;
   }

   public String getState()
   {
      return state;
   }

   public void setState( String state )
   {
      this.state = state;
   }

   public String getPincode()
   {
      return pincode;
   }

   public void setPincode( String pincode )
   {
      this.pincode = pincode;
   }

   @Override
   public String toString()
   {
      return "Address [street=" + street + ", city=" + city + ", state=" + state + ", pincode=" + pincode + "]";
   }

}

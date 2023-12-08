package com.home.hibernate.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author preetham
 */
@Entity
@DiscriminatorValue( "CAR" )
public class FourWheeler extends Vehicle
{
   private String steeringWheel;

   public String getSteeringWheel()
   {
      return steeringWheel;
   }

   public void setSteeringWheel( String steeringWheel )
   {
      this.steeringWheel = steeringWheel;
   }

}

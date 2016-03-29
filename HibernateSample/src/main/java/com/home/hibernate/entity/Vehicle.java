package com.home.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author preetham
 */
@Entity
@Table( name = "VEHICLE" )
// @Inheritance(strategy=InheritanceType.SINGLE_TABLE) //testSingleTableStrategy test case
// @DiscriminatorColumn(name="VEHICLE_TYPE", discriminatorType=DiscriminatorType.STRING) //testSingleTableStrategy test case
// @Inheritance(strategy=InheritanceType.TABLE_PER_CLASS) //testTablePerClassStrategy test case
// @Inheritance(strategy=InheritanceType.JOINED) //testTableJoinedStrategy test case
public class Vehicle
{
   @Id
   @GeneratedValue( strategy = GenerationType.AUTO )
   @Column( name = "VEHICLE_ID" )
   private int    id;

   @Column( name = "VEHICLE_NAME" )
   private String name;

   public Vehicle()
   {

   }

   public Vehicle( String name )
   {
      this.name = name;
   }

   public int getId()
   {
      return id;
   }

   public void setId( int id )
   {
      this.id = id;
   }

   public String getName()
   {
      return name;
   }

   public void setName( String name )
   {
      this.name = name;
   }

   @Override
   public String toString()
   {
      return "Vehicle [id=" + id + ", name=" + name + "]";
   }

}

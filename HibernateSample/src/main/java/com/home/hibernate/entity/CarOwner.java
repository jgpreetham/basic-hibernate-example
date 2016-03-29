package com.home.hibernate.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

/**
 * @author preetham
 */
@Entity
public class CarOwner
{
   @Id
   @GeneratedValue( strategy = GenerationType.AUTO )
   private int             id;
   private String          name;
   @Transient
   private String          details;
   @ManyToMany( cascade = CascadeType.ALL, mappedBy = "owners" )
   private Collection<Car> cars = new ArrayList<>();

   public CarOwner()
   {
      // TODO Auto-generated constructor stub
   }

   public CarOwner( String name )
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

   public Collection<Car> getCars()
   {
      return cars;
   }

   public void setCars( Collection<Car> cars )
   {
      this.cars = cars;
   }

   public String getDetails()
   {
      return details;
   }

   public void setDetails( String details )
   {
      this.details = details;
   }

}

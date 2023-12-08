package com.home.hibernate.entity;

import javax.persistence.Embeddable;

/**
 * @author preetham
 */
@Embeddable
public class Insurance
{
   private String name;
   private String level;

   public Insurance()
   {

   }

   public Insurance( String name, String level )
   {
      this.name = name;
      this.level = level;
   }

   public String getName()
   {
      return name;
   }

   public void setName( String name )
   {
      this.name = name;
   }

   public String getLevel()
   {
      return level;
   }

   public void setLevel( String level )
   {
      this.level = level;
   }

   @Override
   public String toString()
   {
      return "Company [name=" + name + ", level=" + level + "]";
   }

}

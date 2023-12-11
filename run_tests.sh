#!/bin/bash
set -ex

# shellcheck disable=SC2013
for test in $(
  grep "public void test" src/test/java/com/home/hibernate/test/HibernateTest.java  \
  | awk '{print substr($3, 1, length($3) - 3)}' \
  ); do
    mvn test -Dtest="HibernateTest#${test}"
done
